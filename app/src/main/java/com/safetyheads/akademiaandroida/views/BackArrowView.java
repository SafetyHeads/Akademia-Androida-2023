package com.safetyheads.akademiaandroida.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.safetyheads.akademiaandroida.R;

public class BackArrowView extends ConstraintLayout {
    private ImageView arrow;

    public BackArrowView(@NonNull Context context) {
        super(context);
        initView();
    }

    public BackArrowView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BackArrowView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        final View view = inflate(getContext(), R.layout.view_arrow, this);
        arrow = findViewById(R.id.arrow);
    }

    public void setImage(@DrawableRes int imageRes) {
        arrow.setImageResource(imageRes);
    }


}
