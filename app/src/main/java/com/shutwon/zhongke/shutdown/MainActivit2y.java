package com.shutwon.zhongke.shutdown;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivit2y extends AppCompatActivity {

    private DevicePolicyManager dpm;
    private ComponentName componentName;
 public void killProces(){
     ActivityManager manager=(ActivityManager)getSystemService(ACTIVITY_SERVICE);
     manager.killBackgroundProcesses("com.hootuu.Ad");
 }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main2);
        System.out.println("===============================");
    //   wakeUpAndUnlock(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                dpm = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
                componentName = new ComponentName(getApplicationContext(),
                        AdminReceiver.class);
                if (dpm.isAdminActive(componentName)){//判断是否有权限(激活了设备管理器)

                  //  dpm.lockNow();// 直接锁屏

                //    android.os.Process.killProcess(android.os.Process.myPid());
                }else{
                    activeManager();//激活设备管理器获取权限
                }
            }
        }).start();

    }
    public void LockScreen(View v){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                dpm = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
                componentName = new ComponentName(getApplicationContext(), AdminReceiver.class);
                if (dpm.isAdminActive(componentName)) {//判断是否有权限(激活了设备管理器)
                    killProces();
                    dpm.lockNow();// 直接锁屏
                    System.out.println("==dpm==");
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("=================");
                            sendBroadcast(new Intent("zhongke.com.test"));
                            wakeUpAndUnlock(MainActivit2y.this);
                        }
                    },5*1000);
                    //杀死当前应用
                  //  android.os.Process.killProcess(android.os.Process.myPid());
                }else{
                    activeManager();//激活设备管理器获取权限
                }
            }
        }).start();
    }

    private void activeManager() {
        //使用隐式意图调用系统方法来激活指定的设备管理器
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "一键锁屏");
        startActivity(intent);
    }
    @SuppressLint("Wakelock")
    @SuppressWarnings("deprecation")
    public static void wakeUpAndUnlock(Context context) {
        // 获取电源管理器对象
        PowerManager pm = (PowerManager) context
                .getSystemService(Context.POWER_SERVICE);
        // 获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(
                PowerManager.ACQUIRE_CAUSES_WAKEUP
                        | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
        // 点亮屏幕
        wl.acquire();
        // 释放
        wl.release();
        // 得到键盘锁管理器对象
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        // 解锁
        kl.disableKeyguard();
    }
}
