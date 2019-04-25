package com.karumi.androidanimations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.recyclical.ViewHolder
import com.afollestad.recyclical.datasource.dataSourceOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.karumi.androidanimations.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureSections()
    }

    private fun configureSections() {
        val layoutManager = LinearLayoutManager(requireContext())
        val dataSource = dataSourceOf(*Section.values())

        allSections.setup {
            withLayoutManager(layoutManager)
            withDataSource(dataSource)

            withItem<Section>(R.layout.view_section) {
                onBind(::SectionViewHolder) { _, item ->
                    name.text = when (item) {
                        Section.PathDrawing -> R.string.section_path_drawing
                        Section.ViewAnimation -> R.string.section_view_animation_framework
                        Section.PropertyAnimation -> R.string.section_property_animation_framework
                        Section.AnimatedVector -> R.string.section_animated_vector_drawable
                        Section.LayoutTransition -> R.string.section_layout_transition
                    }.resolve()
                }

                onClick { _, item ->
                    val directions = when (item) {
                        Section.PathDrawing -> MainFragmentDirections.actionMainFragmentToPathDrawingFragment()
                        Section.ViewAnimation -> MainFragmentDirections.actionMainFragmentToViewAnimatorFragment()
                        Section.PropertyAnimation -> MainFragmentDirections.actionMainFragmentToPropertyAnimationFragment()
                        Section.AnimatedVector -> MainFragmentDirections.actionMainFragmentToDrawableGraphicsAnimationFragment()
                        Section.LayoutTransition -> MainFragmentDirections.actionMainFragmentToLayoutTransitionFragment()
                    }
                    findNavController().navigate(directions)
                }
            }
        }
    }

    private enum class Section {
        PathDrawing, ViewAnimation, PropertyAnimation, AnimatedVector, LayoutTransition
    }

    private class SectionViewHolder(itemView: View) : ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.sectionName)
    }
}