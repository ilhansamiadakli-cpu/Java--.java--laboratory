# Nesne Yönelimli Programlama - Hafta 07: Miras (Inheritance) Vize Hazırlık Rehberi

Bu belge, KTÜ Of Teknoloji Fakültesi Yazılım Mühendisliği Bölümü **Nesne Yönelimli Programlama (Hafta 07)** ders notları ve laboratuvar kodları (`Kisi`, `Hesap`, `BilesimDemo`, `MirasHatalariDemo` vb.) temel alınarak hazırlanmıştır. 

Sınavda çıkması en muhtemel soru tiplerini, hocanın beklediği **"tam puanlık"** yanıt kalıplarını ve derleyicinin yakalayamadığı tuzakları içerir.

---

## Bölüm 1: Klasik Kavram ve Tasarım Soruları

### Soru 1: "Private alanlar miras alınmadığı için alt sınıf nesnesinin belleğinde bulunmazlar." Bu iddia doğru mudur? `Ogrenci` ve `Kisi` sınıfları üzerinden kanıtlayarak açıklayınız.

* **Kısa Yanıt:** **Yanlıştır.** `private` alanlar alt sınıf tarafından adıyla doğrudan **görülmezler** (erişilemezler), ancak alt sınıf nesnesinin belleğinde (Heap) **var olurlar** ve değerlerini taşırlar.
* **Kanıt (`Kisi.java` & `Ogrenci.java`):** 
  * `Kisi` sınıfında `private final String tcNo;` alanı tanımlanmıştır.
  * `Ogrenci` sınıfı `Kisi`'den türediğinde doğrudan `this.tcNo` yazamaz (derleme hatası alır).
  * Ancak `Ogrenci` nesnesi üzerinden `o.maskeliTcNo()` veya `o.getAd()` çağrıldığında doğru sonuç döner. Eğer `tcNo` ve `ad` alanları `Ogrenci` nesnesinin belleğinde yer almasaydı, bu metotlar okuyacak veri bulamazdı.
* **Bellek Yerleşimi:** Bir `Yonetici` nesnesi oluşturulduğunda bellekte 3 ayrı nesne oluşmaz; tek bir nesne bloğu içinde `Kisi` + `Personel` + `Yonetici` alanları katmanlı olarak üst üste yerleşir.

---

### Soru 2: Bir alt sınıf üst sınıftan neleri devralır, neleri devralmaz? Yapıcılar (constructors) neden miras alınmaz?

1. **Devralınanlar:**
   * `public` ve `protected` alanlar/metotlar (doğrudan erişilir).
   * Paket düzeyi (default) üyeler (yalnızca alt sınıf aynı paketteyse).
   * `static` üyeler (ancak alt sınıfta yeni kopyası oluşmaz, tek kopya olarak paylaşılır).
   * `final` metotlar (devralınır ve çağrılır, ancak **ezilemez**).
2. **Devralınmayanlar / Görünmeyenler:**
   * **Yapıcılar (Constructors):** Miras alınmazlar, `super(...)` ile çağrılırlar.
   * **`private` üyeler:** Bellekte vardır ama alt sınıfa görünmezler (getter/setter ile ulaşılır).
3. **Yapıcıların Miras Alınmama Gerekçesi:** Yapıcının görevi o sınıfa ait nesneyi geçerli bir durumda başlatmaktır. `Kisi(ad, tcNo, dogumYili)` yapıcısı, bir `Ogrenci` nesnesinin `ogrenciNo` ve `bolum` alanlarını nasıl başlatacağını bilemez. Bu yüzden yapıcılar devralınmaz, zincirleme olarak çağrılır.

---

### Soru 3: `super(...)` çağrısı neden yapıcının İLK satırı (deyimi) olmak zorundadır? Bu kural olmasaydı ne olurdu?

* **Gerekçe (Nesne Başlatma Sırası):** Bir alt sınıf nesnesi inşa edilirken önce üst sınıfın (temelin) sağlam kurulması gerekir. Üst sınıfın alanları başlatılmadan alt sınıfın kodunun çalışması tehlikelidir.
* **Kural Olmasaydı ("Yarı Kurulmuş Nesne" Sorunu):** Alt sınıf, `super(...)` çağrısından önce üst sınıftan miras aldığı bir metodu (`yas()`, `maskeliTcNo()` vb.) çağırabilir veya üst sınıfın `protected` bir alanını kullanmaya çalışabilirdi. Üst sınıf henüz başlatılmadığı için bu alanlar `null` veya `0` olurdu ve program **yarı kurulmuş bir nesne** üzerinde çalışarak hatalı sonuç veya `NullPointerException` üretirdi.

---

### Soru 4: Kalıtım (`extends` / IS-A) ile Bileşim (`alan` / HAS-A) arasındaki fark nedir? Karar verirken hangi iki sınama yapılır?

