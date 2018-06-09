// IMyAidlInterface.aidl
package com.example.myservice;

// Declare any non-default types here with import statements
import com.example.myservice.Student;
import com.example.myservice.IMyAidlInterfaceCallBackListener;

interface IMyAidlInterface {
     void registerListener(IMyAidlInterfaceCallBackListener listener);
     void unregisterListener(IMyAidlInterfaceCallBackListener listener);
     String getMessage();
     List<Student> getStudentList();
}
