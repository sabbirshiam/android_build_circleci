package com.example.navigationbottom.viewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import com.example.navigationbottom.viewpager.views.FirstFragment
import com.example.navigationbottom.viewpager.views.SecondFragment
import com.example.navigationbottom.viewpager.views.ThirdFragment
import com.shiam.navigationbottom.R

import kotlinx.android.synthetic.main.activity_view_pager.*
import kotlinx.android.synthetic.main.content_view_pager.*


class ViewPagerActivity : AppCompatActivity() {

    private var pageCount = 2

    private var viewList = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        setSupportActionBar(toolbar)
        initializeViewList()
        vpPager?.offscreenPageLimit = 9
        vpPager?.adapter = CustomViewPagerAdapter(supportFragmentManager,
            object : CustomViewPagerAdapter.ContentManager {
                override fun getCount(): Int {
                    return getItemCount()
                }

                override fun removeItem(position: Int) {
                    removeViewItem(position)
                }

                override fun getItemPosition(item: Any): Int {
//                    return PagerAdapter.POSITION_UNCHANGED
                     var index = viewList.indexOf(item as Fragment)
                    return when{
                        index >= 0 -> index
                        else -> PagerAdapter.POSITION_NONE
                    }
                }
            },
            object : CustomViewPagerAdapter.ContentCreator {
                override fun getItem(position: Int): Fragment {
                    return getItemByPosition(position)
                }
            })

        fab.setOnClickListener { view ->
            when {
                pageCount > 2 -> {
                    var fragments = (vpPager?.adapter as? CustomViewPagerAdapter)?.getRegisteredFragment(pageCount - 2)
                    (vpPager?.adapter as? CustomViewPagerAdapter)?.removeItem(pageCount - 2)
                    pageCount=2
                    vpPager?.adapter?.notifyDataSetChanged()
                    vpPager?.setCurrentItem(pageCount, false)
                }
                else -> {
                    pageCount++
                    if (viewList.size < 3) { viewList.add(1, SecondFragment())}
                    vpPager?.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    private fun removeViewItem(position: Int) {
        viewList.removeAt(position)
    }

    private fun initializeViewList() {
        viewList.add(0, FirstFragment())
        viewList.add(1, SecondFragment())
        viewList.add(2, ThirdFragment())
    }

    private fun getItemByPosition(position: Int): Fragment {
        return viewList[position]
    }

    private fun getItemCount(): Int {
        return pageCount
    }
}
