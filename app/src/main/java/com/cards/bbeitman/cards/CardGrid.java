package com.cards.bbeitman.cards;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;


public class CardGrid extends GridView {

    public CardGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardGrid(Context context) {
        super(context);
    }

    public CardGrid(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
