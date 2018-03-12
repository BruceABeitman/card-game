package com.cards.bbeitman.cards.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.GridView;

import com.cards.bbeitman.cards.R;
import com.cards.bbeitman.cards.activities.adapters.GameBoardAdapter;
import com.cards.bbeitman.cards.models.Deck;

public class Game extends Activity {

    Resources res;
    Deck deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        res = getResources();

        deck = new Deck();
        deck.populateDeck(this);

        buildGameBoard();
    }

    private void buildGameBoard() {
        GridView gridView = findViewById(R.id.game_board);
        gridView.setAdapter(new GameBoardAdapter(this, 3, 3, res));
    }

    @Override
    public void onBackPressed()
    {
        // Make back go to main menu
        super.onBackPressed();
        startActivity(new Intent(Game.this, MainMenu.class));
        finish();
    }
}
