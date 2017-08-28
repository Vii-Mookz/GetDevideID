package com.example.vii_mook.getdevideid;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringDef;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by vii-mook on 8/28/2017 AD.
 */

public class AndroidTelephonyManager extends Activity {
    protected static volatile UUID uuid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},1);

    TextView txtDevice = findViewById(R.id.deviceid);
        TextView txtSN = findViewById(R.id.txtSNM);
        TextView txtSNMobile = findViewById(R.id.txtSN);
        TextView txtCheck = findViewById(R.id.txtCheck);
    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        txtDevice.setText(getDeviceID(telephonyManager));
        txtSN.setText(telephonyManager.getSimSerialNumber());

//        txtSNMobile.setText(uuid.toString());
        txtSNMobile.setText(getSerial());
        txtCheck.setText(telephonyManager.getLine1Number());
}


public static String getSerial() {
    String serial = null;
    try {
        Class<?> c = Class.forName("android.os.SystemProperties");
        Method get = c.getMethod("get", String.class, String.class);
        serial = (String) get.invoke(c, "ril.serialnumber", "unknown");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (NoSuchMethodException e) {
        e.printStackTrace();
    } catch (InvocationTargetException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    }
    return serial;
}
    String getDeviceID(TelephonyManager telephonyManager) {

        String id = telephonyManager.getDeviceId();
        if (id == null) {
            id = "not available";

        }
        int phoneType = telephonyManager.getPhoneType();
        switch (phoneType) {
            case TelephonyManager.PHONE_TYPE_NONE:
                return "NONE:" + id;
            case TelephonyManager.PHONE_TYPE_GSM:
                return "GSM: IMEI=" + id;
            case TelephonyManager.PHONE_TYPE_CDMA:
                return "CDMA: MEID/ESN=" + id;

            default:
                return "UNKNOWN: ID=" + id;
        }
    }

}
