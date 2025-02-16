package com.coindex.ui

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.coindex.R
import com.coindex.basic.BasicViewPager2Adapter
import com.coindex.basic.BindingActivity
import com.coindex.databinding.ActivityNavigationBinding
import com.coindex.ui.fragment.CoinsFragment
import com.coindex.ui.fragment.ExchangeFragment
import com.coindex.ui.fragment.FollowFragment
import com.coindex.ui.fragment.HomeFragment
import com.coindex.ui.fragment.PropertyFragment
import com.coindex.utils.LogUtils.logE
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

/**
 * 滑动版
 */
@Deprecated( message = "Use MainActivity instead",
    replaceWith = ReplaceWith(
        expression = "MainActivity",
        imports = ["com.coindex.ui"]
    ),
    level = DeprecationLevel.WARNING)
class NavigationActivity() : BindingActivity<ActivityNavigationBinding>(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragmentList = listOf(
            HomeFragment.newInstance("首页",""),
            CoinsFragment.newInstance("行情",""),
            ExchangeFragment.newInstance("交易",""),
            FollowFragment.newInstance("关注",""),
            PropertyFragment.newInstance("资产","")
        )
        val fragmentTitleList = listOf(
            "首页",
            "行情",
            "交易",
            "关注",
            "资产"
        )

        val adapter = BasicViewPager2Adapter(
            supportFragmentManager,
            lifecycle,
            fragmentList,
            fragmentTitleList
        )
        binding.vpNavigation.adapter = adapter

        // 缓存自定义布局视图
        val tabViews = fragmentTitleList.mapIndexed { index, title ->
            val customView = layoutInflater.inflate(R.layout.item_navigation_tab, null)
            customView.findViewById<ImageView>(R.id.tab_icon).setImageResource(
                when (index) {
                    0 -> R.drawable.ic_navigation_home
                    1 -> R.drawable.ic_navigation_home
                    2 -> R.drawable.ic_navigation_home
                    3 -> R.drawable.ic_navigation_home
                    4 -> R.drawable.ic_navigation_home
                    else -> R.drawable.ic_navigation_home
                }
            )
            customView.findViewById<TextView>(R.id.tab_text).text = title
            customView
        }

        TabLayoutMediator(binding.tabNavigation, binding.vpNavigation) { tab, position ->
            "tab.position=$position".logE()
            tab.customView = tabViews[position]
        }.attach()

        binding.tabNavigation.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                "onTabSelected=${tab?.position}".logE()
                updateTabState(tab?.customView,true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                "onTabUnselected=${tab?.position}".logE()
                updateTabState(tab?.customView,false)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                "onTabReselected=${tab?.position}".logE()
                updateTabState(tab?.customView,true)
            }
        })
        binding.tabNavigation.selectTab(binding.tabNavigation.getTabAt(0))
    }

    private fun updateTabState(customView: View?, isSelected: Boolean) {
        customView?.findViewById<ImageView>(R.id.tab_icon)?.let {
            it.setColorFilter( ContextCompat.getColor(this@NavigationActivity, if (isSelected) R.color.navigation_selected else R.color.navigation_unselected) )

        }
        customView?.findViewById<TextView>(R.id.tab_text)?.let {
            it.setTextColor( ContextCompat.getColor(this@NavigationActivity, if (isSelected) R.color.navigation_selected else R.color.navigation_unselected) )
        }
    }
}