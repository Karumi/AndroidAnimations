package com.karumi.androidanimations.layouttransition

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.LayoutRes
import androidx.transition.ChangeBounds
import androidx.transition.Scene
import androidx.transition.Transition
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import androidx.transition.TransitionValues
import com.karumi.androidanimations.R

interface ExerciseLayoutTransition {
    class VH(itemView: View) : com.afollestad.recyclical.ViewHolder(itemView) {
        val masterScene: ViewGroup = itemView.findViewById(R.id.masterScene)
    }

    class Binder(val getContext: () -> Context) {
        operator fun invoke(receiver: VH) =
            receiver.bind()

        private fun VH.bind() {
            masterScene.findViewById<Button>(R.id.button).setOnClickListener {
                animateToEndState(this)
            }
        }

        private fun animateToEndState(viewHolder: VH) {
            animateToLayout(
                viewHolder,
                R.layout.view_layout_transition_exercise_end_state
            ) { animateToStartState(viewHolder) }
        }

        private fun animateToStartState(viewHolder: VH) {
            animateToLayout(
                viewHolder,
                R.layout.view_layout_transition_start_state
            ) { animateToEndState(viewHolder) }
        }

        private fun animateToLayout(
            viewHolder: VH,
            @LayoutRes layoutRes: Int,
            onEnterTransition: () -> Unit
        ) {
            val scene = Scene.getSceneForLayout(
                viewHolder.masterScene,
                layoutRes,
                getContext()
            )
            scene.setEnterAction {
                viewHolder.masterScene.findViewById<Button>(R.id.button)
                    .setOnClickListener {
                        onEnterTransition()
                    }
            }

            TransitionManager.go(
                scene,
                TransitionSet()
                    .addTransition(RotateTransition())
                    .addTransition(ChangeBounds())
            )
        }
    }
}

class RotateTransition : Transition() {
    companion object {
        private const val ROTATION_KEY = "androidanimations:rotation:rotation"
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        val rotation = transitionValues.view.rotation
        transitionValues.values[ROTATION_KEY] = rotation
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        val rotation = transitionValues.view.rotation
        transitionValues.values[ROTATION_KEY] = rotation
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        startValues ?: return null
        endValues ?: return null

        val startRotation = startValues.values.getOrElse(ROTATION_KEY, { 0f }) as Float
        val endRotation = endValues.values.getOrElse(ROTATION_KEY, { 0f }) as Float

        return ObjectAnimator.ofFloat(endValues.view, View.ROTATION, startRotation, endRotation)
    }
}