package com.zkp.breath.jetpack.room

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table")
    fun queryUsers(): MutableList<User>

    @Insert
    fun insert(users: List<User>)

    @Insert
    fun insert(user: User)

    @Update
    fun update(vararg user: User)

    @Delete
    fun delete(vararg user: User)
}