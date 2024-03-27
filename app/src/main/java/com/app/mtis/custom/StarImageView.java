package com.app.mtis.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.mtis.R;
// It turns out i dont need this class for now...
public class StarImageView extends androidx.appcompat.widget.AppCompatImageView {
    private static final int srcStarFilled = R.drawable.icon_star; // Warning: Both are the same
    private static final int srcStarBorder = R.drawable.icon_star; // Warning: Both are the same for now

    public StarImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StarImageView(@NonNull Context context) {
        super(context);
    }

    public StarImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setSrcStar(boolean main){
        if(main) this.setImageResource(srcStarFilled);
        else this.setImageResource(srcStarBorder);
    }

}
