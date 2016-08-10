package com.auto.mm;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;
import com.auto.mm.assist.QQLitesAccess;
import com.auto.mm.assist.WechatAccess;
import com.auto.mm.database.Config;
import com.auto.mm.recore.PhoneReceiver;
import com.auto.mm.recore.ScreenOffReceiver;
import com.auto.mm.util.AccessServiceUtil;
import com.auto.mm.util.IAccessServiceImpl;
import com.auto.mm.wechat.SendMessages;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


/**
 * 微信辅助服务

 设计过程:

 QQ:每日早晨06:20分开启

 启动QQ--每5分钟推送一次指定的广告信息
 11点钟停止到下午14点钟
 夜间21点钟停止推送
 之后上报推送数据到我的微信

 实现过程:

 通过定时线程启动预设APP
 通过遍历控件ID或唯一文本值来确定所要进行的操作
 服务在开启的同时启动一个定时线程,用于执行指定策略(输入预设内容，点击处理按钮等…)

 * Created by Ecvit Cnweaks@ecvit.com
 */
public class AccessService extends AccessibilityService{
        //打开系统无障碍设置界面
        /*定时器部分
         */
        public static int TIMER_HOUR = 5;//小时
        public static int TIMER_MINU = 53;//分钟
        public static int TIMER_MOUM = 8;//月
        public static int TIMER_TDAY = 8;//天
        public static int TIMER_YEAR = 2016;//年
        public  boolean okmp = false;//微信
        public  boolean qqlite = false;//QQ
        public static String INPUT_WC = "LauncherUI";//微信聊天界面输入框
        public static String INPUT_QQ = "com.tencent.mobileqq.activity.ChatActivity";//QQ聊天界面     
        public static String PACKEG_WX = "com.tencent.mm";//微信包名
        public static String PACKEG_QQ = "com.tencent.qqlite";//QQ包名
        public static String CLASS_WX = "com.tencent.mm.ui.LauncherUI";//微信首页类名
        public static String CLASS_QQ = "com.tencent.mobileqq.activity.SplashActivity";//QQ首页类名
        public static int WECHATA = 0;//QQ首页
        public static int MOBILEQQ = 1;//QQ首页类名
        public static int WECHATB = 2;//QQ首页类名

        public static String ID_MSGEDIT = "com.tencent.mm:id/yq";//消息编辑框
        public static String ID_TITLE = "com.tencent.mm:id/cg_";//聊天窗口标题(名称)
        public static  final String TAG = "Access";
        public static String[] MSG_QUN = {"测试群(3)" , "快联支付"};
        
        public static QQLitesAccess lite = new QQLitesAccess();
        public static WechatAccess wechat = new WechatAccess();
        public static AccessServiceUtil util = new AccessServiceUtil();
        public static  List<IAccessServiceImpl> impls = new ArrayList<IAccessServiceImpl>();
        private AccessibilityNodeInfo editText = null;
        public static String SENDCONTEXT = "软件测试消息，无需理会，谢谢";
        public static  boolean isblockT = true;
        public static  boolean isblockQ = true;


        public boolean isroom = true;
        public static SendMessages sende = new SendMessages();
        public static PowerManager pm;

        public static KeyguardManager km;

        public static KeyguardManager.KeyguardLock kl;

        public static ScreenOffReceiver sreceiver;

        public static PhoneReceiver preceiver;

        public static  TelephonyManager tm;

        public static  PhoneStateListener listener;
        public static void addImpl(IAccessServiceImpl serviceImpl){
                impls.add(serviceImpl);
            }
        protected void onServiceConnected(){
                Log.i(TAG, "服务接入成功");
                AccessibilityServiceInfo info = getServiceInfo();
                info.notificationTimeout = 600;
                info.packageNames = new String[]{PACKEG_WX , PACKEG_QQ};
                setServiceInfo(info);
                info.getSettingsActivityName();
                
                //获取电源管理器对象
                pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
                //得到键盘锁管理器对象
                km = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
                //得到键盘锁管理器对象
                kl = km.newKeyguardLock("unLock");
                editText = null;
                okmp = true;
                //qqlite = true;
                if (okmp){
                        Log.i(TAG , "微信辅助已启用");   
                    }
                if (qqlite){
                        Log.i(TAG , "QQ辅助已启用");   
                    }
                //注册广播接收器
                sreceiver = new ScreenOffReceiver();
                IntentFilter filter = new IntentFilter();
                filter.addAction(Intent.ACTION_SCREEN_OFF);
                filter.addAction(Intent.ACTION_SCREEN_ON);
                registerReceiver(sreceiver, filter);

                preceiver = new PhoneReceiver();
                filter = new IntentFilter();
                filter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);
                registerReceiver(preceiver, filter);

                tm = (TelephonyManager)getSystemService(Service.TELEPHONY_SERVICE);  
                //设置一个监听器
                tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

                Toast.makeText(this, "辅助工具已连接", Toast.LENGTH_LONG).show();         

            }


        //绑定线程
        public boolean OnBind(Intent intent){
                return true;
            }


        public void onAccessibilityEvent(AccessibilityEvent event){     
                if (event.getPackageName().equals(PACKEG_WX) && okmp){            
                        wechat.Acest(event , this);

                    }
                if (event.getPackageName().equals(PACKEG_QQ) && qqlite){
                        lite.Acest(event , this);   


                    }  


                for (IAccessServiceImpl impl : impls){
                        impl.Acest(event, this);
                    }
                Iterator<IAccessServiceImpl> iterator = impls.iterator();
                while (iterator.hasNext()){
                        if (iterator.next().isCompleted()){
                                iterator.remove();
                            }
                    }
            }



        public void onInterrupt(){

                Toast.makeText(this, "辅助工具失去连接", Toast.LENGTH_LONG).show();
            }


 


        //剪切板内容设置
        public void SetClipData(String text){
                ClipboardManager clipboard = (ClipboardManager)
                    getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("message", text);
                clipboard.setPrimaryClip(clip);

            }




        @Override
        public void onDestroy(){
                super.onDestroy();
                Log.i("demo", "关闭");


                editText = null;

                //注销广播接收器
                unregisterReceiver(sreceiver);
                unregisterReceiver(preceiver);

                Config.total = 0;
                Config.replaied = 0;
                Toast.makeText(this, "辅助工具失去连接", Toast.LENGTH_LONG).show();
            }


    }
   
