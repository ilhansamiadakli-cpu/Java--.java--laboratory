# Nesne Yönelimli Programlama - Hafta 07: Kağıda Kod Yazma Sınav Soruları (`Örneksoru.md`)

Bu doküman, vize sınavında **kağıda elle Java kodu yazma** (problem çözme, çıktıdan kod üretme ve tasarım düzeltme) sorularına hazırlanmak için ders föyündeki standartlara birebir uygun olarak hazırlanmıştır.

> **Kağıda Kod Yazarken Puan Kaybettirmeyen 5 Altın Kural:**
> 1. Alt sınıf yapıcısının **ilk satırına** mutlaka `super(...);` yazın.
> 2. Üst sınıftan ezdiğiniz her metodun üstüne mutlaka `@Override` ekleyin.
> 3. Alanları (değişkenleri) aksini gerektirmedikçe `private` (sabitse `private final`) tanımlayın.
> 4. Ortak davranışı ezerken üst sınıfın işini tekrar yazmayın; `super.metotAdi()` ile çağırıp üzerine ekleyin.
> 5. Yapıcı içinde doğrulama (validation) istenmişse `if` kontrolü yapıp `IllegalArgumentException` fırlatın veya uyarı basın.

---

## Soru 1: Çıktıdan Geriye Doğru Sınıf Tasarlama (`Ziyaretci` Sınıfı)

**Problem:**  
Ders kapsamında yazılan `Kisi` sınıfının imzası aşağıda verilmiştir:
* `public Kisi(String ad, String tcNo, int dogumYili)`
* `public boolean resitMi()`
* `public String kimlikKarti()` -> `"Ad | *********TC | Yas yasinda"` döndürür.

Bu hiyerarşiye `Kisi` sınıfından türeyen **`Ziyaretci`** adlı yeni bir alt sınıf ekleyiniz:
1. Sınıfın değiştirilemez (`final`) `ziyaretNedeni` ve `gorusulenBirim` (`String`) alanları olmalıdır.
2. Yapıcısı tüm bilgileri almalı ve üst sınıf yapıcısını uygun şekilde çağırmalıdır.
3. `gecerliMi()` adlı metot, ziyaretçinin reşit olup olmadığını (`resitMi()`) kontrol etmeli ve ziyaret nedeni boş değilse `true` döndürmelidir.
4. `kimlikKarti()` metodunu, aşağıdaki ekran çıktısını birebir üretecek şekilde (`super` kullanarak) eziniz.
5. `main` metodu içinde bir `Ogrenci` ve bir `Ziyaretci` nesnesini **tek bir `Kisi[]` dizisinde** tutup döngüyle kimlik kartlarını ekrana yazdırınız.

**Beklenen Ekran Çıktısı:**
```text
Elif Demir | *********02 | 21 yasinda | OGRENCI YZM376014 (Yazilim Muhendisligi)
Ahmet Yılmaz | *********88 | 30 yasinda | ZIYARETCI [Birim: Dekanlik, Neden: Toplanti]
Ziyaretci girisi gecerli mi? -> true
```

### Çözüm 1 (Kağıda Yazılacak Kod):

```java
public class Ziyaretci extends Kisi {

    private final String ziyaretNedeni;
    private final String gorusulenBirim;

    public Ziyaretci(String ad, String tcNo, int dogumYili,
                     String gorusulenBirim, String ziyaretNedeni) {
        // 1. KURAL: super(...) ilk satir olmak zorundadir!
        super(ad, tcNo, dogumYili);
        this.gorusulenBirim = gorusulenBirim;
        this.ziyaretNedeni  = ziyaretNedeni;
    }

    public String getZiyaretNedeni()  { return ziyaretNedeni; }
    public String getGorusulenBirim() { return gorusulenBirim; }

    public boolean gecerliMi() {
        // Miras alinan resitMi() dogrudan cagriliyor
        return resitMi() && ziyaretNedeni != null && !ziyaretNedeni.isBlank();
    }

    @Override
    public String kimlikKarti() {
        // Ortak kisim yeniden yazilmaz, super ile alinip uzerine eklenir
        return super.kimlikKarti() + " | ZIYARETCI [Birim: " + gorusulenBirim 
               + ", Neden: " + ziyaretNedeni + "]";
    }

    // Test (main) kismi:
    public static void main(String[] args) {
        Ogrenci o = new Ogrenci("Elif Demir", "22345678902", 2005,
                                "YZM376014", "Yazilim Muhendisligi");
        Ziyaretci z = new Ziyaretci("Ahmet Yılmaz", "11122233388", 1996,
                                    "Dekanlik", "Toplanti");

        // IS-A sayesinde farkli alt siniflar tek ust tur dizisinde tutulur
        Kisi[] kisiler = { o, z };

        for (Kisi k : kisiler) {
            System.out.println(k.kimlikKarti());
        }
        System.out.println("Ziyaretci girisi gecerli mi? -> " + z.gecerliMi());
    }
}
```

