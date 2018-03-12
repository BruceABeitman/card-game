package com.cards.bbeitman.cards.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.cards.bbeitman.cards.R;
import com.cards.bbeitman.cards.activities.adapters.CardAdapter;
import com.cards.bbeitman.cards.models.Card;
import com.cards.bbeitman.cards.models.Deck;

public class OverviewDeck extends Activity {

    Resources res;
    FloatingActionButton myFab;
    Deck deck;
    Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deck_overview);

        Intent intent = getIntent();
        card = (Card) getIntent().getSerializableExtra("card");
        res = getResources();

        // Populate floating button
        myFab = (FloatingActionButton)  findViewById(R.id.build_card);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(OverviewDeck.this, BuildCard.class);
                OverviewDeck.this.startActivity(myIntent);
            }
        });

        deck = new Deck();
        deck.populateDeck(this);
        TextView textView = (TextView) findViewById(R.id.deck_size);
        textView.setText(String.valueOf(deck.getCards().size()));

        buildDeckGrid();
    }

    private void buildDeckGrid() {
        GridView gridView = findViewById(R.id.deck_grid);
        gridView.setAdapter(new CardAdapter(this, deck.getCards(), res));
    }

    @Override
    public void onBackPressed()
    {
        // Make back go to main menu
        super.onBackPressed();
        startActivity(new Intent(OverviewDeck.this, MainMenu.class));
        finish();
    }
}
