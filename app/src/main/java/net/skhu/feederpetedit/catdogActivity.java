package net.skhu.feederpetedit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class catdogActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final int petType = intent.getIntExtra("petType", 0);
        final int age = intent.getIntExtra("age", 0);
        final double weight = intent.getDoubleExtra("weight", 0);
        final boolean nursing = intent.getBooleanExtra("nursing", false);
        final boolean pregnant = intent.getBooleanExtra("pregnant", false);
        final boolean obesity = intent.getBooleanExtra("obesity", false);

        if (petType == 1 && age <= 1) {
            //  MER_Cat_baby(age, weight);


        }
        //새끼고양이 MER

        else if (petType == 2 && age <= 1) {
            MER_Dog_baby(age, weight);

        }
        //새끼강아지 MER

        if (petType == 1 && (age > 1 && age <= 10)) {
            MER_Cat_adult(age, weight);

        }
        //성묘 MER

        else if (petType == 2 && (age > 1 && age <= 9)) {
            MER_Dog_adult(age, weight);

        }
        //성견 MER

        if (petType == 1 && (age > 10 && age <= 15)) {
            MER_Cat_old(age, weight);

        }
        //노령 고양이 MER

        else if (petType == 2 && (age > 9 && age <= 15)) {
            MER_Dog_old(age, weight);

        }
        //노령 강아지 MER

        if (petType == 1 && nursing == true) {
            MER_Cat_nursing(nursing, weight);
        }
        //수유중인 고양이 MER
        if (petType == 2 && nursing == true) {
            MER_Dog_nursing(nursing, weight);
        }
        //수유중인 강아지 MER

        if (petType == 1 && pregnant == true) {
            MER_Cat_pregnant(pregnant, weight);
        }
        //임신중인 고양이 MER

        if (petType == 1 && obesity == true) {
            MER_Cat_obesity(obesity, weight);
        }
        //비만인 고양이 MER
        if (petType == 2 && obesity == true) {
            MER_Dog_obesity(obesity, weight);
        }
        //비만인 강아지 MER

    }
}