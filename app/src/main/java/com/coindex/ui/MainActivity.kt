package com.coindex.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import com.coindex.R
import com.coindex.basic.BindingActivity
import com.coindex.databinding.ActivityMainBinding
import com.coindex.ui.fragment.CoinsFragment
import com.coindex.ui.fragment.ExchangeFragment
import com.coindex.ui.fragment.FollowFragment
import com.coindex.ui.fragment.HomeFragment
import com.coindex.ui.fragment.PropertyFragment
import com.coindex.utils.LogUtils.logE
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


/**
 * 首页
 */
class MainActivity : BindingActivity<ActivityMainBinding>() {

    private val KEY_FRAGMENT_HOME: String = "KEY_FRAGMENT_HOME"
    private val KEY_FRAGMENT_COINS: String = "KEY_FRAGMENT_COINS"
    private val KEY_FRAGMENT_EXCHANGE: String = "KEY_FRAGMENT_EXCHANGE"
    private val KEY_FRAGMENT_FOLLOW: String = "KEY_FRAGMENT_FOLLOW"
    private val KEY_FRAGMENT_PROPERTY: String = "KEY_FRAGMENT_PROPERTY"

    var homeFragment: HomeFragment? = null
    var coinsFragment: CoinsFragment? = null
    var exchangeFragment: ExchangeFragment? = null
    var followFragment: FollowFragment? = null
    var propertyFragment: PropertyFragment? = null
    var fragmentList: MutableList<Fragment> = mutableListOf()
    val tabTitles = listOf(
        "首页",
        "行情",
        "交易",
        "关注",
        "资产"
    )
    val tabIcons = listOf(
        R.drawable.ic_navigation_home,
        R.drawable.ic_navigation_coins,
        R.drawable.ic_navigation_exchange,
        R.drawable.ic_navigation_follow,
        R.drawable.ic_navigation_property
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            homeFragment = supportFragmentManager.getFragment(
                savedInstanceState,
                KEY_FRAGMENT_HOME
            ) as HomeFragment?
            coinsFragment = supportFragmentManager.getFragment(
                savedInstanceState,
                KEY_FRAGMENT_COINS
            ) as CoinsFragment?
            exchangeFragment = supportFragmentManager.getFragment(
                savedInstanceState,
                KEY_FRAGMENT_EXCHANGE
            ) as ExchangeFragment?
            followFragment = supportFragmentManager.getFragment(
                savedInstanceState,
                KEY_FRAGMENT_FOLLOW
            ) as FollowFragment?
            propertyFragment = supportFragmentManager.getFragment(
                savedInstanceState,
                KEY_FRAGMENT_PROPERTY
            ) as PropertyFragment?
        }
        fragmentList.clear()
        if (homeFragment==null)homeFragment=HomeFragment.newInstance("首页", "")
        fragmentList.add(homeFragment!!)
        if (coinsFragment==null)coinsFragment=CoinsFragment.newInstance("行情", "")
        fragmentList.add(coinsFragment!!)
        if (exchangeFragment==null)exchangeFragment=ExchangeFragment.newInstance("交易", "")
        fragmentList.add(exchangeFragment!!)
        if (followFragment==null)followFragment=FollowFragment.newInstance("关注", "")
        fragmentList.add(followFragment!!)
        if (propertyFragment==null)propertyFragment=PropertyFragment.newInstance("资产", "")
        fragmentList.add(propertyFragment!!)

        // 添加自定义布局视图
        tabTitles.forEachIndexed { index, title ->
            val tab = binding.tabMainBottom.newTab()
            val customView = layoutInflater.inflate(R.layout.item_navigation_tab, null)
            customView.findViewById<ImageView>(R.id.tab_icon).setImageResource(tabIcons[index])
            customView.findViewById<TextView>(R.id.tab_text).text = title
            tab.customView = customView
            binding.tabMainBottom.addTab(tab)
        }

        binding.tabMainBottom.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.apply {
                    "onTabSelected=${position}".logE()
                    showFragment(fragmentList[position], false)
                    updateTabState(customView, true)
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.apply {
                    "onTabUnselected=${position}".logE()
                    updateTabState(customView, false)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                tab?.apply {
                    "onTabReselected=${position}".logE()
                    showFragment(fragmentList[position], false)
                    updateTabState(customView, true)
                }
            }
        })
        binding.tabMainBottom.selectTab(binding.tabMainBottom.getTabAt(0))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (homeFragment != null && homeFragment!!.isAdded) {
            supportFragmentManager.putFragment(outState, KEY_FRAGMENT_HOME, homeFragment!!)
        }
        if (coinsFragment != null && coinsFragment!!.isAdded) {
            supportFragmentManager.putFragment(outState, KEY_FRAGMENT_COINS, coinsFragment!!)
        }
        if (exchangeFragment != null && exchangeFragment!!.isAdded) {
            supportFragmentManager.putFragment(outState, KEY_FRAGMENT_EXCHANGE, exchangeFragment!!)
        }
        if (followFragment != null && followFragment!!.isAdded) {
            supportFragmentManager.putFragment(outState, KEY_FRAGMENT_FOLLOW, followFragment!!)
        }
        if (propertyFragment != null && propertyFragment!!.isAdded) {
            supportFragmentManager.putFragment(outState, KEY_FRAGMENT_PROPERTY, propertyFragment!!)
        }
    }

    private fun updateTabState(customView: View?, isSelected: Boolean) {
        customView?.findViewById<ImageView>(R.id.tab_icon)?.let {
            it.setColorFilter(
                ContextCompat.getColor(
                    this@MainActivity,
                    if (isSelected) R.color.navigation_selected else R.color.navigation_unselected
                )
            )

        }
        customView?.findViewById<TextView>(R.id.tab_text)?.let {
            it.setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    if (isSelected) R.color.navigation_selected else R.color.navigation_unselected
                )
            )
        }
    }

    private fun showFragment(fragment: Fragment, replace: Boolean) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (replace) {
            transaction.replace(R.id.fl_main_content, fragment)
        } else {
            if (!fragment.isAdded) {
                transaction.add(R.id.fl_main_content, fragment, fragment.javaClass.simpleName)
            }
            hideAllFragment(transaction, fragment)
            transaction.show(fragment)
            transaction.setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
        }
        transaction.commit()
    }

    private fun hideAllFragment(transaction: FragmentTransaction, withoutFragment: Fragment) {
        for (i in fragmentList.indices) {
            if (fragmentList[i] != withoutFragment && fragmentList[i].isAdded) {
                transaction.hide(fragmentList[i])
                transaction.setMaxLifecycle(fragmentList[i], Lifecycle.State.CREATED)
            }
        }
    }
}