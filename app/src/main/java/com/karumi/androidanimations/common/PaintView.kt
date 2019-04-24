package com.karumi.androidanimations.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class PaintView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paths = mutableListOf<Pair<Path, Int>>()

    operator fun plusAssign(pathAndColor: Pair<Path, Int>) {
        paths.add(pathAndColor)
        invalidate()
    }

    private val paint: Paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 16f
        isAntiAlias = true
        strokeJoin = Paint.Join.ROUND
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        paths.forEach {
            paint.color = it.second
            canvas.drawPath(it.first, paint)
        }
    }
}