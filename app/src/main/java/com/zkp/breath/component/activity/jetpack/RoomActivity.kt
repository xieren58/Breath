package com.zkp.breath.component.activity.jetpack

import android.os.Bundle
import android.view.LayoutInflater
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityRoomBinding

/**
 * Room 包含 3 个主要组件：
 * 1. RoomDatabase（数据库），包含了DAO，并且提供创建和连接数据库的方法。
 * 2. DAO（Data Access Object） 包含用于访问数据库的方法
 * 3. Entity 表示数据库中的表。
 *
 *
 */
class RoomActivity : BaseActivity() {

    private lateinit var binding: ActivityRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
    }
}