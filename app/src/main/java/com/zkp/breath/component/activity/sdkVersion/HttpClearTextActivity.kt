package com.zkp.breath.component.activity.sdkVersion

import android.os.Bundle
import android.util.Log
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityHttpClearTextBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.UnknownServiceException
import kotlin.concurrent.thread

/**
 * 明文HTTP限制:
 * 当 targetSdkVersion >= Build.VERSION_CODES.P 时，默认限制了HTTP请求，并出现相关日志：
 * java.net.UnknownServiceException: CLEARTEXT communication to xxx not permitted by network security policy
 *
 * 兼容方法：
 * 1. 在AndroidManifest.xml中Application添加如下节点代码
 *    <application android:usesCleartextTraffic="true">
 *
 * 2. 在res目录新建xml目录，然后新建network_config.xml文件，文件内容如下：
 *      <?xml version="1.0" encoding="utf-8"?>
 *      <network-security-config>
 *          <base-config cleartextTrafficPermitted="true" />
 *      </network-security-config>
 *
 *   然后在AndroidManifest.xml中Application添加如下节点代码：
 *     android:networkSecurityConfig="@xml/network_config"
 *
 * 注意：这个限制是想推荐开发者使用https协议，增加接口访问的安全性。
 *
 */
class HttpClearTextActivity : BaseActivity() {

    private lateinit var binding: ActivityHttpClearTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHttpClearTextBinding.inflate(layoutInflater)
        setContentView(binding.root)
        httpClearText()
    }

    private fun httpClearText() {
        thread {
            val urlResponse = getURLResponse("http://baidu.com")
            Log.i("测试明文", "onCreate: $urlResponse")
        }
    }

    /**
     * 获取指定URL的响应字符串
     * @param urlString
     * @return
     */
    private fun getURLResponse(urlString: String): String? {
        var conn: HttpURLConnection? = null //连接对象
        var `is`: InputStream? = null
        var resultData = ""
        try {
            val url = URL(urlString) //URL对象
            conn = url.openConnection() as HttpURLConnection //使用URL打开一个链接
            conn.setDoInput(true) //允许输入流，即允许下载
            conn.setDoOutput(true) //允许输出流，即允许上传
            conn.setUseCaches(false) //不使用缓冲
            conn.setRequestMethod("GET") //使用get请求
            `is` = conn.getInputStream() //获取输入流，此时才真正建立链接
            val isr = InputStreamReader(`is`)
            val bufferReader = BufferedReader(isr)
            var inputLine = ""
            while (bufferReader.readLine()?.also { inputLine = it } != null) {
                resultData += """
                $inputLine
                """.trimIndent()
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: UnknownServiceException) {
            Log.i("http明文限制", "e: ${e.message}")
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (`is` != null) {
                try {
                    `is`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (conn != null) {
                conn.disconnect()
            }
        }
        return resultData
    }


}