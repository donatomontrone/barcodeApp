@echo off
REM === CONFIGURAZIONE ===
set JFX_LIB=C:\Users\DonatoMontrone\Downloads\javafx-sdk-17.0.15\lib
set APP_NAME=BarcodeApp
set MAIN_JAR=BarcodeApp.jar
set MAIN_CLASS=org.doazz.barcode.Main
set MODULES=java.base,java.desktop,java.logging,javafx.controls,javafx.fxml,javafx.graphics
set OUTPUT_RUNTIME=custom-runtime
set ICON=src\main\resources\images\icon.ico
set INPUT_DIR=target
set RESOURCE_DIR=src\main\resources
set INSTALL_DIR="C:\Program Files\BarcodeApp"
set VENDOR="Montrone"
set UUID=12345678-1234-5678-1234-567812345678

REM === STEP 1: PULIZIA PRECEDENTE ===
if exist %OUTPUT_RUNTIME% rd /s /q %OUTPUT_RUNTIME%

REM === STEP 2: CREAZIONE RUNTIME CON JLINK ===
echo Creazione della runtime personalizzata con jlink...
jlink ^
  --module-path "%JAVA_HOME%\jmods;%JFX_LIB%" ^
  --add-modules %MODULES% ^
  --output %OUTPUT_RUNTIME% ^
  --compress=2 ^
  --no-header-files ^
  --no-man-pages

if errorlevel 1 (
  echo Errore durante jlink, esco.
  pause
  exit /b 1
)

REM === STEP 3: CREAZIONE INSTALLER CON JPACKAGE ===
echo Creazione dell'installer con jpackage...
jpackage ^
  --name %APP_NAME% ^
  --input %INPUT_DIR% ^
  --main-jar %MAIN_JAR% ^
  --main-class %MAIN_CLASS% ^
  --type exe ^
  --icon %ICON% ^
  --java-options "-Dprism.order=sw -Dprism.verbose=true -Xmx512m" ^
  --runtime-image %OUTPUT_RUNTIME% ^
  --win-menu ^
  --win-dir-chooser ^
  --win-menu-group %APP_NAME% ^
  --win-shortcut ^
  --win-upgrade-uuid "%UUID%" ^
  --description "%APP_NAME% - Gestione rapida dei tuoi articoli" ^
  --install-dir %INSTALL_DIR% ^
  --resource-dir %RESOURCE_DIR% ^
  --vendor %VENDOR% ^
  --copyright "© 2025 - %VENDOR%"

if errorlevel 1 (
  echo Errore durante jpackage, esco.
  pause
  exit /b 1
)

echo ✅ Build completato!
pause
