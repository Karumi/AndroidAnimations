package com.karumi.androidanimations.coordinatorlayout

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Matrix
import android.graphics.Rect
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Scroller
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
        val dataSource = dataSourceOf((0..10).map { Item(it) })

        allItems.setup {
            withLayoutManager(layoutManager)
            withDataSource(dataSource)
            withItem<Item>(R.layout.view_custom_behavior_item) {
                onBind(::VH) { _, item ->
                    name.text = "#${item.id}"
                }
            }
        }

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
                outRect.right = requireContext().resources.getDimension(R.dimen.margin4x).toInt()
            }
        }
        allItems.addItemDecoration(dividerItemDecoration)
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

    private val parallaxFactor = 0.2f
    private var isAnimatingFling = false
    private var previousX = 0

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: ImageView,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        isAnimatingFling = false
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
        val matrix = Matrix(child.imageMatrix)
        matrix.postTranslate(-parallaxFactor * dxConsumed.toFloat(), -dyConsumed.toFloat())
        child.imageMatrix = matrix
    }

    override fun onNestedFling(
        coordinatorLayout: CoordinatorLayout,
        child: ImageView,
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        val recyclerView = target as RecyclerView
        val canScroll = recyclerView.canScrollHorizontally(velocityX.toInt())

        if (!canScroll) {
            return false
        }

        val scroller = Scroller(coordinatorLayout.context)
        scroller.fling(
            0,
            0,
            velocityX.toInt(),
            velocityY.toInt(),
            0,
            0,
            Int.MIN_VALUE,
            Int.MAX_VALUE
        )

        previousX = 0
        isAnimatingFling = true
        ValueAnimator.ofFloat(0f, 1f)
            .apply {
                duration = scroller.duration.toLong()
                start()
            }
            .addUpdateListener {
                if (!isAnimatingFling) {
                    it.cancel()
                    return@addUpdateListener
                }

                scroller.computeScrollOffset()
                val matrix = Matrix(child.imageMatrix)
                val currX = scroller.currX
                val xDiff = currX - previousX.toFloat()
                matrix.postTranslate(-parallaxFactor * xDiff, 0f)
                child.imageMatrix = matrix
                previousX = currX
            }
        return true
    }

}