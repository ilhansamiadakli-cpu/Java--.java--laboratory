# NYP Hafta 03 — Vize Çalışma ve Laboratuvar Özeti
**Konu:** Nesne Yöneliminin Dört Temel İlkesi (Kapsülleme, Soyutlama, Miras, Çok Biçimlilik)[cite: 63]

---

## 1. Neden 4 İlkeye İhtiyaç Duyarız? ("Tip Kodu + switch" Sorunu)
Nesnenin türünü bir `String tur` alanında tutup (`"daire"`, `"kare"`) metotların içinde `switch(tur)` yazmak **Kırılgan Tasarım (Fragile Design)** üretir ve 3 büyük kusuru vardır[cite: 69, 71]:
1. **Sessiz Yanlış Sonuç:** `switch` içinde unutulan yeni bir tür (örn. `"ucgen"`) derleme hatası vermez, `default` kolundan sessizce `0.0` döndürür[cite: 69, 70, 71].
2. **Ölü Alanlar:** Dikdörtgen için gereken `b` (ikinci kenar) alanı, daire ve kare nesnelerinde bellekte boşuna yer kaplar ve anlamsız veri taşıma riski yaratır[cite: 69, 70, 71].
3. **Çok Noktalı Değişiklik:** Yeni bir şekil eklendiğinde `alan()`, `cevre()`, `tanit()` içindeki tüm `switch` bloklarını tek tek bulup değiştirmek gerekir[cite: 70, 71].

> **Çözüm:** Tür Hiyerarşisi ve Çok Biçimlilik. Her tür kendi verisini ve formülünü kendi sınıfında taşır[cite: 71, 72].

---

## 2. Dört Temel İlke (Çekirdek Özeti)

| İlke | Temel Soru | Java Aracı | Vize İçin Altın Kural |
| :--- | :--- | :--- | :--- |
| **1. Kapsülleme** *(Encapsulation)* | Veriye kim, hangi kuralla dokunabilir?[cite: 67] | `private`, `final`, iş metotları (`olc`, `cek`)[cite: 49, 67, 74, 79] | Sadece `private` + `getter/setter` yazmak kapsülleme değildir[cite: 92]. En güçlü kapsülleme (Düzey 3), `setter` yazmayıp veriyi iş metoduyla değiştirmektir[cite: 80]. |
| **2. Soyutlama** *(Abstraction)* | Kullanana NE söylenir, NASIL gizlenir?[cite: 67] | `abstract class`, `interface`[cite: 67] | Sözleşmeyi (imzayı) öne çıkarır, gövdeyi alt sınıfa bırakır[cite: 73, 74]. Soyut sınıftan `new` ile nesne üretilemez[cite: 93]. |
| **3. Miras** *(Inheritance)* | Ortak olan nerede bir kez yazılır?[cite: 67] | `extends`, `super(...)`[cite: 67, 75] | **IS-A (Bir ...-dir)** ilişkisidir[cite: 88]. Alt sınıf yapıcısının **ilk satırı** `super(...)` olmak zorundadır[cite: 51, 94]. |
| **4. Çok Biçimlilik** *(Polymorphism)* | Aynı çağrı nasıl farklı davranır?[cite: 67] | `@Override` + Dinamik Bağlama[cite: 67, 75, 86] | Üst tür referansı (`Sekil s`), alt tür nesnesini (`new Daire()`) tutar[cite: 85, 87]. Hangi gövdenin çalışacağına çalışma zamanında **JVM** karar verir[cite: 86, 87]. |

---

## 3. Vizede Kesin Çıkacak Kritik Ayrımlar

### A. Bildirim Türü (Statik) vs. Gerçek Tür (Dinamik)

    Kisi ref = new Ogrenci("Emre", "98765432109", 2005, 376512, 3.42);

