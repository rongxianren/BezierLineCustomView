package com.rongxianren.bezierlinecustomview;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by wty on 2016/11/11.
 */

public class BezierEvaluator implements TypeEvaluator<PointF> {

    private PointF pointF_1;
    private PointF pointF_2;


    public BezierEvaluator(PointF pointF_1, PointF pointF_2) {
        this.pointF_1 = pointF_1;
        this.pointF_2 = pointF_2;
    }

    @Override
    public PointF evaluate(float fraction, PointF startPoint, PointF endPoint) {
        PointF result = new PointF();
        result.x = (float) (startPoint.x * Math.pow((1 - fraction), 3)
                + 3 * endPoint.x * fraction * Math.pow(1 - fraction, 2)
                + 3 * this.pointF_1.x * Math.pow(fraction, 2) * (1 - fraction)
                + this.pointF_2.x * Math.pow(fraction, 3));

        result.y = (float) (startPoint.y * Math.pow((1 - fraction), 3)
                + 3 * endPoint.y * fraction * Math.pow(1 - fraction, 2)
                + 3 * this.pointF_1.y * Math.pow(fraction, 2) * (1 - fraction)
                + this.pointF_2.y * Math.pow(fraction, 3));
        return result;
    }
}
