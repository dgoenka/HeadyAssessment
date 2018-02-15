package com.divyanshgoenka.headyassessment.android.animation;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;


public class FixedSpeedScroller extends Scroller {

    private static final int ANIMATION_DURATION = 500;

    public FixedSpeedScroller(Context context) {
        super(context);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }


    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, FixedSpeedScroller.ANIMATION_DURATION);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, FixedSpeedScroller.ANIMATION_DURATION);
    }
}
