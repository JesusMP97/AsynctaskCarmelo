package com.example.asynctaskcarmelo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button bAsynctask, bThread;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        initEvents();
    }

    private void lanzaThread(){
        Toast.makeText(MainActivity.this, "Lanzando thread", Toast.LENGTH_SHORT).show();

        new Thread(new Runnable() {
            int progreso = 0;

            @Override
            public void run() {
                while(progreso < 1000){
                    progreso++;
                    progressBar.setProgress(progreso/10);
                    SystemClock.sleep(20);
                }
                lanzarActividad();
            }
        }).start();

    }

    private void lanzarActividad() {
        Intent i = new Intent(this, Main2Activity.class);
        startActivity(i);
    }

    private void initComponents() {
        bAsynctask = findViewById(R.id.btAsync);
        bThread = findViewById(R.id.btThread);
        progressBar = findViewById(R.id.progressBar);
    }

    private void initEvents() {
        bAsynctask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ContadorTask().execute();
            }
        });

        bThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzaThread();
            }
        });
    }

    private class ContadorTask extends AsyncTask<Void, Integer, Void>{

        int progreso;

        @Override
        protected Void doInBackground(Void... voids) {

            while(progreso < 1000){
                progreso++;
                publishProgress(progreso);
                SystemClock.sleep(20);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Lanzando asynctask", Toast.LENGTH_SHORT).show();
            progreso = 0;
            bAsynctask.setClickable(false);
            bThread.setClickable(false);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]/10);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            bAsynctask.setClickable(true);
            bThread.setClickable(true);
            Toast.makeText(MainActivity.this, "Asynctask terminado", Toast.LENGTH_SHORT).show();
            lanzarActividad();
        }
    }

}
