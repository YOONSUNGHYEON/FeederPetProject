package net.skhu.feederpetedit;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent  = getIntent(); //인텐트값 받기
        TextView petinfoText = (TextView)findViewById(R.id.petinfoText);
        final ImageButton feederButton = (ImageButton)findViewById(R.id.feederButton);
        final ImageButton cameraButton = (ImageButton)findViewById(R.id.cameraButton);
        final ImageButton reservButton = (ImageButton)findViewById(R.id.reservButton);

        feederButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();

            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        reservButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReservationActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });



    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url= new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected  void onPreExecute(){
            target = "http://zc753951.cafe24.com/FeedRecordList.php";
        }



        @Override
        public  void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result){
            Intent intent = new Intent(MainActivity.this, FeederActivity.class);
            intent.putExtra("feedRecordList", result);
            MainActivity.this.startActivity(intent);
        }
    }
}
