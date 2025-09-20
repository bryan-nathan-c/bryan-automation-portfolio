# bryan-automation-portfolio
***Proyek ini dibuat sebagai portofolio untuk menampilkan kerangka kerja pengujian otomatis yang mencakup dua bagian utama, yaitu pengujian web dan API. Di dalamnya terdapat skenario nyata seperti menambahkan produk ke keranjang belanja di website dan melakukan operasi dasar pada data pengguna melalui API. Semua pengujian disusun dengan rapi agar mudah dipahami, terstruktur, dan bisa dijalankan baik secara lokal maupun melalui alur kerja otomatis. Tujuan dari proyek ini adalah memberikan contoh bagaimana proses pengujian dapat dilakukan secara menyeluruh, konsisten, dan menghasilkan laporan yang jelas untuk membantu memastikan kualitas sistem.***

## 🚀 Tools & Libraries

- Java 17
  
- Gradle 8
- Cucumber – BDD dengan Gherkin
- Selenium WebDriver – Web UI automation
- Rest-Assured – API testing
- WebDriverManager – Driver management
- JUnit 5 – Test runner
- GitHub Actions – CI/CD pipeline

# Cara Menjalankan Projek Ini
## 1. Clone repository
```git clone https://github.com/bryan-nathan-c/bryan-automation-portfolio.git```
```cd bryan-automation-portfolio```

## 2. Jalankan Web UI tests
```./gradlew runWebTests```

## 3. Jalankan API tests
```./gradlew runApiTests```

## 4. Atau jalankan semua sekaligus
```./gradlew test```

## 5. Menampilkan laporan
HTML: ```build/reports/cucumber/html/index.html```

JSON: ```build/reports/cucumber/json/cucumber.json```
