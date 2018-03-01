package com.cards.bbeitman.cards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class BuildDeck extends Activity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.build_deck);

        Intent intent = getIntent();
        String value = intent.getStringExtra("key");

        addListenerOnButton();
    }

    // Button listener for picking an element
    public void addListenerOnButton() {

        button = (Button) findViewById(R.id.element_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final int element = new Random().nextInt(4) + 1;
                TextView tv = (TextView)findViewById(R.id.element_result);
                tv.setText(Integer.toString(element));
            }
        });

    }
}
