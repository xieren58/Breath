package com.zkp.breath.component.activity.jetpack

import android.os.Bundle
import android.view.LayoutInflater
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityDataStoreBinding


class DataStoreActivity : BaseActivity() {

    companion object {
        private const val PREFERENCE_NAME = "DataStoreDemo"
    }

    private lateinit var binding: ActivityDataStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataStoreBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        // PreferenceDataStoreFactory的createDataStore()
//        var dataStore: DataStore<Preferences> = this.createDataStore(name = PREFERENCE_NAME)

    }

}