package com.karumi.androidanimations.common

import android.animation.TypeEvaluator
import android.animation.ValueAnimator


/*
 See https://gist.github.com/aritraroy/f5aac68cf42e83270d71f9bf58fac19c
 */
class GammaEvaluator : TypeEvaluator<Int> {

    override fun evaluate(fraction: Float, startValue: Int, endValue: Int): Int {
        val startA = (startValue shr 24 and 0xff) / 255.0f
        var startR = (startValue shr 16 and 0xff) / 255.0f
        var startG = (startValue shr 8 and 0xff) / 255.0f
        var startB = (startValue and 0xff) / 255.0f

        val endA = (endValue shr 24 and 0xff) / 255.0f
        var endR = (endValue shr 16 and 0xff) / 255.0f
        var endG = (endValue shr 8 and 0xff) / 255.0f
        var endB = (endValue and 0xff) / 255.0f

        // convert from sRGB to linear
        startR = eocfsRGB(startR)
        startG = eocfsRGB(startG)
        startB = eocfsRGB(startB)

        endR = eocfsRGB(endR)
        endG = eocfsRGB(endG)
        endB = eocfsRGB(endB)

        // compute the interpolated color in linear space
        var a = startA + fraction * (endA - startA)
        var r = startR + fraction * (endR - startR)
        var g = startG + fraction * (endG - startG)
        var b = startB + fraction * (endB - startB)

        // convert back to sRGB in the [0..255] range
        a *= 255.0f
        r = oecfsRGB(r) * 255.0f
        g = oecfsRGB(g) * 255.0f
        b = oecfsRGB(b) * 255.0f

        return Math.round(a) shl 24 or (Math.round(r) shl 16) or (Math.round(g) shl 8) or Math.round(
            b
        )
    }

    companion object {

        /**
         * Returns an instance of `GammaEvaluator` that may be used in
         * [ValueAnimator.setEvaluator]. The same instance may
         * be used in multiple `Animator`s because it holds no state.
         *
         * @return An instance of `GammaEvaluator`.
         */
        val instance = GammaEvaluator()

        /**
         * Opto-electronic conversion function for the sRGB color space
         * Takes a gamma-encoded sRGB value and converts it to a linear sRGB value
         */
        internal fun oecfsRGB(linear: Float): Float {
            // IEC 61966-2-1:1999
            return if (linear <= 0.0031308f)
                linear * 12.92f
            else
                (Math.pow(linear.toDouble(), 1.0 / 2.4) * 1.055 - 0.055).toFloat()
        }

        /**
         * Electro-optical conversion function for the sRGB color space
         * Takes a linear sRGB value and converts it to a gamma-encoded sRGB value
         */
        internal fun eocfsRGB(srgb: Float): Float {
            // IEC 61966-2-1:1999
            return if (srgb <= 0.04045f) srgb / 12.92f else Math.pow(
                (srgb + 0.055) / 1.055,
                2.4
            ).toFloat()
        }
    }

}