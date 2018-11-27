package net.skhu.feederpetedit;

import java.util.Calendar;
import java.util.GregorianCalendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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

    int mYear, mMonth, mDay, mHour, mMinute;
    String amount;
    TextView mTxtDate;
    TextView mTxtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        Intent intent = getIntent();
        final int feedGram = intent.getIntExtra("feedGram", 0);
        final String feedDate = intent.getStringExtra("feedDate");
        final String feedTime = intent.getStringExtra("feedTime");

        //텍스트뷰 2개 연결
        mTxtDate = (TextView) findViewById(R.id.date);
        mTxtTime = (TextView) findViewById(R.id.time);

        //현재 날짜와 시간을 가져오기위한 Calendar 인스턴스 선언

        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);

        UpdateNow();//화면에 텍스트뷰에 업데이트 해줌.

        final TextView feedReservationText = (TextView) findViewById(R.id.feedReservationText);
        final TextView timeTextView = (TextView) findViewById(R.id.timeTextView);
        final TextView date = (TextView) findViewById(R.id.date);
        final TextView time = (TextView) findViewById(R.id.time);
        final TextView gramTextView = (TextView) findViewById(R.id.gramTextView);
        final TextView amountText = (TextView) findViewById(R.id.amountText);
        final TextView gView = (TextView) findViewById(R.id.gView);
        final EditText amountEditText = ( EditText) findViewById(R.id.amountEditText);
        final Button btnchangedate = (Button) findViewById(R.id.btnchangedate);
        final Button btnchangetime = (Button) findViewById(R.id.btnchangetime);
        final Button giveButton = (Button) findViewById(R.id.giveButton);
        final Button reservButton = (Button) findViewById(R.id.reservButton);

        giveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                int feedGram = jsonResponse.getInt("feedGram");
                              //  AlertDialog.Builder builder = new AlertDialog.Builder(ReservationActivity.this);
                             //   builder.setMessage(amount + "g을 주시겠습니까?")
                            //        .setPositiveButton("확인", null)
                           //                  .create()
                          //                   .show();

                                AlertDialog.Builder builder = new AlertDialog.Builder(ReservationActivity.this);
                                builder.setTitle("알림");
                                builder.setMessage("예약을 하시겠습니까?");
                                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int index) {
                                        Toast.makeText(ReservationActivity.this, "예약을 하였습니다", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder.setNegativeButton("아니오", null);
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ReservationActivity.this);
                                builder.setTitle("알림");
                                builder.setMessage("예약에 실패했습니다");
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };
                ReservRequest ReserveRequest = new ReservRequest(feedGram, feedDate, feedTime, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ReservationActivity.this);
                queue.add(ReserveRequest);

            }
        });

        reservButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ReservationActivity.this);
                                builder.setTitle("알림");
                                builder.setMessage("예약을 하시겠습니까?");
                                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int index) {
                                        Toast.makeText(ReservationActivity.this, "예약을 하였습니다", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder.setNegativeButton("아니오", null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ReservationActivity.this);
                                builder.setTitle("알림");
                                builder.setMessage("예약에 실패했습니다");
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                        }
                    };
                ReservRequest ReserveRequest = new ReservRequest(feedGram, feedDate, feedTime, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ReservationActivity.this);
                queue.add(ReserveRequest);
                };

            });
        }


    public void mOnClick(View v) {
        switch (v.getId()) {
            //날짜 대화상자 버튼이 눌리면 대화상자를 보여줌
            case R.id.btnchangedate:
                //여기서 리스너도 등록함
                new DatePickerDialog(ReservationActivity.this, mDateSetListener, mYear,
                        mMonth, mDay).show();

                break;

            //시간 대화상자 버튼이 눌리면 대화상자를 보여줌
            case R.id.btnchangetime:
                //여기서 리스너도 등록함
                new TimePickerDialog(ReservationActivity.this, mTimeSetListener, mHour,
                        mMinute, false).show();

                break;

        }

    }

    //날짜 대화상자 리스너 부분
    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //사용자가 입력한 값을 가져온뒤
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            //텍스트뷰의 값을 업데이트함

            UpdateNow();

        }
    };

    //시간 대화상자 리스너 부분
    TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //사용자가 입력한 값을 가져온뒤
            mHour = hourOfDay;
            mMinute = minute;
            //텍스트뷰의 값을 업데이트함

            UpdateNow();

        }

    };

    //텍스트뷰의 값을 업데이트 하는 메소드

    void UpdateNow() {
        mTxtDate.setText(String.format("%d/%d/%d", mYear, mMonth + 1, mDay));
        mTxtTime.setText(String.format("%d:%d", mHour, mMinute));

    }


}
