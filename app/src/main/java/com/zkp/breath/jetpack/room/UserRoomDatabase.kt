package com.zkp.breath.jetpack.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * 当升级Android应用的时候，有时需要更改数据库中数据的结构，要用户升级应用的时候保持原有的数据不变，使用数据库移植
 * Migration是必须的。在Room数据库中Room persistence library库提供了升级数据库时保存用户数据的方法。使用定义
 * Migration对象，每个对象包含数据库的开始版本号和结束版本号。当然不一定非要定义MIGRATION_1_2, MIGRATION_2_3,
 * MIGRATION_3_4, MIGRATION_1_4这么多，也可以一次从MIGRATION_1_4，跳过中间的MIGRATION，但是如果不提供足够的
 * MIGRATION，从当前版本变到最新版本，Room数据库会清楚原来的数据重写创建。
 */
@Database(entities = [User::class], version = 1)
abstract class UserRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        private var instance: UserRoomDatabase? = null

        @Synchronized
        fun get(context: Context): UserRoomDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        UserRoomDatabase::class.java, "UserDb")
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {

                            }
                        }).build()
            }
            return instance!!
        }
    }
}