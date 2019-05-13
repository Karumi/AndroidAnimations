package com.karumi.androidanimations.common

import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.PointF
import android.util.Property

class PathProperty<T>(private val mProperty: Property<T, PointF>, path: Path) :
    Property<T, Float>(Float::class.java, mProperty.name) {
    private val pathMeasure: PathMeasure = PathMeasure(path, false)
    private val pathLength: Float
    private val position = FloatArray(2)
    private val pointF = PointF()
    private var currentFraction: Float = 0.toFloat()

    init {
        pathLength = pathMeasure.length
    }

    override fun get(target: T): Float? {
        return currentFraction
    }

    override fun set(target: T, fraction: Float?) {
        currentFraction = fraction!!
        pathMeasure.getPosTan(pathLength * fraction, position, null)
        pointF.x = position[0]
        pointF.y = position[1]
        mProperty.set(target, pointF)
    }
}