package com.zkp.breath.jetpack.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * 当升级Android应用的时候，有时需要更改数据库中数据的结构，要用户升级应用的时候保持原有的数据不变，使用数据库移植
 * Migration是必须的。在Room数据库中Room persistence library库提供了升级数据库时保存用户数据的方法。使用定义
 * Migration对象，每个对象包含数据库的开始版本号和结束版本号。当然不一定非要定义MIGRATION_1_2, MIGRATION_2_3,
 * MIGRATION_3_4, MIGRATION_1_4这么多，也可以一次从MIGRATION_1_4，跳过中间的MIGRATION，但是如果不提供足够的
 * MIGRATION，从当前版本变到最新版本，Room数据库会清楚原来的数据重写创建。
 *
 * entities是数据，有几个元素就建立几张表。
 */
@Database(entities = [User::class], version = 1)
abstract class UserRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        private var instance: UserRoomDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.i("UserRoomDatabase", "migrate: ")
                database.execSQL("alter table user_table add column sex text")
            }
        }

        @Synchronized
        fun get(context: Context): UserRoomDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        UserRoomDatabase::class.java, "UserDb")
                        .addCallback(object : RoomDatabase.Callback() {
                            /**
                             *  对应数据库中的所有表都创建完毕后回调，只会回调一次。
                             */
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                Log.i("UserRoomDatabase", "onCreate: ")
                            }

                            /**
                             * 当表单被打开的时候会回调
                             */
                            override fun onOpen(db: SupportSQLiteDatabase) {
                                super.onOpen(db)
                                Log.i("UserRoomDatabase", "onOpen: ")
                            }

                            /**
                             * 破坏性迁移（暴力迁移）
                             * 调用fallbackToDestructiveMigration()回调
                             */
                            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                                super.onDestructiveMigration(db)
                                Log.i("UserRoomDatabase", "onDestructiveMigration: ")
                            }

                        })
//                        .fallbackToDestructiveMigration()
//                        .addMigrations(MIGRATION_1_2)
                        .build()
            }
            return instance!!
        }
    }
}