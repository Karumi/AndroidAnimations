package com.karumi.androidanimations.layouttransition

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.LayoutRes
import androidx.transition.Scene
import com.karumi.androidanimations.R

class ExerciseLayoutTransition {
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

            TODO(
                """
                | Create a custom Transition to rotate views automatically if their "rotation"
                | property changes.
                |
                | Use TransitionManager to animate views to the configured scene above.
                | Remember the animation has to rotate the current view with your own implementation
                | of a sharedElementTransition and move to the right position.
                """.trimMargin()
            )
        }
    }
}