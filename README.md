# Kotlin Compose Broadcast Receivers Sample App

This repository demonstrates the implementation of **Broadcast Receivers** in an Android app using **Kotlin** and **Jetpack Compose**. The app includes:

1. **Phone Boot Broadcast Receiver**: Sends a notification when the phone boots.
2. **Internet Change Broadcast Receiver**: Dynamically listens for connectivity changes.

## Permissions Required

The app requires the following permissions to function correctly:

### 1. **Toast and Notification Permissions**
   - Starting from **Android 13 (API 33)**, **POST_NOTIFICATIONS** permission is required to display notifications.
   - You need to explicitly grant this permission when the app requests it during runtime.
   - Without this permission, the app cannot notify you about phone boot events.

### 2. **BOOT_COMPLETED Permission**
   - **RECEIVE_BOOT_COMPLETED** permission is used to trigger the app's behavior when the phone completes booting.
   - This permission is declared in the manifest and does not require user interaction.

### 3. **Internet Change Detection**
   - The app dynamically registers a receiver to monitor internet connectivity changes and doesn't require additional permissions.

## Features

- **Phone Boot Notification**:  
  When the phone boots, the app triggers a broadcast receiver to:
  - Log the boot event.
  - Display a **notification** (requires POST_NOTIFICATIONS permission).

- **Internet Change Listener**:  
  Dynamically listens for changes in network connectivity and updates the UI accordingly.
