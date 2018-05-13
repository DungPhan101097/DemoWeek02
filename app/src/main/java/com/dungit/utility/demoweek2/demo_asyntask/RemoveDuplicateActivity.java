package com.dungit.utility.demoweek2.demo_asyntask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dungit.utility.demoweek2.R;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by DUNGIT on 5/13/2018.
 */

public class RemoveDuplicateActivity extends AppCompatActivity {

    private TextView tvDisplayResult;
    private TextView tvDisplayTimeFinished;
    private Button btnRun;
    private ProgressBar pbProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_duplicate);

        tvDisplayResult = findViewById(R.id.tv_display_result);
        tvDisplayTimeFinished = findViewById(R.id.tv_display_time);
        btnRun = findViewById(R.id.btn_run);
        pbProgress = findViewById(R.id.pb_progress);

        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RemoveDuplicateAsyncTask().execute("input.in");
            }
        });
    }

    private class RemoveDuplicateAsyncTask extends AsyncTask<String, Integer, Float>{

        @Override
        protected void onPreExecute() {
            pbProgress.setVisibility(View.VISIBLE);
            pbProgress.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Float aFloat) {
            pbProgress.setVisibility(View.INVISIBLE);
            tvDisplayTimeFinished.setText(String.format("\nFinished in : %fs", aFloat));
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
           pbProgress.setMax(values[0]);
           pbProgress.setProgress(values[1]);
           tvDisplayResult.append(String.format("\nFinished line %d\nThis line has %d diffirent numbers\n",
                   values[1], values[2]));
        }


        @Override
        protected Float doInBackground(String... strings) {
           String fileName = strings[0];
           long start = System.currentTimeMillis();
            InputStream inputStream = null;
            try {
                inputStream = RemoveDuplicateActivity.this.getAssets().open(fileName, Context.MODE_PRIVATE);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                Set<Integer> mySet = new TreeSet<>();
                line = reader.readLine();
                int numLine = Integer.parseInt(line);

                for(int i = 0; i< numLine; i++){
                    line = reader.readLine();
                    mySet.clear();
                    String[] tokens = line.split(" ");
                    for(String elem : tokens){
                        mySet.add(Integer.parseInt(elem));
                    }

                    publishProgress(numLine, i + 1, mySet.size());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            long end = System.currentTimeMillis();

            float secondFinish = ((float) (end - start)) / 1000;

            return secondFinish;
        }
    }
}