---

## Soru 2: Uzatma Noktası (Extension Point) ile `KrediliHesap` Tasarımı

**Problem:**  
Derste incelenen `Hesap` sınıfında `protected double bakiye;` alanı ve para çekme metodu şu şekildedir:
```java
public class Hesap {
    protected double bakiye;
    // ... yapici ve diger metotlar ...
    public boolean cek(double miktar) {
        if (miktar <= 0) return false;
        if (miktar > bakiye) {
            System.out.println("RET: yetersiz bakiye");
            return false;
        }
        bakiye -= miktar;
        return true;
    }
}
```
Banka, bakiyenin belirli bir **`krediLimiti`** değerine kadar eksiye düşmesine izin veren **`KrediliHesap`** sınıfını istemektedir.
* **a)** `Hesap` sınıfına dokunmadan `KrediliHesap` sınıfını ve ezilmiş `cek(double)` metodunu yazınız.
* **b)** Kod tekrarını önlemek için `Hesap` sınıfına `protected double cekilebilirUstSinir()` adlı bir **uzatma noktası (extension point)** ekleyerek `Hesap.cek()` metodunu düzenleyiniz ve `KrediliHesap`'ı yalnızca bu uzatma noktasını ezecek şekilde yeniden yazınız.

### Çözüm 2 (Kağıda Yazılacak Kod):

#### a) `Hesap` Sınıfına Dokunmadan Çözüm:
*(Not: `Hesap.cek()` doğrudan `miktar > bakiye` kontrolü yaptığı için `super.cek(miktar)` çağrılamaz; kurallar alt sınıfta tekrar yazılmak zorunda kalır).*

```java
public class KrediliHesap extends Hesap {

    private final double krediLimiti;

    public KrediliHesap(String sahip, double acilisBakiyesi, double krediLimiti) {
        super(sahip, acilisBakiyesi);
        if (krediLimiti < 0) {
            throw new IllegalArgumentException("Kredi limiti negatif olamaz.");
        }
        this.krediLimiti = krediLimiti;
    }

    @Override
    public boolean cek(double miktar) {
        if (miktar <= 0) {
            System.out.println("UYARI: miktar pozitif olmalidir.");
            return false;
        }
        // Bakiye + krediLimiti kadar cekime izin verilir
        if (miktar > (bakiye + krediLimiti)) {
            System.out.println("RET: kredi limiti asildi!");
            return false;
        }
        bakiye -= miktar; // bakiye protected oldugu icin dogrudan erisilir
        return true;
    }
}
```

#### b) Uzatma Noktası (Extension Point) ile İyi Tasarım Çözümü (Tam Puanlık Yanıt):

```java
// 1. Ust sinifta uzatma noktasi acilir:
public class Hesap {
    protected double bakiye;
    // ...
    /** Uzatma noktasi: alt siniflar yalnizca cekilebilir siniri degistirir. */
    protected double cekilebilirUstSinir() {
        return bakiye;
    }

    public boolean cek(double miktar) {
        if (miktar <= 0) return false;
        if (miktar > cekilebilirUstSinir()) { // bakiye yerine metot cagrildi!
            System.out.println("RET: limit yetersiz");
            return false;
        }
        bakiye -= miktar;
        return true;
    }
}

// 2. Alt sinif cek() metodunu YENIDEN YAZMAZ, yalnizca uzatma noktasini ezer:
public class KrediliHesap extends Hesap {

    private final double krediLimiti;

    public KrediliHesap(String sahip, double acilisBakiyesi, double krediLimiti) {
        super(sahip, acilisBakiyesi);
        this.krediLimiti = krediLimiti;
    }

    @Override
    protected double cekilebilirUstSinir() {
        return bakiye + krediLimiti;
    }
}
```

