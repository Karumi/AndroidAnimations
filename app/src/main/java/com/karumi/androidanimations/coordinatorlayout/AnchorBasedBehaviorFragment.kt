package com.karumi.androidanimations.coordinatorlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.recyclical.datasource.dataSourceOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
import com.karumi.androidanimations.R
import com.karumi.androidanimations.base.BaseFragment
import com.karumi.androidanimations.extensions.px
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_anchor_based_behavior.*

class AnchorBasedBehaviorFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_anchor_based_behavior, container, false).also {
        setToolbarScrollFlags(SCROLL_FLAG_SCROLL or SCROLL_FLAG_SNAP or SCROLL_FLAG_ENTER_ALWAYS)
        setToolbarHeight(200.px)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureItems()
    }

    override fun onDestroyView() {
        setToolbarScrollFlags(0)
        setToolbarHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
        super.onDestroyView()
    }

    private fun setToolbarScrollFlags(flags: Int) {
        val toolbar = requireActivity().collapsingToolbarLayout
        val params = toolbar.layoutParams as AppBarLayout.LayoutParams
        toolbar.isTitleEnabled = true
        params.scrollFlags = flags
        toolbar.layoutParams = params
    }

    private fun setToolbarHeight(heightInPx: Int) {
        val appBarLayout = requireActivity().appBarLayout
        val params = appBarLayout.layoutParams as CoordinatorLayout.LayoutParams
        params.height = heightInPx
        appBarLayout.layoutParams = params
    }

    private fun configureItems() {
        val layoutManager = LinearLayoutManager(requireContext())
        val dataSource = dataSourceOf(
            *(0..100).map { Item }.toTypedArray()
        )

        allItems.setup {
            withLayoutManager(layoutManager)
            withDataSource(dataSource)

            withItem<Item>(R.layout.view_anchor_based_behavior_item) {
                onBind(::VH) { _, _ -> }
            }
        }
    }

    class VH(itemView: View) : com.afollestad.recyclical.ViewHolder(itemView)

    object Item
}