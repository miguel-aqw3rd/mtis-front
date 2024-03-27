package com.app.mtis.custom;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.mtis.R;
// It turns out i dont need this class for now...
public class ExpandImageView extends androidx.appcompat.widget.AppCompatImageView {
    private static final int srcExpanded = R.drawable.icon_star; // Warning: Both are the same
    private static final int srcCompact = R.drawable.icon_star; // Warning: Both are the same for now

    public ExpandImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandImageView(@NonNull Context context) {
        super(context);
    }

    public ExpandImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setSrcExpanded(boolean main){
        if(main) this.setImageResource(srcExpanded);
        else this.setImageResource(srcCompact);
    }

}
