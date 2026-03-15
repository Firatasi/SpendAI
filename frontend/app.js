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

        // VERİYİ BURADA BİR KERE OKUYORUZ VE DEĞİŞKENE ATIYORUZ
        let data = {};
        try {
            data = await response.json(); 
        } catch (e) {
            console.log("JSON parse edilemedi, muhtemelen gövde boş.");
        }

        if (response.ok) {
            // Backend'den 'access_token' ve 'role' geldiğini varsayıyoruz
            const token = data.access_token || data.token;
            const role = data.role; 

            if (token) {
                localStorage.setItem('token', token);
                localStorage.setItem('role', role);

                messageElement.style.color = "green";
                messageElement.innerText = "Giriş Başarılı! Yönlendiriliyorsunuz...";

                setTimeout(() => {
                    // Rol kontrolü: ROLE_ADMIN veya ROLE_USER
                    if (role === 'ROLE_ADMIN') {
                        window.location.href = 'admin.html';
                    } else {
                        window.location.href = 'anasayfa.html';
                    }
                }, 1000);
            }
        } else {
            // Hata durumunda zaten datayı yukarıda okumuştuk
            messageElement.style.color = "red";
            messageElement.innerText = "Hata: " + (data.message || "Giriş yapılamadı. Bilgileri kontrol et.");
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
    // Backend'deki metodun @PostMapping("/login") ise burası böyle kalmalı
    sendRequest('login', payload); 
});