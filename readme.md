🚀 SpendAI - Akıllı Harcama Yönetim Sistemi

SpendAI, geleneksel harcama takibi yöntemlerini Yapay Zeka ile birleştiren, kullanıcıların finansal verilerini doğal dille yönetebilmelerini sağlayan modern bir Full-Stack platformdur.

🛠️ Kullanılan Teknolojiler

Backend

Spring Boot 3.x: Uygulama mimarisi ve yüksek performanslı API katmanı.

Spring AI: Google Gemini / OpenAI modelleri ile entegrasyon (Harcama analizi için).

Spring Security & JWT: Stateless (durumsuz) yetkilendirme ve güvenli rol tabanlı erişim yönetimi.

Spring Data JPA: PostgreSQL ile ilişkisel veri yönetimi ve hızlı sorgulama.

Lombok: Boilerplate kod azaltımı ve temiz kod yapısı.

Frontend

HTML5 & CSS3: Modern, responsive (duyarlı) ve kullanıcı dostu arayüz tasarımı.

Vanilla JavaScript: Gerçek zamanlı veri işleme, asenkron API etkileşimi (Fetch API) ve dinamik DOM yönetimi.

🌟 Öne Çıkan Özellikler

1. AI Destekli Akıllı Kayıt (Yeni! 🤖)

Kullanıcılar karmaşık formlar doldurmak yerine, günlük konuşma dilinde harcamalarını yazar.

Örnek Giriş: "Bugün teknoloji mağazasından 2500 TL'ye kulaklık aldım."

AI İşleme: Yapay zeka bu cümleden tutarı (2500), kategoriyi (Teknoloji) ve açıklamayı (Kulaklık) otomatik olarak ayıklar ve JSON formatında veritabanına işler.

2. Gelişmiş Güvenlik ve Yetkilendirme

JWT (JSON Web Token): Güvenli oturum yönetimi ve yetkilendirme.

Rol Tabanlı Erişim: * Admin Paneli: Sistemdeki tüm kullanıcıları ve verileri yönetme yetkisi.

Kullanıcı Paneli: Kişisel harcama geçmişini görüntüleme ve AI asistanını kullanma.

3. Finansal Analiz ve Raporlama

Kullanıcının harcama alışkanlıklarını analiz ederek kategori bazlı harcama raporları sunar.

Harcama geçmişini kronolojik sırada listeleyerek bütçe takibini kolaylaştırır.

⚙️ Kurulum ve Çalıştırma

Ön Gereksinimler

Java 17 veya üzeri (JDK 25 önerilir)

PostgreSQL Veritabanı

Google Gemini API Key (Spring AI için)

Adımlar

Projeyi klonlayın:

git clone [https://github.com/kullaniciadi/SpendAI.git](https://github.com/kullaniciadi/SpendAI.git)


application.properties dosyasındaki veritabanı ve API anahtarı bilgilerini güncelleyin:

spring.ai.google.genai.api-key=YOUR_API_KEY
spring.datasource.url=jdbc:postgresql://localhost:5432/spendai


Projeyi Maven ile derleyin ve çalıştırın:

mvn clean install
mvn spring-boot:run


Tarayıcıdan http://localhost:8080 adresine gidin.

📧 İletişim

Bu proje finansal okuryazarlığı artırmak ve teknoloji ile bütçe yönetimini kolaylaştırmak amacıyla geliştirilmiştir. Her türlü geri bildirim için iletişime geçebilirsiniz!