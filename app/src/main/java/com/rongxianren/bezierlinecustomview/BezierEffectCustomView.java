package com.rongxianren.bezierlinecustomview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Created by wty on 2016/11/11.
 */

public class BezierEffectCustomView extends RelativeLayout {


    private int mViewWidth;
    private int mViewHeight;
    private Drawable[] drawables = new Drawable[7];

    private Random random = new Random();
    private RelativeLayout.LayoutParams mParams;
    private long mDuration = 3000;


    public BezierEffectCustomView(Context context) {
        this(context, null);
    }

    public BezierEffectCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    private void initView() {
        drawables[0] = ContextCompat.getDrawable(getContext(), R.drawable.icon_ya1);
        drawables[1] = ContextCompat.getDrawable(getContext(), R.drawable.icon_ya2);
        drawables[2] = ContextCompat.getDrawable(getContext(), R.drawable.icon_ya3);
        drawables[3] = ContextCompat.getDrawable(getContext(), R.drawable.icon_ya4);
        drawables[4] = ContextCompat.getDrawable(getContext(), R.drawable.icon_ya5);
        drawables[5] = ContextCompat.getDrawable(getContext(), R.drawable.icon_ya6);
        drawables[6] = ContextCompat.getDrawable(getContext(), R.drawable.icon_ya7);
        mParams = new RelativeLayout.LayoutParams(drawables[0].getIntrinsicWidth(), drawables[0].getIntrinsicHeight());
        mParams.addRule(ALIGN_PARENT_BOTTOM);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();
    }

    public void addChildView() {
        ImageView iv = new ImageView(getContext());
        Drawable drawable = drawables[random.nextInt(drawables.length)];
        iv.setImageDrawable(drawable);
        addView(iv, mParams);
        AnimatorSet animatorSet = getCustomAnimationEffect(iv);

        animatorSet.start();

    }

    private AnimatorSet getCustomAnimationEffect(final View view) {

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);

        ValueAnimator bezierAnimator = getBezierAnimation(view);
        animatorSet.setDuration(1000);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorSet, bezierAnimator);

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                BezierEffectCustomView.this.removeView(view);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        return set;
    }

    private ValueAnimator getBezierAnimation(final View view) {

        PointF startPoiont = new PointF(0, mViewHeight - drawables[0].getIntrinsicHeight());
        PointF endPoint = new PointF(random.nextInt(mViewWidth), 0);

        PointF pointF_1 = getTogglePoint(1);
        PointF pointF_2 = getTogglePoint(2);

        BezierEvaluator bezierEvaluator = new BezierEvaluator(pointF_1, pointF_2);
        ValueAnimator bezierAnimator = ValueAnimator.ofObject(bezierEvaluator, startPoiont, endPoint);
        bezierAnimator.setDuration(3000);
        bezierAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF pointf = (PointF) valueAnimator.getAnimatedValue();
                view.setX(pointf.x);
                view.setY(pointf.y);
                view.setAlpha(1 - valueAnimator.getAnimatedFraction());
            }
        });

        return bezierAnimator;
    }

    private PointF getTogglePoint(int index) {
        PointF pointf = new PointF();
        if (1 == index) {
            pointf.y = mViewHeight / 2 + random.nextInt(mViewHeight / 2);
        } else {
            pointf.y = random.nextInt(mViewHeight / 2);
        }
        pointf.x = random.nextInt(mViewWidth);
        return pointf;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        addChildView();
        return super.onTouchEvent(event);
    }
}
