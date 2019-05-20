package com.karumi.androidanimations.coordinatorlayout

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
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
        val dataSource = dataSourceOf((0..100).map { Item(it) })

        allItems.setup {
            withLayoutManager(layoutManager)
            withDataSource(dataSource)
            withItem<Item>(R.layout.view_custom_behavior_item) {
                onBind(::VH) { _, item ->
                    name.text = "#${item.id}"
                }
            }
        }

        val pagerSnapHelper = LinearSnapHelper()
        pagerSnapHelper.attachToRecyclerView(allItems)
    }

    class VH(itemView: View) : ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
    }

    data class Item(val id: Int)
}

class ParallaxBehavior(
    context: Context,
    attrs: AttributeSet
) : CoordinatorLayout.Behavior<ImageView>(context, attrs) {
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: ImageView,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        Log.d("GERSIO", "Axes: $axes - Type: $type")
        return true
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: ImageView,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        Log.d("GERSIO", "scroll: $dxConsumed - $dxUnconsumed - $type")
    }

    override fun onNestedFling(
        coordinatorLayout: CoordinatorLayout,
        child: ImageView,
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        Log.d("GERSIO", "fling: $velocityX")
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
    }
}