---

## Soru 3: Yanlış Kalıtımı Bileşime (HAS-A) Dönüştürme (`IyiAraba`)

**Problem:**  
Bir yazılımcı `Motor` sınıfının metotlarını hazır kullanmak için aşağıdaki hatalı kodu yazmıştır:
```java
class Motor {
    private final int gucHp;
    Motor(int gucHp) { this.gucHp = gucHp; }
    int gucHp() { return gucHp; }
    void calistir() { System.out.println("Motor calisti (" + gucHp + " HP)"); }
    void yakitPompasiTest() { System.out.println("[Servis] pompa testi"); }
}

// YANLIS TASARIM:
class KotuAraba extends Motor {
    private final String plaka;
    KotuAraba(String plaka, int gucHp) { super(gucHp); this.plaka = plaka; }
}
```
1. Bu tasarımın **iki somut mimari hatasını** kısaca yazınız.
2. Kalıtımı kaldırıp **bileşim (composition / HAS-A)** kullanarak hem ön hem arka motora sahip olabilen çift motorlu **`IyiAraba`** sınıfını yazınız.
3. Sınıfın `toplamGuc()` metodu iki motorun gücünü toplamalı, `kontagiAc()` metodu ise her iki motoru da çalıştırmalıdır (delegasyon).

### Çözüm 3 (Kağıda Yazılacak Kod):

**1. Tasarım Hataları:**
* "Araba bir motordur" (IS-A) önermesi anlamsızdır (`kotu instanceof Motor` `true` döner).
* `Motor` sınıfının iç servis ayrıntısı olan `yakitPompasiTest()` metodu arabanın dış arayüzüne sızmıştır.
* Araba kalıtım yüzünden yalnızca tek motora sahip olabilir; iki motorlu yapılamaz.

**2. ve 3. Doğru Bileşim (`IyiAraba`) Kodu:**

```java
public class IyiAraba {

    private final String plaka;
    private final Motor onMotor;    // HAS-A (Bilesim)
    private final Motor arkaMotor;  // HAS-A (Ikinci motor destegi)

    public IyiAraba(String plaka, Motor onMotor, Motor arkaMotor) {
        if (onMotor == null || arkaMotor == null) {
            throw new IllegalArgumentException("Motorlar null olamaz.");
        }
        this.plaka     = plaka;
        this.onMotor   = onMotor;
        this.arkaMotor = arkaMotor;
    }

    public String getPlaka() {
        return plaka;
    }

    // Delegasyon: Iki motorun gucu toplanir
    public int toplamGuc() {
        return onMotor.gucHp() + arkaMotor.gucHp();
    }

    // Delegasyon: Her iki motor calistirilir
    public void kontagiAc() {
        System.out.println(plaka + ": kontak acildi.");
        onMotor.calistir();
        arkaMotor.calistir();
    }

    @Override
    public String toString() {
        return plaka + " | Toplam Guc: " + toplamGuc() + " HP";
    }
    // NOT: Motor'un yakitPompasiTest() metodu IyiAraba'nin arayuzunde GORUNMEZ!
}
```

---

## Soru 4: Üç Seviyeli Miras Zinciri (`Yayin -> Dergi -> AkademikDergi`)

**Problem:**  
Kütüphane sistemi için 3 seviyeli bir sınıf hiyerarşisi kurulacaktır. Aşağıdaki çıktıya bakarak `Yayin`, `Dergi` ve `AkademikDergi` sınıflarını eksiksiz kodlayınız.
* `Yayin`: `baslik` (`String`) ve `yil` (`int`) alanlarını tutar. `oduncGunu()` metodu varsayılan olarak `14` döndürür. `künye()` metodu `"Baslik (Yil)"` döndürür.
* `Dergi`: `Yayin`'dan türer, ek olarak `sayi` (`int`) alanı vardır. Dergiler yalnızca `7` gün ödünç verilebilir (`oduncGunu()` ezilir). `künye()` metodu üst künyenin sonuna `" - Sayi: X"` ekler.
* `AkademikDergi`: `Dergi`'den türer, ek olarak `endeks` (`String`, örn: `"SCI"`) alanı vardır. Akademik dergiler ödünç verilemez (`oduncGunu()` `0` döndürür). `künye()` metodu üst künyenin sonuna `" [Endeks: Y]"` ekler.

