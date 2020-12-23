package com.zkp.breath.component.activity.weight

import android.os.Bundle
import com.hi.dhl.binding.viewbind
import com.zkp.breath.R
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityEditTextBinding

class EditTextActivity : BaseActivity(R.layout.activity_svga) {

    val binding by viewbind<ActivityEditTextBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}