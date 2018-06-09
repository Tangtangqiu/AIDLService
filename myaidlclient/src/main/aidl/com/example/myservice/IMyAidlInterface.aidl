// IMyAidlInterface.aidl
package com.example.myservice;

// Declare any non-default types here with import statements
import com.example.myservice.Student;
import com.example.myservice.IMyAidlInterfaceCallBackListener;

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

     void registerListener(IMyAidlInterfaceCallBackListener listener);
     void unregisterListener(IMyAidlInterfaceCallBackListener listener);
     String getMessage();
     List<Student> getStudentList();
}
