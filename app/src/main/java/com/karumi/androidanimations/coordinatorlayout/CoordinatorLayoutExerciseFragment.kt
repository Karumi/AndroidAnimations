package com.karumi.androidanimations.coordinatorlayout

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.recyclical.RecyclicalSetup
import com.afollestad.recyclical.ViewHolder
import com.afollestad.recyclical.datasource.dataSourceOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.airbnb.lottie.LottieDrawable
import com.karumi.androidanimations.R
import com.karumi.androidanimations.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_coordinator_layout_exercise.*

class CoordinatorLayoutExerciseFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_coordinator_layout_exercise, container, false)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animationView.setAnimation(R.raw.material_wave_loading)
        animationView.repeatMode = LottieDrawable.RESTART
        animationView.repeatCount = LottieDrawable.INFINITE

        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        val dataSource = dataSourceOf((0..20).map { Item(it) })

        allItems.setup {
            withLayoutManager(layoutManager)
            withDataSource(dataSource)
            withItem<Item>(R.layout.view_coordinator_exercise) {
                onBind(::VH) { _, item -> name.text = "#${item.id}" }
            }
            withVerticalOffset(layoutManager)
        }
    }

    private fun RecyclicalSetup.withVerticalOffset(layoutManager: LinearLayoutManager) {
        val dividerItemDecoration = object : DividerItemDecoration(
            requireContext(),
            layoutManager.orientation
        ) {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.bottom =
                    requireContext().resources.getDimension(R.dimen.margin2x).toInt()
            }
        }
        allItems.addItemDecoration(dividerItemDecoration)
    }

    class VH(itemView: View) : ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
    }

    data class Item(val id: Int)
}