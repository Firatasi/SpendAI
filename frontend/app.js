const API_URL = "http://localhost:8080/api/auth"; 
const messageElement = document.getElementById('message');

function toggleAuth() {
    const loginSec = document.getElementById('loginSection');
    const registerSec = document.getElementById('registerSection');
    loginSec.style.display = loginSec.style.display === 'none' ? 'block' : 'none';
    registerSec.style.display = registerSec.style.display === 'none' ? 'block' : 'none';
    messageElement.innerText = "";
}

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
            if (endpoint === 'register') {
                messageElement.style.color = "green";
                messageElement.innerText = "Kayıt Başarılı! Şimdi giriş yapabilirsiniz.";
                setTimeout(toggleAuth, 1500); 
                return;
            }

            const token = data.access_token || data.token;
            const role = data.role; 

            if (token) {
                localStorage.setItem('token', token);
                localStorage.setItem('role', role);

                messageElement.style.color = "green";
                messageElement.innerText = "Giriş Başarılı! Yönlendiriliyorsunuz...";

                setTimeout(() => {
                    if (role === 'ROLE_ADMIN' || role === 'ADMIN') {
                        window.location.href = 'admin.html';
                    } else if (role === 'ROLE_USER' || role === 'USER') {
                        window.location.href = 'anasayfa.html'; 
                    } else {
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