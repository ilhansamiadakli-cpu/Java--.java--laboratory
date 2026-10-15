&lt;div align=&quot;center&quot;&gt;

# ☕ Java Laboratory

### *Sıfırdan İleriye: Kişisel Java Öğrenme, OOP ve Akademik Çalışma Alanı*

[Java](https://www.google.com/search?q=https%3A%2F%2Fwww.oracle.com%2Fjava%2F)
[OOP](https://www.google.com/search?q=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FObject-oriented_programming)
[Git](https://www.google.com/search?q=https%3A%2F%2Fgit-scm.com%2F)
[VS Code](https://www.google.com/search?q=https%3A%2F%2Fcode.visualstudio.com%2F)
[License: MIT](https://www.google.com/search?q=https%3A%2F%2Fopensource.org%2Flicenses%2FMIT)

&lt;p align=&quot;center&quot;&gt;
  Bu depo; bir yazılım mühendisliği öğrencisi olarak Java programlama dilini öğrenirken geliştirdiğim algoritmaları, bağımsız deneme kodlarımı ve Karadeniz Teknik Üniversitesi (KTÜ) Nesne Yönelimli Programlama dersi kapsamındaki haftalık laboratuvar projelerimi topladığım merkezi çalışma laboratuvarımdır.
&lt;/p&gt;

---

&lt;/div&gt;

## 📌 Deponun Amacı ve Kapsamı

Bu depo iki temel çalışma aksından oluşur:
1. 🧪 **Kişisel Deneme Laboratuvarı (`Denemeler/`):** Yeni öğrenilen dil özelliklerinin, algoritma alıştırmalarının ve terminal tabanlı geliştirici araçlarının (`lazygit`, otomasyon scriptleri) akademik kısıtlama olmadan özgürce deneyimlendiği kum havuzu (*sandbox*).
2. 🎓 **Nesne Yönelimli Programlama Laboratuvarı (`Nesne_Tabanlı_Programlama_dersi/`):** Üniversite müfredatına paralel olarak yürütülen, haftalık föylere dayalı modüler OOP projeleri (kapsülleme onarımları, durum güvenliği, değişmez sınıflar, paket düzeni vb.).

---

## 📂 Dizin Yapısı

```text
Java--.java--laboratory/
├── .vscode/                                    <-- VS Code ve Code Runner çoklu kaynak ayarları
├── .gitignore                                  <-- Derleme (.class, bin/) ve sistem dosyaları filtresi
├── README.md                                   <-- Depo ana tanıtım ve indeks dokümanı
│
├── Denemeler/                                  <-- 🧪 Kişisel Java Kum Havuzu (Sandbox)
│   ├── README.md                               <-- Deneme alanı vizyonu ve çalışma felsefesi
│   ├── lazygit.txt                             <-- Terminalden hızlı Git/Lazygit yönetim kılavuzu
│   ├── bin/                                    <-- Deneme kodlarının derlenmiş .class dosyaları
│   └── SRC/                                    <-- Sürekli güncellenen deneme ve algoritma sınıfları
│
└── Nesne_Tabanlı_Programlama_dersi/            <-- 🎓 KTÜ Yazılım Müh. OOP Dersi Çalışma Alanı
    ├── README.MD                               <-- Ders müfredatı, kurallar ve haftalık ilerleme tablosu
    ├── Res/                                    <-- Orijinal laboratuvar föyleri (PDF arşivi)
    │   ├── Hafta_01_Laboratuvar_Foyu.pdf
    │   ├── Hafta_02_Laboratuvar_Foyu.pdf
    │   ├── Hafta_03_Laboratuvar_Foyu.pdf
    │   └── Hafta_04_Laboratuvar_Foyu.pdf
    ├── Hafta_04/                               <-- 4. Hafta: Kapsülleme, İmmutability & Paketler
    │   ├── README.md                           <-- Görev analizleri, çıktılar ve kontrol soruları
    │   ├── bin/                                <-- Hafta 4 derlenmiş .class dosyaları
    │   └── src/                                <-- Görev 0-5 kaynak kodları ve kutuphane paketi
    └── Hafta_XX/                               <-- Gelecek haftalık oturumlar (Hafta 5, 6, 7...)
```

---

## 🚀 Çalışma Ortamı & Araçlar

- **Programlama Dili:** Java (OpenJDK 21 LTS)
- **Geliştirme Ortamı:** Visual Studio Code + *Extension Pack for Java*
- **Hızlı Çalıştırıcı:** Code Runner Eklentisi *(alt paketleri otomatik derleyecek şekilde yapılandırılmış)*
- **Sürüm Kontrolü:** Git & GitHub
- **Terminal Sürüm Yönetimi:** [Lazygit](https://www.google.com/search?q=https%3A%2F%2Fgithub.com%2Fjesseduffield%2Flazygit) *(Hızlı commit, branch ve push operasyonları için)*

---

## 💻 Nasıl Derlenir ve Çalıştırılır?

Her iki çalışma alanında da kaynak kodlar `src` (veya `SRC`) altında tutulurken, derlenen dosyalar `bin` dizinine yönlendirilir.

### 1. Kişisel Denemeleri Çalıştırma
```bash
# Denemeler dizinine geçin
cd Denemeler/SRC

# İlgili sınıfı derleyin ve çalıştırın
javac -d ../bin DosyaAdi.java
java -cp ../bin DosyaAdi
```

### 2. OOP Laboratuvar Görevlerini Çalıştırma (Örn: Hafta 04)
```bash
# İlgili haftanın dizinine geçin
cd Nesne_Tabanlı_Programlama_dersi/Hafta_04

# Alt paketler dahil tüm kaynak dosyaları bin klasörüne derleyin
javac -d bin src/*.java src/kutuphane/*.java

# Görev sürücü programlarını çalıştırın
java -cp bin Gorev1_UrunDemo
java -cp bin Gorev4_KoordinatDemo
java -cp bin Gorev5_KutuphaneDemo
```

> **İpucu:** VS Code kullanıyorsanız, `code-runner.executorMap` ayarı sayesinde herhangi bir demo sınıfında **Run Code** tuşuna basarak veya `main` metodu üzerindeki yerel **Run** bağlantısını kullanarak tek tıkla çalıştırabilirsiniz.

---

## 📈 Öğrenme Yol Haritası (Roadmap)

- [x] Java sözdizimi temelleri, veri tipleri, döngüler ve derleme (`javac` / `java`) mekanizması
- [x] VS Code, Code Runner ve Lazygit terminal iş akışlarının yapılandırılması
- [x] Nesne ve sınıf temelleri, Stack/Heap bellek ayrımı ve referans tipler
- [x] Kapsülleme onarımı, doğrulamalı setter mantığı ve guard clause kullanımı
- [x] Atomik durum güncellemeleri ve iki alan arası tutarlılık kuralları (*invariants*)
- [x] Değişmez (*Immutable*) sınıf tasarımı ve güvenli paylaşım ilkeleri
- [x] Paket bildirimi (`package`) ve paket-özel (*package-private*) erişim yönetimi
- [ ] Yapıcı zincirleme (*Constructor Chaining*) ve parametre doğrulama (Hafta 5)
- [ ] `static` üyeler, sınıf düzeyinde durum yönetimi ve sabitler (Hafta 6)
- [ ] Kalıtım (*Inheritance*) ve Çok Biçimlilik (*Polymorphism*)
- [ ] Soyut Sınıflar (*Abstract Classes*) ve Arayüzler (*Interfaces*)
- [ ] İstisna Yönetimi (*Exception Handling*) ve Giriş/Çıkış (*I/O*) İşlemleri

---

&lt;div align=&quot;center&quot;&gt;

⭐ *Yazılım geliştirme yolculuğumu ve laboratuvar projelerimi incelediğiniz için teşekkürler!*

&lt;/div&gt;
