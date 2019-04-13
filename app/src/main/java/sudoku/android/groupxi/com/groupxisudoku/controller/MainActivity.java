package sudoku.android.groupxi.com.groupxisudoku.controller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.app.Dialog;


import sudoku.android.groupxi.com.groupxisudoku.R;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private int language = 0;
    private int size = 4;
    private int difficulty = 0;
    private boolean isListening = false;
    Dialog selectionWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Pop up window for Info
        selectionWindow = new Dialog(this);
        Button info = findViewById(R.id.infoButton);
        info.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PopupInfo.class));
            }
        });


        Button uploadButton = findViewById(R.id.Uploadbutton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStoragePermissionGranted()){
                    startActivity(new Intent(MainActivity.this, FileAndDirectoryActivity.class));
                }
            }
        });


    }

    // play
    public void playButton(View view) {
        final Button sizeButton;
        final Button languageButton;
        final Button difficultyButton;
        final Button listeningButton;
        Button cancelButton;
        Button startButton;
        final Intent intent = new Intent(this, GameActivity.class);

        selectionWindow.setContentView(R.layout.selection_window);

        sizeButton = selectionWindow.findViewById(R.id.size_button);
        sizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(size == 4){
                    sizeButton.setText("6x6");
                    size = 6;
                }else if(size == 6){
                    sizeButton.setText("9x9");
                    size = 9;
                }else if (size == 9){

                    if(isTablet(MainActivity.this)){
                        sizeButton.setText("12x12");
                        size = 12;
                    }else{
                        sizeButton.setText("4x4");
                        size = 4;
                    }

                }else{
                    sizeButton.setText("4x4");
                    size = 4;
                }
            }
        });

        languageButton = selectionWindow.findViewById(R.id.language_button);
        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(language == 0){
                    language = 1;
                    languageButton.setText("Chinese");
                }else{
                    language = 0;
                    languageButton.setText("English");
                }
            }
        });

        difficultyButton = selectionWindow.findViewById(R.id.difficulty_button);
        difficultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(difficulty == 0){
                    difficulty = 1;
                    difficultyButton.setText("medium");
                }else if(difficulty == 1){
                    difficulty = 2;
                    difficultyButton.setText("hard");
                }else {
                    difficulty = 0;
                    difficultyButton.setText("easy");
                }
            }
        });

        listeningButton = selectionWindow.findViewById(R.id.listening_button);
        listeningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isListening == false){
                    isListening = true;
                    listeningButton.setText("Listening Mode: On");
                }else {
                    isListening = false;
                    listeningButton.setText("Listening Mode: Off");
                }
            }
        });

        cancelButton = selectionWindow.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionWindow.dismiss();
            }
        });

        startButton = selectionWindow.findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionWindow.dismiss();
                intent.putExtra("size", size);
                intent.putExtra("language", language);
                intent.putExtra("source", 1);
                intent.putExtra("listening", isListening);
                startActivity(intent);

            }
        });
        selectionWindow.show();

    }

    // settings
    public void settingsButton(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Permission","Permission is granted");
                return true;
            } else {

                Log.v("Permission","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Permission","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v("Permission","Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission

        }
    }


}
