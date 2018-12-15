package net.skhu.feederpetedit;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.app.DialogFragment;
import android.widget.Switch;
import android.widget.CompoundButton;

import java.io.Serializable;

public class obesityActivity extends AppCompatActivity {

    final TextView textView = (TextView) findViewById(R.id.textView);    //나이
    final TextView textView2 = (TextView) findViewById(R.id.textView2);   //몸무게
    final TextView textView3 = (TextView) findViewById(R.id.textView3);    //100g당 칼로리
    final EditText editAge = (EditText) findViewById(R.id.editAge);
    final EditText editWeight = (EditText) findViewById(R.id.editWeight);
    final EditText editAmount = (EditText) findViewById(R.id.editAmount);

    final String ageText = editAmount.getText().toString().trim();
    final int age = Integer.parseInt(ageText);      //문자열로 받은 ageText를 int형으로 형변환
    final String weightText = editWeight.getText().toString().trim();
    final double weight = Double.parseDouble(weightText);  //문자열로 받은 weightText를 double형으로 형변환
    final String amountText = editAmount.getText().toString().trim();
    final double amount = Integer.parseInt(amountText);   //문자열로 받은 amountText를 double형으로 형변환

    final Switch sw_nursing = (Switch) findViewById(R.id.switch1);   //수유
    final Switch sw_pregnant = (Switch) findViewById(R.id.switch2);   //임신
    final Switch sw_obesity = (Switch) findViewById(R.id.switch3);   //비만

    final String nursingText = sw_nursing.getText().toString();
    final boolean nursing = Boolean.parseBoolean(nursingText);
    final String pregnantText = sw_pregnant.getText().toString();
    final boolean pregnant = Boolean.parseBoolean(pregnantText);
    final String obesityText = sw_obesity.getText().toString();
    final boolean obesity = Boolean.parseBoolean(obesityText);


    double RER1 = 0;
    double RER2 = 0;
    double RER_more20 = 30 * weight + 70;     //체중이 2kg~20kg
    double RER_under2 = 70 * weight * 0.75;    //체중이 2kg 미만
    ///////////////////휴식 상태에서의 에너지 요구량

