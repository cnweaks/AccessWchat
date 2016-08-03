package com.auto.mm.recore;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.auto.mm.database.StaticData;
import com.auto.mm.AccessService;

public //屏幕状态变化广播接收器，黑屏或亮屏显示锁屏界面
class ScreenOffReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
                //若在通话则不显示锁屏界面
                if(StaticData.iscalling)
                    return;
                String action = intent.getAction();
                if (action.equals(Intent.ACTION_SCREEN_OFF)) {
                        Log.i("demo", "screen off");
        
                        /*
                        Intent lockscreen = new Intent(context, LockScreenActivity.class);
                        lockscreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(lockscreen);
                    } else if (action.equals(Intent.ACTION_SCREEN_ON)) {
                        Log.i("demo", "screen on");
                        if(canReply)
                            return;
                        Intent lockscreen = new Intent(context, LockScreenActivity.class);
                        lockscreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(lockscreen);
     
                        */
                        
                        }
            }
    }
    
