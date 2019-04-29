package com.karumi.androidanimations.sharedelements

import android.annotation.SuppressLint
import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.karumi.androidanimations.R
import com.karumi.androidanimations.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_shared_elements_exercise_transition.*

class SharedElementsExerciseTransitionFragment : BaseFragment() {

    private val args: SharedElementsExerciseTransitionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_shared_elements_exercise_transition,
        container,
        false
    ).also {
        postponeEnterTransition()
        TODO("""
            | Create a transition in this very same fragment that makes the selected CircularView
            | transform into the background of this fragment. Use a PathMotion to make the view
            | follow an arc.
            | Make the color name view appear with a slight up movement and a fade effect.
        """.trimMargin())
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        background.viewTreeObserver.addOnGlobalLayoutListener {
            background ?: return@addOnGlobalLayoutListener

            background.setBackgroundColor(args.color.colorInt)
            colorName.text = args.color.nameString
            background.center = PointF(
                background.measuredWidth / 2f,
                background.measuredHeight / 2f
            )
            background.radius = Math.max(background.measuredWidth, background.measuredHeight) * 2f
            startPostponedEnterTransition()
        }
    }

    private val SharedElementsExerciseTransition.Color.colorInt: Int
        @ColorRes get() = when (this) {
            SharedElementsExerciseTransition.Color.BitterSweet -> R.color.bittersweet
            SharedElementsExerciseTransition.Color.Java -> R.color.java
            SharedElementsExerciseTransition.Color.Molten -> R.color.molten
            SharedElementsExerciseTransition.Color.Gulava -> R.color.gulava
        }.let { ContextCompat.getColor(requireContext(), it) }

    private val SharedElementsExerciseTransition.Color.nameString: String
        get() = when (this) {
            SharedElementsExerciseTransition.Color.BitterSweet -> R.string.shared_elements_exercise_color_bittersweet
            SharedElementsExerciseTransition.Color.Java -> R.string.shared_elements_exercise_color_java
            SharedElementsExerciseTransition.Color.Molten -> R.string.shared_elements_exercise_color_molten
            SharedElementsExerciseTransition.Color.Gulava -> R.string.shared_elements_exercise_color_gulava
        }.let { requireContext().getString(it) }
}