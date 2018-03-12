package com.cards.bbeitman.cards.activities.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.cards.bbeitman.cards.R;
import com.cards.bbeitman.cards.models.Card;

import java.util.List;


public class GameBoardAdapter extends BaseAdapter {
    private Context mContext;
    private List<Card> mCards;
    private Resources res;
    private int rows;
    private int columns;

    public GameBoardAdapter (Context context, int rows, int columns, Resources res){
        this.mContext = context;
//        this.mCards = cards;
        this.res = res;
        this.rows = rows;
        this.columns = columns;
    }

    @Override
    public int getCount() {
        return rows * columns;
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
            gridView = inflater.inflate(R.layout.grid_board_cell, null);
            gridView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // Do some job here

                }
            });
        } else {
            gridView = (View) convertView;
        }
        return gridView;
    }
}
