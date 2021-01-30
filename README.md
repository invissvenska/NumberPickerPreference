# NumberPickerPreference
[![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19) [![](https://jitpack.io/v/invissvenska/NumberPickerPreference.svg)](https://jitpack.io/#invissvenska/NumberPickerPreference) <a href="https://github.com/invissvenska/NumberPickerPreference/actions"><img alt="Build Status" src="https://github.com/invissvenska/NumberPickerPreference/workflows/Android-Library%20CI/badge.svg"/></a> <span class="badge-buymeacoffee"><a href="https://www.paypal.com/paypalme/svenvandentweel/3" title="Donate to this project using Buy Me A Coffee"><img src="https://img.shields.io/badge/buy%20me%20a%20coffee-donate-yellow.svg" alt="Buy Me A Coffee donate button" /></a></span>  

## Prerequisites

Add this in your root `build.gradle` file (**not** your module `build.gradle` file):

```gradle
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

## Dependency

Add this to your module's `build.gradle` file (make sure the version matches the JitPack badge above):

```gradle
dependencies {
    ...
    implementation 'com.github.invissvenska:NumberPickerPreference:VERSION'
}
```

## Configuration

Add the NumberDialogPreference to the preferences.xml:

```xml
<nl.invissvenska.numberpickerpreference.NumberDialogPreference
    android:key="preference_key"
    android:title="Preference title"
    app:defaultValue="20" // optional, default is 0
    app:numberPickerPreference_minValue="10" // optional, default is 0
    app:numberPickerPreference_maxValue="60" // optional, default is 100
    app:numberPickerPreference_stepValue="1" // optional, default is 1
    app:numberPickerPreference_unitText=" another quantity" /> // optional, default is ""
```

Override the OnDisplayPreferenceDialog method in your fragment which extends the PreferenceFragmentCompat class:
```java
public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String DIALOG_FRAGMENT_TAG = "CustomPreferenceDialog";
    
    // some other code

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        if (preference instanceof NumberDialogPreference) {
            NumberDialogPreference dialogPreference = (NumberDialogPreference) preference;
            DialogFragment dialogFragment = NumberPickerPreferenceDialogFragment
                    .newInstance(
                            dialogPreference.getKey(),
                            dialogPreference.getMinValue(),
                            dialogPreference.getMaxValue(),
                            dialogPreference.getStepValue(),
                            dialogPreference.getUnitText()
                    );
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(getParentFragmentManager(), DIALOG_FRAGMENT_TAG);
        } else {
            super.onDisplayPreferenceDialog(preference);
        }
    }
}
```

## Usage

To create a NumberPickerPreference with default value of 20, min value of 10, max value of 60, increments of 2 and custom unit text:
```xml
<nl.invissvenska.numberpickerpreference.NumberDialogPreference
    android:key="preference_key"
    android:title="Preference title"
    app:defaultValue="20"
    app:numberPickerPreference_minValue="10"
    app:numberPickerPreference_maxValue="60"
    app:numberPickerPreference_stepValue="2"
    app:numberPickerPreference_unitText=" another quantity" />
```

## Screenshots

**Please click the image below to enlarge.**

<img src="https://raw.githubusercontent.com/invissvenska/NumberPickerPreference/master/media/collage.png">
