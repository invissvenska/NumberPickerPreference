package nl.invissvenska.numberpickerpreference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.preference.DialogPreference;


public class NumberDialogPreference extends DialogPreference {

    private static final Integer DEFAULT_VALUE = 0;
    private Integer minValue = 0;
    private Integer maxValue = 100;
    private String unitText = "";
    private Integer value;

    public NumberDialogPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.numberPickerPreference, 0, 0);
        minValue = ta.getInt(R.styleable.numberPickerPreference_numberPickerPreference_minValue, minValue);
        maxValue = ta.getInt(R.styleable.numberPickerPreference_numberPickerPreference_maxValue, maxValue);
        if (ta.getString(R.styleable.numberPickerPreference_numberPickerPreference_unitText) != null) {
            unitText = ta.getString(R.styleable.numberPickerPreference_numberPickerPreference_unitText);
        }
        ta.recycle();
    }

    public NumberDialogPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public NumberDialogPreference(Context context, AttributeSet attrs) {
        this(context, attrs, androidx.preference.R.attr.dialogPreferenceStyle);
    }

    public NumberDialogPreference(Context context) {
        this(context, null);
    }

    public Integer getValue() {
        if (value == null) {
            return DEFAULT_VALUE;
        }
        return value;
    }

    public static Integer getDefaultValue() {
        return DEFAULT_VALUE;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public String getUnitText() {
        return unitText;
    }

    @Override
    public CharSequence getSummary() {
        return getValue() + getUnitText();
    }

    public Integer getSerializedValue() {
        return getValue();
    }

    public void setSerializedValue(@NonNull final Integer serializedInteger) {
        value = serializedInteger;
        final boolean wasBlocking = shouldDisableDependents();
        persistInt(serializedInteger);
        final boolean isBlocking = shouldDisableDependents();
        if (isBlocking != wasBlocking) {
            notifyDependencyChange(isBlocking);
        }
        setSummary(getSummary());
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }

    @Override
    protected void onSetInitialValue(Object defaultValue) {
        if (defaultValue == null) {
            setSerializedValue(getPersistedInt(DEFAULT_VALUE));
        } else if (defaultValue instanceof String) {
            setSerializedValue(Integer.valueOf((String)defaultValue));
        } else {
            setSerializedValue((Integer) defaultValue);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        if (isPersistent()) {
            // No need to save instance state since it's persistent
            return superState;
        }

        final SavedState myState = new SavedState(superState);
        myState.text = String.valueOf(getValue());
        return myState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state == null || !state.getClass().equals(SavedState.class)) {
            // Didn't save state for us in onSaveInstanceState
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());
        setSerializedValue(Integer.valueOf(myState.text));
    }

}
