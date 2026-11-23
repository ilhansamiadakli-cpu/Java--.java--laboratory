# Java Laboratory

Kişisel Java denemeleri, ders laboratuvarları ve küçük prototipler için otomatik keşif, izole derleme ve terminal tabanlı etkileşimli çalıştırıcı (TUI Launcher) sağlayan çalışma deposu.

## Amaç

Bu depo; farklı klasör yapılarındaki bağımsız Java kaynak dosyalarını ve alt Maven projelerini tek bir `make run` komutu altında toplar. Her proje kendi klasöründe birbirinden izole şekilde derlenir ve çalıştırılır. Bir projedeki sözdizimi hatası veya eksik bağımlılık, başlatıcının açılmasını veya diğer projelerin çalışmasını engellemez.

## Klasör Yapısı

```text
.
├── makefile                            # Merkezi otomasyon komutları (run, sync, clean, lg, push)
├── .gitignore                          # Derleme çıktıları, fat-jar ve IDE dosyaları filtresi
├── README.md                           # Teknik dokümantasyon
├── target/
│   └── targets.manifest                # Otomatik üretilen çalıştırılabilir hedef listesi
├── tools/
│   └── launcher/                       # Bağımsız Maven modülü (Lanterna TUI + tarayıcı + çalıştırıcı)
│       ├── pom.xml
│       └── src/
├── Denemeler/                          # Otomatik keşfedilen kategori klasörü
│   ├── lazygit.txt
│   └── SRC/
├── Nesne_Tabanlı_Programlama_dersi/    # Otomatik keşfedilen kategori klasörü
│   ├── Hafta_01_lab/
│   ├── Hafta_02_ders/
│   └── ...
└── IntelliJProjects/                   # (Varsa) IDE projeleri - tarama ve temizleme dışında tutulur
```

### Tarama ve İzolasyon Kuralları

- **Tarama dışında tutulan kök dizinler:** `IntelliJProjects/`, `tools/`, `target/`, `build/`, `out/`, `bin/`, `.git/` ve `.` ile başlayan tüm gizli klasörler.
- **Otomatik kategori keşfi:** Yukarıdaki liste dışında kalan her kök klasör (mevcut olanlar ve ileride eklenecek yeni klasörler) otomatik olarak bir kategori sekmesi olarak algılanır.
- **`IntelliJProjects` koruması:** Hem proje taraması (`make sync`) hem temizleme (`make clean`) bu dizine kesinlikle dokunmaz.

## Keşif, Derleme ve Çalışma Dizini Mimarisi

### 1. Proje Keşfi (`ProjectScanner`)

Tarayıcı, desteklenen iki klasör düzenini otomatik olarak ayırır:

1. **Basit düzen (`simple`):**
   - Doğrudan klasör içinde `.java` dosyaları (ör. `Denemeler/ornek/Main.java` ve yardımcı `.java` dosyaları)
   - Geleneksel `src/` veya `SRC/` klasörü içeren projeler (alt paketler dahil, ör. `src/kutuphane/*.java`)
   - `pom.xml` olmadan `src/main/java/` düzenine sahip klasörler
2. **Standart Maven düzeni (`maven`):**
   - Kendi `pom.xml` dosyasına sahip alt projeler (`src/main/java/...`)

Aynı proje içinde `public static void main(String[] args)` (ve `String args[]`, `String... args` gibi geçerli varyantlar) içeren her `.java` dosyası ayrı bir çalıştırılabilir hedef olarak kaydedilir. `main` içermeyen aynı kaynak kökündeki diğer `.java` dosyaları yardımcı sınıf olarak derlemeye dahil edilir.

Tüm hedefler `target/targets.manifest` dosyasına aşağıdaki 5 sütunlu formatta yazılır:

```text
Kategori|GörünenAd|AnaSınıf|ÇalışmaDizini|DerlemeTipi
```

### 2. İzole Derleme (`ProjectRunner`)

- **Basit projeler (`simple`):** Her hedef kendi projesinin içindeki `<ÇalışmaDizini>/.out/<AnaSınıf>/` dizinine `javac -encoding UTF-8 -sourcepath <kaynakKök>` ile derlenir. Aynı klasördeki başka bir `main` dosyasında derleme hatası varsa sağlam hedef etkilenmez; kullanılmayan bir yardımcı dosyada hata varsa yalnızca hedef sınıf ve onun bağımlı olduğu sınıflar derlenerek tam izolasyon sağlanır.
- **Maven projeleri (`maven`):** Proje dizininde `mvn -B compile` çalıştırılır ve bağımlılık classpath'i `mvn -B -q dependency:build-classpath -Dmdep.outputFile=target/runtime.cp` ile çözümlenir.

### 3. Çalışma Dizini Garantisi

Bir program TUI üzerinden çalıştırıldığında:

1. Lanterna ekranı ve ham terminal modu geçici olarak tamamen kapatılır, normal terminal oturumu geri verilir.
2. Çocuk süreç `ProcessBuilder` ile başlatılır; `inheritIO()` ile doğrudan terminale bağlanır (klavye girdisi, `Scanner(System.in)` ve renkli çıktı sorunsuz çalışır).
3. `ProcessBuilder.directory(...)` değeri **kesinlikle hedefin kendi `ÇalışmaDizini` klasörüne** ayarlanır. Böylece proje klasöründeki `deneme.txt`, `data.json` gibi göreli dosya yolları her zaman doğru çözülür.
4. Program sonlandığında çıkış kodu yazdırılır ve kullanıcı `Enter` tuşuna bastığında TUI menüsüne geri dönülür.

