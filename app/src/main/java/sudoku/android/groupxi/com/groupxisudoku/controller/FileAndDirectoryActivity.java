package sudoku.android.groupxi.com.groupxisudoku.controller;

import android.Manifest;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import sudoku.android.groupxi.com.groupxisudoku.R;

public class FileAndDirectoryActivity extends ListActivity {

    ListView listview ;
    private List<String> fileList = new ArrayList<String>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_directory);

        listview = findViewById(android.R.id.list) ;
        File root_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        ListDir(root_path);


    }
    @Override
    protected void onListItemClick(ListView l , View v , int position , long id)
    {
        String value = (String) l.getItemAtPosition(position);
        ArrayList<LanguageSample> word_list = readLanguageData(value);

        //Log.d("gy", "onListItemClick: 1");
        Bundle bundle = new Bundle();
        bundle.putSerializable("word_list", word_list);
        bundle.putInt("source", 2);
        Intent intent = new Intent(FileAndDirectoryActivity.this, GameActivity.class);
        intent.putExtras(bundle);

        //Log.d("gy", "onListItemClick: 2");

        startActivity(intent);

    }
    void ListDir(File f) {
        File[] files = f.listFiles();
        if (files != null ){
            for(File file : files) {
                fileList.add(file.getPath());
            }
        }



        ArrayAdapter<String> directoryList = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, fileList);
        listview.setAdapter(directoryList);

    }

    private ArrayList<LanguageSample> readLanguageData(String Path) {
        ArrayList<LanguageSample> LanguageSamples = new ArrayList<>();

        File file_main = new File(Path);
        InputStream is = null;
        try {
            is = new FileInputStream(file_main);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                //Split by ','
                String[] tokens = line.split(",");
                //Reading the data
                LanguageSample sample = new LanguageSample();
                sample.setLang_a(tokens[0]);
                sample.setLang_b(tokens[1]);
                LanguageSamples.add(sample);

                Log.d("MyActivity" , "Added: " + sample);

            }
        } catch (IOException e) {
            Log.e("MyActivity" , "Error reading data file on line" + line, e);
            e.printStackTrace();
        }
        return LanguageSamples;
    }




}
