package com.example.photos32;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.GridLayout;

public class CheckableGridLayout extends GridLayout implements Checkable {
    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};
    private boolean isChecked = false;

    public CheckableGridLayout(Context context, AttributeSet att) {
        super(context, att);
    }

    @Override
    public void setChecked(boolean b) {
        isChecked = b;
        refreshDrawableState();
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        isChecked = !isChecked;
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked())
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        return drawableState;
    }
}
