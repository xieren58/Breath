package com.zkp.breath.jetpack.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

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