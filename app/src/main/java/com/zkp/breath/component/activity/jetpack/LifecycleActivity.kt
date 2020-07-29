package com.zkp.breath.component.activity.jetpack

import android.os.Bundle
import android.view.LayoutInflater
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityLiferecycleBinding
import com.zkp.breath.jetpack.lifecycle.JetPackDefaultLifecycleObserverImp
import com.zkp.breath.jetpack.lifecycle.JetPackLifecycleEventObserverImp

class LifecycleActivity : BaseActivity() {

    private lateinit var binding: ActivityLiferecycleBinding
    private lateinit var jetPackDefaultLifecycleObserverImp: JetPackDefaultLifecycleObserverImp
    private lateinit var jetPackLifecycleEventObserverImp: JetPackLifecycleEventObserverImp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiferecycleBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        jetPackDefaultLifecycleObserverImp = JetPackDefaultLifecycleObserverImp()
        jetPackLifecycleEventObserverImp = JetPackLifecycleEventObserverImp()

        // 添加生命周期观察者
        lifecycle.addObserver(jetPackDefaultLifecycleObserverImp)
        lifecycle.addObserver(jetPackLifecycleEventObserverImp)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 移除生命周期观察者
        lifecycle.removeObserver(jetPackDefaultLifecycleObserverImp)
        lifecycle.removeObserver(jetPackLifecycleEventObserverImp)
    }


}