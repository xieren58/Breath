package com.zkp.breath.component.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.zkp.breath.databinding.ActivityConstraintLayoutBinding

/**
 */
class ConstraintLayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConstraintLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityConstraintLayoutBinding.inflate(LayoutInflater.from(this))
        setContentView(inflate.root)
    }

}