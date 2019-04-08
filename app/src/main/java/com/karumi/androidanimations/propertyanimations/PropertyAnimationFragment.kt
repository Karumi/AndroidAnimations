package com.karumi.androidanimations.propertyanimations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.recyclical.datasource.dataSourceOf
import com.afollestad.recyclical.setup
import com.karumi.androidanimations.R
import com.karumi.androidanimations.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_property_animation.*

class PropertyAnimationFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_property_animation, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureAllAnimations()
    }

    private fun configureAllAnimations() {
        val layoutManager = LinearLayoutManager(requireContext())
        val dataSource = dataSourceOf()

        allAnimations.setup {
            withLayoutManager(layoutManager)
            withDataSource(dataSource)
        }
    }
}