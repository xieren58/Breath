package com.zkp.breath.component.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.zkp.breath.adpter.GridAdapter
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityRecycleViewBinding

class RecycleViewActivity : BaseActivity() {

    private lateinit var binding: ActivityRecycleViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecycleViewBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val arrayList = ArrayList<String>()
        for (i in 0..100) {
            arrayList.add(i.toString())
        }

        val rcv = binding.rcv
        val gridLayoutManager = GridLayoutManager(this, 4)
        gridLayoutManager.orientation = GridLayoutManager.HORIZONTAL
        rcv.layoutManager = gridLayoutManager
        val gridAdapter = GridAdapter(arrayList)
        rcv.adapter = gridAdapter
    }


}