package com.cards.bbeitman.cards.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cards.bbeitman.cards.R;
import com.cards.bbeitman.cards.models.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ViewCard extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    Spinner spinner5;
    Spinner spinner6;
    Spinner spinner7;
    Spinner spinner8;

    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    TextView text5;
    TextView text6;
    TextView text7;
    TextView text8;

    // <Position, Available Powers>
//    HashMap<Integer, Set<Integer>> cardMap = new HashMap<>();
    Card card;
    // <Position, Power>
    HashMap<Integer, Integer> currentMap = new HashMap<>();
    List<Spinner> spinnerList = new ArrayList<>();
    List<TextView> textList = new ArrayList<>();
    List<Integer> activePositions = new ArrayList<>();
    Button confirmButton;
    Context mContext;
    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_view);
        res = getResources();

        LinearLayout topLevel = findViewById(R.id.top_level);
        topLevel.setBackground(ContextCompat.getDrawable(this, R.drawable.purple_background));
        card = (Card) getIntent().getSerializableExtra("card");

        // populate race, class, element
        TextView elementView = findViewById(R.id.element_title);
        elementView.setText(res.getStringArray(R.array.elements)[card.getElementId()-1]);
        TextView raceView = findViewById(R.id.race_title);
        raceView.setText(res.getStringArray(R.array.races)[card.getRaceId()-1]);
        if (card.getClassId() != null) {
            TextView classView = findViewById(R.id.class_title);
            classView.setText(res.getStringArray(R.array.classes)[card.getClassId() - 1]);
        }


        mContext = this;
        confirmButton = (Button) findViewById(R.id.confirmButton);
        confirmButtonListener();

        // Get all spinners
        spinnerSetup();
        textSetup();

        // Populate spinners with options
        // Determine if we're viewing unchosen race or class card
        if (card.getClassId() == null) {
            // Undetermined class, build for determining race parameters
            Iterator it = card.getRaceCardMap().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                Integer position = (Integer) pair.getKey();
                ArrayList<Integer> availablePowers = (ArrayList) pair.getValue();
                activePositions.add(position);
                ArrayAdapter<Integer> itemsAdapter =
                        new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, availablePowers);
                itemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                card.getPowMap().put(position, itemsAdapter.getItem(0));
                spinnerList.get(position-1).setAdapter(itemsAdapter);
            }
            // Deactivate all invalid spinners
            int pos = 1;
            for (Spinner spinner : spinnerList) {
                if (!activePositions.contains(pos)) {
                    spinner.setEnabled(false);
                }
                pos++;
            }
        } else {
            // Build for determining final class & race parameters
            // Place all powMap values, without activating the spinners
            Iterator it = card.getPowMap().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                Integer position = (Integer) pair.getKey();
                Integer power = (Integer) pair.getValue();
                spinnerList.get(position-1).setVisibility(View.GONE);
                textList.get(position-1).setVisibility(View.VISIBLE);
                textList.get(position-1).setText(power.toString());
            }
            // If we only have 1 class position, then place it along with other powMap
            if (card.getClassPowers().size() == 1) {
                Iterator mit = card.getClassCardMap().entrySet().iterator();
                while (mit.hasNext()) {
                    Map.Entry pair = (Map.Entry)mit.next();
                    Integer position = (Integer) pair.getKey();
                    Integer power = (Integer) ((ArrayList)pair.getValue()).get(0);
                    spinnerList.get(position-1).setVisibility(View.GONE);
                    textList.get(position-1).setVisibility(View.VISIBLE);
                    textList.get(position-1).setText(power.toString());
                    card.getPowMap().put(position, power);
                }
            // If we have more than 1 class position, then build spinners for class positions, along with other powMap
            } else {
                Iterator mit = card.getClassCardMap().entrySet().iterator();
                while (mit.hasNext()) {
                    Map.Entry pair = (Map.Entry)mit.next();
                    Integer position = (Integer) pair.getKey();
                    ArrayList<Integer> availablePowers = (ArrayList) pair.getValue();
                    activePositions.add(position);
                    ArrayAdapter<Integer> itemsAdapter =
                            new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, availablePowers);
                    itemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    card.getPowMap().put(position, itemsAdapter.getItem(0));
                    spinnerList.get(position-1).setAdapter(itemsAdapter);
                }
            }
            // Remove all invalid spinners
            int pos = 1;
            for (Spinner spinner : spinnerList) {
                if (!card.getPowMap().containsKey(pos) && !card.getClassPositions().contains(pos)) {
                    spinner.setVisibility(View.GONE);
                    textList.get(pos-1).setVisibility(View.VISIBLE);
                }
                pos++;
            }
        }
    }

    // TODO This should prioritize spinners NOT recently changed...
    private void swapSpinnerValue(Integer thisPos, Integer oldPow, Integer newPow) {
        Iterator it = card.getPowMap().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            Integer currPos = (Integer) entry.getKey();
            Integer currPower = (Integer) entry.getValue();
            // If this spinner is not the current spinner
            if (currPos != thisPos) {
                // and this spinner's current power equals the current spinner's new power
                if (currPower == newPow) {
                    // get all the current spinner's available allPowers
                    Adapter adapter = spinnerList.get(currPos-1).getAdapter();
                    if (adapter != null) {
                        int count = adapter.getCount();
                        // find the index of the current spinner's old power
                        for (int i = 0; i < count; i++) {
                            if ((Integer) adapter.getItem(i) == oldPow) {
                                // Set this spinner's selection to the current spinner's old power's index
                                spinnerList.get(currPos - 1).setSelection(i);
                                // Update the powMap
                                card.getPowMap().put(currPos, oldPow);
                                card.getPowMap().put(thisPos, newPow);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {
            case R.id.spinner1:
                Integer newPow1 = (Integer) spinner1.getSelectedItem();
                Integer oldPow1 = card.getPowMap().get(1);
                swapSpinnerValue(1, oldPow1, newPow1);
                System.out.println("1: was " + oldPow1 + " now is " + newPow1);
                break;
            case R.id.spinner2:
                Integer newPow2 = (Integer) spinner2.getSelectedItem();
                Integer oldPow2 = card.getPowMap().get(2);
                swapSpinnerValue(2, oldPow2, newPow2);
                System.out.println("2: was " + oldPow2 + " now is " + newPow2);
                break;
            case R.id.spinner3:
                Integer newPow3 = (Integer) spinner3.getSelectedItem();
                Integer oldPow3 = card.getPowMap().get(3);
                swapSpinnerValue(3, oldPow3, newPow3);
                System.out.println("3: was " + oldPow3 + " now is " + newPow3);
                break;
            case R.id.spinner4:
                Integer newPow4 = (Integer) spinner4.getSelectedItem();
                Integer oldPow4 = card.getPowMap().get(4);
                swapSpinnerValue(4, oldPow4, newPow4);
                System.out.println("4: was " + oldPow4 + " now is " + newPow4);
                break;
            case R.id.spinner5:
                Integer newPow5 = (Integer) spinner5.getSelectedItem();
                Integer oldPow5 = card.getPowMap().get(5);
                swapSpinnerValue(5, oldPow5, newPow5);
                System.out.println("5: was " + oldPow5 + " now is " + newPow5);
                break;
            case R.id.spinner6:
                Integer newPow6 = (Integer) spinner6.getSelectedItem();
                Integer oldPow6 = card.getPowMap().get(6);
                swapSpinnerValue(6, oldPow6, newPow6);
                System.out.println("6: was " + oldPow6 + " now is " + newPow6);
                break;
            case R.id.spinner7:
                Integer newPow7 = (Integer) spinner7.getSelectedItem();
                Integer oldPow7 = card.getPowMap().get(7);
                swapSpinnerValue(7, oldPow7, newPow7);
                System.out.println("7: was " + oldPow7 + " now is " + newPow7);
                break;
            case R.id.spinner8:
                Integer newPow8 = (Integer) spinner8.getSelectedItem();
                Integer oldPow8 = card.getPowMap().get(8);
                swapSpinnerValue(8, oldPow8, newPow8);
                System.out.println("8: was " + oldPow8 + " now is " + newPow8);
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    // Button listener for confirming card layout
    public void confirmButtonListener() {
        confirmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // If the card has a class, kick back to deck overview
                if (card.getClassId() != null) {
                    card.addCardToDeck(mContext);
                    Intent myIntent = new Intent(ViewCard.this, OverviewDeck.class);
                    ViewCard.this.startActivity(myIntent);
                // Otherwise, kick back to build card for class details
                } else {
                    Intent myIntent = new Intent(ViewCard.this, BuildCard.class);
                    myIntent.putExtra("card", card); //Optional parameters
                    ViewCard.this.startActivity(myIntent);
                }
            }
        });
    }

    private void spinnerSetup() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        spinner5 = (Spinner) findViewById(R.id.spinner5);
        spinner6 = (Spinner) findViewById(R.id.spinner6);
        spinner7 = (Spinner) findViewById(R.id.spinner7);
        spinner8 = (Spinner) findViewById(R.id.spinner8);

        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        spinner3.setOnItemSelectedListener(this);
        spinner4.setOnItemSelectedListener(this);
        spinner5.setOnItemSelectedListener(this);
        spinner6.setOnItemSelectedListener(this);
        spinner7.setOnItemSelectedListener(this);
        spinner8.setOnItemSelectedListener(this);

        spinnerList.add(spinner1);
        spinnerList.add(spinner2);
        spinnerList.add(spinner3);
        spinnerList.add(spinner4);
        spinnerList.add(spinner5);
        spinnerList.add(spinner6);
        spinnerList.add(spinner7);
        spinnerList.add(spinner8);
    }

    private void textSetup() {

        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);
        text5 = (TextView) findViewById(R.id.text5);
        text6 = (TextView) findViewById(R.id.text6);
        text7 = (TextView) findViewById(R.id.text7);
        text8 = (TextView) findViewById(R.id.text8);

        textList.add(text1);
        textList.add(text2);
        textList.add(text3);
        textList.add(text4);
        textList.add(text5);
        textList.add(text6);
        textList.add(text7);
        textList.add(text8);
    }

    @Override
    public void onBackPressed()
    {
        // Make back go to deck overview
        super.onBackPressed();
        startActivity(new Intent(ViewCard.this, OverviewDeck.class));
        finish();
    }
}