> ** Sınav İpucu:** Hoca bu soruda tek kelimelik ("extends" veya "bileşim") cevapları kabul etmez. Aşağıdaki **2 aşamalı karar akışını** yazmanız beklenir.

1. **Birinci Sınama (Cümle Sınaması):** *"X bir Y'dir"* (IS-A) cümlesi her bağlamda anlamlı mı? Yoksa *"X'in bir Y'si vardır"* (HAS-A) cümlesi mi anlamlı?
2. **İkinci Sınama (Sözleşme Sınaması):** IS-A cümlesi doğru olsa bile, alt sınıf üst sınıfın **tüm davranış sözleşmelerini bozmadan** sürdürebiliyor mu?

| Senaryo | Cümle Sınaması | İlişki | Doğru Tasarım | Gerekçe |
| :--- | :--- | :--- | :--- | :--- |
| `VadeliHesap` - `Hesap` | Vadeli hesap bir hesaptır. | **IS-A** | `extends Hesap` | Hesap sözleşmesini bozmaz, yalnızca vade kısıtı ekler. |
| `Araba` - `Motor` | Araba bir motordur (Anlamsız!). Arabanın motoru vardır. | **HAS-A** | `private final Motor motor;` | `Araba extends Motor` yazılırsa motorun iç servis metotları (`yakitPompasiTest()`) arabaya sızar ve araba 2 motorlu olamaz. |
| `Katalog` - `Yayin` | Katalog bir yayındır (Anlamsız!). Kataloğun yayınları vardır. | **HAS-A** | `private Yayin[] raf;` | Katalog yayınları barındıran bir kapsayıcıdır. |
| `Kare` - `Dikdortgen` | Kare bir dikdörtgendir (Geometride doğru, kodda tehlikeli!). | **Sorunlu IS-A** | Ayrı soyutlama / Bileşim | `Dikdortgen` içindeki `genislikAyarla(w)` metodu yüksekliği bağımsız bırakır; `Kare` ise bu sözleşmeyi bozmadan sürdüremez. |

---

### Soru 5: Java çoklu sınıf mirasını (`class X extends A, B`) neden yasaklamıştır? Çoklu arayüz (`implements I1, I2`) kullanımında bu sorun neden yaşanmaz?

* **Elmas Problemi (Diamond Problem):** `Yazici` ve `Tarayici` sınıfları `Cihaz` sınıfından türeyip `yaz()` metodunu farklı biçimde ezsin. `CokFonksiyonlu` sınıfı ikisinden birden türeseydi:
  1. **Davranış Belirsizliği:** `yaz()` çağrıldığında `Yazici`'nın mı yoksa `Tarayici`'nın mı metodu çalışacak?
  2. **Durum (Alan) Belirsizliği:** `Cihaz`'dan gelen bir alan bellekte tek kopya mı olacak, iki kopya mı?
* **Arayüzlerin (`interface`) Çözümü:** Arayüzler **durum (örnek alanı / instance field) taşımazlar**, bu yüzden bellek çakışması asla doğmaz.
* **Java 8 `default` Metot Çakışması (`ObjectKokuDemo.java`):** İki arayüzde aynı imzalı `default String etiket()` metodu varsa, derleyici bunu çalışma zamanına bırakmaz; **derleme hatası** verir ve programcıyı metodu ezip `Yazdirilabilir.super.etiket()` şeklinde açıkça seçim yapmaya zorlar.

---

### Soru 6: `super` ve `final` anahtar sözcüklerinin 3 ayrı kullanımını açıklayınız.

#### `super` Sözcüğünün 3 Kullanımı:
1. **`super(...)`:** Üst sınıfın yapıcısını çağırır. Yapıcının ilk satırı olmak zorundadır.
2. **`super.metot()`:** Alt sınıfta ezilen (override edilen) bir metodun üst sınıftaki orijinal sürümünü çağırır. Ortak işi yeniden yazmadan üzerine ekleme yapmayı sağlar (`super.kimlikKarti()`, `super.cek(miktar)`).
3. **`super.alan`:** Üst sınıftaki alan alt sınıfta gölgelenmişse (aynı adla tekrar tanımlanmışsa) üstteki alana ulaşmak için kullanılır. *(Alan gölgeleme bir tasarım hatası olduğu için bu kullanım son çaredir).*

#### `final` Sözcüğünün 3 Kullanımı:
1. **`final` Alan (Field):** Değeri yalnızca bir kez (bildirimde veya yapıcıda) atanabilir, bir daha değiştirilemez (`private final String hesapNo;`).
2. **`final` Metot:** Alt sınıflar tarafından **ezilemez (override edilemez)**. Kurumsal sözleşmeyi kilitlemek ve yapıcıdan güvenle çağırmak için kullanılır (`public final String hesapNo()`).
3. **`final` Sınıf:** Hiçbir sınıf bu sınıftan `extends` ile **türeyemez** (`String`, `Integer` gibi değişmezlik güvencesi veren sınıflar).

