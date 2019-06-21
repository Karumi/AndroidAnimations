package com.karumi.androidanimations.motionlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.karumi.androidanimations.R
import kotlinx.android.synthetic.main.fragment_motion_layout.*

class MotionLayoutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_motion_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        container.transitionToEnd()
        container.setDebugMode(MotionLayout.DEBUG_SHOW_PATH or MotionLayout.DEBUG_SHOW_PROGRESS)
    }
}