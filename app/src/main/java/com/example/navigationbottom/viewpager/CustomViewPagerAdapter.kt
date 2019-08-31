package com.example.navigationbottom.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

class CustomViewPagerAdapter(fragmentManager: FragmentManager,
                                    private val contentManager: ContentManager,
                                    private val contentCreator: ContentCreator) :
    FragmentStatePagerAdapter(fragmentManager) {

    interface ContentManager {
        fun getCount(): Int
        fun removeItem(position: Int)
    }
    interface ContentCreator {
        fun getItem(position: Int): Fragment
    }

    fun removeItem(position: Int) {
        contentManager.removeItem(position)
    }

    override fun getCount(): Int {
        return contentManager.getCount()
    }

    override fun getItem(position: Int): Fragment {
        return contentCreator.getItem(position)
    }

    override fun getItemPosition(item: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}