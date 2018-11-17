package net.skhu.feederpetedit;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;



public class PetInfoActivity1 extends AppCompatActivity {
    static int petType = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info1);
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");

        final Button nextButton = (Button)findViewById(R.id.nextButton);
        final ImageButton dogButton = (ImageButton)findViewById(R.id.dogButton);
        final ImageButton catButton = (ImageButton)findViewById(R.id.catButton);


        dogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                petType = 2;
                dogButton.setBackgroundColor(getResources().getColor(R.color.colorPetInfoButton));
            }
        });

        catButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                petType = 1;
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(PetInfoActivity1.this, PetInfoActivity2.class);
                nextIntent.putExtra("userID", userID);
                nextIntent.putExtra("petType", petType);
                startActivity(nextIntent);
            }
        });
    }
}
