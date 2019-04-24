package com.karumi.androidanimations.pathdrawing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karumi.androidanimations.R
import com.karumi.androidanimations.base.BaseFragment

class PathDrawingFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_path_drawing, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TODO(
            """
            |Draw the exercise path on screen. You can use absolute coordinates or relative ones.
            |If you follow the relative path (pwn intended), you can use DisplayMetrics:
            |
            |  val displayMetrics = DisplayMetrics()
            |  requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
            |  displayMetrics.widthPixels
        """.trimMargin()
        )
    }
}