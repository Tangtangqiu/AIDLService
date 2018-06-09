package com.example.myservice;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable{

    private int _id;

    private String name;

    private int age;

    public Student() {
    }

    public Student(int _id,String name, int age) {
        this._id = _id;
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    @Override
    public int describeContents() {//暂时return 为0就好
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {//序列化的操作
        dest.writeInt(_id);
        dest.writeString(name);
        dest.writeInt(age);
    }

    public static final Creator<Student> CREATOR = new Creator<Student>(){

        @Override
        public Student createFromParcel(Parcel source) {
            int _id = source.readInt();
            String name = source.readString();
            int age = source.readInt();
            return new Student(_id,name,age);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

}
