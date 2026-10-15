# 🧪 Java Denemeleri & Kod Laboratuvarı (Playground)

[Java](https://www.google.com/search?q=https%3A%2F%2Fwww.oracle.com%2Fjava%2F)
[Environment](https://www.google.com/search?q=https%3A%2F%2Fcode.visualstudio.com%2F)
[Git](https://www.google.com/search?q=https%3A%2F%2Fgit-scm.com%2F)
[Clean Code](https://www.google.com/search?q=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FClean_code)

Bu klasör, Java programlama dilini öğrenme sürecimde teorik bilgileri pratiğe dökmek, yeni sözdizimi özelliklerini deneyimlemek, algoritmik yaklaşımları test etmek ve geliştirici iş akışlarımı optimize etmek amacıyla oluşturduğum **kişisel kod laboratuvarımdır (sandbox)**.

---

## 📌 İçindekiler
- [🎯 Alanın Amacı ve Öğrenme Felsefesi](#-alanın-amacı-ve-öğrenme-felsefesi)
- [🔬 Bu Laboratuvarda Neler Yer Alır?](#-bu-laboratuvarda-neler-yer-alır)
- [📂 Dizin Organizasyonu](#-dizin-organizasyonu)
- [⚡ Hızlı Derleme ve Çalıştırma Akışı](#-hızlı-derleme-ve-çalıştırma-akışı)
- [🛠️ Geliştirici Araçları ve Terminal İş Akışı](#️-geliştirici-araçları-ve-terminal-iş-akışı)

---

## 🎯 Alanın Amacı ve Öğrenme Felsefesi

Yazılım geliştirme sürecinde kalıcı öğrenmenin temel yolu, teorik konulardan hemen sonra serbestçe kod yazmak, sınır durumları sınamak ve gerektiğinde hata yapmaktan çekinmeden kod üzerinde oynamaktır. 

Bu laboratuvar ortamı:
* **Baskısız Deneme Alanı:** Akademik ödev veya proje kısıtlamalarından bağımsız, tamamen merak odaklı kod denemeleri yapma imkânı sunar.
* **Prototipleme ve İterasyon:** Yeni öğrenilen bir kütüphane fonksiyonunu, döngü yapısını veya nesne modelini ana projelere taşımadan önce burada küçük parçalar hâlinde izole olarak test etmeyi sağlar.
* **Refactoring Pratiği:** Çalışan kodu daha temiz, daha okunaklı ve daha modüler hâle getirme egzersizleri için bir kum havuzu görevi görür.

---

## 🔬 Bu Laboratuvarda Neler Yer Alır?

Buradaki kodlar statik değil, **sürekli evrilen ve genişleyen dinamik bir yapıya** sahiptir:

1. **Dil Sözdizimi ve Temel Özellikler:** Değişken tanımlamaları, tür dönüşümleri (casting), koşul yapıları, döngüler ve dizi pratikleri.
2. **Algoritmik Alıştırmalar:** Temel mantık problemleri, matematiksel hesaplamalar ve fonksiyonel metot denemeleri.
3. **Nesneye Yönelik İlk Adımlar:** Sınıf tanımlama, metot zincirleme, parametre aktarımı ve referans tiplerin bellek davranışını gözlemleme.
4. **İş Akışı ve Otomasyon Araçları:** Kod çalıştırma scriptleri, terminal yardımcıları ve versiyon kontrol entegrasyonu denemeleri.

> *Not: `SRC/` klasörü altındaki dosyalar öğrenme yolculuğumun anlık adımlarını yansıttığı için ihtiyaca göre serbestçe değiştirilebilir, yeniden yapılandırılabilir veya yeni deneme sınıfları eklenebilir.*

---

## 📂 Dizin Organizasyonu

```text
Denemeler/
├── README.md               <-- Kod laboratuvarının genel tanıtımı ve felsefesi
├── lazygit.txt             <-- Terminal tabanlı Git yönetimi (Lazygit) hızlı referans kılavuzu
├── bin/                    <-- Derlenen .class dosyalarının tutulduğu dizin (gitignore)
└── SRC/                    <-- Sürekli güncellenen deneme ve alıştırma kodları dizini
```

---

## ⚡ Hızlı Derleme ve Çalıştırma Akışı

Herhangi bir deneme kodunu izole şekilde terminal üzerinden çalıştırmak için:

```bash
# 1. Denemeler kaynak klasörüne geçin
cd Denemeler/SRC

# 2. İlgili Java dosyasını derleyin
javac -d ../bin DosyaAdi.java

# 3. Derlenen sınıfı çalıştırın
java -cp ../bin DosyaAdi
```

VS Code kullanıyorsanız, proje ayarlarında tanımlı Code Runner kısayolu (`Ctrl+Alt+N` / `Cmd+Option+N`) veya `main` metodu üzerindeki **Run** düğmesiyle dosyaları anında derleyip terminalde sonuçları görebilirsiniz.

---

## 🛠️ Geliştirici Araçları ve Terminal İş Akışı

Kod yazma pratiğinin yanında, profesyonel bir yazılım mühendisi refleksi kazanmak adına sürüm kontrol araçları da bu laboratuvarda aktif olarak deneyimlenmektedir:

* **Git & GitHub:** Her mantıksal deneme adımı sonrasında anlamlı commit mesajları oluşturma alışkanlığı.
* **Lazygit:** Klasör içinde yer alan `lazygit.txt` belgesinde özetlendiği üzere, terminalden ayrılmadan hızlı dal (branch), işleme (commit) ve gönderme (push) operasyonlarını pratik etme.

---

&lt;div align=&quot;center&quot;&gt;

*"Öğrenmenin en iyi yolu; denemek, bozmak, düzeltmek ve yeniden yazmaktır."*

&lt;/div&gt;
