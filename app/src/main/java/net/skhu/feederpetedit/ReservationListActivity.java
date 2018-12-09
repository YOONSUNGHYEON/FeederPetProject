package net.skhu.feederpetedit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ReservationListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);

        final ImageButton reservationRegisterButton = (ImageButton)findViewById(R.id.reservationRegisterButton);

        reservationRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReservationListActivity.this, ReservationActivity.class);
                ReservationListActivity.this.startActivity(intent);
            }
        });
    }
}
