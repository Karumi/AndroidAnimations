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
import com.karumi.androidanimations.R
import com.karumi.androidanimations.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_custom_behavior.*


@SuppressLint("RestrictedApi")
class CustomBehaviorFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_custom_behavior, container, false)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        val dataSource = dataSourceOf((0..10).map { Item(it) })

        allItems.setup {
            withLayoutManager(layoutManager)
            withDataSource(dataSource)
            withItem<Item>(R.layout.view_custom_behavior_item) {
                onBind(::VH) { _, item -> name.text = "#${item.id}" }
            }
            withHorizontalOffset(layoutManager)
        }

    }

    private fun RecyclicalSetup.withHorizontalOffset(layoutManager: LinearLayoutManager) {
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
                outRect.right =
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