package com.zkp.breath.jetpack.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


/**
 * 1.表单结构Bean使用@Entity注解表示。tableName表示指定表单名，否则使用bean的类名作为表单名。
 * 2.每一个Entity至少定义一个主键（primary key）,使用注解 @PrimaryKey 来定义主键,一种是在类变量前面加，
 *   一种是在@Entity注解中。
 * 3.使用 @ColumnInfo来改变列的名称。
 * 4.不希望某个变量生成表中的属性列，可以使用注解 @Ignore。
 */
@Entity(tableName = "user_table")
data class User(
        @PrimaryKey val uid: Int,
        @ColumnInfo(name = "first_name") val firstName: String?,
        @ColumnInfo(name = "last_name") val lastName: String?,
        @Ignore val ignore: Int
)