package com.zkp.breath.component.activity.performance

import android.os.Bundle
import android.util.Log
import android.view.Choreographer
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zkp.breath.databinding.ActivityBlockcanaryBinding


/**
 * 界面卡顿检查工具ActivityBlockcanaryBinding的例子
 */
class BlockCanaryActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = BlockCanaryActivity::class.simpleName
    private val mFrameCallback = MyFrameCallback()
    private lateinit var binding: ActivityBlockcanaryBinding
    val isBlockCanaryTest = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlockcanaryBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.btn.setOnClickListener(this)
        choreographerTest()
    }

    private fun choreographerTest() {
        Choreographer.getInstance().postFrameCallback(mFrameCallback);
    }

    inner class MyFrameCallback : Choreographer.FrameCallback {
        private val TAG = "性能检测"
        private var lastTime = 0L

        override fun doFrame(frameTimeNanos: Long) {
            if (lastTime == 0L) {
                //代码第一次初始化。不做检测统计。
                lastTime = frameTimeNanos;
            } else {
                val times = (frameTimeNanos - lastTime) / 1000000
                val frames = (times / 16)

                if (times > 16) {
                    Log.w(TAG, "UI线程超时(超过16ms):" + times + "ms" + " , 丢帧:" + frames);
                }

                lastTime = frameTimeNanos;
            }
            Choreographer.getInstance().postFrameCallback(mFrameCallback);
        }
    }

    override fun onClick(v: View?) {
        if (!isBlockCanaryTest) {
            binding.btn.animate().rotation(360f).setDuration(2000L).setUpdateListener {
                // 动画其实就是在不停的修改位置，但是加入下面卡ui的代码会让Choreographer间隔200毫秒才去渲染下一帧导致动画会变卡顿
                try {
                    Thread.sleep(200)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        } else {
            // 模拟卡ui线程的操作，BlockCanary我们规定的时间是1s，所以这里会触发BlockCanary工具
            try {
                Thread.sleep(2000)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
