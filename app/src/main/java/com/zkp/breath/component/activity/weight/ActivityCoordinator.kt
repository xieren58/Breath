package com.zkp.breath.component.activity.weight

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zkp.breath.R
import com.zkp.breath.adpter.CoordinatorAdapter


/**
 * https://www.jianshu.com/p/bbc703a0015e
 * https://www.jianshu.com/p/eec5a397ce79
 */
class ActivityCoordinator : AppCompatActivity() {

    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator)
        initRecycleView()
    }

    private fun initRecycleView() {
        recyclerView = findViewById(R.id.recyclerView)
        val arrayListOf = arrayListOf("1", "2", "3", "5", "6", "7", "8", "9", "10")
        //当知道Adapter内Item的改变不会影响RecyclerView宽高的时候，可以设置为true让RecyclerView避免重新计算大小
        recyclerView?.setHasFixedSize(true)
        recyclerView?.overScrollMode = View.OVER_SCROLL_NEVER
        recyclerView?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView?.adapter = CoordinatorAdapter(arrayListOf)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add("AppBarLayout").setTitle("切换AppBarLayout布局").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        menu.add("CollapsingToolbarLayout").setTitle("切换CollapsingToolbarLayout布局").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val name = item.title.toString()
        when (name) {
            "切换AppBarLayout布局" -> {
                setContentView(R.layout.activity_coordinator)
                initRecycleView()
            }
            "切换CollapsingToolbarLayout布局" -> {
                setContentView(R.layout.activity_collapsing)
                initRecycleView()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}