package com.zkp.breath.component.activity.jetpack

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.zkp.breath.adpter.StudentAdapter
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityPagingBinding
import com.zkp.breath.jetpack.paging.StudentDb

/**
 * https://www.jianshu.com/p/10bf4bf59122
 * https://juejin.cn/post/6898133386218045453
 *
 * 原理可以查看doc文件夹：Paging原理详解.gif
 */
class PagingActivity : BaseActivity() {

    private lateinit var binding: ActivityPagingBinding
    private lateinit var pagingModel: PagingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagingBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        pagingModel = ViewModelProvider(this).get(PagingViewModel::class.java)
        val adapter = StudentAdapter()
        binding.rcv.layoutManager = LinearLayoutManager(binding.rcv.context)
        binding.rcv.adapter = adapter
        pagingModel.allStudents.observe(this, Observer { adapter.submitList(it) })
    }

    class PagingViewModel(app: Application) : AndroidViewModel(app) {

        val dao = StudentDb.get(app).studentDao()

        val allStudents = LivePagedListBuilder(dao.getAllStudent(), PagedList.Config.Builder()
                .setInitialLoadSizeHint(PAGE_SIZE)              //初始化加载的数量
                .setPageSize(PAGE_SIZE)                         //配置分页加载的数量
                .setEnablePlaceholders(ENABLE_PLACEHOLDERS)     //配置是否启动PlaceHolders
                .build()).build()

        companion object {
            private const val PAGE_SIZE = 15

            private const val ENABLE_PLACEHOLDERS = false
        }
    }

}