package net.skhu.feederpetedit;

import java.util.Calendar;
import java.util.GregorianCalendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Button;
import android.content.DialogInterface;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ReservationActivity extends AppCompatActivity {
    private User user;
    int mYear, mMonth, mDay, mHour, mMinute;
    String amount;
    TextView tv;
    TextView mTxtTime;
    TimePicker tp;
    int h;
    int m;
    String s;
    int resAmount = 0;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        Intent intent = getIntent();

        user = (User) intent.getSerializableExtra("user");
        //텍스트뷰 2개 연결

        //String userID = user.getUserID();
        //final String resAmount


        final String hour;
        final int min;
        Button resBt = (Button) findViewById(R.id.reservButton);
        tp = (TimePicker) findViewById(R.id.timePicker);
        h = tp.getCurrentHour();
        m = tp.getCurrentMinute();

        if (h < 12) s = "AM";
        else s = "PM";


        resBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
                str = amountEditText.getText().toString();
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
                resAmount = Integer.parseInt(str);

                h = tp.getCurrentHour();
                m = tp.getCurrentMinute();
                if (h < 12) s = "AM";
                else s = "PM";
                final Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ReservationActivity.this);
                                builder.setMessage(s + " " + h + "시 " + m + "분에" + "알람 등록에 성공했습니다.").setPositiveButton("확인", null).create().show();
                                Intent intent = new Intent(ReservationActivity.this, ReservationListActivity.class);
                                ReservationActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ReservationActivity.this);
                                builder.setMessage("알람 등록에 실패했습니다.").setNegativeButton("다시 시도", null).create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                };

                ReservRequest reservRequest = new ReservRequest("1", h, m, resAmount, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ReservationActivity.this);
                queue.add(reservRequest);
            }
        });


    }
}