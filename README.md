# Secure Secret Storage in Android

This is a Kotlin app demonstrating secure storage of secret data in Android.

It uses the [Android Jetpack](https://developer.android.com/topic/security/data) (a.k.a. AndroidX)
[`security-crypto`](https://developer.android.com/jetpack/androidx/releases/security) library,
which uses the [Android keystore system](https://developer.android.com/training/articles/keystore)
and the cross-platform [Tink library](https://github.com/google/tink).

The core of this app's functionality is found in the file
[`MainActivity.kt`](app/src/main/java/wtf/daabr/securesecretstorage/MainActivity.kt).

Minimum Android version: [Android 6.0 Marshmallow](https://www.android.com/versions/marshmallow-6-0), i.e. API level 23 - this is
when the [`KeyGenParameterSpec`](https://developer.android.com/reference/kotlin/android/security/keystore/KeyGenParameterSpec)
class was introduced, even though we don't reference it directly here.

Note that version 1.1.0 of the Jetpack Security library technically added support for
[Android 5.0 Lollipop](https://www.android.com/versions/lollipop-5-0) - i.e. API level 21 - and higher, but
without using the keystore. More details [here](https://developer.android.com/topic/security/data-android-versions)
and [here](https://android.googlesource.com/platform/frameworks/support/+/5dc65a7882dabeb8ea4c3bebb5e7d95aa113616a).

Also note that version 1.1.0 of the Jetpack Security library also adds Kotlin-specific extensions. More details
[here](https://android.googlesource.com/platform/frameworks/support/+/c06a5c10503c46142153d9b0cf11349299f28892).

Other code samples:

*   https://developer.android.com/topic/security/data
*   https://developer.android.com/topic/security/data-android-versions
*   Official sample: https://github.com/android/security-samples/tree/main/FileLocker
    *   [EditFragment.kt](https://github.com/android/security-samples/blob/main/FileLocker/app/src/main/java/com/android/example/filelocker/EditFragment.kt)
    *   [ListFragment.kt](https://github.com/android/security-samples/blob/main/FileLocker/app/src/main/java/com/android/example/filelocker/ListFragment.kt)
*   API reference: https://developer.android.com/reference/kotlin/androidx/security/crypto/package-summary
    *   Kotlin extension:
        [EncryptedFile.kt](https://android.googlesource.com/platform/frameworks/support/+/c06a5c10503c46142153d9b0cf11349299f28892/security/security-crypto-ktx/src/main/java/androidx/security/crypto/EncryptedFile.kt)
    *   Kotlin extension:
        [EncryptedSharedPreferences.kt](https://android.googlesource.com/platform/frameworks/support/+/c06a5c10503c46142153d9b0cf11349299f28892/security/security-crypto-ktx/src/main/java/androidx/security/crypto/EncryptedSharedPreferences.kt)
    *   Kotlin extension:
        [MasterKey.kt](https://android.googlesource.com/platform/frameworks/support/+/c06a5c10503c46142153d9b0cf11349299f28892/security/security-crypto-ktx/src/main/java/androidx/security/crypto/MasterKey.kt)

2019 analysis by F-Secure + recommendations and a sample app (FYI but beyond my current needs):

* https://labs.f-secure.com/blog/how-secure-is-your-android-keystore-authentication
* https://github.com/FSecureLABS/android-keystore-audit
