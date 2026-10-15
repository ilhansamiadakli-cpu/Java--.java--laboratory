# ☕ KTÜ Of Teknoloji Fakültesi — Nesne Yönelimli Programlama
## 📚 Hafta 04: Kapsülleme Onarımı, Değişmez Sınıflar ve Paket Düzeni

Bu çalışma alanı, **Karadeniz Teknik Üniversitesi Of Teknoloji Fakültesi Yazılım Mühendisliği Bölümü — Nesne Yönelimli Programlama** dersi 4. hafta laboratuvar oturumunda geliştirilen tüm görevleri, nesne yönelimli tasarım kararlarını ve kontrol sorularının yanıtlarını içerir.

---

## 📌 İçindekiler
- [🎯 Oturumun Genel Amacı](#-oturumun-genel-amacı)
- [📂 Dizin ve Paket Yapısı](#-dizin-ve-paket-yapısı)
- [🛠️ Görev Dağılımı ve Tasarım Prensipleri](#️-görev-dağılımı-ve-tasarım-prensipleri)
  - [Görev 0: Ortam ve Ön Koşul Doğrulaması](#görev-0-ortam-ve-ön-koşul-doğrulaması-puansız--zorunlu)
  - [Görev 1: Kapsülleme İhlalini Onarma](#görev-1-kapsülleme-ihlalini-onarma-20-puan)
  - [Görev 2: İki Alan Arasında Tutarlılık Kuralı](#görev-2-iki-alan-arasında-tutarlılık-kuralı-20-puan)
  - [Görev 3: Salt-Okunur Kimlik ve Otomatik Numara](#görev-3-salt-okunur-kimlik-ve-otomatik-numara-20-puan)
  - [Görev 4: Değişmez (Immutable) Sınıf Tasarımı](#görev-4-değişmez-immutable-sınıf-tasarımı-20-puan)
  - [Görev 5: Paket Düzeni ve Paket-Özel Erişim (Bonus)](#görev-5-paket-düzeni-ve-paket-özel-erişim-20-puan-bonus)
- [🚀 Derleme ve Çalıştırma Rehberi](#-derleme-ve-çalıştırma-rehberi)
- [🧠 Kontrol Soruları ve Gerekçeli Yanıtları](#-kontrol-soruları-ve-gerekçeli-yanıtları)
- [📋 Puanlama ve Teslim Kontrol Listesi](#-puanlama-ve-teslim-kontrol-listesi)

---

## 🎯 Oturumun Genel Amacı

Bu laboratuvar oturumunun ana odağı, bir nesnenin verilerini dış dünyanın hatalı müdahalelerinden korumak (**kapsülleme / encapsulation**), nesneyi her zaman geçerli bir durumda tutmak (**durum koruma / invariant**) ve erişim sınırlarını doğru araçlarla yönetmektir.

Oturum sonunda kazanılan temel yetkinlikler:
1. `public` alanlı bozuk bir sınıfı `private` alan + denetimli kapı düzenine dönüştürebilme.
2. Geçersiz bir veri geldiğinde nesnenin eski geçerli durumunu koruyan koruma koşullu (*guard clause*) setter yazabilme.
3. *"Dışarısı bu alanı doğrudan atamak zorunda mı, yoksa bu bir iş olayı mı?"* ayrımını yaparak setter yerine iş metotları (`stokGir`, `puanHarca`) tasarlayabilme.
4. Ayrı ayrı doğrulanamayan, iki alanın birlikte sağlaması gereken tutarlılık kurallarını tek bir atomik metotta (`ayarla`) çözebilme.
5. Bayat veri (*stale data*) riskini önlemek için türetilebilir bilgilerde alan tutmayıp hesaplayan getter metotları kullanabilme.
6. `private final` alanlar ve `static` sayaç kullanarak dışarıdan değiştirilemeyen salt-okunur kimlikler üretebilme.
7. Değişmez (*immutable*) bir sınıfı 4 altın kuralına göre yazarak güvenli paylaşım ve zincirleme çağrı (*method chaining*) sağlayabilme.
8. Paket bildirimi (`package`) yazıp paket-özel (*default / package-private*) erişimi bilinçli bir tasarım aracı olarak kullanabilme.

---

## 📂 Dizin ve Paket Yapısı

Föy kuralları gereği tüm kaynak dosyalar `src` altında tutulurken, derlenen `.class` dosyaları `bin` klasörüne yönlendirilir:

```text
Hafta_04/
├── bin/                                <-- Derlenmiş .class dosyaları (gitignore'da tutulur)
├── src/                                <-- Kaynak Java kodları
│   ├── Gorev0_Kontrol.java             <-- Görev 0: Sayac ve kontrol sürücüsü
│   ├── Urun.java                       <-- Görev 1: Onarılmış kapsüllü ürün sınıfı
│   ├── Gorev1_UrunDemo.java            <-- Görev 1: Sürücü program
│   ├── KanBasinci.java                 <-- Görev 2: Çift alan tutarlılık sınıfı
│   ├── Gorev2_KanBasinciDemo.java      <-- Görev 2: Sürücü program
│   ├── Uye.java                        <-- Görev 3: Otomatik kimlikli ve puanlı üye sınıfı
│   ├── Gorev3_UyeDemo.java             <-- Görev 3: Sürücü program
│   ├── Koordinat.java                  <-- Görev 4: Değişmez (Immutable) 2D nokta sınıfı
│   ├── Gorev4_KoordinatDemo.java       <-- Görev 4: Sürücü program
│   ├── Gorev5_KutuphaneDemo.java       <-- Görev 5: Kütüphane paketi dışındaki demo sınıfı
│   └── kutuphane/                      <-- Görev 5: Paket klasörü (package kutuphane;)
│       ├── Kitap.java                  <-- Paket-özel boolean oduncte alanına sahip sınıf
│       └── Katalog.java                <-- Kitap[] dizisini ve ödünç işlemlerini yöneten sınıf
└── README.md                           <-- Çalışma ve dokümantasyon rehberi
```

---

## 🛠️ Görev Dağılımı ve Tasarım Prensipleri

### Görev 0: Ortam ve Ön Koşul Doğrulaması (Puansız — Zorunlu)
- **Dosya:** `src/Gorev0_Kontrol.java`
- **Tasarım:** `Sayac` sınıfının `etiket` alanı `private final String` yapılarak yapıcıda sabitlendi. `deger` alanı `private int` tutuldu; dışarıdan keyfi değer atanmasını engellemek için `setDeger()` metodu yazılmadı, yalnızca durumu birer birer artıran `artir()` metodu eklendi.
- **Beklenen Çıktı:**
```text
Java surumu : 25.0.2
Calisma dizini hazir.
Sayac[lab04=3]
Deger yalnizca artir() ile degisti: 3
ORTAM HAZIR.
```

---

### Görev 1: Kapsülleme İhlalini Onarma (20 Puan)
- **Dosyalar:** `src/Urun.java`, `src/Gorev1_UrunDemo.java`
- **Problem:** İlk verilen bozuk kodda `u.fiyat = -10.0;`, `u.ad = "";`, `u.stok = -500;` ve `u.barkod = "0";` atamaları yapılabiliyordu.
- **Onarım Adımları:**
  - `barkod`: `private final String` yapıldı, yalnızca getter yazıldı.
  - `ad`: `private String` yapıldı; `setAd` içine boş veya yalnızca boşluk denetimi kondu.
  - `fiyat`: `private double` yapıldı; `setFiyat` içine negatiflik denetimi kondu.
  - `stok`: `private int` yapıldı; `setStok()` metodu yazılmadı, stok değişimi `stokGir(int adet)` ve `stokCik(int adet)` iş metotlarına devredildi.
  - `getStokDegeri()`: Alan tutulmayıp `return this.fiyat * this.stok;` ile dinamik hesaplandı.
  - Geçersiz isteklerde `RED:` ile başlayan uyarı mesajı verilip `return;` yapıldı.
- **Beklenen Çıktı:**
```text
Baslangic : Urun[8690001, Defter A5, 42.50 TL, stok=30]

--- Gecersiz istekler ---
  RED: fiyat negatif olamaz (-10.00).
  RED: urun adi bos olamaz.
  RED: stok yetersiz (500 > 30).
Degismedi : Urun[8690001, Defter A5, 42.50 TL, stok=30]

--- Gecerli islemler ---
Son durum : Urun[8690001, Defter A5, 45.00 TL, stok=23]
Stok degeri: 1035.00 TL
```

---

### Görev 2: İki Alan Arasında Tutarlılık Kuralı (20 Puan)
- **Dosyalar:** `src/KanBasinci.java`, `src/Gorev2_KanBasinciDemo.java`
- **Problem:** Tansiyon değerlerinde fizyolojik kural gereği her zaman `buyuk > kucuk` ve değerler `40..250` aralığında olmalıdır. Ayrı setter yazılması durumunda nesne `120/130` gibi geçersiz ara durumlara düşer.
- **Tasarım:**
  - `setBuyuk()` ve `setKucuk()` metotları yazılmadı.
  - Yerine iki değeri birlikte alan `public boolean ayarla(int yeniBuyuk, int yeniKucuk)` metodu yazıldı.
  - `private boolean araliktaMi(int deger)` yardımcı metodu ile `EN_DUSUK` (40) ve `EN_YUKSEK` (250) sabitleri denetlendi.
  - Kural ihlalinde `RED:` uyarısı verildi, alanlar korunarak `false` dönüldü.
  - Türetilmiş `getNabizBasinci()` ve `degerlendir()` metotları yazıldı.
- **Beklenen Çıktı:**
```text
Olcum 1 : 118/76 mmHg (NORMAL)
Nabiz basinci: 42

--- Tutarsiz istekler ---
  RED: buyuk tansiyon kucukten buyuk olmalidir (90 <= 130).
  RED: degerler 40..250 araliginda olmalidir.
  RED: buyuk tansiyon kucukten buyuk olmalidir (120 <= 120).
Degismedi: 118/76 mmHg (NORMAL)

--- Gecerli olcumler ---
Olcum 2 : 135/85 mmHg (EVRE-1)
Olcum 3 : 152/96 mmHg (EVRE-2)
Nabiz basinci: 56
```

---

### Görev 3: Salt-Okunur Kimlik ve Otomatik Numara (20 Puan)
- **Dosyalar:** `src/Uye.java`, `src/Gorev3_UyeDemo.java`
- **Tasarım:**
  - Sınıf düzeyinde paylaşılan sayaç: `private static int sonrakiSira = 1;`
  - Dışarıdan değiştirilemeyen kimlik: `private final String uyeNo;`
  - Yapıcıda otomatik biçimlendirme: `String.format("U-%s-%04d", katilimYili, sira)`
  - Puan kontrolü: Keyfi atamayı önleyen `puanEkle(int miktar)` ve bakiye aşımını engelleyen `puanHarca(int miktar)`.
  - Türetilmiş unvan: `getSeviye()` metodu puana göre dinamik olarak `STANDART`, `GUMUS`, `ALTIN`, `PLATIN` döndürür.
- **Beklenen Çıktı:**
```text
  RED: uye adi bos olamaz.
a : Uye[U-2026-0001, Mert Aydin, puan=0, STANDART]
b : Uye[U-2026-0002, Zeynep Sari, puan=0, STANDART]
c : Uye[U-2026-0003, ISIMSIZ, puan=0, STANDART]
Numaralar sinif tarafindan uretildi, disaridan alinmadi.

--- Puan islemleri ---
a : Uye[U-2026-0001, Mert Aydin, puan=120, GUMUS]
a : Uye[U-2026-0001, Mert Aydin, puan=520, ALTIN]
  RED: yetersiz puan (700 > 520).
a : Uye[U-2026-0001, Mert Aydin, puan=500, ALTIN]

--- Salt-okunur alan denemesi ---
a.getUyeNo() = U-2026-0001 (yalnizca okunabilir)
c : Uye[U-2026-0003, Deniz Ak, puan=0, STANDART]
```

---

### Görev 4: Değişmez (Immutable) Sınıf Tasarımı (20 Puan)
- **Dosyalar:** `src/Koordinat.java`, `src/Gorev4_KoordinatDemo.java`
- **Uygulanan 4 Kural:**
  1. `public final class Koordinat`: Sınıf genişletilemez (`extends` engellendi).
  2. `private final double x, y`: Alanlar salt-okunurdur ve yapıcıda sabitlenir.
  3. Hiçbir setter yazılmadı.
  4. Dönüşüm metotları (`otele`, `olcekle`) mevcut nesneyi değiştirmez, `return new Koordinat(...)` ile daima **yeni bir nesne** döndürür.
- **Kazanım:**
  - `new Koordinat(1,1).otele(1,2).olcekle(2).otele(-1,0)` şeklinde zincirleme metot çağrısı (*method chaining*).
  - `public static final Koordinat BASLANGIC = new Koordinat(0,0)` sabitinin durumu hiçbir metot tarafından bozulamaz (güvenli paylaşım).
- **Beklenen Çıktı:**
```text
p                 : (3.00, 4.00)
baslangica uzaklik: 5.00

--- Donusumler YENI nesne uretir ---
q = p.otele(2,-1) : (5.00, 3.00)
r = q.olcekle(3)  : (15.00, 9.00)
p (hala)          : (3.00, 4.00)  <- DEGISMEDI
q (hala)          : (5.00, 3.00)  <- DEGISMEDI

--- Zincirleme kullanim ---
z                 : (3.00, 6.00)
uzaklik(p, z)     : 2.0000

--- Paylasim guvenli ---
  metot icinde uretilen: (100.00, 100.00)
BASLANGIC (hala)  : (0.00, 0.00)
```

---

### Görev 5: Paket Düzeni ve Paket-Özel Erişim (20 Puan Bonus)
- **Dosyalar:** `src/kutuphane/Kitap.java`, `src/kutuphane/Katalog.java`, `src/Gorev5_KutuphaneDemo.java`
- **Tasarım:**
  - `Kitap` sınıfındaki `boolean oduncte;` alanı erişim belirteci olmadan (**paket-özel / package-private**) tanımlandı.
  - Aynı `kutuphane` paketindeki `Katalog` sınıfı `k.oduncte = true;` satırıyla alana doğrudan erişebilir.
  - Paket dışındaki `Gorev5_KutuphaneDemo` sınıfı bu alana doğrudan erişemez (`a.oduncte` derleme hatası verir); durumu yalnızca `public boolean isOduncte()` getter'ı ile okuyabilir.
- **Beklenen Çıktı:**
```text
=== Baslangic katalogu ===
  978-0 - Java: How to Program (Deitel) [RAFTA]
  978-1 - Core Java Vol. I (Horstmann) [RAFTA]
  978-2 - Effective Java (Bloch) [RAFTA]

=== Odunc islemleri ===
  OK : Core Java Vol. I odunc verildi.
  RED: Core Java Vol. I zaten oduncte.
  RED: 978-9 katalogda yok.
  RED: Effective Java zaten rafta.

=== Son durum ===
  978-0 - Java: How to Program (Deitel) [RAFTA]
  978-1 - Core Java Vol. I (Horstmann) [ODUNCTE]
  978-2 - Effective Java (Bloch) [RAFTA]
Raftaki kitap sayisi: 2

=== Paket disindan erisim sinirlari ===
a.isOduncte() = false  (public getter)
a.oduncte alanina dogrudan yazma yolu KAPALI.
```

---

## 🚀 Derleme ve Çalıştırma Rehberi

### Terminal ile Derleme ve Çalıştırma

Projenin `Hafta_04` dizininde terminali açıp aşağıdaki adımları uygulayabilirsiniz:

```bash
# 1. Kaynak dosyaları derleyip bin klasörüne çıkarma
javac -d bin src/*.java src/kutuphane/*.java

# 2. Görevleri tek tek çalıştırma
java -cp bin Gorev0_Kontrol
java -cp bin Gorev1_UrunDemo
java -cp bin Gorev2_KanBasinciDemo
java -cp bin Gorev3_UyeDemo
java -cp bin Gorev4_KoordinatDemo
java -cp bin Gorev5_KutuphaneDemo
```

### VS Code Code Runner ile Çalıştırma
`.vscode/settings.json` içerisindeki `code-runner.executorMap` ayarınız:
```json
"java": "cd $dir && mkdir -p ../bin && javac -d ../bin $(find . -name \"*.java\") && java -cp ../bin $fileNameWithoutExt"
```
olduğunda, herhangi bir demo sınıfı açıp üstteki **Run Code** tuşuna basarak alt paketler dahil otomatik derleyip çalıştırabilirsiniz.

---

## 🧠 Kontrol Soruları ve Gerekçeli Yanıtları

### Görev 0 Kontrol Soruları
1. **`deger` alanı için neden `setDeger()` yazılmadı?**  
   *Yanıt:* Sayaç değeri dışarıdan rastgele bir sayıya atanabilecek bir özellik değil, yalnızca sayma olayı (`artir()`) gerçekleştiğinde birer birer artması gereken denetimli bir durumdur.
2. **`etiket` alanını `final` yapmak neyi engeller?**  
   *Yanıt:* Yapıcıda ilk değerini aldıktan sonra nesnenin ömrü boyunca sınıf içi veya dışından tekrar değiştirilmesini engeller.

### Görev 1 Kontrol Soruları
1. **`barkod` alanını `final` yapmak yeterli miydi, `private` da gerekli miydi?**  
   *Yanıt:* İkisi farklı koruma sağlar: `final` değerin sonradan değiştirilememesini garantiler; `private` ise dış kodun sınıfın iç veri temsiline doğrudan bağımlı olmasını engelleyip kapsülleme sağlar.
2. **`getStokDegeri()` yerine `private double stokDegeri;` alanı tutsaydınız hangi sorun ortaya çıkardı?**  
   *Yanıt:* Veri tutarsızlığı (*stale data*) ortaya çıkardı: Fiyat veya stok güncellendiğinde bu alanın da güncellenmesi unutulursa (örn. `u.setFiyat(45.00)` sonrası stok değeri eski fiyat üzerinden hesaplanmış kalarak) nesne tutarsız duruma düşer.
3. **`stokCik(500)` çağrısından sonra `stok` alanının değeri neden değişmedi?**  
   *Yanıt:* Metot içindeki `if (adet > this.stok)` koruma koşulu yetersiz stoku yakalayıp ekrana `RED:` uyarısı basmış ve `return;` ile atama satırını çalıştırmadan çıkmıştır.

### Görev 2 Kontrol Soruları
1. **Neden `setBuyuk()` ve `setKucuk()` yerine tek bir `ayarla()` metodu yazıldı?**  
   *Yanıt:* `buyuk > kucuk` kuralı iki alana birden bağlıdır. Ayrı setter'lar olsaydı, `120/80` tansiyonunu `150/130` yapmak isteyen kod önce `setKucuk(130)` çağırdığında nesne geçici olarak `120/130` gibi **geçersiz bir ara duruma** düşerdi.
2. **`ayarla()` metodunun `boolean` döndürmesi çağıran koda ne kazandırır?**  
   *Yanıt:* Çağıran kodun atamanın başarılı olup olmadığını programatik olarak (`if (olcum.ayarla(...))`) test edebilmesini sağlar.
3. **`araliktaMi()` yardımcı metodunu neden `private` yaptınız?**  
   *Yanıt:* Dış dünyayı ilgilendirmeyen, yalnızca sınıfın kendi iç doğrulama mantığında kullandığı bir uygulama detayı olduğu için gizlenmiştir.

### Görev 3 Kontrol Soruları
1. **`uyeNo` alanını yalnızca `private` yapsaydınız (yani `final` olmasaydı) hangi risk doğardı?**  
   *Yanıt:* Sınıfın kendi içindeki başka bir metotta (örneğin `setAd` içinde) yanlışlıkla `this.uyeNo = "...";` yazılması durumunda üyenin kimliği bozulabilirdi; `final` bunu derleme aşamasında engeller.
2. **`puan` alanı için setter yazmamak, `puanEkle()`/`puanHarca()` yazmak hangi iş kuralını korumayı mümkün kılıyor?**  
   *Yanıt:* Puanın keyfi biçimde doğrudan atanmasını engelleyip, yalnızca pozitif işlemlerle değişmesini ve asla mevcut bakiyenin altına düşmemesini (eksiye inmemesini) sağlar.
3. **`getSeviye()` metodunun karşılığı olan bir alan tutulsaydı ne olurdu?**  
   *Yanıt:* Puan her değiştiğinde seviyenin de senkronize güncellenmesi gerekirdi; unutulduğunda puanı 520 olan üyenin unvanı hâlâ `"STANDART"` kalarak veri tutarsızlığı üretirdi.

### Görev 4 Kontrol Soruları
1. **Sınıfı `final` yapmasaydık, bir alt sınıf değişmezliği nasıl delebilirdi?**  
   *Yanıt:* `class MutableKoordinat extends Koordinat` şeklinde bir alt sınıf yazılıp `getX()` ve `getY()` metotları ezilerek zamanla değişen dinamik değerler döndürülebilirdi.
2. **`otele()` metodu `void` olsaydı ve `this.x += dx;` yazsaydık hangi kural ihlal edilirdi? `BASLANGIC` sabiti ne olurdu?**  
   *Yanıt:* Nesne durumunun oluşturulduktan sonra asla değişmemesi kuralı ihlal edilirdi. Ortak paylaşılan `Koordinat.BASLANGIC` sabiti `(0.00, 0.00)` noktasından kayarak tüm programın geometri hesaplamalarını bozardı.
3. **Değişmez nesnenin “paylaşımı güvenlidir” ifadesi ne anlama gelir?**  
   *Yanıt:* Değişmez nesnelerin kopyasını almaya gerek kalmadan referans olarak herkese dağıtılabilmesi anlamına gelir; çünkü hiçbir metot veya iş parçacığı (*thread*) onun durumunu bozamaz.

### Görev 5 Kontrol Soruları
1. **`oduncte` alanını `private` yapsaydık `Katalog` sınıfı nasıl çalışırdı?**  
   *Yanıt:* `Kitap` sınıfına paket-özel bir `void setOduncte(boolean)` metodu yazılması gerekirdi. Kapsülleme açısından daha katı ve iyi olurdu; ancak yakın işbirliği yapan iki sınıf arasında fazladan aracı metot yükü getirirdi.
2. **`oduncte` alanını `public` yapsaydık hangi iş kuralı delinirdi?**  
   *Yanıt:* Paket dışındaki demo programından `a.oduncte = true;` yazılarak katalog denetimi, stok ve mevcudiyet kontrolü atlatılmış olurdu.
3. **Paket-özel erişim ile `protected` arasındaki fark bu görevde nerede kendini gösterirdi?**  
   *Yanıt:* Alan `protected` olsaydı, `kutuphane` paketi dışında tanımlanacak bir `class OzelKitap extends Kitap` alt sınıfı paket sınırını aşarak alana erişebilirdi; paket-özel erişim ise paket dışındaki alt sınıflara dahi kapalıdır.

---

## 📋 Puanlama ve Teslim Kontrol Listesi

| Ölçüt | Durum |
| :--- | :---: |
| Tüm alanlar `private` yapıldı (sabitler `public static final`, `oduncte` paket-özel) | ✅ |
| Her setter içinde doğrulama koşulu ve geçersizlikte `RED:` uyarısı mevcut | ✅ |
| Geçersiz isteklerde nesnenin eski geçerli durumu korundu | ✅ |
| Salt-okunur alanlar `final` yapıldı ve setter yazılmadı | ✅ |
| `Koordinat` sınıfı `final` yapıldı; dönüşümler yeni nesne üretiyor | ✅ |
| `stokDegeri`, `nabizBasinci`, `seviye` için alan tutulmayıp dinamik metot yazıldı | ✅ |
| Tanımlayıcılarda hiçbir Türkçe karakter kullanılmadı | ✅ |
| Tüm sınıflarda `toString()` metodu ezildi | ✅ |
| Arşiv formatı: `Hafta04_<Numara>_<AdSoyad>.zip` | ✅ |
