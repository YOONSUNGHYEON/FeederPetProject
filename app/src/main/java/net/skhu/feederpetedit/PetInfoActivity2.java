package net.skhu.feederpetedit;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PetInfoActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info2);

        Intent intent = getIntent();
        final int petType = intent.getIntExtra("petType", 0);
        final String userID = intent.getStringExtra("userID");

        final EditText petNameEditText = (EditText)findViewById(R.id.petNameEditText);
        final Button nextButton = (Button)findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(petNameEditText.getText())){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(PetInfoActivity2.this);
                    builder1.setMessage("이름을 입력해주세요.")
                            .setNegativeButton("다시입력",null)
                            .create()
                            .show();
                }
                else
                {
                    final String petName = petNameEditText.getText().toString();


                    final Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    Intent intent = new Intent(PetInfoActivity2.this, MainActivity.class);
                                    PetInfoActivity2.this.startActivity(intent);
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(PetInfoActivity2.this);
                                    builder.setMessage("저장을 실패했습니다.")
                                            .setNegativeButton("다시 시도", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    };

                    PetInfoRequest petInfoRequest = new PetInfoRequest(petType, petName, userID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(PetInfoActivity2.this);
                    queue.add(petInfoRequest);
                }
            }
        });
    }
}
