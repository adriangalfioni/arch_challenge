# RETO ARQUITECTURA - DevExpert

A continuación se indican los distintos cambios que se han realizado a lo largo de los diferentes vivos correspondientes al reto de arquitectura:

- **Día 1**: Limpiamos el codigo eliminando properties no utilizadas, eliminando código muerto y solucionamos los distintos warnings que se indicaban al momento de compilar la app. 
- **Día 2**: Utilizamos el patron Repository y creamos DataSources para de esta forma separar la capa de datos. Se crearon dentro del paquete data los repositorios correspondientes a Ticket y ScanCounter con sus correspondientes DataSources.
- **Día 3**: Creamos casos de uso que representan una acción o tarea especifica. Los casos de uso se encuentran en el paquete domain.
- **Día 4**: Creamos la capa de presentación utilizando ViewModels y UiStates  para conectar la lógica de negocio con la interfaz de usuario. Separamos las screens junto con sus ViewModels y uiStates en paquetes separados dentro de ui.
- **Día 4**: Creamos en el paquete di un objecto AppModule que se encarga de instanciar los data sources, casos de uso y repositorios, ademas provee funciones publicas que retornan los repositorios ya instanciados con sus dependencias. Luego hacemos uso del objeto para recuperar los repositorios. Tambien creamos un test para el caso de uso ProcessTicketUseCase en el que utilizamos un Fake para el TicketDataSource.

# Base Code: SplitBill 📱

An intelligent Android app that helps you split bills by scanning receipts using AI technology.

## Overview

SplitBill is a modern Android application built with Jetpack Compose that uses Firebase AI to automatically process receipt images and help you split expenses with friends, family, or colleagues. Simply scan a receipt, and the AI will extract the items and prices, allowing you to easily select what each person ordered and calculate individual totals.

## Features

- **📸 Receipt Scanning**: Capture receipt images using your device's camera
- **🤖 AI Processing**: Powered by Firebase AI to automatically extract items and prices from receipts
- **💰 Bill Splitting**: Select items for each person and calculate individual totals
- **📊 Smart Item Detection**: Automatically identifies menu items, quantities, and prices
- **🌍 Multi-language Support**: Available in English and Spanish
- **📱 Modern UI**: Built with Jetpack Compose for a smooth, native Android experience

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: Clean Architecture with MVVM pattern
- **AI Processing**: Firebase AI (Vertex AI)
- **Navigation**: Navigation Compose
- **Local Storage**: DataStore Preferences
- **Serialization**: Kotlinx Serialization
- **Build System**: Gradle with Version Catalogs

## Getting Started

### Prerequisites

- Android Studio Hedgehog | 2023.1.1 or later
- JDK 11 or later
- Android SDK API 26+ (minimum) / API 36 (target)
- Firebase project with AI services enabled

### Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd split-bill
   ```

2. **Firebase Configuration**
   - Create a Firebase project at [console.firebase.google.com](https://console.firebase.google.com)
   - Enable Firebase AI services
   - Download `google-services.json` and place it in the `app/` directory

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run the app**
   - Open the project in Android Studio
   - Select a device or emulator
   - Click Run

## Usage

1. **Launch the app** and you'll see the home screen with scan counter
2. **Tap "Scan Ticket"** to open the camera
3. **Take a photo** of your receipt
4. **Wait for AI processing** - the app will extract items and prices automatically
5. **Select items** for each person by tapping on them
6. **View totals** for selected items
7. **Mark as paid** when done

## Configuration

### Build Variants

- **Debug**: Development build with debug logging
- **Release**: Production build with code obfuscation and optimization

### Scan Limits

The app implements a scan counter system to manage usage. Users have a limited number of scans available.

## License

MIT License

Copyright (c) 2025 SplitBill

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.