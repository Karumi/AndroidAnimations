package com.karumi.androidanimations.coordinatorlayout

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
import kotlinx.android.synthetic.main.fragment_view_animation.*

class CoordinatorLayoutFragment : BaseFragment() {

    private val coordinatorLayoutExample = CoordinatorLayoutExample.Binder(::requireContext)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_coordinator_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureExamplesList()
    }

    private fun configureExamplesList() {
        val layoutManager = LinearLayoutManager(requireContext())
        val dataSource = dataSourceOf(
            *Example.values()
        )

        allAnimations.setup {
            withLayoutManager(layoutManager)
            withDataSource(dataSource)

            withItem<Example>(R.layout.view_coordinator_layout_example) {
                onBind(CoordinatorLayoutExample::VH) { _, item ->
                    coordinatorLayoutExample(this, item) {
                        val directions = when (item) {
                            Example.Default -> CoordinatorLayoutFragmentDirections
                                .actionCoordinatorLayoutFragmentToDefaultBehaviorFragment()
                            Example.Custom -> CoordinatorLayoutFragmentDirections
                                .actionCoordinatorLayoutFragmentToCustomBehaviorFragment()
                        }
                        findNavController().navigate(directions)
                    }
                }
            }
        }
    }

    enum class Example {
        Default, Custom
    }
}