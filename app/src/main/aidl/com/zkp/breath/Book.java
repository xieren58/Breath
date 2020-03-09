package com.zkp.breath;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 首先了解下 AIDL 文件支持的几种数据类型：
 * 基本数据类型
 * String、CharSequence
 * ArrayList、HashMap，其内部元素也需要被AIDL支持
 * 实现了 Parcelable 接口的对象（要额外创建一个同名的.aidl文件）
 * AIDL 类型的接口，非普通接口
 * <p>
 * 不要在.aidl文件中的导包处写注释，不然build可能有问题
 */
public class Book implements Parcelable {
    private int id;
    private String name;
    private String gender;
    private int age;

    protected Book(Parcel in) {
        id = in.readInt();
        name = in.readString();
        gender = in.readString();
        age = in.readInt();
    }

    public Book(int id, String name, String gender, int age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public Book() {
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(gender);
        dest.writeInt(age);
    }

    public void readFromParcel(Parcel source) {
        id = source.readInt();
        name = source.readString();
        gender = source.readString();
        age = source.readInt();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
