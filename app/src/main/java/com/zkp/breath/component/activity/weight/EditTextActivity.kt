package com.zkp.breath.component.activity.weight

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityEditTextBinding

class EditTextActivity : BaseActivity() {

    private lateinit var binding: ActivityEditTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTextBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getFocus()
        addTextChangedListener()
    }

    /**
     * 添加text改变监听器
     */
    private fun addTextChangedListener() {
        binding.et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.i("addTextChangedListener", "onTextChanged: $s")
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    /**
     * EditText默认不获取焦点，不自动弹出键盘：
     * targetSdkVersion >= Build.VERSION_CODES.P 情况下，且设备版本为Android P以上版本，解决方法在onCreate
     * 中加入如下代码，可获得焦点。
     *
     * 如果要自动弹出键盘：  KeyboardUtils.showSoftInput()
     */
    private fun getFocus() {
        binding.et.requestFocus()
    }

}