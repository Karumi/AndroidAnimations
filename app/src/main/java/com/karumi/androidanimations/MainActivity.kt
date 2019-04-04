package com.karumi.androidanimations

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.recyclical.ViewHolder
import com.afollestad.recyclical.datasource.dataSourceOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.karumi.androidanimations.extensions.exhaustive
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureSections()
    }

    private fun configureSections() {
        val layoutManager = LinearLayoutManager(this)
        val dataSource = dataSourceOf(*Section.values())

        allSections.setup {
            withLayoutManager(layoutManager)
            withDataSource(dataSource)

            withItem<Section>(R.layout.view_section) {
                onBind(::SectionViewHolder) { _, item ->
                    name.text = when (item) {
                        Section.ViewAnimator -> R.string.section_view_animator_framework
                        Section.PropertyAnimation -> R.string.section_property_animation_framework
                        Section.AnimatedVector -> R.string.section_animated_vector_drawable
                    }.resolve()
                }

                onClick { _, item ->
                    when (item) {
                        Section.ViewAnimator -> R.string.section_view_animator_framework
                        Section.PropertyAnimation -> R.string.section_property_animation_framework
                        Section.AnimatedVector -> R.string.section_animated_vector_drawable
                    }.exhaustive
                }
            }
        }
    }

    private fun Int.resolve(): String = getString(this)

    private enum class Section {
        ViewAnimator, PropertyAnimation, AnimatedVector
    }

    private class SectionViewHolder(itemView: View) : ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.sectionName)
    }
}
