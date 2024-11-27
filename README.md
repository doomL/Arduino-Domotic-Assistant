# LARA - Arduino Domotic Assistant

This project is a **Smart Domotic Assistant** that uses **Arduino** and a **Bluetooth module** to control a domotic house. As part of this project, the house was entirely **3D printed**, and the Arduino connections were **hand-wired**. The application provides speech-based control of various home appliances using an **Android application**.

---

## Features

- **Speech Control**: The assistant responds to spoken commands to control home appliances.
- **Bluetooth Communication**: Connects to an Arduino via Bluetooth for seamless operation.
- **Customizable Responses**: Predefined commands trigger specific actions and verbal acknowledgments.
- **3D Printed Model**: A miniature representation of a smart home for demonstration purposes.
- **Real-Time Feedback**: Provides real-time responses, including gas detection and temperature updates.

---

## Demonstration Images

### 3D Printed Smart House
![3D Printed House](https://github.com/Doominator96/Arduino-Domotic-Assistant/raw/master/img1.jpg)
![3D Printed House Interior](https://github.com/Doominator96/Arduino-Domotic-Assistant/raw/master/img2.jpg)

### Android Application Interface
![Android App](https://github.com/Doominator96/Arduino-Domotic-Assistant/raw/master/app.jpg)

---

## Project Structure

### Android Application
- **Speech-to-Text**: Converts user speech into text to interpret commands.
- **Text-to-Speech**: Provides voice feedback based on system actions.
- **Bluetooth Communication**:
  - Establishes a connection with the Arduino using the **Bluetooth MAC address**.
  - Sends commands to control appliances.
  - Receives data (e.g., temperature or gas levels).

### Arduino Setup
- **Bluetooth Module**: HC-05/HC-06 or compatible module for wireless communication.
- **Hand-Wired Connections**: Manual wiring to interface the Arduino with LEDs, fans, or other appliances.
- **Command Handling**: Receives predefined commands (e.g., `salottoOn`) and triggers the respective relays or modules.

---

## Commands Overview

### Lights
- **Kitchen**:  
  - `Accendi la luce in cucina` → Turns on the kitchen light.  
  - `Spegni la luce in cucina` → Turns off the kitchen light.
- **Living Room**:  
  - `Accendi la luce in salotto` → Turns on the living room light.  
  - `Spegni la luce in salotto` → Turns off the living room light.

### Heating and Cooling
- **Heater**:  
  - `Accendi la stufa` or `Fa freddo` → Turns on the heater.  
  - `Spegni la stufa` or `Fa caldo` → Turns off the heater.
- **Fan**:  
  - `Accendi il ventilatore` → Turns on the fan.  
  - `Spegni il ventilatore` → Turns off the fan.

### Sensors
- **Temperature**: `Quanti gradi ci sono` → Reads and announces the current temperature.  
- **Gas Detection**: Provides warnings if hazardous gas levels are detected.

### Greetings
- `Ciao` → Responds with "Ciao anche a te".  
- `Come ti chiami` → Responds with "Mi chiamo Lara".

---

## Technical Highlights

1. **Speech Recognition**:
   - Utilizes the `SpeechRecognizer` API for free-form voice command recognition.
   - Listens for specific phrases mapped to corresponding actions.

2. **Text-to-Speech**:
   - Implements `TextToSpeech` to provide auditory feedback.
   - Configured for **Italian language** responses.

3. **Bluetooth Communication**:
   - Sets up a secure socket connection using the device's MAC address.
   - Employs separate threads for sending and receiving data to avoid blocking the UI thread.

4. **Gas Detection**:
   - Uses a periodic handler to monitor incoming gas levels via the Arduino and alerts the user.

---

## How to Use

1. **Setup Arduino**:
   - Connect the Bluetooth module to the Arduino.
   - Program the Arduino to handle commands such as `cameraOn` or `stufaOff`.

2. **Prepare the Android App**:
   - Clone or download the project repository.
   - Build and run the app on an Android device.
   - Ensure Bluetooth is enabled and paired with the Arduino module.

3. **Control with Voice**:
   - Open the app, press the microphone button, and issue commands.
   - Example: Say *"Accendi la luce in salotto"* to turn on the living room light.

---

## Requirements

### Hardware
- **Arduino** (e.g., Uno, Mega)
- **Bluetooth Module** (e.g., HC-05)
- 3D printed house model with LEDs and relays
- Sensors for gas and temperature monitoring

### Software
- Android device with Bluetooth capability
- Android Studio for building the app
- Arduino IDE for programming the microcontroller

---

## Future Improvements

- **Expand Appliance Support**: Add more commands for other devices like curtains, locks, or sprinklers.
- **Language Options**: Support additional languages beyond Italian.
- **Wi-Fi Integration**: Extend connectivity using Wi-Fi for broader control.
- **Smartphone Feedback**: Display real-time sensor data on the app interface.

---

**Contributors**:  
Project developed by [Doominator96](https://github.com/Doominator96)  
