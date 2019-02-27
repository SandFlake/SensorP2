package com.mau.dalvi.weatherchannel;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class GuessFragment extends Fragment {

    private TextView tvGuess;
    private EditText etGuess;
    private Button btnGuess;
    private Controller controller;
    public static String userGuess;


    public GuessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guess, container, false);
        initializeComponents(view);
        return view;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
    private void initializeComponents(View view) {
        tvGuess = view.findViewById(R.id.tvGuess);
        etGuess = view.findViewById(R.id.etGuess);
        btnGuess = view.findViewById(R.id.btnGuess);
        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etGuess.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Go on, guess!", Toast.LENGTH_LONG).show();
                }
                else {
                    userGuess = etGuess.getText().toString();
                    controller.btnAltitudeClicked(userGuess);
                    Log.d("guess of mine", "" + userGuess);
                }

                }
        }
        );
    }

    public static String getGuess(){
        return userGuess;
    }



    }




