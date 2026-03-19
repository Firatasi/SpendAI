// Backend'deki @RequestMapping ile birebir aynı olmalı!
const API_URL = "http://localhost:8080/api/auth"; 
const messageElement = document.getElementById('message');

// Panel Değiştirme (Giriş/Kayıt arası geçiş)
function toggleAuth() {
    const loginSec = document.getElementById('loginSection');
    const registerSec = document.getElementById('registerSection');
    loginSec.style.display = loginSec.style.display === 'none' ? 'block' : 'none';
    registerSec.style.display = registerSec.style.display === 'none' ? 'block' : 'none';
    messageElement.innerText = "";
}

// Ortak İstek Gönderme Fonksiyonu
async function sendRequest(endpoint, payload) {
    try {
        console.log(`İstek atılıyor: ${API_URL}/${endpoint}`);
        
        const response = await fetch(`${API_URL}/${endpoint}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(payload)
        });

        let data = {};
        try {
            data = await response.json(); 
        } catch (e) {
            console.log("JSON parse edilemedi.");
        }

        if (response.ok) {
            // Kayıt işlemi mi yoksa giriş işlemi mi olduğunu anlıyoruz
            if (endpoint === 'register') {
                messageElement.style.color = "green";
                messageElement.innerText = "Kayıt Başarılı! Şimdi giriş yapabilirsiniz.";
                setTimeout(toggleAuth, 1500); // 1.5 sn sonra giriş ekranına atar
                return;
            }

            // GİRİŞ İŞLEMİ (Login)
            const token = data.access_token || data.token;
            const role = data.role; 

            if (token) {
                localStorage.setItem('token', token);
                localStorage.setItem('role', role);

                messageElement.style.color = "green";
                messageElement.innerText = "Giriş Başarılı! Yönlendiriliyorsunuz...";

                setTimeout(() => {
                    // ROL KONTROLÜ VE YÖNLENDİRME
                    // Backend'den 'ROLE_ADMIN' veya sadece 'ADMIN' gelebilir, ikisini de kontrol ediyoruz
                    if (role === 'ROLE_ADMIN' || role === 'ADMIN') {
                        window.location.href = 'admin.html';
                    } else if (role === 'ROLE_USER' || role === 'USER') {
                        // Kullanıcılar direkt harcama sayfasına (dashboard/anasayfa)
                        window.location.href = 'anasayfa.html'; 
                    } else {
                        // Bilinmeyen rol durumu
                        window.location.href = 'index.html';
                    }
                }, 1000);
            }
        } else {
            messageElement.style.color = "red";
            messageElement.innerText = "Hata: " + (data.message || "İşlem başarısız. Bilgileri kontrol et.");
        }

    } catch (error) {
        console.error("Hata Detayı:", error);
        messageElement.style.color = "red";
        messageElement.innerText = "Sunucu bağlantı hatası!";
    }
}

// Form Dinleyicileri
document.getElementById('registerForm').addEventListener('submit', (e) => {
    e.preventDefault();
    const payload = {
        username: document.getElementById('regUsername').value,
        email: document.getElementById('regEmail').value,
        password: document.getElementById('regPassword').value
    };
    sendRequest('register', payload);
});

document.getElementById('loginForm').addEventListener('submit', (e) => {
    e.preventDefault();
    const payload = {
        username: document.getElementById('logUsername').value,
        password: document.getElementById('logPassword').value
    };
    sendRequest('login', payload); 
});