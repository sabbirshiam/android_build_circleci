package com.example.navigationbottom.viewpager

import android.view.ViewGroup
import android.util.SparseArray
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


    // Sparse array to keep track of registered fragments in memory
    private val registeredFragments = SparseArray<Fragment>()


    // Register the fragment when the item is instantiated
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        registeredFragments.put(position, fragment)
        return fragment
    }

    // Unregister when the item is inactive
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //registeredFragments.remove(position)
        //container.removeViewAt(position)
        super.destroyItem(container, position, `object`)
    }

    // Returns the fragment for the position (if instantiated)
    fun getRegisteredFragment(position: Int): Fragment {
        return registeredFragments.get(position)
    }

    fun removeItem(position: Int) {
        val index = registeredFragments.indexOfKey(position)
        when {
            index > 0 -> {
                registeredFragments.removeAt(index)
                contentManager.removeItem(index)
            }
        }
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