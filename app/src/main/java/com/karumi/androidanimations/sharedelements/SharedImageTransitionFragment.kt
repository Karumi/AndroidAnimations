package com.karumi.androidanimations.sharedelements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.AutoTransition
import androidx.transition.ChangeBounds
import androidx.transition.ChangeTransform
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import androidx.transition.TransitionSet
import com.karumi.androidanimations.R
import com.karumi.androidanimations.base.BaseFragment
import com.karumi.androidanimations.common.CircularReveal
import com.karumi.androidanimations.common.TextResize
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_shared_image_transition.*

class SharedImageTransitionFragment : BaseFragment() {

    companion object {
        val sharedElementsTransition
            get() = TransitionSet()
                .addTransition(
                    TransitionSet()
                        .addTransition(ChangeTransform())
                        .addTransition(ChangeBounds())
                        .addTransition(TextResize())
                        .addTarget(R.id.userName)
                ).addTransition(
                    TransitionSet()
                        .addTransition(AutoTransition())
                        .addTransition(ChangeTransform())
                        .addTransition(CircularReveal())
                        .addTarget(R.id.avatar)
                )
        val defaultTransition
            get() = Fade()
    }

    private val args: SharedImageTransitionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_shared_image_transition, container, false).also {
        fadeInGradientViewAfterTransitionFinishes()
        enterTransition = defaultTransition
        postponeEnterTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fadeOutGradientViewBeforeClosingFragment()
        configureViews()
    }

    private fun fadeInGradientViewAfterTransitionFinishes() {
        sharedElementEnterTransition =
            sharedElementsTransition.addListener(object : TransitionListenerAdapter() {
                override fun onTransitionEnd(transition: Transition) {
                    val userNameGradient = userNameGradient ?: return
                    userNameGradient.animate()
                        .alpha(0.5f)
                        .start()
                }
            })
    }

    private fun fadeOutGradientViewBeforeClosingFragment() {
        requireActivity().onBackPressedDispatcher.addCallback {
            val userNameGradient = userNameGradient ?: return@addCallback false
            userNameGradient.animate()
                .alpha(0f)
                .apply { duration = 20 }
                .withEndAction {
                    userNameGradient.visibility = View.GONE
                    requireActivity()
                        .findNavController(R.id.hostFragment)
                        .popBackStack()
                }
                .start()
            return@addCallback true
        }
    }

    private fun configureViews() {
        userName.text = args.userFullName
        Picasso.get()
            .load(args.avatarUrl)
            .fit()
            .noFade()
            .centerCrop()
            .into(avatar, object : Callback {
                override fun onSuccess() {
                    startPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {
                    startPostponedEnterTransition()
                }
            })
    }
}