package com.example.navigationbottom.viewpager

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.example.navigationbottom.viewpager.views.SecondFragment
import android.util.SparseArray



class CustomViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val contentManager: ContentManager,
    private val contentCreator: ContentCreator
) : FragmentStatePagerAdapter(fragmentManager) {

    interface ContentManager {
        fun getCount(): Int
        fun removeItem(position: Int)
        fun getItemPosition(item: Any): Int
    }

    interface ContentCreator {
        fun getItem(position: Int): Fragment
    }

    // Sparse array to keep track of registered fragments in memory
    private val registeredFragments = SparseArray<Fragment>()

    fun removeItem(position: Int) {
        registeredFragments.remove(position)
        contentManager.removeItem(position)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        registeredFragments.put(position, fragment)
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        registeredFragments.remove(position)
        super.destroyItem(container, position, `object`)
    }

    override fun getCount(): Int {
        return contentManager.getCount()
    }

    override fun getItem(position: Int): Fragment {
        return contentCreator.getItem(position)
    }

    // Returns the fragment for the position (if instantiated)
    fun getRegisteredFragment(position: Int): Fragment {
        return registeredFragments.get(position)
    }

    override fun getItemPosition(item: Any): Int {
        return contentManager.getItemPosition(item)
    }
}