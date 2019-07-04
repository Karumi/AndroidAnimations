package com.karumi.androidanimations.motionlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.karumi.androidanimations.R
import kotlinx.android.synthetic.main.fragment_motion_layout.*

class MotionLayoutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_motion_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ViewPagerAdapter(requireFragmentManager())
        adapter.addPage("BitterSweet", R.layout.motion_layout_view_pager_page1)
        adapter.addPage("Java", R.layout.motion_layout_view_pager_page2)
        adapter.addPage("Gulava", R.layout.motion_layout_view_pager_page3)
        pager.adapter = adapter
        tabLayout.setupWithViewPager(pager)
        pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                val numberOfPages = 3
                container.progress = (position + positionOffset) / (numberOfPages - 1)
            }

            override fun onPageSelected(position: Int) {
            }

        })
    }
}

class Page : Fragment() {

    private var layoutId = 0

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        if (args != null) {
            layoutId = args.getInt("layout")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(layoutId, container, false)
    }
}

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragmentList = ArrayList<Fragment>()
    private val fragmentTitleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    private fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }

    fun addPage(s: String, layout: Int) {
        val page = Page()
        val arg = Bundle()
        arg.putInt("layout", layout)
        page.arguments = arg
        addFragment(page, s)
    }
}