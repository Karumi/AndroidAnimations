package com.karumi.androidanimations.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View

class CircularView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var radius: Float = 2000f
        set(value) {
            field = value
            invalidate()
        }
    var center: PointF = PointF(0f, 0f)
        set(value) {
            field = value
            invalidate()
        }

    var centerX: Float
        get() = center.x
        set(value) {
            center = PointF(value, center.y)
        }

    var centerY: Float
        get() = center.y
        set(value) {
            center = PointF(center.x, value)
        }

    override fun draw(canvas: Canvas?) {
        val path = Path().apply {
            addCircle(center.x, center.y, radius, Path.Direction.CW)
        }

        path.let { canvas?.clipPath(it) }
        super.draw(canvas)
    }
}