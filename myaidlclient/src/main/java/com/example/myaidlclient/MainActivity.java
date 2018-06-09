package com.example.myaidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myservice.IMyAidlInterface;
import com.example.myservice.IMyAidlInterfaceCallBackListener;
import com.example.myservice.Student;

import java.util.List;

import static com.example.myservice.IMyAidlInterfaceCallBackListener.*;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView tv;
    private TextView tv2;
    private Button btn;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
        tv2 = findViewById(R.id.tv2);
        btn = findViewById(R.id.btn);

        Intent intent = new Intent("com.example.myservice.MyService");
        intent.setPackage("com.example.myservice");
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(message);
                try {
                    List<Student> studentList = iMyAidlInterface.getStudentList();
                    tv.setText(studentList.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private IMyAidlInterfaceCallBackListener listener = new IMyAidlInterfaceCallBackListener.Stub() {
        @Override
        public void getNewStudentArrive(final Student student) throws RemoteException {
            Log.e(TAG, "getNewStudentArrive: "+Thread.currentThread().getName());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv2.setText(student.toString());
                }
            });

        }

    };

    private IMyAidlInterface iMyAidlInterface;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected: "+Thread.currentThread().getName());
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            Log.e(TAG, "onServiceConnected: "+iMyAidlInterface);
            try {
                iMyAidlInterface.registerListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            try {
                message = iMyAidlInterface.getMessage();
                Log.e(TAG, "onServiceConnected: "+ message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected: ");
            iMyAidlInterface = null;
        }
    };


    @Override
    protected void onDestroy() {
        try {
            iMyAidlInterface.unregisterListener(listener);
            unbindService(serviceConnection);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