    Intent intent = getIntent();     //성현이가 이 부분 없앰
    final int petType = intent.getIntExtra("petType", 0);    //성현이가 이 부분 없앰

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obesity);


        Intent intent = getIntent();
        User obesity = (User) intent.getSerializableExtra("obesity");


        final Button inputButton = (Button) findViewById(R.id.inputButton);
        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kalCal(weight, age, amount);
                AlertDialog.Builder builder = new AlertDialog.Builder(obesityActivity.this);
                builder.setTitle("사료 급여시 적정 식사량");
                builder.setMessage("일일 칼로리(MER)÷라벨에 표기된 100g당" + amount + "*100(g)")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();

            }
        });

        final Button obesityButton = (Button)findViewById(R.id.obesityButton);
        obesityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(obesityActivity.this, obesityPicture.class);
                obesityActivity.this.startActivity(intent);
            }
        });


        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(obesityActivity.this, catdogActivity.class);
                obesityActivity.this.startActivity(intent);
            }
        });

        /*

        CompoundButton.OnCheckedChangeListener listener1 = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent data = new Intent(obesityActivity.this, catdogActivity.class);
                data.putExtra("nursing", switch1.isChecked());
                startActivity(data);


            }

        };
        switch1.setOnCheckedChangeListener(listener1);

        CompoundButton.OnCheckedChangeListener listener2 = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent data = new Intent(obesityActivity.this, catdogActivity.class);
                data.putExtra("pregnant", switch2.isChecked());
                startActivity(data);


            }

        };
        switch2.setOnCheckedChangeListener(listener2);

        CompoundButton.OnCheckedChangeListener listener3 = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent data = new Intent(obesityActivity.this, catdogActivity.class);
                data.putExtra("obesity", switch3.isChecked());
                startActivity(data);


            }

        };
        switch3.setOnCheckedChangeListener(listener3);*/


    }

    public void kalCal(double weight, int age, double amount) {


        double num = 0.75 * 0.75;
        double basic_metabolic1 = 70 * weight * num;    //기초대사량

/*        double neutralizationed_pet = basic_metabolic1 * 1.6;  //중성화
        double no_neutralization_pet = basic_metabolic1 * 1.8;  //중성화x
        double need_diet = basic_metabolic1 * 1;    //체중감량
        double weight_gain = basic_metabolic1 * 1.7;     //체중증량
        double light_activity = basic_metabolic1 * 2;    //활동적음
        double middle_activity = basic_metabolic1 * 3;    //활동중간
        double heavy_activity = basic_metabolic1 * 6;     //활동많음
        double period_of_growth_under4 = basic_metabolic1 * 3;   //성장기 4개월 미만
        double period_of_growth_more4 = basic_metabolic1 * 2;    //성장기 4개월 이상
        ////////////////////////////////////////////////////////////////////////////////////반려견 별 일일유지대사량  오직 개만 해당함
이 부분은 없어도 될거 같다
*/
        double moderate_amount_Cat_baby = MER_Cat_baby(age, weight) * amount * 100;
        double moderate_amount_Dog_baby = MER_Dog_baby(age, weight) * amount * 100;
        double moderate_amount_Cat_adult = MER_Cat_adult(age, weight) * amount * 100;
        double moderate_amount_Dog_adult = MER_Dog_adult(age, weight) * amount * 100;
        double moderate_amount_Cat_old = MER_Cat_old(age, weight) * amount * 100;
        double moderate_amount_Dog_old = MER_Dog_old(age, weight) * amount * 100;

//////////////////////////////////////////////////////////////////////////////////////////////////사료 급여시 적정 식사량


        double baby_dog = 0;   //새끼 강아지
        double baby_cat = 0;   //새끼 고양이




    }

    public void kalCal2(double weight) {

        double pregnant_cat = 0;  //임신한 고양이
        double nursing_dog = 0;
        //수유중인 개
        double nursing_cat = 0;
        //수유중인 고양이
        double obesity_pet = 0;    //비만
        double old_pet = 0;    //노령
        double adult_dog = 0;    //성견
        double adult_cat = 0;   //성묘

        double moderate_amount_Cat_pregnant = MER_Cat_pregnant(pregnant, weight) * amount * 100;
        double moderate_amount_Cat_obesity = MER_Cat_obesity(obesity, weight) * amount * 100;
        double moderate_amount_Dog_obesity = MER_Dog_obesity(obesity, weight) * amount * 100;
        double moderate_amount_Cat_nursing = MER_Cat_nursing(nursing, weight) * amount * 100;
        double moderate_amount_Dog_nursing = MER_Dog_nursing(nursing, weight) * amount * 100;
//////////////////////////////////////////////////////////////////////////////////////////////////사료 급여시 적정 식사량



    }





    public Double MER_Cat_baby(int age, double weight) {
        if (weight < 2) {
            RER1 = RER_under2;
            baby_cat = 2.5 * RER1;
        } else if (weight > 2 && weight < 20) {
            RER2 = RER_more20;
            baby_cat = 2.5 * RER2;
        }
        return baby_cat;

    }

    public double MER_Cat_adult(int age, double weight) {
        if (weight < 2) {
            RER1 = RER_under2;
            adult_cat = 1.4 * RER1;
        } else if (weight > 2 && weight < 20) {
            RER2 = RER_more20;
            adult_cat = 1.4 * RER2;
        }
        return adult_cat;
    }

    public double MER_Cat_old(int age, double weight) {
        if (weight < 2) {
            RER1 = RER_under2;
            old_pet = 0.7 * RER1;
        } else if (weight > 2 && weight < 20) {
            RER2 = RER_more20;
            old_pet = 0.7 * RER2;
        }
        return old_pet;
    }

    public double MER_Cat_nursing(boolean nursing, double weight) {
        if (weight < 2) {
            RER1 = RER_more20;
            nursing_cat = 4 * RER1;
        } else if (weight > 2 && weight < 20) {
            RER2 = RER_under2;
            nursing_cat = 4 * RER2;


        }
        return nursing_cat;
    }

    public double MER_Cat_pregnant(boolean pregnant, double weight) {
        if (weight < 2) {
            RER1 = RER_under2;
            pregnant_cat = 3 * RER1;
        } else if ((weight > 2 && weight < 20)) ;
        {
            RER2 = RER_more20;
            pregnant_cat = 3 * RER2;
        }
        return pregnant_cat;
    }

    public double MER_Cat_obesity(boolean obesity, double weight) {
        if (weight < 2) {
            RER1 = RER_under2;
            obesity_pet = 0.7 * RER1;
        } else if (weight > 2 && weight < 20) {
            RER2 = RER_more20;
            obesity_pet = 0.7 * RER2;
        }
        return obesity_pet;
    }

    public double MER_Dog_baby(int age, double weight) {          //나이에 관한 조건문 추가
        if (weight < 2) {
            RER1 = RER_under2;
            baby_dog = 3 * RER1;
        } else if (weight > 2 && weight < 20) {
            RER2 = RER_more20;
            baby_dog = 3 * RER2;
        }
        return baby_dog;

    }

    public double MER_Dog_adult(int age, double weight) {
        if (weight < 2) {
            RER1 = RER_under2;
            adult_dog = 2 * RER1;
        } else if (weight > 2 && weight < 20) {
            RER2 = RER_more20;
            adult_dog = 2 * RER2;

        }
        return adult_dog;
    }

    public double MER_Dog_old(int age, double weight) {
        if (weight < 2) {
            RER1 = RER_under2;
            old_pet = 0.7 * RER1;
        } else if (weight > 2 && weight < 20) {
            RER2 = RER_more20;
            old_pet = 0.7 * RER2;
        }
        return old_pet;
    }

    public double MER_Dog_nursing(boolean nursing, double weight) {
        if (weight < 2) {
            RER1 = RER_under2;
            nursing_dog = 6 * RER1;

        } else if (weight > 2 && weight < 20) {
            RER2 = RER_more20;
            nursing_dog = 6 * RER2;


        }
        return nursing_dog;
    }

    public double MER_Dog_obesity(boolean obesity, double weight) {
        if (weight < 2) {
            RER1 = RER_under2;
            obesity_pet = 0.7 * RER1;
        } else if (weight > 2 && weight < 20) {
            RER2 = RER_more20;
            obesity_pet = 0.7 * RER2;
        }
        return obesity_pet;
    }


}


