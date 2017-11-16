package com.shutwon.zhongke.shutdown;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.admin.DeviceAdminReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import android.widget.Toast;

/**
 * Created by zxb on 2016/7/11.
 * Description:
 */
public class AdminReceiver extends DeviceAdminReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
     //   System.out.println(intent.getAction().);
        try {
           Intent  intents =new Intent();
            ComponentName componentName =new ComponentName("com.hootuu.Ad","com.hootuu.Ad.Main2Activity");
            intents.setComponent(componentName);
            intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intents);
        /*    Intent intents = new Intent();

           PackageManager packageManager = context.getPackageManager();

            intents = packageManager.getLaunchIntentForPackage("com.hootuu.Ad");

            intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP) ;

            context.startActivity(intents);*/
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "没有安装", Toast.LENGTH_SHORT).show();
        }

    }
    @SuppressLint("Wakelock")
    @SuppressWarnings("deprecation")
    public static void wakeUpAndUnlock(Context context) {
        // 获取电源管理器对象
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        // 获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
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
