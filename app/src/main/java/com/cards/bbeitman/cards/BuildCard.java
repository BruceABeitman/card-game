package com.cards.bbeitman.cards;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BuildCard extends Activity {

    Button elementButton;
    Button raceButton;
    Button racePowerButton1;
    Button racePowerButton2;
    Button racePowerButton3;
    Button racePowerButton4;
    Button classButton;
    Button classPowerButton1;
    Button classPowerButton2;
    Button classPowerButton3;
    Button cardButton;

    RelativeLayout elementSection;
    RelativeLayout raceSection;
    RelativeLayout racePowerSection;
    RelativeLayout classSection;
    RelativeLayout classPowerSection;
    LinearLayout classPower1Section;
    LinearLayout classPower2Section;
    LinearLayout classPower3Section;

    boolean racePower1Chosen = false;
    boolean racePower2Chosen = false;
    boolean racePower3Chosen = false;
    boolean racePower4Chosen = false;
    boolean classPower1Chosen = false;
    boolean classPower2Chosen = false;
    boolean classPower3Chosen = false;

    boolean choosingRace = true;
    boolean choosingClass = false;

    Integer elementResult;
    Integer raceResult;
    Integer classResult;
    ArrayList<Integer> allPowers = new ArrayList<>();
    ArrayList<Integer> classPowers = new ArrayList<>();
    Card card;
    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_build);

        Intent intent = getIntent();
        card = (Card) getIntent().getSerializableExtra("card");
        res = getResources();

        // Find buttons
        elementButton = (Button) findViewById(R.id.element_button);
        raceButton = (Button) findViewById(R.id.race_button);
        racePowerButton1 = (Button) findViewById(R.id.race_power_button_1);
        racePowerButton2 = (Button) findViewById(R.id.race_power_button_2);
        racePowerButton3 = (Button) findViewById(R.id.race_power_button_3);
        racePowerButton4 = (Button) findViewById(R.id.race_power_button_4);
        classButton = (Button) findViewById(R.id.class_button);
        classPowerButton1 = (Button) findViewById(R.id.class_power_button_1);
        classPowerButton2 = (Button) findViewById(R.id.class_power_button_2);
        classPowerButton3 = (Button) findViewById(R.id.class_power_button_3);
        cardButton = (Button) findViewById(R.id.card_button);

        // Find layouts
        elementSection = (RelativeLayout) findViewById(R.id.element_section);
        raceSection = (RelativeLayout) findViewById(R.id.race_section);
        racePowerSection = (RelativeLayout) findViewById(R.id.race_powers_section);
        classSection = (RelativeLayout) findViewById(R.id.class_section);
        classPowerSection = (RelativeLayout) findViewById(R.id.class_powers_section);

        classPower1Section = (LinearLayout) findViewById(R.id.class_power_1_section);
        classPower2Section = (LinearLayout) findViewById(R.id.class_power_2_section);
        classPower3Section = (LinearLayout) findViewById(R.id.class_power_3_section);

        // Button listeners
        rollElementButton();
        rollRaceButton();
        rollRacePower1Button();
        rollRacePower2Button();
        rollRacePower3Button();
        rollRacePower4Button();
        rollClassButton();
        rollClassPower1Button();
        rollClassPower2Button();
        rollClassPower3Button();
        toCardButton();

        System.out.println("card: " + card);
        // If we have a currentMap, then we're returning from placing race allPowers
        // And we are now choosing class
        if (card == null || card.getPowMap().isEmpty()) {
            // Hide all but top sections/buttons
            raceSection.setVisibility(View.GONE);
            racePowerSection.setVisibility(View.GONE);
            classSection.setVisibility(View.GONE);
            classPowerSection.setVisibility(View.GONE);
            cardButton.setVisibility(View.GONE);
        } else {
            choosingRace = false;
            choosingClass = true;
            // Hide all but top sections/buttons
            elementSection.setVisibility(View.GONE);
            raceSection.setVisibility(View.GONE);
            racePowerSection.setVisibility(View.GONE);
            classPowerSection.setVisibility(View.GONE);
            classSection.setVisibility(View.VISIBLE);
            cardButton.setVisibility(View.VISIBLE);
        }

    }

    // Button listener for picking an element
    public void rollElementButton() {
        elementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int elementId = new Random().nextInt(4) + 1;
                TextView tv = (TextView)findViewById(R.id.element_result);
                tv.setText(res.getStringArray(R.array.elements)[elementId-1]);

                elementResult = elementId;
                elementButton.setEnabled(false);
                raceSection.setVisibility(View.VISIBLE);
            }
        });
    }

    // Button listener for picking a race
    public void rollRaceButton() {

        raceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int raceId = new Random().nextInt(20) + 1;
                TextView tv = (TextView)findViewById(R.id.race_result);
                tv.setText(res.getStringArray(R.array.races)[raceId-1]);

                raceResult = raceId;
                raceButton.setEnabled(false);
                racePowerSection.setVisibility(View.VISIBLE);
            }
        });
    }

    // Button listener for picking a race
    public void rollClassButton() {

        classButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int classId = new Random().nextInt(12) + 1;
                TextView tv = (TextView)findViewById(R.id.class_result);
                tv.setText(res.getStringArray(R.array.classes)[classId-1]);

                card.setClassId(classId);
                // Determine what/if further class powers are chosen
                List<Integer> moveableClassPositions = new ArrayList<>();
                // Loop over class's positions
                for (Integer classPosition : card.getClassPositions()) {
                    // If the race's positions contains the class's position, increment power
                    if (card.getRacePositions().contains(classPosition)) {
                        card.getPowMap().put(classPosition, card.getPowMap().get(classPosition) + 1);
                    // If the race's position does not contain class's position, add position as a moveable position
                    } else {
                        moveableClassPositions.add(classPosition);
                    }
                }
                // Set all powers as chosen initially, set them as false as we make their corresponding section visible
                classPower1Chosen = true;
                classPower2Chosen = true;
                classPower3Chosen = true;
                classPower1Section.setVisibility(View.GONE);
                classPower2Section.setVisibility(View.GONE);
                classPower3Section.setVisibility(View.GONE);
                // Set specific visibility as per moveable positions
                if (moveableClassPositions.size() > 0) {
                    classPower1Chosen = false;
                    classPower1Section.setVisibility(View.VISIBLE);
                }
                if (moveableClassPositions.size() > 1) {
                    classPower2Chosen = false;
                    classPower2Section.setVisibility(View.VISIBLE);
                }
                if (moveableClassPositions.size() > 2) {
                    classPower3Chosen = false;
                    classPower3Section.setVisibility(View.VISIBLE);
                }

                classResult = classId;
                classButton.setEnabled(false);
                if (moveableClassPositions.size() > 0) {
                    classPowerSection.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    // Button listener for picking first race power
    public void rollRacePower1Button() {

        racePowerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int element = new Random().nextInt(9) + 1;
                TextView tv = (TextView)findViewById(R.id.race_power_result_1);
                tv.setText(Integer.toString(element));

                racePower1Chosen = true;
                racePowerButton1.setEnabled(false);
                if (checkPowerChosens()) {
                    cardButton.setVisibility(View.VISIBLE);
                }

                allPowers.add(element);
            }
        });
    }

    // Button listener for picking first race power
    public void rollRacePower2Button() {

        racePowerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int element = new Random().nextInt(9) + 1;
                TextView tv = (TextView)findViewById(R.id.race_power_result_2);
                tv.setText(Integer.toString(element));

                racePower2Chosen = true;
                racePowerButton2.setEnabled(false);
                if (checkPowerChosens()) {
                    cardButton.setVisibility(View.VISIBLE);
                }

                allPowers.add(element);
            }
        });
    }

    // Button listener for picking first race power
    public void rollRacePower3Button() {

        racePowerButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int element = new Random().nextInt(9) + 1;
                TextView tv = (TextView)findViewById(R.id.race_power_result_3);
                tv.setText(Integer.toString(element));

                racePower3Chosen = true;
                racePowerButton3.setEnabled(false);
                if (checkPowerChosens()) {
                    cardButton.setVisibility(View.VISIBLE);
                }

                allPowers.add(element);
            }
        });
    }

    // Button listener for picking first race power
    public void rollRacePower4Button() {
        racePowerButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int element = new Random().nextInt(9) + 1;
                TextView tv = (TextView)findViewById(R.id.race_power_result_4);
                tv.setText(Integer.toString(element));

                racePower4Chosen = true;
                racePowerButton4.setEnabled(false);
                if (checkPowerChosens()) {
                    cardButton.setVisibility(View.VISIBLE);
                }

                allPowers.add(element);
            }
        });
    }

    // Button listener for picking first race power
    public void rollClassPower1Button() {

        classPowerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int element = new Random().nextInt(9) + 1;
                TextView tv = (TextView)findViewById(R.id.class_power_result_1);
                tv.setText(Integer.toString(element));

                classPower1Chosen = true;
                classPowerButton1.setEnabled(false);
                allPowers.add(element);
                classPowers.add(element);
            }
        });
    }

    // Button listener for picking first race power
    public void rollClassPower2Button() {

        classPowerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int element = new Random().nextInt(9) + 1;
                TextView tv = (TextView)findViewById(R.id.class_power_result_2);
                tv.setText(Integer.toString(element));

                classPower2Chosen = true;
                classPowerButton2.setEnabled(false);
                allPowers.add(element);
                classPowers.add(element);
            }
        });
    }

    // Button listener for picking first race power
    public void rollClassPower3Button() {

        classPowerButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int element = new Random().nextInt(9) + 1;
                TextView tv = (TextView)findViewById(R.id.class_power_result_3);
                tv.setText(Integer.toString(element));

                classPower3Chosen = true;
                classPowerButton3.setEnabled(false);
                allPowers.add(element);
                classPowers.add(element);
            }
        });
    }

    // Button listener for going to card activity
    public void toCardButton() {
        cardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (choosingRace) {
                    Intent myIntent = new Intent(BuildCard.this, CardView.class);
                    card = new Card(elementResult, raceResult, allPowers);
                    myIntent.putExtra("card", card); //Optional parameters
                    BuildCard.this.startActivity(myIntent);
                }
                if (choosingClass && checkAllPowersChosen()) {
                    card.setClassPowers(classPowers);
                    // Build intent for placing any class data in CardView activity
                    Intent myIntent = new Intent(BuildCard.this, CardView.class);
                    myIntent.putExtra("card", card); //Optional parameters
                    BuildCard.this.startActivity(myIntent);
                }
            }
        });
    }

    private boolean checkPowerChosens() {
        if (racePower1Chosen && racePower2Chosen && racePower3Chosen && racePower4Chosen) {
            return true;
        }
        return false;
    }

    private boolean checkAllPowersChosen() {
        if (classPower1Chosen && classPower2Chosen && classPower3Chosen) {
            return true;
        }
        return false;
    }
}
