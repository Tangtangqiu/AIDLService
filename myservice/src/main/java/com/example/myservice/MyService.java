package com.example.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {
    private static final String TAG = MyService.class.getSimpleName();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new IMyService();
    }

    private RemoteCallbackList<IMyAidlInterfaceCallBackListener>  remoteCallbackList = new RemoteCallbackList<>();
    private IMyAidlInterfaceCallBackListener listeners;
    public class IMyService extends IMyAidlInterface.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void registerListener(IMyAidlInterfaceCallBackListener listener) throws RemoteException {
            listeners = listener;
            remoteCallbackList.register(listener);
        }

        @Override
        public void unregisterListener(IMyAidlInterfaceCallBackListener listener) throws RemoteException {
            listeners = null;
            remoteCallbackList.unregister(listener);
        }

        @Override
        public String getMessage() throws RemoteException {

            Log.e(TAG, "getMessage: "+Thread.currentThread().getName());

            return "得到啦";
        }

        @Override
        public List<Student> getStudentList() throws RemoteException {

            List<Student> list = new ArrayList<>();

            Student student = new Student(0,"小花",20);
            Student student1 = new Student(1,"小明",21);
            Student student2 = new Student(2,"小逃",22);

            list.add(student);
            list.add(student1);
            list.add(student2);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(10000);

                    try {
                        listeners.getNewStudentArrive(new Student(10,"小哥",29));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


            return list;
        }
    }
}
