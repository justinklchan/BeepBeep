package com.example.beepbeep;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.Manifest;
import android.view.View;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int grantResults[];
    TextView tv;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO},1);
//        onRequestPermissionsResult(1,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO},grantResults);
        Constants.startButton = (Button)findViewById(R.id.button);
//        Constants.stopButton = (Button)findViewById(R.id.button);
        this.tv = (TextView)findViewById(R.id.textView);
        this.et = (EditText)findViewById(R.id.editTextNumber);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Your code here
    }

    Worker task;
    public void onstart(View v) {
        String fname=System.currentTimeMillis()+"";
        tv.setText(fname);
        int delay=Integer.parseInt(et.getText().toString());
        Constants.startButton.setEnabled(false);
        task = new Worker(this,1,5, Constants.fs,fname,delay);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}