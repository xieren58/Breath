package com.zkp.breath.component.activity

import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.Request
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.zkp.breath.MainActivity
import com.zkp.breath.R
import com.zkp.breath.adpter.GlideAdapter
import com.zkp.breath.adpter.ViewPagerAdapter
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityGlideBinding
import com.zkp.breath.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.security.auth.login.LoginException

class GlideActivity : BaseActivity() {

    private lateinit var binding: ActivityGlideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGlideBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        val arrayListOf = arrayListOf(
                "https://friendshipout.oss-cn-shenzhen.aliyuncs.com/backgroundPicture/1a3eb8b7背景.png",
                "https://friendshipout.oss-cn-shenzhen.aliyuncs.com/backgroundPicture/b01eb218背景5.png",
                "https://friendshipout.oss-cn-shenzhen.aliyuncs.com/backgroundPicture/49d209e3背景2.png",
                "https://friendshipout.oss-cn-shenzhen.aliyuncs.com/backgroundPicture/f6620121背景7.png",
                "https://friendshipout.oss-cn-shenzhen.aliyuncs.com/backgroundPicture/5da173a0背景10.png"
        )

        val viewpager2 = binding.vp2
        viewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val viewPagerAdapter = GlideAdapter(arrayListOf)
        viewpager2.adapter = viewPagerAdapter
        viewpager2.offscreenPageLimit = 1


        GlobalScope.launch(Dispatchers.IO) {
            val submit = Glide.with(this@GlideActivity)
                    .asBitmap()
                    .load("https://friendshipout.oss-cn-shenzhen.aliyuncs.com/backgroundPicture/1a3eb8b7背景.png")
                    .submit(100, 100)
            val get = submit.get()
        }

    }


}