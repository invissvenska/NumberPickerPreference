package nl.invissvenska.numberpickerpreference;

import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NumberPickerPreferenceDialogFragment extends CustomPreferenceDialogFragmentCompat {
    private static final String ARG_MIN_VALUE = "min_value";
    private static final String ARG_MAX_VALUE = "max_value";
    private static final String ARG_STEP_VALUE = "step_value";
    private static final String ARG_UNIT_TEXT = "unit_text";
    private static final String SAVE_STATE_TIME = "save_state_time";

    protected NumberPicker np;

    private int minValue;
    private int stepValue;

    public static NumberPickerPreferenceDialogFragment newInstance(
            @NonNull final String key,
            final Integer minValue,
            final Integer maxValue,
            final Integer stepValue,
            @Nullable final String unitText
    ) {
        final NumberPickerPreferenceDialogFragment fragment = new NumberPickerPreferenceDialogFragment();
        final Bundle b = new Bundle(4);
        b.putString(ARG_KEY, key);
        b.putInt(ARG_MIN_VALUE, minValue);
        b.putInt(ARG_MAX_VALUE, maxValue);
        b.putInt(ARG_STEP_VALUE, stepValue);
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

        minValue = getArguments().getInt(ARG_MIN_VALUE, 0);
        final int maxValue = getArguments().getInt(ARG_MAX_VALUE, 100);
        stepValue = getArguments().getInt(ARG_STEP_VALUE, 1);

        final String[] values = getDisplayValues(minValue, maxValue, stepValue);

        np = new NumberPicker(getActivity());
        np.setMinValue(0);
        np.setMaxValue(values.length - 1);
        np.setDisplayedValues(values);
        np.setValue(getSelectedValue(values, text));
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    }

    private Integer getSelectedValue(String[] values, Integer serializedValue) {
        for (int i = 0; i < values.length; i++) {
            if (serializedValue.equals(Integer.valueOf(values[i]))) {
                return i;
            }
        }
        return 0;
    }

    private String[] getDisplayValues(int min, int max, int step) {
        int length = (max - min) / step + 1;
        String[] arrayValues = new String[length];
        for (int i = 0; i < length; i++) {
            arrayValues[i] = String.valueOf(min + (i * step));
        }
        return arrayValues;
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            Integer value;
            value = (minValue + (np.getValue() * stepValue));
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
