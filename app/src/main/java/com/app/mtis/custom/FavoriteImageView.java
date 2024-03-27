package com.app.mtis.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.mtis.R;

public class FavoriteImageView extends androidx.appcompat.widget.AppCompatImageView {
    private static final int srcFavorite = R.drawable.icon_heart_filled;
    private static final int srcNotFavorite = R.drawable.icon_heart_border;

    public FavoriteImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FavoriteImageView(@NonNull Context context) {
        super(context);
    }

    public FavoriteImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSrcFavorite(boolean favorite){
        if(favorite) this.setImageResource(srcFavorite);
        else this.setImageResource(srcNotFavorite);
    }

}
