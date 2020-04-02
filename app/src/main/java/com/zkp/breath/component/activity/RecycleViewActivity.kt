package com.zkp.breath.component.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zkp.breath.adpter.GridAdapter
import com.zkp.breath.adpter.decoration.GridItemDecoration
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
        rcv.itemAnimator?.changeDuration = 0
        rcv.overScrollMode = View.OVER_SCROLL_NEVER
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL)
        rcv.layoutManager = staggeredGridLayoutManager
        val gridAdapter = GridAdapter(arrayList)
        rcv.adapter = gridAdapter
        rcv.addItemDecoration(GridItemDecoration())
    }


}