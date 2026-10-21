# NYP Hafta 02: Sınıflar ve Nesneler - Vize Çalışma Notları

Bu doküman, Nesne Yönelimli Programlama (NYP) Hafta 2 laboratuvar ve ders notlarının vize öncesi hızlı tekrarı için hazırlanmıştır.

---

## 1. Yapıcı Metotlar (Constructors)

Bir nesnenin `new` ile bellekte üretildiği anda çalışarak, nesneyi **geçerli ve eksiksiz** bir başlangıç durumuna getiren özel metotlardır.

**Üç Altın Kural:**
1. Adı, sınıf adıyla **birebir aynı** olmalıdır.
2. Dönüş türü yoktur (Başına `void`, `int`, `String` vb. **kesinlikle yazılmaz**).
3. Yalnızca `new` anahtar sözcüğü ile birlikte nesne doğarken çağrılır.

* **Varsayılan (Default) Yapıcı:** Sınıfta *hiç* yapıcı yazmazsanız derleyici gizlice parametresiz boş bir yapıcı üretir. Ancak siz parametreli bir yapıcı yazdığınız an bu varsayılan yapıcı **kaybolur**.
* **Aşırı Yükleme (Overloading):** Aynı sınıfta birden çok yapıcı bulunabilmesidir. Derleyici; parametrelerin *sayısına, türüne ve sırasına* bakarak karar verir.
* **Kopya Yapıcı:** Parametre olarak aynı sınıftan başka bir nesne alıp, ondan tamamen **bağımsız yeni bir nesne** üreten yapıcıdır. *(Not: `Ogrenci o2 = o1;` ataması kopya üretmez, sadece aynı nesneye bakan ikinci bir referans oluşturur.)*

---

## 2. `this` Anahtar Sözcüğü ve Zincirleme

* **Alan Gölgeleme (Shadowing):** Metot veya yapıcı parametresi sınıf alanıyla aynı ada sahipse, parametre alanı gölgeler. Alana değer atamak için `this.alanAdi = parametre;` yazılması zorunludur.
* **Yapıcı Zincirleme (`this(...)`):** Kod tekrarını önlemek için bir yapıcının, aynı sınıftaki ana "tam yapıcıyı" çağırmasıdır.
  * ⚠️ **Kritik Kural:** `this(...)` çağrısı, bulunduğu yapıcının **ilk satırı** olmak zorundadır.
* **Zincirleme Çağrı:** Metodun sonunda `return this;` kullanılarak akıcı (fluent) çağrılar (`zaman.ilerlet(10).ilerlet(5)`) elde edilebilir.

---

## 3. Kapsülleme (Encapsulation)

Nesnenin iç durumunu dış dünyadan koruma prensibidir.
* Sınıf alanları (değişkenler) `private` yapılarak dışarıdan doğrudan erişime kapatılır.
* Erişim ve değiştirme işlemleri yalnızca `public` getter ve setter metotları ile sağlanır.
* Doğrulama mantığı (örn: "fiyat negatif olamaz") çağrı yerlerinde değil, **sınıfın içindeki yapıcıda ve setter metotlarında** toplanır.
* Hesaplanabilen değerler (örn. dikdörtgenin alanı) bir değişken olarak tutulmamalı, hesaplayan bir metot olarak yazılmalıdır.

---

## 4. Bellek, Yaşam Döngüsü ve Kaynak Yönetimi

* **Doğuş:** `new` dendiğinde Öbek (Heap) bellekte yer açılır. Yapıcı çalışmadan hemen önce tüm alanlar **varsayılan değerlerine** (`0`, `0.0`, `false`, `null`) kurulur; çöp değer içermezler.
* **Çöp Toplayıcı (Garbage Collector):** Java'da C++'taki gibi manuel bellek temizliği (`free()`, destructor) yoktur. Bir nesneye giden tüm referanslar koptuğunda, GC arka planda belleği temizler. Ne zaman çalışacağı **belirsizdir**.
  * `System.gc()` sadece bir öneridir, `finalize()` ise kullanımdan kaldırılmıştır.
* **Kaynak Kapatma (`AutoCloseable`):** Dosya veya ağ bağlantısı gibi hemen kapatılması gereken kaynaklar için `AutoCloseable` arayüzü ve `try-with-resources` bloğu kullanılır. Hata (Exception) fırlatılsa bile `close()` metodu otomatik çalışır.

---

## 5. `toString()` Metodunun Ezilmesi

* Java'da `System.out.println(nesne)` çağrıldığında arka planda otomatik olarak o nesnenin `toString()` metodu çalıştırılır.
* Anlamlı ve okunabilir bir çıktı (örn: adres yerine kitap bilgileri) almak için bu metot sınıf içinde `@Override` edilmelidir.
* **İmza:** `public String toString()` şeklinde olmalıdır. İçeride formatlı metin üretmek için `String.format(...)` döndürülmesi tavsiye edilir.