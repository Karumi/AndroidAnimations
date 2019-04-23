package com.karumi.androidanimations.drawablegraphicsanimation

import android.content.Context
import android.view.View
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.karumi.androidanimations.R

interface DrawableGraphicsLottieAnimation {
    class VH(itemView: View) : com.afollestad.recyclical.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.animationName)
        val animationView: LottieAnimationView = itemView.findViewById(R.id.animationView)
    }

    class Binder(val getContext: () -> Context) {
        operator fun invoke(receiver: VH) = receiver.bind()

        private fun VH.bind() {
            name.text =
                R.string.drawable_graphics_animation_with_lottie.let { getContext().getString(it) }
            animate(animationView)
        }

        private fun animate(view: LottieAnimationView) {
            view.setAnimation(R.raw.material_wave_loading)
            view.repeatMode = LottieDrawable.RESTART
            view.repeatCount = LottieDrawable.INFINITE
            view.playAnimation()
        }
    }
}