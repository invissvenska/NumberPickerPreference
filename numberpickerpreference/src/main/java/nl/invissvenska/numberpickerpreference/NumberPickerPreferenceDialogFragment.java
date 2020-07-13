package nl.invissvenska.numberpickerpreference;

import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NumberPickerPreferenceDialogFragment extends CustomPreferenceDialogFragmentCompat {
    private static final String ARG_MIN_VALUE = "min_value";
    private static final String ARG_MAX_VALUE = "max_value";
    private static final String ARG_UNIT_TEXT = "unit_text";
    private static final String SAVE_STATE_TIME = "save_state_time";

    protected NumberPicker np;

    public static NumberPickerPreferenceDialogFragment newInstance(
            @NonNull final String key,
            final Integer minValue,
            final Integer maxValue,
            @Nullable final String unitText
    ) {
        final NumberPickerPreferenceDialogFragment fragment = new NumberPickerPreferenceDialogFragment();
        final Bundle b = new Bundle(4);
        b.putString(ARG_KEY, key);
        b.putInt(ARG_MIN_VALUE, minValue);
        b.putInt(ARG_MAX_VALUE, maxValue);
        b.putString(ARG_UNIT_TEXT, unitText);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Integer text;
        if (savedInstanceState == null) {
            text = getNumberPickerDialogPreference().getSerializedValue();
        } else {
            text = savedInstanceState.getInt(SAVE_STATE_TIME);
        }

        final int minValue = getArguments().getInt(ARG_MIN_VALUE, 0);
        final int maxValue = getArguments().getInt(ARG_MAX_VALUE, 100);

        np = new NumberPicker(getActivity());
        np.setMinValue(minValue);
        np.setMaxValue(maxValue);
        np.setValue(text);
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            Integer value;

            value = np.getValue();
            if (getNumberPickerDialogPreference().callChangeListener(value)) {
                getNumberPickerDialogPreference().setSerializedValue(value);
            }
        }
    }

    @Override
    View getPickerView() {
        return np;
    }

    private NumberDialogPreference getNumberPickerDialogPreference() {
        return (NumberDialogPreference) getPreference();
    }
}
