package com.zkp.breath.component.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zkp.breath.CoordinatorAdapter
import com.zkp.breath.R

class ActivityCoordinator : AppCompatActivity() {

    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator)

        recyclerView = findViewById(R.id.recyclerView)
        init()
    }

    private fun init() {
        initRecycleView()
    }

    private fun initRecycleView() {
        val arrayListOf = arrayListOf("1", "2", "3", "5", "6", "7", "8", "9", "10")
        recyclerView?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView?.adapter = CoordinatorAdapter(arrayListOf)
    }

}