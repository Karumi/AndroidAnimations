package com.karumi.androidanimations.extensions

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Path
import android.graphics.PathMeasure
import android.os.Build
import android.view.View

fun animatePath(view: View, path: Path): ValueAnimator =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        ObjectAnimator.ofFloat(view, View.X, View.Y, path)
    } else {
        val coordinates = FloatArray(2)
        val pathMeasure = PathMeasure(path, true)
        ValueAnimator.ofFloat(0f, pathMeasure.length).apply {
            addUpdateListener {
                val distance = it.animatedValue as Float
                pathMeasure.getPosTan(distance, coordinates, null)
                view.translationX = coordinates[0]
                view.translationY = coordinates[1]
            }
        }
    }