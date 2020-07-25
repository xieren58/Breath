package com.zkp.breath.component.activity.jetpack

import android.os.Bundle
import android.view.LayoutInflater
import androidx.startup.AppInitializer
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityAppStartupBinding
import com.zkp.breath.jetpack.startup.StartUpLibrary3

/**
 * https://juejin.im/post/5ee4bbe4f265da76b559bdfe
 *
 * App Startup：提供了在 App 启动时初始化组件简单、高效的方法，简单的说就是 App Startup 提供了一个ContentProvider
 * 来运行所有依赖项的初始化，避免每个第三方库使用自身提供ContentProvider进行初始化，从而提高了应用的程序的启动速度，（
 * 可参考AppStartup的作用.jpeg），有自动初始化和手动（延迟）初始化两种方法。
 *
 * 作用（基本没作用）：
 * 1. 第三方还是使用自身的提供ContentProvider进行初始化。
 * 2. 即便自身提供的库是内部使用，这么约定好还是可以用，除非能达到优化，否则强行使用startup感觉没必要。
 */
class AppStartupActivity : BaseActivity() {

    private lateinit var binding: ActivityAppStartupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppStartupBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        // 手动初始化（延迟初始化）
        AppInitializer.getInstance(this).initializeComponent(StartUpLibrary3::class.java)
    }


    /**
     * 在 App 启动时运行多个 ContentProvider，会增加了 App 启动运行的时间。
     *
     * ActivityThread的handleBindApplication的伪代码，下面可以看出ContentProvider 的 onCreate
     * 方法会先于 Application 的 OnCreate 方法执行。
     */
//    private void handleBindApplication(AppBindData data) {
//        ......
//
//        if (!data.restrictedBackupMode) {
//            if (!ArrayUtils.isEmpty(data.providers)) {
//                // 创建ContentProvider
//                installContentProviders(app, data.providers);
//            }
//        }
//
//        ......
//
//        try {
//            // 调用调用 Application 的 OnCreate 方法
//            mInstrumentation.callApplicationOnCreate(app);
//        } catch (Exception e) {
//            ......
//        }
//
//        ......
//    }

}