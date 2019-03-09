package sudoku.android.groupxi.com.groupxisudoku.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import sudoku.android.groupxi.com.groupxisudoku.PopupInfo;
import sudoku.android.groupxi.com.groupxisudoku.R;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private int language = 0;

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
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("language", language);
        startActivity(intent);
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
