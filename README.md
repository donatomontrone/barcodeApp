# 📦 BarcodeApp

**BarcodeApp** è un'applicazione sviluppata in **JavaFX** che consente la gestione semplice e intuitiva di articoli tramite **scanner di codici a barre** o **input da tastiera**. L'app permette di registrare articoli, visualizzarne il prezzo, aggiungerne di nuovi tramite scansione, nonché modificarli o eliminarli.

Include un **database interno H2** che memorizza in modo persistente tutti gli articoli registrati.

## 🚀 Caratteristiche principali

- ✅ **Registrazione degli articoli** con nome, codice a barre, prezzo e altre informazioni.
- 🔍 **Ricerca rapida del prezzo** tramite scanner di codici a barre o digitazione manuale.
- ➕ **Aggiunta automatica di nuovi articoli** al momento della scansione, se non presenti in archivio.
- 🛠️ **Modifica o eliminazione** di articoli esistenti.
- 🗃️ **Database integrato con H2** per la gestione locale e persistente dei dati.
- 📦 **Esportabile come eseguibile `.exe`** per Windows tramite `jpackage`.

## 🖥️ Tecnologie utilizzate

- Java 17+
- JavaFX
- H2 Database (embedded)
- Gradle o Maven
- JPackage (per creare `.exe`)

## 📂 Come usare

### 1. Clona il repository

```bash
git clone https://github.com/donatomontrone/BarcodeApp.git
cd BarcodeApp
```

### 2. Costruzione del progetto

#### Con Gradle:

```bash
./gradlew build
./gradlew run
```

#### Con Maven:

```bash
mvn clean install
mvn javafx:run
```

### 3. Generazione dell’eseguibile `.exe` (opzionale)

> Richiede una JDK compatibile con `jpackage` (es. JDK 17+)

```bash
jpackage --name BarcodeApp --input target --main-jar BarcodeApp.jar --main-class org.doazz.barcode.Main --type exe --icon src/main/resources/images/icon.ico --java-options "-Xmx512m" --module-path path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
```

*(Personalizza i parametri in base al tuo progetto)*

## 🤝 Contribuire

Contributi, segnalazioni di bug o suggerimenti sono sempre benvenuti!

## 📄 Licenza

Distribuito sotto la licenza MIT. Vedi `LICENSE` per maggiori dettagli.