* **Bildirim Türü (Statik Tür - `Kisi`):** Sol taraftır[cite: 87]. **Derleyici** bakar[cite: 87]. Hangi metotların **çağrılabileceğini** belirler (`ref.getOrtalama()` derlenmez, çünkü `Kisi`'de yok)[cite: 54, 87].
* **Gerçek Tür (Dinamik Tür - `Ogrenci`):** Sağ taraftır (`new` ile üretilen)[cite: 87]. **JVM** çalışma zamanında bakar[cite: 86, 87]. Ezilmiş (`@Override`) metotlardan **hangi gövdenin çalışacağını** belirler (`ref.tanit()` çağrıldığında `Ogrenci.tanit()` çalışır)[cite: 51, 53, 87].

### B. Güvenli Aşağı Dönüşüm (`instanceof` ve Downcasting)
Üst tür referansıyla alt sınıfa özgü bir metodu (`onurListesinde()`) çağırmak için önce `instanceof` ile yoklama yapılır, sonra dönüştürülür[cite: 87]:

    if (k instanceof Ogrenci) {
        Ogrenci o = (Ogrenci) k; // Güvenli dönüşüm
        o.onurListesinde();
    }

> **Uyarı:** `instanceof` kontrolü yapmadan doğrudan `(Ogrenci) k` yazılırsa ve `k` bellekte bir `Personel` ise çalışma zamanında **`ClassCastException`** patlar[cite: 54, 88].

### C. Soyut Sınıf (`abstract class`) vs. Arayüz (`interface`)
* **`extends` (Soyut Sınıf - IS-A):** "Bu nedir?" sorusuna yanıt verir (`Ogrenci extends Kisi`)[cite: 83, 84, 88]. Ortak **veri (alan)** ve ortak gövdeli metot paylaşımı varsa seçilir[cite: 88, 95]. Bir sınıf **yalnızca tek bir** sınıftan miras alabilir (Elmas Problemi nedeniyle)[cite: 88, 89].
* **`implements` (Arayüz - CAN-DO):** "Bu ne yapabilir?" sorusuna yanıt verir (`KrediKarti implements Odeme`)[cite: 82, 83, 84, 88]. Ortak ata yoktur, ortak **yetenek/sözleşme** vardır[cite: 88]. Bir sınıf **birden çok** arayüzü gerçekleyebilir[cite: 83, 88].

---

## 4. Laboratuvar Görevleri ve Mimari Açıklamaları

* **Görev 1 (İlke Tespiti):**
  * **Bölge A (Kapsülleme):** `private int can;` alanına doğrudan erişilememesi, sadece `hasarAl()` içindeki kuralla değişmesi (`Math.max(0, can - miktar)` ile sıfırın altına düşmeme koruması)[cite: 46, 47].
  * **Bölge B (Soyutlama):** Sınıf adını bilmeden `Savasabilir` arayüzü (sözleşmesi) üzerinden `saldiriGucu()` çağrılması[cite: 47].
  * **Bölge C (Miras):** `Savasci` sınıfının `hasarAl()` metodunu hiç yazmadan `extends Karakter` sayesinde hazır kullanması[cite: 47, 48].
  * **Bölge D (Çok Biçimlilik):** `Karakter[]` dizisi içinde tek bir `for` döngüsüyle `tanit()` çağrıldığında her nesnenin kendi ezilmiş (`@Override`) metnini basması[cite: 46, 47, 48].
* **Görev 2 (`Sensor` - Kapsülleme Onarımı):**
  * Alanlar `private`, kimlik alanları (`kod`, `birim`) `final` yapıldı[cite: 49].
  * `setDeger()` **yazılmadı**; çünkü sensör değeri dışarıdan keyfi ayarlanmaz, ölçülür[cite: 50].
  * Kural `main` içine değil, doğrudan `olc(double)` metodunun içine gömüldü (Düzey 3 Kapsülleme)[cite: 49, 50, 80].
* **Görev 3 (`Kisi` ve `Ogrenci` - Miras):**
  * `Ogrenci extends Kisi` ile `getAd()`, `maskeliTc()`, `yas()` metotları miras alındı, `Ogrenci` içinde tekrar yazılmadı[cite: 51, 52].
  * `Ogrenci` yapıcısının ilk satırında `super(ad, tcNo, dogumYili);` ile üst sınıf başlatıldı[cite: 51, 52].
* **Görev 4 (`Personel` ve `Kisi[]` - Çok Biçimlilik):**
  * `listele(Kisi[])` metodunda hiçbir `if`, `switch` veya alt sınıf adı (`Ogrenci`, `Personel`) geçmeden tek döngüde dinamik bağlama çalıştırıldı[cite: 53, 54].
  * Onur listesindeki öğrenciler `instanceof Ogrenci` sınamasıyla güvenli şekilde sayıldı[cite: 53, 87].

---

## 5. Laboratuvar Kontrol Soruları ve Cevapları (Dosya Sonlarına Eklenecekler)

### Görev 0 — Kontrol Soruları
* **S1: `java -cp bin Sekil` komutunu çalıştırırsanız ne olur? Neden?**[cite: 45]
  * **Cevap:** Çalışmaz ve `main` metodu bulunamadı hatası verir[cite: 44, 45]. Ayrıca `Sekil` soyut (`abstract`) bir sınıftır, doğrudan nesnesi üretilemez ve tek başına çalıştırılmak için değil, alt sınıflara ortak bir şablon oluşturmak için tasarlanmıştır[cite: 44, 73, 74].
* **S2: `SekilDemo.java` dosyası `Daire`, `Kare` ve `Ucgen` sınıflarını `import` etmiyor. Buna rağmen neden derleniyor?**[cite: 45]
  * **Cevap:** Tüm sınıflar aynı klasörde (varsayılan paket / default package içinde) yer almaktadır[cite: 44]. Java'da aynı paketteki sınıflar birbirini doğrudan görür, `import` bildirimine ihtiyaç duymazlar.

### Görev 1 — Kontrol Soruları (`Gorev1_IlkeTespiti.java` sonuna)
* **S1: B bölgesindeki `Savasabilir s = new Savasci(...)` bildiriminde, `s` referansıyla `hasarAl()` metodunu çağırabilir misiniz? Neden?**[cite: 47]
  * **Cevap:** Hayır, çağrılamaz (derleme hatası verir). Çünkü derleyici referansın bildirim türüne (statik türüne), yani `Savasabilir` arayüzüne bakar[cite: 87]. `hasarAl()` metodu `Savasabilir` sözleşmesinde değil, `Karakter` sınıfında tanımlıdır[cite: 46].
* **S2: C bölgesinde `Savasci` sınıfı `hasarAl()` metodunu hiç yazmamış. Bu metot nereden geliyor ve `can` alanına nasıl erişebiliyor?**[cite: 48]
  * **Cevap:** `extends Karakter` ifadesiyle üst sınıf olan `Karakter` sınıfından miras (inheritance) yoluyla gelmektedir. `can` alanı `Karakter` içinde `private` olsa bile, `hasarAl()` metodu zaten `Karakter` sınıfının kendi üyesi olduğu için kendi `private` alanına doğrudan erişebilir[cite: 46].
* **S3: D bölgesindeki dizinin türü `Karakter[]`. Diziye bir `Buyucu` koyabilmemizin nedeni nedir?**[cite: 48]
  * **Cevap:** `Buyucu` sınıfı `Karakter` sınıfından türemiştir (`Buyucu extends Karakter`), yani aralarında **IS-A ("Büyücü bir Karakterdir")** ilişkisi vardır[cite: 88]. Üst tür referansı, kendisinden türeyen tüm alt tür nesnelerini tutabilir[cite: 85].

### Görev 2 — Kontrol Soruları (`Sensor.java` sonuna)
* **S1: `kod` alanını `private` yapmak yetmez, ayrıca `final` de yaptık. `final` olmasaydı hangi kapı açık kalırdı?**[cite: 50]
  * **Cevap:** `private` sadece sınıf dışından doğrudan erişimi engeller. `final` olmasaydı, sınıfın kendi içindeki bir metotta yanlışlıkla `this.kod = ...` yazılarak veya sonradan bir setter eklenerek sensörün değişmez kimliği çalışma zamanında değiştirilebilirdi. `final` bu alanı doğumdan sonra tamamen salt-okunur yapar[cite: 49, 74].
* **S2: `olc()` metodunun `boolean` döndürmesi, `void` döndürmesine göre çağıran tarafa ne kazandırır?**[cite: 50]
  * **Cevap:** Çağıran kod, ölçümün sensör tarafından kabul edilip edilmediğini (`true`/`false`) anında öğrenir ve program akışını buna göre yönlendirebilir[cite: 49, 50]. `void` olsaydı çağıran taraf işlemin başarılı olup olmadığını bilemezdi.
* **S3: `EN_DUSUK` ve `EN_YUKSEK` sabitlerini `public` yapmak kapsüllemeyi ihlal eder mi? Gerekçelendirin.**[cite: 50]
  * **Cevap:** İhlal etmez. Çünkü bu alanlar `final` olduğu için dışarıdan değiştirilemezler (salt-okunurdurlar) ve nesnenin iç durumunu bozma riski taşımazlar[cite: 78]. Aksine, sensörün geçerli ölçüm sınırlarını dış dünyaya bildiren yararlı birer sözleşme bilgisidirler.

### Görev 3 — Kontrol Soruları (`Kisi.java` / `Ogrenci.java` sonuna)
* **S1: `Ogrenci` sınıfı `Kisi` sınıfındaki `private String ad;` alanına doğrudan erişebilir mi? `getAd()` metoduna erişebilir mi? İkisi arasındaki farkı açıklayın.**[cite: 52]
  * **Cevap:** `ad` alanına doğrudan erişemez çünkü `private` üyeler yalnızca tanımlandıkları sınıfın (`Kisi`) içinden görünürler[cite: 84]. Ancak `Ogrenci` bu alanı bellekte devralır ve `Kisi`'nin sunduğu `public getAd()` kapısı üzerinden dolaylı ve güvenli olarak okuyabilir[cite: 51, 84].
* **S2: `super(ad, tcNo, dogumYili);` çağrısını yapıcının ikinci satırına yazarsanız ne olur?**[cite: 52]
  * **Cevap:** `Constructor call must be the first statement in a constructor` derleme hatası alınır[cite: 19, 51]. Alt sınıf kendi alanlarını başlatmadan önce üst sınıfın (temelin) eksiksiz başlatılması zorunludur; bu yüzden `super(...)` daima ilk satırda yer almalıdır[cite: 51, 75, 94].
* **S3: `k instanceof Ogrenci` ifadesi neden `false`, `o instanceof Kisi` ifadesi neden `true` döner?**[cite: 52]
  * **Cevap:** Miras tek yönlü bir **IS-A** ilişkisidir[cite: 52, 88]. Her öğrenci bir kişidir (`o instanceof Kisi -> true`), ancak her kişi bir öğrenci olmak zorunda değildir (`k` doğrudan bir `Kisi` nesnesidir, `Ogrenci` değildir; bu yüzden `false` döner)[cite: 52, 88].

### Görev 4 — Kontrol Soruları (`Gorev4_CokBicimlilikDemo.java` sonuna)
* **S1: `Kisi ref = kayitlar[1];` bildiriminden sonra `ref.getOrtalama()` çağrısı neden derlenmez? Nesne gerçekte bir `Ogrenci` olmasına rağmen.**[cite: 54]
  * **Cevap:** Derleyici nesnenin bellekteki gerçek türüne (`Ogrenci`) değil, referansın bildirim türüne (statik türüne, yani `Kisi`) bakar[cite: 87]. `Kisi` sınıfında `getOrtalama()` adında bir metot tanımlı olmadığı için derleyici bu çağrıya izin vermez[cite: 51, 54, 87].
* **S2: `listele()` metoduna beşinci bir tür (örneğin `Mezun extends Kisi`) eklenirse bu metotta kaç satır değişir? Neden?**[cite: 54]
  * **Cevap:** **0 satır değişir** (hiçbir değişiklik gerekmez)[cite: 86]. Çünkü `listele()` metodu alt sınıfların adlarına değil, `Kisi` üst sınıfına ve dinamik bağlamaya (`k.tanit()`) dayanmaktadır[cite: 53, 86]. Yeni eklenen `Mezun` sınıfı `tanit()` metodunu ezdiği sürece döngü onu otomatik olarak doğru çalıştırır[cite: 86].
* **S3: `getClass().getSimpleName()` çağrısı derleme zamanında mı çalışma zamanında mı sonuç üretir? Yanıtınızı çıktıyla gerekçelendirin.**[cite: 54]
  * **Cevap:** **Çalışma zamanında (runtime)** sonuç üretir[cite: 86, 87]. Çıktıda referansın bildirim türü `Kisi` olmasına rağmen `ref.getClass().getSimpleName()` çağrısının `"Ogrenci"` döndürmesi, JVM'nin çalışma anında bellekteki gerçek nesneye baktığının kanıtıdır[cite: 53, 86, 87].