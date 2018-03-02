package com.cards.bbeitman.cards;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CardView extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    Spinner spinner5;
    Spinner spinner6;
    Spinner spinner7;
    Spinner spinner8;

    HashMap<Integer, Set<Integer>> cardMap = new HashMap<>();
    List<Spinner> spinnerList = new ArrayList<>();
    List<Integer> activePositions = new ArrayList<>();
    Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_view);

        cardMap = (HashMap<Integer, Set<Integer>>) getIntent().getSerializableExtra("cardMap");

        confirmButton = (Button) findViewById(R.id.confirmButton);
        confirmButtonListener();

        // Get all spinners
        spinnerSetup();

        // Populate spinners with options
        Iterator it = cardMap.entrySet().iterator();
        int index = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Integer position = (Integer) pair.getKey();
            ArrayList<Integer> availablePowers = (ArrayList) pair.getValue();
            activePositions.add(position);
            ArrayAdapter<Integer> itemsAdapter =
                    new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, availablePowers);
            itemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerList.get(position-1).setAdapter(itemsAdapter);
            index++;
        }

        // Deactivate all invalid spinners
        int pos = 1;
        for (Spinner spinner : spinnerList) {
            if (!activePositions.contains(pos)) {
                spinner.setEnabled(false);
            }
            pos++;
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {
            case R.id.spinner1:
                Integer currentPow1 = (Integer) spinner1.getSelectedItem();
                System.out.println("1: " + currentPow1);
                break;
            case R.id.spinner2:
                Integer currentPow2 = (Integer) spinner2.getSelectedItem();
                System.out.println("2: " + currentPow2);
                break;
            case R.id.spinner3:
                Integer currentPow3 = (Integer) spinner3.getSelectedItem();
                System.out.println("3: " + currentPow3);
                break;
            case R.id.spinner4:
                Integer currentPow4 = (Integer) spinner4.getSelectedItem();
                System.out.println("4: " + currentPow4);
                break;
            case R.id.spinner5:
                System.out.println("5");
                break;
            case R.id.spinner6:
                System.out.println("6");
                break;
            case R.id.spinner7:
                System.out.println("7");
                break;
            case R.id.spinner8:
                System.out.println("8");
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
//                final int element = new Random().nextInt(4) + 1;
//                TextView tv = (TextView)findViewById(R.id.element_result);
//                tv.setText(Integer.toString(element));
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
}
