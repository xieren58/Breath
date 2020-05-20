package com.zkp.breath.component.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityVmBinding
import com.zkp.breath.jetpack.viewmodel.JetPackViewModel

class ViewModelActivity : BaseActivity() {

    private lateinit var binding: ActivityVmBinding
    private lateinit var viewModel: JetPackViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVmBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(JetPackViewModel::class.java)
    }


}