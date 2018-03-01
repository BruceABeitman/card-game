package com.cards.bbeitman.cards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class BuildDeck extends Activity {

    Button elementButton;
    Button classButton;
    Button classPowerButton1;
    Button classPowerButton2;
    Button classPowerButton3;
    Button classPowerButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.build_deck);

        Intent intent = getIntent();
        String value = intent.getStringExtra("key");

        rollElementButton();
        rollClassButton();
        rollClassPower1Button();
        rollClassPower2Button();
        rollClassPower3Button();
        rollClassPower4Button();
    }

    // Button listener for picking an element
    public void rollElementButton() {
        elementButton = (Button) findViewById(R.id.element_button);
        elementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int element = new Random().nextInt(4) + 1;
                TextView tv = (TextView)findViewById(R.id.element_result);
                tv.setText(Integer.toString(element));
            }
        });
    }

    // Button listener for picking a class
    public void rollClassButton() {
        classButton = (Button) findViewById(R.id.class_button);
        classButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int element = new Random().nextInt(20) + 1;
                TextView tv = (TextView)findViewById(R.id.class_result);
                tv.setText(Integer.toString(element));
            }
        });
    }

    // Button listener for picking first class power
    public void rollClassPower1Button() {
        classPowerButton1 = (Button) findViewById(R.id.class_power_button_1);
        classPowerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int element = new Random().nextInt(9) + 1;
                TextView tv = (TextView)findViewById(R.id.class_power_result_1);
                tv.setText(Integer.toString(element));
            }
        });
    }

    // Button listener for picking first class power
    public void rollClassPower2Button() {
        classPowerButton2 = (Button) findViewById(R.id.class_power_button_2);
        classPowerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int element = new Random().nextInt(9) + 1;
                TextView tv = (TextView)findViewById(R.id.class_power_result_2);
                tv.setText(Integer.toString(element));
            }
        });
    }

    // Button listener for picking first class power
    public void rollClassPower3Button() {
        classPowerButton3 = (Button) findViewById(R.id.class_power_button_3);
        classPowerButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int element = new Random().nextInt(9) + 1;
                TextView tv = (TextView)findViewById(R.id.class_power_result_3);
                tv.setText(Integer.toString(element));
            }
        });
    }

    // Button listener for picking first class power
    public void rollClassPower4Button() {
        classPowerButton4 = (Button) findViewById(R.id.class_power_button_4);
        classPowerButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int element = new Random().nextInt(9) + 1;
                TextView tv = (TextView)findViewById(R.id.class_power_result_4);
                tv.setText(Integer.toString(element));
            }
        });
    }

}
