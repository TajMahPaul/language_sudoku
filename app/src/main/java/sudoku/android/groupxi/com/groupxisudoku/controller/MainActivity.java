package sudoku.android.groupxi.com.groupxisudoku.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.Dialog;

import sudoku.android.groupxi.com.groupxisudoku.R;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private int language = 0;
    private int size = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Pop up window for Info
        Button info = (Button) findViewById(R.id.infoButton);
        info.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PopupInfo.class));
            }
        });

        final Button languageButton = findViewById(R.id.languageButton);
        languageButton.setText("English");
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
    }

    // play
    public void playButton(View view) {
        final Intent intent = new Intent(this, GameActivity.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        //builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("size of grids");
        final String[] choices = {"4x4", "6x6", "9x9", "12x12"};

        builder.setSingleChoiceItems(choices, 2, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

                if(which == 0){
                    size = 4;
                }else if(which == 1){
                    size = 6;
                }else if(which == 2){
                    size = 9;
                }else{
                    size = 12;
                }
            }
        });
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

                intent.putExtra("size", size);
                intent.putExtra("language", language);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.show();
//        Intent intent = new Intent(this, GameActivity.class);
//        intent.putExtra("language", language);
//        startActivity(intent);
    }

    public void resumeButton(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("resume_game", true);
        startActivity(intent);
    }

    // settings
    public void settingsButton(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}
