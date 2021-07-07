package com.karumi.androidanimations.pathdrawing

import android.graphics.Matrix
import android.graphics.Path
import android.graphics.RectF
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karumi.androidanimations.R
import com.karumi.androidanimations.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_path_drawing.*

class PathDrawingFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_path_drawing, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         *  ┌───────┬─────────┬>──────────────────┐
         *  │(-1,-1)│       ┌─┘ ╲                 │
         *  ├───────┘     ┌─┘  │ ╲                │
         *  │           ┌─┘       ╲               │
         *  │     ┌─────┴┐     │   ╲ ┌─────┐      │
         *  │     │A line│          ╲┤B arc├──╲   │
         *  │     ├──────┘     │     └─────┘   ╲  │
         *  │   ┌─┘                             ╲ │
         * ┌┴┐┌─┘              │                 ╲│
         * │█├┴ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┬─▼
         * └┬┘                 │              ┌─┘ │
         *  │ ▲                             ┌─┘   │
         *  │  ╲  ┌─────┐      │     ┌──────┤     │
         *  │   ╲ │D arc│            │C line│     │
         *  │    ╲┴─────┴──╲   │     └┬─────┘     │
         *  │               ╲       ┌─┘           │
         *  │                ╲ │  ┌─┘     ┌───────┤
         *  │                 ╲ ┌─┘       │ (1,1) │
         *  └──────────────────<┴─────────┴───────┘
         */

        val path = Path().apply {
            moveTo(-1f, 0f)
            /* A line */ lineTo(0f, -1f)
            /* B arc  */ arcTo(RectF(0f, -2f, 2f, 0f), 180f, -90f)
            /* C line */ lineTo(0f, 1f)
            /* D arc  */arcTo(RectF((-2f), 0f, 0f, 2f), 0f, -90f)
            transform(transformMatrix)
        }

        val xAxisPath = Path().apply {
            moveTo(-1f, 0f)
            lineTo(1f, 0f)
            transform(transformMatrix)
        }

        val yAxisPath = Path().apply {
            moveTo(0f, (-1f))
            lineTo(0f, 1f)
            transform(transformMatrix)
        }

        val boundsPath = Path().apply {
            addRect(RectF(-1f, -1f, 1f, 1f), Path.Direction.CW)
            transform(transformMatrix)
        }

        paintView += path to 0xFFE62A65.toInt()
        paintView += xAxisPath to 0xFF323443.toInt()
        paintView += yAxisPath to 0xFF323443.toInt()
        paintView += boundsPath to 0xFF323443.toInt()
    }

    private val transformMatrix: Matrix by lazy {
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)

        val scaleFactor = displayMetrics.widthPixels / 2.2f

        Matrix().apply {
            setScale(scaleFactor, scaleFactor)
            preTranslate(1.1f, 1.1f)
        }
    }
}