## Make Komutları

| Komut | Açıklama |
| --- | --- |
| `make` veya `make run` | Gerekiyorsa `tools/launcher` fat-jar paketini (`mvn package`) oluşturur, `target/targets.manifest` dosyasını günceller ve TUI menüsünü başlatır. |
| `make sync` | Tüm depoyu tarar, keşfedilen kategorileri ve hedefleri ayrıntılı log ile ekrana basar ve `target/targets.manifest` dosyasını yeniler. |
| `make clean` | `target/`, `.out/`, `*.out` ve `*.class` derleme çıktılarını temizler. `IntelliJProjects` dizinini korur. |
| `make lg` | Sistemde kuruluysa `lazygit` arayüzünü başlatır. |
| `make push` | `git add -A`, otomatik commit ve `git push` çalıştırır. Özel mesaj için: `make push MSG="commit mesajı"`. |

## TUI Launcher Özellikleri ve Kısayollar

Launcher açıldığında imleç doğrudan arama kutusundadır.

### Arama Özellikleri

- **Türkçe karakter normalizasyonu:** `ç, ğ, ı, İ, ö, ş, ü` karakterleri büyük/küçük harf duyarsız olarak ASCII karşılıklarına dönüştürülür (ör. `asal sayi` sorgusu `AsalSayıBulma` sınıfını bulur).
- **Çok kelimeli ve kısaltma destekli puanlama:** Birden fazla kelimeyle daraltma (ör. `hafta 4 kutup`) ve CamelCase/ayraç baş harfleriyle kısaltma eşleştirme (ör. `g5kd` -> `Gorev5_KutuphaneDemo`) desteklenir.
- **Sağ önizleme paneli:** Seçili hedefin derleme durumunu (`[OK]`, `[ESKI]`, `[--]`, `[HATA]`), çalışma dizinini, klasördeki dosyaları (`.txt`, `.json` vb.) ve son derleme çıktısını gösterir.
- **CLI argüman kutusu:** `Tab` ile geçilerek programa gönderilecek komut satırı parametreleri girilebilir (tırnaklı argümanlar desteklenir).

### Klavye Kısayolları

| Tuş | İşlev |
| --- | --- |
| `↑` / `↓` / `PgUp` / `PgDn` | Arama kutusundan çıkmadan hedef listesinde gezinir |
| `Enter` | Seçili hedefi derler ve kendi çalışma dizininde çalıştırır |
| `F5` | Seçili hedefi yalnızca derler (çalıştırmadan) |
| `r` (liste odağında) / `Ctrl+R` | Seçili hedefi yalnızca derler |
| `Tab` / `Shift+Tab` | Odak alanını değiştirir (`Arama` -> `Liste` -> `CLI Argümanları`) |
| `←` / `→` (liste odağında veya arama boşken) | Kategori sekmeleri arasında geçiş yapar |
| `F2` / `F3` veya `Ctrl+←` / `Ctrl+→` | Herhangi bir alandan kategori sekmelerini değiştirir |
| `F6` | TUI içinden proje taramasını (`targets.manifest`) yeniler |
| `Esc` | Arama/argüman metni doluysa temizler; boşsa çıkış yapar |
| `q` (liste odağında) / `Ctrl+Q` / `Ctrl+C` | Uygulamadan çıkar |

## Gereksinimler ve Kurulum

Gerekli araçlar:
- **JDK 25 LTS**
- **Apache Maven 3.8+**
- **GNU Make**
- **lazygit** (opsiyonel, `make lg` için)

### macOS (Homebrew)

```bash
brew install openjdk@25 maven make lazygit
```

JDK'yı sistemin tanıması için (gerekirse):

```bash
sudo ln -sfn $(brew --prefix)/opt/openjdk@25/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-25.jdk
echo 'export PATH="$(brew --prefix)/opt/openjdk@25/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

### Linux (Debian / Ubuntu)

```bash
sudo apt update
sudo apt install -y openjdk-25-jdk maven make git
```

`lazygit` kurulumu (Ubuntu/Debian):

```bash
LAZYGIT_VERSION=$(curl -s "https://api.github.com/repos/jesseduffield/lazygit/releases/latest" | grep -Po '"tag_name": "v\K[^"]*')
curl -Lo lazygit.tar.gz "https://github.com/jesseduffield/lazygit/releases/latest/download/lazygit_${LAZYGIT_VERSION}_Linux_x86_64.tar.gz"
tar xf lazygit.tar.gz lazygit
sudo install lazygit /usr/local/bin
rm -f lazygit lazygit.tar.gz
```

Arch Linux için:

```bash
sudo pacman -S jdk25-openjdk maven make lazygit
```

### Windows (winget veya Chocolatey)

PowerShell üzerinden `winget` ile:

```powershell
winget install EclipseAdoptium.Temurin.25.JDK
winget install Apache.Maven
winget install GnuWin32.Make
winget install JesseDuffield.lazygit
```

Alternatif olarak WSL (Ubuntu) veya Git Bash ortamında Linux komutlarıyla da kullanılabilir. Kurulum sonrasında terminali yeniden başlatıp sürümleri doğrulayın:

```bash
java -version
javac -version
mvn -version
make --version
```
