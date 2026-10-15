# ☕ KTÜ Of Teknoloji Fakültesi — Nesne Yönelimli Programlama Dersi Laboratuvarı

[Java](https://www.google.com/search?q=https%3A%2F%2Fwww.oracle.com%2Fjava%2F)
[OOP](https://www.google.com/search?q=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FObject-oriented_programming)
[VS Code](https://www.google.com/search?q=https%3A%2F%2Fcode.visualstudio.com%2F)
[License: MIT](https://www.google.com/search?q=https%3A%2F%2Fopensource.org%2Flicenses%2FMIT)

Bu çalışma alanı, **Karadeniz Teknik Üniversitesi Of Teknoloji Fakültesi Yazılım Mühendisliği Bölümü** 2. Sınıf Güz Dönemi **Nesne Yönelimli Programlama** dersinin haftalık laboratuvar oturumlarını, kaynak kodlarını, ders föylerini ve nesneye yönelik yazılım mühendisliği pratiklerini barındırır.

---

## 📌 İçindekiler
- [🎯 Çalışma Alanının Amacı ve Kapsamı](#-çalışma-alanının-amacı-ve-kapsamı)
- [📂 Dizin ve Modüler Proje Mimarisi](#-dizin-ve-modüler-proje-mimarisi)
- [🗓️ Haftalık Müfredat ve İlerleme Tablosu](#️-haftalık-müfredat-ve-ilerleme-tablosu)
- [📐 Laboratuvar Kodlama ve Tasarım Standartları](#-laboratuvar-kodlama-ve-tasarım-standartları)
- [⚙️ Ortam Yapılandırması ve Derleme Kılavuzu](#️-ortam-yapılandırması-ve-derleme-kılavuzu)
- [📦 Laboratuvar Teslim Prosedürü](#-laboratuvar-teslim-prosedürü)

---

## 🎯 Çalışma Alanının Amacı ve Kapsamı

Bu klasörün temel hedefi; teorik derste aktarılan nesne yönelimli programlama ilkelerini (**kapsülleme, kalıtım, polimorfizm, soyutlama**) laboratuvar ortamında temiz kod (*clean code*) standartlarıyla gerçeğe dönüştürmektir.

Bu alanda geliştirilen her proje şu mühendislik hedeflerini gözetir:
* **Veri Güvenliği ve Kapsülleme:** Durumu koruyan sınıflar, doğrulamalı setter mekanizmaları ve değişmez (*immutable*) nesneler tasarlamak.
* **İş Odaklı Tasarım:** Ezbere `getter/setter` yazmak yerine, nesnenin durumunu iş olaylarıyla (*business events*) yönetmek.
* **Tutarlılık (Invariants):** Birden fazla alanın bir arada sağlaması gereken kuralları atomik metotlarla garanti altına almak.
* **Paket Mimarisi:** Kodları paketlere bölerek genel erişim (`public`) ile paket-özel (`package-private`) sınırlarını bilinçli kullanmak.

---

## 📂 Dizin ve Modüler Proje Mimarisi

Ders boyunca her haftanın kodları ve kaynak föyleri birbirine karışmayacak şekilde **haftalık modüler klasörler** hâlinde organize edilir:

```text
Nesne_Tabanlı_Programlama_dersi/
├── README.MD                           <-- Bu doküman (Ders genel tanıtımı ve fihrist)
│
├── Res/                                <-- Laboratuvar dokümanları ve orijinal föyler
│   ├── Hafta_01_Laboratuvar_Foyu.pdf   <-- Ortam kurulumu ve sözdizimi
│   ├── Hafta_02_Laboratuvar_Foyu.pdf   <-- Temel yapılar ve algoritmalar
│   ├── Hafta_03_Laboratuvar_Foyu.pdf   <-- Sınıf/nesne temelleri
│   └── Hafta_04_Laboratuvar_Foyu.pdf   <-- Kapsülleme, değişmez sınıf ve paketler
│
├── Hafta_01/                           <-- 1. Hafta çalışma alanı
│   ├── bin/                            <-- Derlenmiş .class dosyaları
│   └── src/                            <-- Kaynak kodlar
│
├── Hafta_02/                           <-- 2. Hafta çalışma alanı
│   ├── bin/
│   └── src/
│
├── Hafta_03/                           <-- 3. Hafta çalışma alanı
│   ├── bin/
│   └── src/
│
├── Hafta_04/                           <-- 4. Hafta çalışma alanı (Aktif)
│   ├── bin/                            <-- Görev 0-5 derlenmiş dosyaları
│   ├── src/                            <-- Kaynak kodlar
│   │   ├── Gorev0_Kontrol.java         <-- Ortam doğrulama ve Sayac
│   │   ├── Urun.java                   <-- Kapsülleme onarımı
│   │   ├── Gorev1_UrunDemo.java
│   │   ├── KanBasinci.java             <-- Çift alan tutarlılık kuralı
│   │   ├── Gorev2_KanBasinciDemo.java
│   │   ├── Uye.java                    <-- static sayaç ve salt-okunur kimlik
│   │   ├── Gorev3_UyeDemo.java
│   │   ├── Koordinat.java              <-- Değişmez (Immutable) 2D nokta
│   │   ├── Gorev4_KoordinatDemo.java
│   │   ├── Gorev5_KutuphaneDemo.java   <-- Paket dışı demo
│   │   └── kutuphane/                  <-- Paket-özel erişim paketi
│   │       ├── Kitap.java
│   │       └── Katalog.java
│   └── README.md                       <-- Hafta 4 detaylı çözüm ve kontrol soruları rehberi
│
└── Hafta_XX/                           <-- İlerleyen haftalar (Hafta 5, 6, 7...)
```

> **Neden Haftalık Klasör Yapısı?**  
> 1. Farklı haftalarda yer alan benzer isimli sınıflar (örn. birden fazla `Urun` veya `Demo`) birbiriyle çakışmaz.  
> 2. Föyde istenen `javac -d bin src/*.java` kuralına tam uyum sağlanır.  
> 3. Oturum sonunda teslim edilecek `HaftaXX_<Numara>_<AdSoyad>.zip` arşivi yalnızca ilgili haftanın `src` dizini seçilerek saniyeler içinde oluşturulur.

---

## 🗓️ Haftalık Müfredat ve İlerleme Tablosu

| Hafta | Temel Konular ve Hedefler | Föy Referansı | Durum |
| :---: | :--- | :--- | :---: |
| **01** | Java Geliştirme Ortamı (JDK 21, VS Code), Sözdizimi Temelleri, `javac`/`java` Komutları | `Res/Hafta_01_Laboratuvar_Foyu.pdf` | Tamamlandı |
| **02** | Veri Tipleri, Operatörler, Koşul Yapıları, Döngüler ve Konsol Giriş/Çıkış İşlemleri | `Res/Hafta_02_Laboratuvar_Foyu.pdf` | Tamamlandı |
| **03** | Sınıf ve Nesne Kavramı, Bellek Modeli (Stack vs. Heap), Referans Tipler, Temel Metotlar | `Res/Hafta_03_Laboratuvar_Foyu.pdf` | Tamamlandı |
| **04** | Kapsülleme Onarımı, Doğrulamalı Setter, Türetilmiş Bilgi, Salt-Okunur Kimlik, Değişmez (*Immutable*) Sınıf, Paket Düzeni | `Res/Hafta_04_Laboratuvar_Foyu.pdf` | **Tamamlandı** |
| **05** | Yapıcı Metotlar (*Constructors*), Yapıcı Zincirleme (*Constructor Chaining*), `this()` ve Parametre Doğrulama | — | *Sırada* |
| **06** | `static` Değişkenler, `static` Metotlar ve Sınıf Sabitleri (*Constants*) | — | Planlandı |
| **07** | Kalıtım (*Inheritance*), `super` Kullanımı ve Metot Ezme (*Method Overriding*) | — | Planlandı |
| **08** | Polimorfizm (*Çok Biçimlilik*) ve Dinamik Metot Bağlama (*Dynamic Dispatch*) | — | Planlandı |
| **09** | Soyut Sınıflar (*Abstract Classes*) ve Arayüzler (*Interfaces*) | — | Planlandı |
| **10** | İstisna Yönetimi (*Exception Handling*) ve Giriş/Çıkış (*I/O*) İşlemleri | — | Planlandı |

---

## 📐 Laboratuvar Kodlama ve Tasarım Standartları

Laboratuvar föylerinde belirtilen ve değerlendirmede puan kesintisini önleyen **altın kurallar**:

1. **Erişim Kısıtlaması (Encapsulation):**  
   Tüm sınıf alanları varsayılan olarak `private` tanımlanmalıdır. Tek istisna `public static final` sabitler ve bilinçli paket işbirliği gerektiren `package-private` durumlardır.
2. **Doğrulamalı Setter ve Durum Koruma (Guard Clause):**  
   Setter yazıldığında gelen değer mutlaka doğrulanmalıdır (`if (fiyat < 0)`). Geçersiz istek durumunda ekrana `RED:` ile başlayan açıklayıcı bir uyarı yazılmalı, atama yapılmamalı ve nesne **eski geçerli durumunu** korumalıdır.
3. **Setter mı, İş Metodu mu? Ayrımı:**  
   Dışarıdan doğrudan atanmaması gereken durumlar için setter yerine iş metotları (`stokGir()`, `stokCik()`, `puanEkle()`) yazılmalıdır.
4. **Türetilmiş Bilgilerde Alan Tutmama:**  
   Mevcut alanlardan hesaplanabilen bilgiler (`stokDegeri = fiyat * stok`, `nabizBasinci = buyuk - kucuk`) için ayrı değişken tutulmaz; anlık hesaplayan getter yazılır (bayat veri engellenir).
5. **İki Alan Arası Tutarlılık (*Invariant*):**  
   Birbirine bağlı alanlar (`buyuk > kucuk`) ayrı setter'larla güncellenmez; nesneyi geçersiz ara duruma düşürmemek için tek bir atomik metotla (`boolean ayarla(...)`) atanır.
6. **Değişmezlik (*Immutability*):**  
   Sınıf `final`, alanlar `private final` yapılır, setter yazılmaz ve dönüşüm metotları daima `return new Sinif(...)` ile yeni nesne döndürür.
7. **Adlandırma ve Karakter Kuralları:**  
   * Sınıf adları `PascalCase`, değişken/metot adları `camelCase`, sabitler `SCREAMING_SNAKE_CASE`.
   * Tanımlayıcılarda, paketlerde ve dosya isimlerinde **kesinlikle Türkçe karakter (ç, ğ, ı, ö, ş, ü) kullanılmaz.**
8. **`toString()` Metodunun Ezilmesi:**  
   Her sınıfta anlamlı bir `toString()` ezilmeli ve nesneler konsola bu metot üzerinden yazdırılmalıdır.

---

## ⚙️ Ortam Yapılandırması ve Derleme Kılavuzu

### 1. Manuel Terminal Kullanımı
İlgili haftanın klasörüne gidilerek tüm kaynak kodlar `bin` dizinine derlenir ve sınıf yolu (*classpath*) belirtilerek çalıştırılır:

```bash
# 1. İlgili haftanın çalışma dizinine geçin
cd Nesne_Tabanlı_Programlama_dersi/Hafta_04

# 2. Paketler dahil tüm Java dosyalarını bin klasörüne derleyin
javac -d bin src/*.java src/kutuphane/*.java

# 3. İlgili demo sınıfını çalıştırın
java -cp bin Gorev1_UrunDemo
java -cp bin Gorev5_KutuphaneDemo
```

### 2. VS Code ve Code Runner Entegrasyonu
Proje kök dizinindeki `.vscode/settings.json` dosyasında şu ayar yer aldığında, açık olan herhangi bir dosyada Code Runner tuşuna basıldığında o haftanın tüm kodları otomatik derlenir:

```json
{
  "java.project.outputPath": "bin",
  "java.project.sourcePaths": [
    "Denemeler/SRC",
    "Nesne_Tabanlı_Programlama_dersi/src",
    "Nesne_Tabanlı_Programlama_dersi/Hafta_04/src"
  ],
  "code-runner.executorMap": {
    "java": "cd $dir && mkdir -p ../bin && javac -d ../bin $(find . -name \"*.java\") && java -cp ../bin $fileNameWithoutExt"
  },
  "code-runner.runInTerminal": true,
  "code-runner.clearPreviousOutput": true
}
```

---

## 📦 Laboratuvar Teslim Prosedürü

Laboratuvar oturumu sonunda geliştirilen kodların teslimi için izlenecek adımlar:

1. İlgili haftanın `src` klasörünün derlendiğinden ve tüm test çıktılarının föydeki beklenen çıktılarla birebir örtüştüğünden emin olun.
2. Sıkıştırılmış zip dosyasını ders sorumlusunun belirlediği formatta adlandırın:  
   `HaftaXX_<OgrenciNumarasi>_<AdSoyad>.zip` (Örn: `Hafta04_123456_AhmetYilmaz.zip`)
3. Arşiv içeriğinde sadece kaynak dosyaların (`src/...`) yer aldığından emin olun (`bin` veya geçici dosyalar eklenmez).
4. Kodlarınızı Git ile commit edip reponuza push'layın:
   ```bash
   git add Nesne_Tabanlı_Programlama_dersi/
   git commit -m "feat(lab): Hafta XX laboratuvar gorevleri tamamlandi"
   git push origin main
   ```
