package com.karumi.androidanimations.sharedelements

import android.annotation.SuppressLint
import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.ArcMotion
import androidx.transition.ChangeBounds
import androidx.transition.ChangeTransform
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import androidx.transition.TransitionSet
import com.karumi.androidanimations.R
import com.karumi.androidanimations.base.BaseFragment
import com.karumi.androidanimations.common.CircularView
import com.karumi.androidanimations.common.GlobalPositionTransition
import com.karumi.androidanimations.common.RadiusTransition
import com.karumi.androidanimations.extensions.px
import kotlinx.android.synthetic.main.fragment_shared_elements_exercise_transition.*

class SharedElementsExerciseTransitionFragment : BaseFragment() {

    companion object {
        private const val easinessFactor = 1.2f

        private val arcMotion = ArcMotion().apply {
            minimumHorizontalAngle = 45f
            minimumVerticalAngle = 45f
        }

        private val Transition.accelerated: Transition
            get() {
                interpolator = AccelerateInterpolator(easinessFactor)
                return this
            }

        private val Transition.decelerated: Transition
            get() {
                interpolator = DecelerateInterpolator(easinessFactor)
                return this
            }

        private val Transition.withArc: Transition
            get() {
                setPathMotion(arcMotion)
                return this
            }

        val enterSharedElementTransition
            get() = TransitionSet()
                .addTransition(GlobalPositionTransition().accelerated.withArc)
                .addTransition(RadiusTransition().decelerated)
                .apply { ordering = TransitionSet.ORDERING_SEQUENTIAL }

        val exitSharedElementTransition
            get() = TransitionSet()
                .addTransition(RadiusTransition().accelerated)
                .addTransition(
                    TransitionSet()
                        .addTransition(ChangeBounds())
                        .addTransition(ChangeTransform())
                        .decelerated
                        .withArc
                ).apply { ordering = TransitionSet.ORDERING_SEQUENTIAL }

        val defaultTransition get() = Fade()
    }

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
        enterTransition = defaultTransition
        exitTransition = defaultTransition
        sharedElementReturnTransition = exitSharedElementTransition
        sharedElementEnterTransition = fadeInColorNameAfterTransitionFinishes()
        postponeEnterTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val background = args.color.backgroundView ?: return

        background.viewTreeObserver.addOnGlobalLayoutListener {
            colorName ?: return@addOnGlobalLayoutListener
            configureBackgroundView(background)
            startPostponedEnterTransition()
        }

        fadeOutColorNameBeforeClosingFragment()
    }

    @SuppressLint("ResourceAsColor")
    private fun configureBackgroundView(background: CircularView) {
        colorName.text = args.color.nameString
        background.setBackgroundColor(args.color.colorInt)
        val offset = 24.px
        background.center = PointF(0f, 0f)
        background.radius = Math.hypot(
            background.measuredWidth.toDouble() + offset,
            background.measuredHeight.toDouble() + offset
        ).toFloat()
        background.left -= offset
        background.top -= offset
    }

    private fun fadeInColorNameAfterTransitionFinishes() = enterSharedElementTransition.apply {
        addListener(object : TransitionListenerAdapter() {
            override fun onTransitionEnd(transition: Transition) {
                colorName ?: return

                colorName.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .start()
            }
        })
    }

    private fun fadeOutColorNameBeforeClosingFragment() {
        requireActivity().onBackPressedDispatcher.addCallback {
            val colorName = colorName ?: return@addCallback false
            colorName.animate()
                .alpha(0f)
                .translationY(24.px.toFloat())
                .apply { duration = 200 }
                .withEndAction {
                    colorName.visibility = View.GONE
                    requireActivity()
                        .findNavController(R.id.hostFragment)
                        .popBackStack()
                }
                .start()
            return@addCallback true
        }
    }

    private val SharedElementsExerciseTransition.Color.backgroundView: CircularView?
        get() = when (this) {
            SharedElementsExerciseTransition.Color.BitterSweet -> background1
            SharedElementsExerciseTransition.Color.Java -> background2
            SharedElementsExerciseTransition.Color.Molten -> background3
            SharedElementsExerciseTransition.Color.Gulava -> background4
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