**Beklenen Çıktı:**
```text
Yapay Zeka (2026) - Sayi: 42 [Endeks: SCI-E] | Odunc Suresi: 0 gun
```

### Çözüm 4 (Kağıda Yazılacak Kod):

```java
// 1. En Ust Sinif (Base Class)
class Yayin {
    private final String baslik;
    private final int yil;

    public Yayin(String baslik, int yil) {
        this.baslik = baslik;
        this.yil    = yil;
    }

    public int oduncGunu() {
        return 14;
    }

    public String kunye() {
        return baslik + " (" + yil + ")";
    }
}

// 2. Orta Sinif
class Dergi extends Yayin {
    private final int sayi;

    public Dergi(String baslik, int yil, int sayi) {
        super(baslik, yil); // Yayin yapicisini cagirir
        this.sayi = sayi;
    }

    @Override
    public int oduncGunu() {
        return 7;
    }

    @Override
    public String kunye() {
        return super.kunye() + " - Sayi: " + sayi;
    }
}

// 3. En Alt Sinif
class AkademikDergi extends Dergi {
    private final String endeks;

    public AkademikDergi(String baslik, int yil, int sayi, String endeks) {
        super(baslik, yil, sayi); // Dergi yapicisini cagirir
        this.endeks = endeks;
    }

    @Override
    public int oduncGunu() {
        return 0; // Odunc verilemez
    }

    @Override
    public String kunye() {
        return super.kunye() + " [Endeks: " + endeks + "]";
    }
}

// Test Sinifi
public class KutuphaneTest {
    public static void main(String[] args) {
        Yayin y = new AkademikDergi("Yapay Zeka", 2026, 42, "SCI-E");
        System.out.println(y.kunye() + " | Odunc Suresi: " + y.oduncGunu() + " gun");
    }
}
```

---

## Soru 5: Hatalı Kodu Bulma ve Düzeltme (Klasik Sınav Sorusu)

**Problem:**  
Aşağıdaki kodda derleme veya çalışma zamanı hatasına yol açan **4 hatayı** satır numaralarıyla bulunuz, nedenlerini açıklayınız ve kodun düzeltilmiş halini yazınız.

```java
1  final class Ust {
2      private int x;
3      public Ust(int x) { this.x = x; }
4      public final void goster() { System.out.println("x = " + x); }
5  }
6  
7  class Alt extends Ust {
8      private int y;
9      public Alt(int x, int y) {
10         this.y = y;
11         super(x);
12     }
13     @Override
14     public void goster() {
15         System.out.println("x = " + x + ", y = " + y);
16     }
17 }
```

### Çözüm 5:

#### Bulunan 4 Hata:
1. **Satır 1 ve 7 (`final class Ust`):** `Ust` sınıfı `final` tanımlandığı için `Alt` sınıfı ondan türeyemez (`Cannot subclass the final class Ust`).
2. **Satır 10-11 (`super(x)` sırası):** `super(x);` çağrısı yapıcının **ilk satırı** olmak zorundadır; `this.y = y;` satırından sonraya yazılamaz.
3. **Satır 4 ve 14 (`final void goster()`):** Üst sınıfta `final` olarak işaretlenen `goster()` metodu alt sınıfta **ezilemez (override edilemez)**.
4. **Satır 2 ve 15 (`private int x` erişimi):** `x` alanı `Ust` sınıfında `private` olduğu için `Alt` sınıfı içinden doğrudan `x` adıyla okunamaz (ya `protected` yapılmalı ya da `getX()` / `super.goster()` kullanılmalıdır).

#### Kodun Düzeltilmiş Hali:

```java
class Ust { // 1. final kaldirildi
    private final int x;

    public Ust(int x) { 
        this.x = x; 
    }

    public int getX() { return x; }

    public void goster() { // 2. final kaldirildi
        System.out.println("x = " + x); 
    }
}

class Alt extends Ust {
    private final int y;

    public Alt(int x, int y) {
        super(x);   // 3. Ilk satira tasindi
        this.y = y;
    }

    @Override
    public void goster() {
        // 4. private x'e getX() ile erisildi (veya super.goster() kullanilir)
        System.out.println("x = " + getX() + ", y = " + y);
    }
}
```