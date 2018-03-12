package com.cards.bbeitman.cards.activities.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cards.bbeitman.cards.R;
import com.cards.bbeitman.cards.models.Card;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CardAdapter extends BaseAdapter {
    private Context mContext;
    private List<Card> mCards;
    private Resources res;

    public CardAdapter (Context context, List<Card> cards, Resources res){
        this.mContext = context;
        this.mCards = cards;
        this.res = res;
    }

    @Override
    public int getCount() {
        return mCards.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            // get layout from xml file
            gridView = inflater.inflate(R.layout.card_view, null);
//            gridView.setLayoutParams(new LinearLayout.LayoutParams(400, 600));

            // get current card
            Card card = mCards.get(position);

            // hide configurables
            List<TextView> powerTexts = hideConfigurable(gridView, position);

            // populate powers
            Iterator it = card.getPowMap().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                // Get corresponding textView based on power position
                TextView textView = powerTexts.get(((Integer) pair.getKey()) - 1);
                textView.setText(String.valueOf(pair.getValue()));
            }
            // populate race, class, element
            TextView elementView = gridView.findViewById(R.id.element_title);
            elementView.setText(res.getStringArray(R.array.elements)[card.getElementId()-1]);
            TextView raceView = gridView.findViewById(R.id.race_title);
            raceView.setText(res.getStringArray(R.array.races)[card.getRaceId()-1]);
            TextView classView = gridView.findViewById(R.id.class_title);
            classView.setText(res.getStringArray(R.array.classes)[card.getClassId()-1]);
        } else {
            gridView = (View) convertView;
        }
        return gridView;
    }

    private List<TextView> hideConfigurable(View gridView, int position) {
        // Remove all spinners
        Spinner spinner1 = gridView.findViewById(R.id.spinner1);
        Spinner spinner2 = gridView.findViewById(R.id.spinner2);
        Spinner spinner3 = gridView.findViewById(R.id.spinner3);
        Spinner spinner4 = gridView.findViewById(R.id.spinner4);
        Spinner spinner5 = gridView.findViewById(R.id.spinner5);
        Spinner spinner6 = gridView.findViewById(R.id.spinner6);
        Spinner spinner7 = gridView.findViewById(R.id.spinner7);
        Spinner spinner8 = gridView.findViewById(R.id.spinner8);
        spinner1.setVisibility(View.GONE);
        spinner2.setVisibility(View.GONE);
        spinner3.setVisibility(View.GONE);
        spinner4.setVisibility(View.GONE);
        spinner5.setVisibility(View.GONE);
        spinner6.setVisibility(View.GONE);
        spinner7.setVisibility(View.GONE);
        spinner8.setVisibility(View.GONE);
        // Make all textViews visible
        TextView text1 = gridView.findViewById(R.id.text1);
        TextView text2 = gridView.findViewById(R.id.text2);
        TextView text3 = gridView.findViewById(R.id.text3);
        TextView text4 = gridView.findViewById(R.id.text4);
        TextView text5 = gridView.findViewById(R.id.text5);
        TextView text6 = gridView.findViewById(R.id.text6);
        TextView text7 = gridView.findViewById(R.id.text7);
        TextView text8 = gridView.findViewById(R.id.text8);
        text1.setVisibility(View.VISIBLE);
        text2.setVisibility(View.VISIBLE);
        text3.setVisibility(View.VISIBLE);
        text4.setVisibility(View.VISIBLE);
        text5.setVisibility(View.VISIBLE);
        text6.setVisibility(View.VISIBLE);
        text7.setVisibility(View.VISIBLE);
        text8.setVisibility(View.VISIBLE);
        // Hide confirm button
        Button confirmButton = gridView.findViewById(R.id.confirmButton);
        confirmButton.setVisibility(View.GONE);
        // Build textView list
        List<TextView> textViews = new ArrayList<>();
        textViews.add(text1);
        textViews.add(text2);
        textViews.add(text3);
        textViews.add(text4);
        textViews.add(text5);
        textViews.add(text6);
        textViews.add(text7);
        textViews.add(text8);
        return textViews;
    }
}
