package com.zkp.breath.jetpack.room

import androidx.room.*
import com.zkp.breath.kotlin.User


/**
 * 1.表单结构Bean使用@Entity注解表示。tableName表示指定表单名，否则使用bean的类名作为表单名。
 * 2.每一个Entity至少定义一个主键（primary key）,使用注解 @PrimaryKey 来定义主键,一种是在类变量前面加，
 *   一种是在@Entity注解中。如果要定义复合主键，则应使用primaryKeys()方法(如果表中每一个字段都可能重复，无法使用单
 *   一字段作为主键，这时我们可以将多个字段设置为复合主键，由复合主键标识唯一性)。如果{@Entity}及其超类都定义了
 *   {@PrimaryKey}，则子类的{@PrimaryKey}定义将覆盖父项的{@PrimaryKey}。
 * 3.使用 @ColumnInfo来改变列的名称。
 * 4.不希望某个变量生成表中的属性列，可以使用注解 @Ignore。
 * 5.子类不需要父类的某个变量生成表中的属性列，那么可以ignoredColumns
 * 6.如果定义的Entity类里面有个对象，并且希望定义的Entity中的表列字段包含Entity类对象中的变量，
 *   可以在Entity类对象中加@Embedded标注。
 * 7.通过@ForeignKey注解来进行表的关联。
 * 8.数据库索引用于提高数据库表的数据访问速度的,数据库里面的索引有单列索引和组合索引,Room里面可以通过@Entity的
 *   indices属性来给表格添加索引。
 *
 */
@Entity(tableName = "user_table", indices = [Index("first_name"),
    Index(value = ["first_name", "last_name"])])
open class User {

    @PrimaryKey
    var uid: Int = 0

    @ColumnInfo(name = "first_name")
    var firstName: String? = null

    @ColumnInfo(name = "last_name")
    var lastName: String? = null

    // 迁移测试的字段
//    @ColumnInfo(name = "sex")
//    var sex: String? = "男"

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

        override fun toString(): String {
            return "Address(street=$street, state=$state, city=$city, postCode=$postCode)"
        }
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

        override fun toString(): String {
            return "Book(bookId=$bookId, title=$title, userId=$userId)"
        }

    }

    @Entity(ignoredColumns = ["lastName"])
    class UserSub : com.zkp.breath.jetpack.room.User()

    override fun toString(): String {
        return "User(uid=$uid, firstName=$firstName, lastName=$lastName,ignore=$ignore, address=$address)"
//        return "User(uid=$uid, firstName=$firstName, lastName=$lastName, sex=$sex, ignore=$ignore, address=$address)"
    }

}

