package com.auto.mm.recore;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.auto.mm.database.Config;

//通话状态变化广播接收器，通话期间不弹出锁屏活动
public class PhoneReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
                        Config.iscalling = true;
                        Log.i("demo", "去电");
                    } else {
                        Config.iscalling = true;
                        Log.i("demo", "来电");
                    }
            }
       
        //通话状态变化广播接收器，通话期间不弹出锁屏活动
        private PhoneStateListener listener = new PhoneStateListener() {
                @Override
                public void onCallStateChanged(int state, String incomingNumber) {
                        super.onCallStateChanged(state, incomingNumber);
                        switch(state) {
                                case TelephonyManager.CALL_STATE_IDLE:
                                    Config.iscalling = false;
                                    Log.i("demo", "挂断");
                                    break;
                                case TelephonyManager.CALL_STATE_OFFHOOK:
                                    Config.iscalling = true;
                                    Log.i("demo", "接听");
                                    break;
                                case TelephonyManager.CALL_STATE_RINGING:
                                    Config.iscalling = true;
                                    Log.i("demo", "来电");
                                    break;
                            }
                    }
            };
        private TelephonyManager tm;
    }
