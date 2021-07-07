package com.karumi.androidanimations.pathdrawing

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
            moveTo((-1f).scaled, 0f.scaled)
            /* A line */ lineTo(0f.scaled, (-1f).scaled)
            /* B arc  */ arcTo(RectF(0f.scaled, (-2f).scaled, 2f.scaled, 0f.scaled), 180f, -90f)
            /* C line */ lineTo(0f.scaled, 1f.scaled)
            /* D arc  */ arcTo(RectF((-2f).scaled, 0f.scaled, 0f.scaled, 2f.scaled), 0f, -90f)
        }

        val xAxisPath = Path().apply {
            moveTo((-1f).scaled, 0f.scaled)
            lineTo(1f.scaled, 0f.scaled)
        }

        val yAxisPath = Path().apply {
            moveTo(0f.scaled, (-1f).scaled)
            lineTo(0f.scaled, 1f.scaled)
        }

        val boundsPath = Path().apply {
            addRect(RectF((-1f).scaled, (-1f).scaled, 1f.scaled, 1f.scaled), Path.Direction.CW)
        }

        paintView += path to 0xFFE62A65.toInt()
        paintView += xAxisPath to 0xFF323443.toInt()
        paintView += yAxisPath to 0xFF323443.toInt()
        paintView += boundsPath to 0xFF323443.toInt()
    }

    private val Float.scaled: Float
        get() {
            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)

            val scaleFactor = displayMetrics.widthPixels / 2.2f
            return (this + 1.1f) * scaleFactor
        }
}