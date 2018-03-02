package com.cards.bbeitman.cards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class BuildDeck extends Activity {

    Button elementButton;
    Button classButton;
    Button classPowerButton1;
    Button classPowerButton2;
    Button classPowerButton3;
    Button classPowerButton4;
    Button cardButton;

    RelativeLayout elementSection;
    RelativeLayout classSection;
    RelativeLayout classPowerSection;

    boolean power1Chosen = false;
    boolean power2Chosen = false;
    boolean power3Chosen = false;
    boolean power4Chosen = false;

    Integer elementResult;
    Integer raceResult;

    HashMap<Integer, ArrayList<Integer>> cardMap = new HashMap<>();
    ArrayList<Integer> powers = new ArrayList<>();

    HashMap<Integer, ArrayList<Integer>> classPosMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.build_deck);

        Intent intent = getIntent();
        String value = intent.getStringExtra("key");

        // Build map of class positions
        buildClassPosMap();

        // Find buttons
        elementButton = (Button) findViewById(R.id.element_button);
        classButton = (Button) findViewById(R.id.class_button);
        classPowerButton1 = (Button) findViewById(R.id.class_power_button_1);
        classPowerButton2 = (Button) findViewById(R.id.class_power_button_2);
        classPowerButton3 = (Button) findViewById(R.id.class_power_button_3);
        classPowerButton4 = (Button) findViewById(R.id.class_power_button_4);
        cardButton = (Button) findViewById(R.id.card_button);

        // Find layouts
        elementSection = (RelativeLayout) findViewById(R.id.element_section);
        classSection = (RelativeLayout) findViewById(R.id.class_section);
        classPowerSection = (RelativeLayout) findViewById(R.id.class_powers_section);

        // Button listeners
        rollElementButton();
        rollClassButton();
        rollClassPower1Button();
        rollClassPower2Button();
        rollClassPower3Button();
        rollClassPower4Button();
        toCardButton();

        // Hide all but top sections/buttons
        classSection.setVisibility(View.GONE);
        classPowerSection.setVisibility(View.GONE);
        cardButton.setVisibility(View.GONE);

    }

    // Button listener for picking an element
    public void rollElementButton() {
        elementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int element = new Random().nextInt(4) + 1;
                TextView tv = (TextView)findViewById(R.id.element_result);
                tv.setText(Integer.toString(element));

                elementResult = element;
                elementButton.setEnabled(false);
                classSection.setVisibility(View.VISIBLE);
            }
        });
    }

    // Button listener for picking a class
    public void rollClassButton() {

        classButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int element = new Random().nextInt(20) + 1;
                TextView tv = (TextView)findViewById(R.id.class_result);
                tv.setText(Integer.toString(element));

                raceResult = element;
                classButton.setEnabled(false);
                classPowerSection.setVisibility(View.VISIBLE);
            }
        });
    }

    // Button listener for picking first class power
    public void rollClassPower1Button() {

        classPowerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int element = new Random().nextInt(9) + 1;
                TextView tv = (TextView)findViewById(R.id.class_power_result_1);
                tv.setText(Integer.toString(element));

                power1Chosen = true;
                classPowerButton1.setEnabled(false);
                if (checkPowerChosens()) {
                    cardButton.setVisibility(View.VISIBLE);
                }

                powers.add(element);
            }
        });
    }

    // Button listener for picking first class power
    public void rollClassPower2Button() {

        classPowerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int element = new Random().nextInt(9) + 1;
                TextView tv = (TextView)findViewById(R.id.class_power_result_2);
                tv.setText(Integer.toString(element));

                power2Chosen = true;
                classPowerButton2.setEnabled(false);
                if (checkPowerChosens()) {
                    cardButton.setVisibility(View.VISIBLE);
                }

                powers.add(element);
            }
        });
    }

    // Button listener for picking first class power
    public void rollClassPower3Button() {

        classPowerButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int element = new Random().nextInt(9) + 1;
                TextView tv = (TextView)findViewById(R.id.class_power_result_3);
                tv.setText(Integer.toString(element));

                power3Chosen = true;
                classPowerButton3.setEnabled(false);
                if (checkPowerChosens()) {
                    cardButton.setVisibility(View.VISIBLE);
                }

                powers.add(element);
            }
        });
    }

    // Button listener for picking first class power
    public void rollClassPower4Button() {
        classPowerButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int element = new Random().nextInt(9) + 1;
                TextView tv = (TextView)findViewById(R.id.class_power_result_4);
                tv.setText(Integer.toString(element));

                power4Chosen = true;
                classPowerButton4.setEnabled(false);
                if (checkPowerChosens()) {
                    cardButton.setVisibility(View.VISIBLE);
                }

                powers.add(element);
            }
        });
    }

    // Button listener for going to card activity
    public void toCardButton() {
        cardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent myIntent = new Intent(BuildDeck.this, CardView.class);
                for (Integer pos : classPosMap.get(raceResult)) {
                    // Rotate power to next in list
                    powers.add(powers.remove(0));
                    ArrayList<Integer> powersCopy = new ArrayList<>(powers);
                    cardMap.put(pos, powersCopy);
                }
                myIntent.putExtra("cardMap", cardMap); //Optional parameters
                BuildDeck.this.startActivity(myIntent);
            }
        });
    }

    private boolean checkPowerChosens() {
        if (power1Chosen && power2Chosen && power3Chosen && power4Chosen) {
            return true;
        }
        return false;
    }

    private void buildClassPosMap() {
        classPosMap.put(1, new ArrayList<Integer>(Arrays.asList(1, 3, 5, 7)));
        classPosMap.put(2, new ArrayList<Integer>(Arrays.asList(2, 3, 4, 7)));
        classPosMap.put(3, new ArrayList<Integer>(Arrays.asList(1, 4, 5, 8)));
        classPosMap.put(4, new ArrayList<Integer>(Arrays.asList(2, 4, 6, 8)));
        classPosMap.put(5, new ArrayList<Integer>(Arrays.asList(2, 3, 6, 7)));
        classPosMap.put(6, new ArrayList<Integer>(Arrays.asList(2, 5, 6, 7)));
        classPosMap.put(7, new ArrayList<Integer>(Arrays.asList(3, 6, 7, 8)));
        classPosMap.put(8, new ArrayList<Integer>(Arrays.asList(1, 4, 5, 6)));
        classPosMap.put(9, new ArrayList<Integer>(Arrays.asList(1, 2, 6, 7)));
        classPosMap.put(10, new ArrayList<Integer>(Arrays.asList(1, 2, 5, 8)));
        classPosMap.put(11, new ArrayList<Integer>(Arrays.asList(2, 3, 7, 8)));
        classPosMap.put(12, new ArrayList<Integer>(Arrays.asList(1, 2, 4, 5)));
        classPosMap.put(13, new ArrayList<Integer>(Arrays.asList(1, 5, 6, 8)));
        classPosMap.put(14, new ArrayList<Integer>(Arrays.asList(2, 3, 5, 6)));
        classPosMap.put(15, new ArrayList<Integer>(Arrays.asList(1, 2, 5, 6)));
        classPosMap.put(16, new ArrayList<Integer>(Arrays.asList(3, 4, 5, 8)));
        classPosMap.put(17, new ArrayList<Integer>(Arrays.asList(1, 4, 7, 8)));
        classPosMap.put(18, new ArrayList<Integer>(Arrays.asList(1, 2, 3, 6)));
        classPosMap.put(19, new ArrayList<Integer>(Arrays.asList(2, 3, 6, 8)));
        classPosMap.put(20, new ArrayList<Integer>(Arrays.asList(3, 4, 6, 7)));
    }

}