---

## Bölüm 2: Kod Okuma ve Çıktı Tahmin Soruları (Garanti Soru Tipi)

### Soru 7: Yapıcı Zinciri ve Başlatma Sırası (`YapiciZinciriDemo.java`)

Aşağıdaki kod çalıştırıldığında ekrana hangi sırayla ne yazar?

```java
class A {
    int a = yaz("1. A alan", 10);
    static { System.out.println("0a. A static"); }
    { System.out.println("2. A blok"); }
    A() { System.out.println("3. A yapici"); }
    static int yaz(String m, int d) { System.out.println(m); return d; }
}
class B extends A {
    int b = yaz("4. B alan", 20);
    static { System.out.println("0b. B static"); }
    { System.out.println("5. B blok"); }
    B() { System.out.println("6. B yapici"); }
}
public class Test {
    public static void main(String[] args) {
        System.out.println("--- Birinci Nesne ---");
        B b1 = new B();
        System.out.println("--- Ikinci Nesne ---");
        B b2 = new B();
    }
}
```

#### Çıktı ve Kuralları:
```text
--- Birinci Nesne ---
0a. A static
0b. B static
1. A alan
2. A blok
3. A yapici
4. B alan
5. B blok
6. B yapici
--- Ikinci Nesne ---
1. A alan
2. A blok
3. A yapici
4. B alan
5. B blok
6. B yapici
```
* **Altın Kural 1:** `static` bloklar sınıf belleğe yüklenirken **en başta, yukarıdan aşağıya (`A -> B`) ve yalnızca BİR KEZ** çalışır. İkinci nesnede (`b2`) bir daha çalışmazlar!
* **Altın Kural 2:** Nesne oluşturulurken çağrı yukarı çıkar, yürütme en üstten aşağıya iner. Her sınıfta sıra: **Alan Başlatıcı -> Başlatma Bloğu -> Yapıcı Gövdesi** şeklindedir.

---

## Bölüm 3: Derleyicinin Yakalamadığı 3 Büyük Hata (`MirasHatalariDemo.java`)

> **Çok Önemli:** Bu 3 durumun hiçbiri derleme hatası vermez! Sınavda *"Bu kodda ne hata vardır ve çıktısı nedir?"* şeklinde sorulur.

### Tuzak 1: Alan Gölgeleme (Field Hiding)

```java
class A { 
    int d = 1; 
    int oku() { return d; } 
}
class B extends A { 
    int d = 2; // Üstteki d silinmedi, gölgelendi!
    @Override
    int oku() { return d; } 
}
```
`A ref = new B();` satırından sonra:
* **`ref.d` kaçtır?** -> **`1`**
* **`ref.oku()` kaçtır?** -> **`2`**
* **`((B) ref).d` kaçtır?** -> **`2`**
* **Sınav Açıklaması:** Tek nesne içinde hem `A.d` hem de `B.d` olmak üzere iki ayrı alan oluşur. **Alan erişimleri derleme zamanında referansın STATİK türüne (`A`) göre çözülür.** Metot çağrıları ise çalışma zamanında nesnenin **GERÇEK (dinamik) türüne (`B`)** göre çözülür.

---

### Tuzak 2: Ezme (Override) Yerine Yanlışlıkla Aşırı Yükleme (Overload)

```java
class Ust { 
    void yaz(Object o) { System.out.println("UST"); } 
}
class Alt extends Ust { 
    // Parametre Object yerine String yazıldı!
    void yaz(String s) { System.out.println("ALT"); } 
}
```
`Ust u = new Alt();` ve `u.yaz("merhaba");` çağrıldığında ekrana ne yazar?
* **Çıktı:** **`UST`**
* **Sınav Açıklaması:** `Alt` sınıfındaki metot `Object` yerine `String` parametresi aldığı için üstteki metodu **ezmemiştir (override etmemiştir)**; farklı imzalı yeni bir metot ekleyerek **aşırı yüklemiştir (overload)**. `u` referansının statik türü `Ust` olduğu ve `Ust` sınıfında yalnızca `yaz(Object)` bulunduğu için `UST` çalışır.
* **Çözüm:** Alt sınıftaki metoda `@Override` etiketi eklenmelidir. Eklendiğinde derleyici *"must override or implement a supertype method"* hatası vererek hatayı anında yakalar.

---

### Tuzak 3: Üst Sınıf Yapıcısından Ezilebilir (Overridable) Metot Çağırmak

