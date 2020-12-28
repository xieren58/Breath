package com.zkp.breath.jetpack.navigation

import android.os.Bundle
import android.view.View
import com.hi.dhl.binding.viewbind
import com.zkp.breath.component.activity.base.ClickBaseActivity
import com.zkp.breath.databinding.ActivityNavigationBinding

/**
 * https://juejin.cn/post/6908585769699377165
 * https://juejin.cn/post/6844904165362270215#heading-11
 */
class NavigationActivity : ClickBaseActivity() {

    private val binding by viewbind<ActivityNavigationBinding>()

    override fun onDebouncingClick(v: View) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}