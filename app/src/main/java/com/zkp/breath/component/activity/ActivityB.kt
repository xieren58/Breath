package com.zkp.breath.component.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import com.zkp.breath.R

class ActivityB : AppCompatActivity(), View.OnClickListener {

    val TAG = ActivityB::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)
        Log.i(TAG, "onCreate()")

        val btn = findViewById<Button>(R.id.btn_activity_b)
        btn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_activity_b -> {
                setResult(ResponseCode.ActivityBCode)
                finish()
            }
            else -> ToastUtils.showShort("其他")
        }
    }

}
