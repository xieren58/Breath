package com.zkp.breath.jetpack.room

import androidx.room.*


/**
 * 1.表单结构Bean使用@Entity注解表示。tableName表示指定表单名，否则使用bean的类名作为表单名。
 * 2.每一个Entity至少定义一个主键（primary key）,使用注解 @PrimaryKey 来定义主键,一种是在类变量前面加，
 *   一种是在@Entity注解中。
 * 3.使用 @ColumnInfo来改变列的名称。
 * 4.不希望某个变量生成表中的属性列，可以使用注解 @Ignore。
 * 5.子类不需要父类的某个变量生成表中的属性列，那么可以ignoredColumns
 * 6.如果定义的Entity类里面有个对象，并且希望定义的Entity中的表列字段包含Entity类对象中的变量，
 *   可以在Entity类对象中加@Embedded标注。
 * 7.通过@ForeignKey注解来进行表的关联。
 *
 * https://juejin.im/post/6844903763296124942
 */
@Entity(tableName = "user_table")
open class User {

    @PrimaryKey
    var uid: Int = 0

    @ColumnInfo(name = "first_name")
    var firstName: String? = null

    @ColumnInfo(name = "last_name")
    var lastName: String? = null

    @Ignore
    var ignore: Int = -1

    @Embedded
    var address: Address? = null


    class Address {
        var street: String? = null
        var state: String? = null
        var city: String? = null

        @ColumnInfo(name = "post_code")
        var postCode: Int = -1
    }

    @Entity(foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["uid"], childColumns = ["user_id"])
    ])
    class Book {
        @PrimaryKey
        var bookId: Int = -1

        var title: String? = null

        @ColumnInfo(name = "user_id")
        var userId: Int = -1
    }


    @Entity(ignoredColumns = ["lastName"])
    class UserSub : User()

}

