// IMyAidlInterfaceCallBackListener.aidl
package com.example.myservice;

// Declare any non-default types here with import statements
import com.example.myservice.Student;
interface IMyAidlInterfaceCallBackListener {

    void getNewStudentArrive(in Student student);
}
