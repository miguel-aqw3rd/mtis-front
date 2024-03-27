package com.app.mtis.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.mtis.R;

public class UpArrowImageView extends androidx.appcompat.widget.AppCompatImageView {
    private static final int srcUpArrowFilled = R.drawable.icon_uparrow_filled;
    private static final int srcUpArrowBorder = R.drawable.icon_uparrow_border;

    public UpArrowImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public UpArrowImageView(@NonNull Context context) {
        super(context);
    }

    public UpArrowImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setSrcArrow(boolean bool){
        if(bool) this.setImageResource(srcUpArrowFilled);
        else this.setImageResource(srcUpArrowBorder);
    }

}
