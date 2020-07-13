package nl.invissvenska.numberpickerpreference.sample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainFragment extends Fragment {

    private SharedPreferences preferences;
    private TextView demo1;
    private TextView demo2;
    private TextView demo3;
    private TextView demo4;
    private TextView demo5;
    private TextView demo6;

    public MainFragment() {
        //keep default constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);

        FloatingActionButton fb = view.findViewById(R.id.fab);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, new SettingsFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        demo1 = view.findViewById(R.id.demo1);
        demo2 = view.findViewById(R.id.demo2);
        demo3 = view.findViewById(R.id.demo3);
        demo4 = view.findViewById(R.id.demo4);
        demo5 = view.findViewById(R.id.demo5);
        demo6 = view.findViewById(R.id.demo6);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        demo1.setText("Demo_1 value is: " + preferences.getInt("demo_1", 20));
        demo2.setText("Demo_2 value is: " + preferences.getInt("demo_2", 0));
        demo3.setText("Demo_3 value is: " + preferences.getInt("demo_3", 0));
        demo4.setText("Demo_4 value is: " + preferences.getInt("demo_4", 20));
        demo5.setText("Demo_5 value is: " + preferences.getInt("demo_5", 0));
        demo6.setText("Demo_6 value is: " + preferences.getInt("demo_6", 0));
    }
}
