package com.karumi.androidanimations.sharedelements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.recyclical.datasource.dataSourceOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.karumi.androidanimations.R
import com.karumi.androidanimations.base.BaseFragment
import com.karumi.androidanimations.extensions.sharedElements
import kotlinx.android.synthetic.main.fragment_view_animation.*

class SharedElementsFragment : BaseFragment() {

    private val sharedImageTransition =
        SharedImageTransition.Binder(::requireContext, ::configureSharedImageTransition)
    private val sharedBackgroundPathTransition =
        SharedBackgroundPathTransition.Binder(
            ::requireContext,
            ::configureSharedBackgroundPathTransition
        )
    private val sharedElementsExerciseTransition =
        SharedElementsExerciseTransition.Binder(
            ::requireContext,
            ::configureSharedElementsExerciseTransition
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_shared_elements, container, false).apply {
        postponeEnterTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureAllAnimations()
    }

    private fun configureSharedImageTransition(
        avatarUrl: String,
        userFullName: String,
        sharedElements: SharedImageTransition.SharedElements
    ) {
        sharedElementEnterTransition = SharedImageTransitionFragment.sharedElementsTransition
        exitTransition = SharedImageTransitionFragment.defaultTransition

        val args = SharedImageTransitionFragmentArgs(avatarUrl, userFullName)
        findNavController().navigate(
            R.id.action_sharedElementsFragment_to_sharedImageTransitionFragment,
            args.toBundle(),
            null,
            sharedElements(sharedElements.avatar, sharedElements.userName)
        )
    }

    private fun configureSharedBackgroundPathTransition(
        sharedElements: SharedBackgroundPathTransition.SharedElements
    ) {
        sharedElementEnterTransition =
            SharedBackgroundPathTransitionFragment.sharedElementTransition
        exitTransition = SharedBackgroundPathTransitionFragment.defaultTransition

        findNavController().navigate(
            R.id.action_sharedElementsFragment_to_sharedBackgroundPathTransitionFragment,
            null,
            null,
            sharedElements(sharedElements.background)
        )
    }

    private fun configureSharedElementsExerciseTransition(
        color: SharedElementsExerciseTransition.Color,
        sharedElements: SharedElementsExerciseTransition.SharedElements
    ) {
        sharedElementEnterTransition =
            SharedElementsExerciseTransitionFragment.exitSharedElementTransition
        exitTransition = SharedElementsExerciseTransitionFragment.defaultTransition

        val args = SharedElementsExerciseTransitionFragmentArgs(color)

        findNavController().navigate(
            R.id.action_sharedElementsFragment_to_sharedElementsExerciseTransitionFragment,
            args.toBundle(),
            null,
            sharedElements(sharedElements.circularView)
        )
    }

    private fun configureAllAnimations() {
        val layoutManager = LinearLayoutManager(requireContext())
        val dataSource = dataSourceOf(
            ShareImage,
            BackgroundPath,
            Exercise
        )

        allAnimations.setup {
            withLayoutManager(layoutManager)
            withDataSource(dataSource)

            withItem<ShareImage>(R.layout.view_shared_image_element_transition) {
                onBind(SharedImageTransition::VH) { _, _ ->
                    sharedImageTransition(this) {
                        startPostponedEnterTransition()
                    }
                }
            }

            withItem<BackgroundPath>(R.layout.view_shared_background_path_transition) {
                onBind(SharedBackgroundPathTransition::VH) { _, _ ->
                    sharedBackgroundPathTransition(this) {
                        startPostponedEnterTransition()
                    }
                }
            }

            withItem<Exercise>(R.layout.view_shared_elements_exercise_transition) {
                onBind(SharedElementsExerciseTransition::VH) { _, _ ->
                    sharedElementsExerciseTransition(this) {
                        startPostponedEnterTransition()
                    }
                }
            }
        }
    }

    object ShareImage
    object BackgroundPath
    object Exercise
}