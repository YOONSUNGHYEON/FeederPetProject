package net.skhu.feederpetedit;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;


public class FeederActivity extends AppCompatActivity {
   // private BluetoothSPP bt;
    private ListView listView;
    private FeedRecordListAdapter adapter;
    private List<FeedRecord> feedRecordList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeder);
        Intent intent = getIntent();

        final EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        Button giveButton = (Button) findViewById(R.id.giveButton);

        listView = (ListView) findViewById(R.id.listView);
        feedRecordList = new ArrayList<FeedRecord>();
        adapter = new FeedRecordListAdapter(getApplicationContext(), feedRecordList);
        listView.setAdapter(adapter);

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("feedRecordList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String userID, feedID, feedAmount, feedRegdate;
            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                feedID = object.getString("feedID");
                userID = object.getString("userID");
                feedAmount = object.getString("feedAmount");
                feedRegdate = object.getString("feedRegdate");

                FeedRecord feedRecord = new FeedRecord(feedID, userID, feedAmount, feedRegdate);
                feedRecordList.add(feedRecord);
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        giveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = amountEditText.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(FeederActivity.this);
                builder.setMessage(amount + "g을 주시겠습니까?")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
               //new BackgroundTask().execute(); // 확인버튼에 모터연결하려고 넣어봄
               // bt.send("on", true);
            }

        });

        class BackgroundTask extends AsyncTask<Void, Void, String> {
            String target;

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(target);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String temp;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((temp = bufferedReader.readLine()) != null) {
                        stringBuilder.append(temp + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

        }
    }
}
