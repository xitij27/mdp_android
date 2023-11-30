package com.example.mdpgrp36;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mdpgrp36.Arena.ArenaMap;
import com.example.mdpgrp36.Bluetooth.BTConnectionPage;
import com.example.mdpgrp36.Tab.PageViewModel;



public class MapSettingsFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String TAG = "Map Settings Fragment";
    private static boolean isSetStart = false;

    private PageViewModel pageViewModel;

    Button btnStartPt, btnAddObstacle, btnResetMap, btnChangeObsFace, btnStartFastestCar, btButton;
    ArenaMap arenaMap;
    EditText inputX, inputY, inputDir;
    RadioGroup rgobs;
    RadioButton oNone,o1,o2,o3,o4,o5,o6,o7,o8;
    int temp = -1;


    public static Fragment newInstance(int index) {
        MapSettingsFragment fragment = new MapSettingsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        SharedPreferences sharedPreferences;
        View root = inflater.inflate(R.layout.activity_settings, container, false);

        arenaMap = MainActivity.getArenaMap();
        btnAddObstacle = root.findViewById(R.id.btnAddObstacles);
        btnResetMap = root.findViewById(R.id.btnResetMap);
        btnStartPt = root.findViewById(R.id.btnStartPoint);
        btButton = root.findViewById(R.id.bluetoothButton);
        btnChangeObsFace = root.findViewById(R.id.btnChangeObsFace);
        btnStartFastestCar = root.findViewById(R.id.btnStartFastestCar);
        rgobs = root.findViewById(R.id.rgobs);
        oNone = root.findViewById(R.id.rbnone);
        o1 = root.findViewById(R.id.rb1);
        o2 = root.findViewById(R.id.rb2);
        o3 = root.findViewById(R.id.rb3);
        o4 = root.findViewById(R.id.rb4);
        o5 = root.findViewById(R.id.rb5);
        o6 = root.findViewById(R.id.rb6);
        o7 = root.findViewById(R.id.rb7);
        o8 = root.findViewById(R.id.rb8);
        /*inputX = root.findViewById(R.id.inputX);
        inputY = root.findViewById(R.id.inputY);
        inputDir = root.findViewById(R.id.inputDir);*/

        btnResetMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d(TAG,"btnResetMap: Reset Map");
                Toast.makeText(getContext(),"Resetting Map!",Toast.LENGTH_SHORT).show();
                arenaMap.resetArena();
                isSetStart = false;
                rgobs.clearCheck();
                //ControllerFragment.counter=0;
            }
        });

        btnStartPt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!isSetStart) {
                    Log.d(TAG, "btnStartPt: Set starting point");
                    arenaMap.setStartingPoint(true);
                    Log.d(TAG, "btnStartPt: Starting point set to TRUE.");
                    isSetStart = true;
//                MainActivity.printMessage("START");
                }
            }
        });

        btnAddObstacle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d(TAG,"btnAddObstacle: Add obstacle");
                Toast.makeText(getContext(),"Sending obstacles!",Toast.LENGTH_SHORT).show();
                MainActivity.printMessage(arenaMap.getObstacles());
            }
        });

        //To bluetooth page
        btButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getActivity(), BTConnectionPage.class);
                //((MainActivity) getActivity()).startActivity(intent);
                startActivity(intent);
            }
        });

        btnChangeObsFace.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG,"btnChangeObsFace: Setting obstacle face!");
                if (o1.isChecked()) {
                    temp = 0;
                } else if (o2.isChecked()) {
                    temp = 1;
                } else if (o3.isChecked()) {
                    temp = 2;
                } else if (o4.isChecked()) {
                    temp = 3;
                } else if (o5.isChecked()) {
                    temp = 4;
                } else if (o6.isChecked()) {
                    temp = 5;
                } else if (o7.isChecked()) {
                    temp = 6;
                }else if (o8.isChecked()) {
                    temp = 7;
                }else if (oNone.isChecked()){
                    temp = -1;
                    rgobs.clearCheck();
                }
                if(temp != -1){
                arenaMap.enableObsEdit(temp);
                arenaMap.setObstacleFace();
                arenaMap.disableObsEdit(temp);
                }

            }
        });

        btnStartFastestCar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG,"btnStartFastestCar: Start!");
                arenaMap.setObstacleFace();

                MainActivity.printMessage("START");
            }
        });

        /*sendObsdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xstr = inputX.getText().toString();
                String ystr = inputY.getText().toString();
                String dirstr = inputDir.getText().toString();
                if(xstr != null && !"".equals(xstr) && ystr != null && !"".equals(ystr) && dirstr != null && !"".equals(dirstr))
                {
                    int xval = Integer.parseInt(xstr);
                    int yval = Integer.parseInt(ystr);

                }
            }
        });*/

        return root;
    }

}
