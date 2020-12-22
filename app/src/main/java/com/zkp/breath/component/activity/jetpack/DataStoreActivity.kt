package com.zkp.breath.component.activity.jetpack

import android.os.Bundle
import android.view.LayoutInflater
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.zkp.breath.component.activity.base.BaseActivity
import com.zkp.breath.databinding.ActivityDataStoreBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


/**
 * https://juejin.cn/post/6881442312560803853#heading-1
 *
 * 替换SharedPreferences
 *
 */
class DataStoreActivity : BaseActivity() {

    companion object {
        private const val PREFERENCE_NAME = "DataStoreDemo"
    }

    private lateinit var binding: ActivityDataStoreBinding
    private lateinit var dataStore: DataStore<Preferences>
    private val KEY_BYTE_CODE = preferencesKey<Boolean>("ByteCode")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataStoreBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        dataStore = this.createDataStore(name = PREFERENCE_NAME)
    }

    fun readData(key: Preferences.Key<Boolean>): Flow<Boolean> =
            dataStore.data
                    .catch {
                        // 当读取数据遇到错误时，如果是 `IOException` 异常，发送一个 emptyPreferences 来重新使用
                        // 但是如果是其他的异常，最好将它抛出去，不要隐藏问题
                        if (it is IOException) {
                            it.printStackTrace()
                            emit(emptyPreferences())
                        } else {
                            throw it
                        }
                    }.map { preferences ->
                        preferences[key] ?: false
                    }


    /**
     * edit是挂起函数，所以不会阻塞线程
     */
    suspend fun saveData(key: Preferences.Key<Boolean>) {
        dataStore.edit { mutablePreferences ->
            val value = mutablePreferences[key] ?: false
            mutablePreferences[key] = !value
        }
    }

}