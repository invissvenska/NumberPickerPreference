package nl.invissvenska.numberpickerpreference.sample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import nl.invissvenska.numberpickerpreference.NumberDialogPreference;
import nl.invissvenska.numberpickerpreference.NumberPickerPreferenceDialogFragment;

public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String DIALOG_FRAGMENT_TAG = "CustomPreferenceDialog";

    public SettingsFragment() {
        //keep empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

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
