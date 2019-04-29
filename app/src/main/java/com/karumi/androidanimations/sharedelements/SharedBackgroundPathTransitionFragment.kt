package com.karumi.androidanimations.sharedelements

import android.graphics.PointF
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.ChangeBounds
import androidx.transition.ChangeTransform
import androidx.transition.Slide
import androidx.transition.TransitionSet
import com.karumi.androidanimations.R
import com.karumi.androidanimations.base.BaseFragment
import com.karumi.androidanimations.common.CircularView
import com.karumi.androidanimations.common.RadiusTransition
import kotlinx.android.synthetic.main.fragment_shared_background_path_transition.*

class SharedBackgroundPathTransitionFragment : BaseFragment() {

    companion object {
        val sharedElementTransition
            get() = TransitionSet()
                .addTransition(ChangeBounds())
                .addTransition(ChangeTransform())
                .addTransition(RadiusTransition())
                .addTarget(CircularView::class.java)
        val defaultTransition
            get() = Slide(Gravity.TOP).excludeTarget(R.id.background, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_shared_background_path_transition,
        container,
        false
    ).also {
        postponeEnterTransition()
        enterTransition = defaultTransition
        sharedElementEnterTransition = sharedElementTransition
        sharedElementReturnTransition = sharedElementTransition
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        background.viewTreeObserver.addOnGlobalLayoutListener {
            background ?: return@addOnGlobalLayoutListener
            background.radius = background.measuredWidth.toFloat() / 2
            background.center =
                PointF(
                    background.measuredWidth.toFloat() / 2,
                    background.measuredHeight.toFloat() / 2
                )
            startPostponedEnterTransition()
        }
    }
}