package com.zkp.breath.component.activity.weight

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.zkp.breath.databinding.ActivityConstraintLayoutBinding

/**
 * 约束布局的demo
 */
class ConstraintLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityConstraintLayoutBinding.inflate(LayoutInflater.from(this))
        setContentView(inflate.root)
    }

}