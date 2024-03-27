package com.app.mtis.custom;

import static java.lang.Math.ceil;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mtis.R;

public class CustomDivider extends RecyclerView.ItemDecoration {
    private Paint dividerPaint;
    private static final float STROKE_WIDTH = 3.0f;  // Change the width of the line here

    public CustomDivider(Context context) {
        dividerPaint = new Paint();
        dividerPaint.setColor(ContextCompat.getColor(context, R.color.black));
        dividerPaint.setStrokeWidth(STROKE_WIDTH);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = (int) (top + dividerPaint.getStrokeWidth());
            c.drawLine(left, top, right, bottom, dividerPaint);
        }
    }

}