```java
class UstRapor {
    UstRapor() {
        System.out.println(baslik()); // TEHLİKE!
    }
    String baslik() { return "genel"; }
}
class AltRapor extends UstRapor {
    private String konu = "donem sonu";
    AltRapor() {
        super();
        System.out.println(baslik());
    }
    @Override
    String baslik() { return "rapor: " + konu; }
}
```
`new AltRapor();` çağrıldığında ekrana ne yazar?
* **Çıktı:**
  ```text
  rapor: null
  rapor: donem sonu
  ```
* **Sınav Açıklaması:** `new AltRapor()` çağrıldığında önce `UstRapor()` yapıcısı çalışır. Bu sırada `AltRapor`'un `konu = "donem sonu"` alan ataması **henüz çalışmamıştır** (`konu` alanı `null` durumdadır). `UstRapor` yapıcısı `baslik()` metodunu çağırdığında, dinamik bağlama nedeniyle `AltRapor`'un ezilmiş `baslik()` metodu çalışır ve henüz atanmamış olan `konu` alanını `null` olarak ekrana basar.
* **Çözüm:** Bir yapıcının içinden yalnızca `private`, `static` veya `final` metotlar çağrılmalıdır (çünkü bunlar ezilemez).

---

## Bölüm 4: Uzatma Noktası (Extension Point) ve Sözleşme Koruma (`Hesap.java` & `VadeliHesap.java`)

### Soru 8: Bir metodu ezerken `super.metot()` çağrısını unutmanın bedeli nedir?
* `VadeliHesap` sınıfında `cek(double miktar)` metodu ezilirken, vade kontrolü yapıldıktan sonra `return super.cek(miktar);` çağrılmıştır.
* Eğer öğrenci `super.cek(miktar)` yazmayı unutup doğrudan `bakiye -= miktar; return true;` yazarsa, üst sınıftaki (`Hesap.java`):
  1. **Negatif/sıfır miktar kontrolü** (`miktar <= 0`) devre dışı kalır.
  2. **Yetersiz bakiye kontrolü** (`miktar > bakiye`) devre dışı kalır; hesaptan bakiyeden fazla para çekilerek bakiye eksiye düşer.

### Soru 9: Uzatma Noktası (Extension Point) Deseni Nedir?
* `Hesap` sınıfında `public double aylikKesinti()` metodu `return bakiye * kesintiOrani();` şeklinde yazılmış; `protected double kesintiOrani()` ise `0.0` döndürmüştür.
* `VadeliHesap` sınıfı `aylikKesinti()` metoduna hiç dokunmamış, yalnızca `protected` olan `kesintiOrani()` metodunu ezerek `0.005` döndürmüştür.
* **Avantajı:** Hesaplama formülü tek yerde (`Hesap` içinde) güvende kalır; alt sınıflar tüm algoritmayı baştan yazmak yerine yalnızca kendilerine ayrılan en küçük uzatma noktasını (oranı) değiştirir.

---

## Bölüm 5: `java.lang.Object` Sınıfı

* Açıkça `extends` yazmayan her sınıf örtük olarak `Object` sınıfından türer (`class Bos` -> `class Bos extends Object`).
* **`toString()`:** Ezilmezse `SinifAdi@onaltilikHashCode` (ör. `Bos@7a81197d`) döndürür. Hata ayıklamayı kolaylaştırmak için her sınıfta ezilmelidir.
* **`equals(Object)`:** Ezilmezse varsayılan olarak **`==` (referans/adres eşitliği)** kontrolü yapar. İçeriği birebir aynı olan iki ayrı nesne için `false` döner.

---

## Sınav Öncesi Hızlı Derleyici Hataları Sözlüğü

| Derleyici Hatası | Sebebi | Çözümü |
| :--- | :--- | :--- |
| `Implicit super constructor X() is undefined` | Üst sınıfta parametresiz yapıcı yok ve alt sınıf yapıcısında `super(...)` yazılmadı. | Alt sınıf yapıcısının ilk satırına uygun parametreli `super(...)` ekle. |
| `Constructor call must be the first statement in a constructor` | `super(...)` veya `this(...)` ilk satıra yazılmadı. | Çağrıyı yapıcının ilk satırına taşı. |
| `The method f() of type Alt must override or implement a supertype method` | `@Override` yazıldı ama metot adı veya parametre türleri üst sınıfla uyuşmuyor. | İmzayı üst sınıftaki metotla birebir aynı yap. |
| `Cannot override the final method from Ust` | Üst sınıfta `final` olarak işaretlenmiş bir metot alt sınıfta ezilmeye çalışıldı. | Metodu ezmekten vazgeç. |
| `The type Alt cannot subclass the final class Ust` | `final class` olarak tanımlanmış bir sınıftan `extends` ile türetme yapılmaya çalışıldı. | Kalıtım yerine bileşim (alan olarak tutma) kullan. |