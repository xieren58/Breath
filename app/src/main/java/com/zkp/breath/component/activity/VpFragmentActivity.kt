package com.zkp.breath.component.activity

import android.os.Bundle
import androidx.fragment.app.commit
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.component.fragment.test.TestFragmentC
import com.zkp.breath.databinding.ActivityFragmentBinding

class VpFragmentActivity : BaseActivity() {

    private lateinit var binding: ActivityFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                val simpleName = TestFragmentC::class.java.simpleName
                // 调用前一定要 setReorderingAllowed(true)
                addToBackStack(simpleName)
                add(R.id.fcv, TestFragmentC(), simpleName)
            }
        }
    }

}