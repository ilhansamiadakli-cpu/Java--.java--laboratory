.DEFAULT_GOAL := run

LAUNCHER_DIR  := tools/launcher
LAUNCHER_POM  := $(LAUNCHER_DIR)/pom.xml
LAUNCHER_JAR  := $(LAUNCHER_DIR)/target/launcher-shaded.jar
MANIFEST_FILE := target/targets.manifest
LAUNCHER_SRCS := $(shell find $(LAUNCHER_DIR)/src -name '*.java' 2>/dev/null) $(LAUNCHER_POM)

JAVA          ?= java
MVN           ?= mvn
IDE_DIR       ?= IntelliJProjects

.PHONY: run sync build-launcher clean lg push help

run: $(LAUNCHER_JAR)
	@$(JAVA) -Dfile.encoding=UTF-8 -jar $(LAUNCHER_JAR) --sync --quiet
	@$(JAVA) -Dfile.encoding=UTF-8 -jar $(LAUNCHER_JAR)

build-launcher: $(LAUNCHER_JAR)

$(LAUNCHER_JAR): $(LAUNCHER_SRCS)
	@command -v $(MVN) >/dev/null 2>&1 || { \
		echo "[HATA] Maven ($(MVN)) bulunamadi. Kurulum icin README.md dosyasina bakin."; \
		exit 1; \
	}
	@echo "[build] tools/launcher fat-jar derleniyor..."
	@$(MVN) -f $(LAUNCHER_POM) -q -DskipTests package

sync: $(LAUNCHER_JAR)
	@echo "[sync] Proje taramasi baslatiliyor..."
	@$(JAVA) -Dfile.encoding=UTF-8 -jar $(LAUNCHER_JAR) --sync --verbose

clean:
	@echo "[clean] Derleme ciktilari temizleniyor ($(IDE_DIR) korunuyor)..."
	@rm -rf target
	@find . \
		\( -path "./$(IDE_DIR)" -o -path "./.git" \) -prune -o \
		-type d \( -name "target" -o -name ".out" \) -prune -exec rm -rf {} +
	@find . \
		\( -path "./$(IDE_DIR)" -o -path "./.git" \) -prune -o \
		-type f \( -name "*.class" -o -name "*.out" \) -exec rm -f {} +
	@echo "[clean] Tamamlandi."

lg:
	@if command -v lazygit >/dev/null 2>&1; then \
		lazygit; \
	else \
		echo "[HATA] lazygit kurulu degil. Kurulum icin README.md dosyasina bakin."; \
		exit 1; \
	fi

push:
	@git add -A
	@if git diff --cached --quiet; then \
		echo "[git] Commit edilecek yeni degisiklik yok."; \
	else \
		COMMIT_MSG="$(MSG)"; \
		if [ -z "$$COMMIT_MSG" ]; then \
			COMMIT_MSG="chore: update laboratory ($$(date '+%Y-%m-%d %H:%M'))"; \
		fi; \
		git commit -m "$$COMMIT_MSG"; \
	fi
	@git push

help:
	@echo "Kullanilabilir komutlar:"
	@echo "  make / make run   : Manifesti gunceller, gerekirse launcher'i derler ve TUI'yi baslatir"
	@echo "  make sync         : Proje taramasini detayli log ile yeniler"
	@echo "  make clean        : target/, .out/, *.out ve *.class ciktilarini temizler ($(IDE_DIR) korunur)"
	@echo "  make lg           : Kuruluysa lazygit arayuzunu acar"
	@echo "  make push         : Otomatik git add / commit / push yapar (opsiyonel: MSG=\"mesaj\")"
