package com.cards.bbeitman.cards;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

public class Deck implements Serializable {

    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
    }

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void populateDeck(Context context) {
        String cardFile = "cards.txt";
        ObjectInputStream input;
//        File file = context.getFileStreamPath(cardFile);
        try {
            input = new ObjectInputStream(new FileInputStream(new File(context.getFilesDir()+File.separator+cardFile)));
            Deck deck = (Deck) input.readObject();
            cards = deck.getCards();
            input.close();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            File f = new File(context.getFilesDir(), cardFile);
            try {
                f.createNewFile();
            } catch (IOException ine) {
                ine.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
