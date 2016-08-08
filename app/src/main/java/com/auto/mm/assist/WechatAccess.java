package com.auto.mm.assist;
import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.auto.mm.database.Contact;
import com.auto.mm.database.Config;
import com.auto.mm.util.AccessServiceUtil;
import com.auto.mm.util.IAccessServiceImpl;
import com.auto.mm.util.ShellUtils;
import com.auto.mm.wechat.AddQunFriends;
import com.auto.mm.wechat.SendMessages;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.Date;
import android.provider.Settings;

public class WechatAccess implements IAccessServiceImpl{

        @Override
        public void onInterrupt(){
                //Toast.makeText(this, "微信辅助功能已接入", Toast.LENGTH_LONG).show();
                Log.i(TAG , "微信辅助功能已接入");
            }
        @Override
        public boolean isCompleted(){
                // TODO: Implement this method
                return false;
            }
        private static final String TAG = "Access";
        private static int n = 0;
        private static String MSG_CONT=null;
        public static boolean state = true , 
        isclickA = true ,//列表处理
        isclickB = true ,//列表处理
        isback = true ,//返回
        item = true ,//群列表
        isclickHome = true,//是否返回首屏幕
        iscroll = false ,//是否滚动屏幕
        isroom = true,//是否进入房间
        toqun = true,//是否跳转到群列表界面
        ismsg = true;//是否启动消息推送
        public static String WC_HOME = "ui.LauncherUI";//首页
        public static String WC_FIND = "NearbyFriendsUI";//附近人
        public static String WC_DEX = "ContactInfoUI";//详细资料
        public static String WC_ROOM = "ChattingUI";//聊天窗口对话框
        public static String WC_QROOM = "chatting.ChattingUI";//群聊天界面框
        public static String WC_ALLP = "SeeRoomMemberUI";//全部群友
        public static String WC_ALLL = "ui.contact.ChatroomContactUI";//群列表
        public static String WC_ADDUSER = "SayHiWithSnsPermissio";//添加用户界面
        public static int i = 0 , all = 0;;//序列指针
        public static String ID_EDIT = "com.tencent.mm:id/c0_";//编辑框，添加时
        public static String ID_BACK = "com.tencent.mm:id/ez";//返回按钮
        public static String ID_SENDMSG = "com.tencent.mm:id/yw";//发消息
        public static String ID_MSGEDIT = "com.tencent.mm:id/yq";//消息编辑框
        public static String ID_VOIDESAY = "com.tencent.mm:id/a7r";//视频聊天
        public static String ID_SEND = "com.tencent.mm:id/a7q";//发送
        public static String ID_QUNLIST = "com.tencent.mm:id/ek";//群列表
        public static String ID_LISTVIEW = "com.tencent.mm:id/i4";//群成员列表单个View
        public static String ID_LIST = "com.tencent.mm:id/au_";//群成员列表
        public static String ID_ISFRIENDY = "com.tencent.mm:id/dq";//是否允许查看朋友圈
        public static String ID_TOQUN = "com.tencent.mm:id/dn";//进入群资料页
        public static String WC_ZILO = "ContactInfoUI";//详细资料

        Timer timer;
        public static  List<IAccessServiceImpl> impls = new ArrayList<IAccessServiceImpl>();
        public static AccessServiceUtil util = new AccessServiceUtil();
        public static AddQunFriends addf = new AddQunFriends();
        public static SendMessages send = new SendMessages();
        private boolean canReply = false;//能否回复且每次收到消息只回复一次
        private int mode = 1;//微信通知模式：1.详细通知2.非详细通知
        public static String PACKEG_WX = "com.tencent.mm";//微信包名
        public static String PACKEG_QQ = "com.tencent.qqlite";//QQ包名

        public void Acest(AccessibilityEvent event , AccessibilityService services){
                if (ismsg){
                        TimerTo(services);
                        ismsg = false;
                    }
                String eventType = "";
                switch (event.getEventType()){          
                        case event.TYPE_NOTIFICATION_STATE_CHANGED:
                            eventType = " 状态栏变化";
                            List<CharSequence> texts = event.getText();
                            if (!texts.isEmpty()){
                                    String message = texts.get(0)+"";

                                    //过滤微信内部通知消息
                                    if (isInside(message)){
                                            return;
                                        }
                                    Config.total++;
                                    setData(message);
                                    //Log.i("demo", "收到通知栏消息:" + message);
                                    //收到信息发送更新锁屏界面广
                                    Intent i = new Intent("com.auto.mm.SHOW_ACTION");
                                    services.sendBroadcast(i);
                                    if (!Config.auto)
                                        return;
                                    //微信的两种通知消息类型，mode=1为详细内容，mode=2为通用类型
                                    if (message.equals("微信：你收到了一条消息。"))
                                        mode = 2;
                                    else
                                        mode = 1;
                                    //判断是否指定好友并进行过滤
                                    if (Config.isfriend && (mode == 1) && (!message.split(":")[0].equals(Config.friend))){
                                            Log.i("demo", "不匹配"); 
                                            return;
                                        }


                                    //模拟打开通知栏消息  
                                    if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification){
                                            Log.i("demo", "标题栏canReply=true");
                                            canReply = true;
          try{
                                                    Notification notification = (Notification) event.getParcelableData();  
                                                    PendingIntent pendingIntent = notification.contentIntent;  
                                                    pendingIntent.send();
                                                }catch (PendingIntent.CanceledException e){  
                                                    e.printStackTrace();  
                                                }
                                        }
                                    break;
                                }


                            break;

                        case event.TYPE_VIEW_CLICKED:
                            eventType = " 点击视图";
                            break;
                        case event.TYPE_VIEW_LONG_CLICKED:
                            eventType = " 长安视图";
                            break;
                        case event.TYPE_VIEW_SELECTED:
                            eventType = " 选择视图";
                            break;
                        case event.TYPE_VIEW_FOCUSED:
                            eventType = " 视图变化";
                            RunAcest(event , services);
                            SetClipData(MSG_CONT, services);
                            break;
                        case event.TYPE_VIEW_TEXT_CHANGED:
                            eventType = " 文字改变";
                            break;
                        case event.TYPE_WINDOW_STATE_CHANGED:
                            RunAcest(event , services);
                            eventType = " 窗口变化";
                            AccessibilityNodeInfo ifd =  event.getSource();
                            SetClipData(MSG_CONT, services);
                            if (ifd != null){
                                    CharSequence ids = ifd .getViewIdResourceName(); 
                                    Log.i(TAG , ids + "==点击的ID");
                                }
                            break;
                        case event.TYPE_WINDOW_CONTENT_CHANGED:
                            RunAcest(event , services);
                            eventType = " 窗口内容改变";
                            break;
                        case event.TYPE_VIEW_SCROLLED:
                            eventType = " 创建视图";
                            CharSequence chat =  event.getPackageName(); 
                            if (chat.length() > 1){
                                    RunAcest(event , services);
                                }
                            break;

                    }           
                Log.i(TAG, "事件类型: " + eventType);

            }

        //剪切板内容设置
        public void SetClipData(String text , AccessibilityService services){
                ClipboardManager clipboard = (ClipboardManager)
                    services.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("message", text);
                clipboard.setPrimaryClip(clip);

            }
        //微信群调度协调器
        public void RunAcest(AccessibilityEvent event , AccessibilityService server){
                CharSequence res = event.getClassName();
                String resu = "";
                if (res.length() < 1){
                        return;
                    }else{
                        resu = res.toString();         
                        Log.i(TAG , resu + "<<<----位置");
                    }

                if (resu.contains(WC_HOME)){
                        Log.i(TAG , "主界面/聊天窗口<<<----位置");
                        AccessibilityNodeInfo nodeInfo = event.getSource();
                        if (nodeInfo == null){
                                return;
                            }
                        List<AccessibilityNodeInfo> no = util.findNodesByViewId(nodeInfo , ID_TITLE);
                        if(!no.isEmpty()){
                            
                       // MSG_CONT = no.get(0).getText()+"\n<<<<<<<<群名称";
                        Log.i(TAG , no.get(0).getText()+"群名称");
                        }
                        if (!no.isEmpty() && canReply){
                                String title= no.get(0).getText() + "";                         
                                for (int i = 0; i < MSG_QUN.length; i++){
                                        if (title.contains(MSG_QUN[i]) && !title.isEmpty()){
                                                SetClipData(MSG_CONT , server);
                                                if(send.MSGInput(nodeInfo)){
                                                    send.performBack(server);
                                                        performHome(server);     
                                                }
                                                
                                            }
                                        Log.i(TAG , "<<<输入消息");        
                                        canReply = false;
                                        
                                    }                     
                                if (toqun){
                                        send.ToQunList(event.getSource());
                                        //  toqun = false;
                                    }       
                    }
}
                if (resu.contains(WC_ALLL)){
                        Log.i(TAG , "群列表界面<<<----位置");
                        //              Log.i(TAG , util.findNodesByText(event.getSource(), "测试群").get(0).getViewIdResourceName()+"");
                        send.GetList(event , ID_QUNLIST);

                    }

               
                if (resu.contains(WC_ZILO)){
                        Log.i(TAG , "用户详细资料页<<<----位置");
                        item = true;
                        if (isclickA){
                                isclickA = false;
                                if (!util.findNodesByText(event.getSource() , "添加到通讯录").isEmpty()){
                                        addf. Textclick("添加到通讯录" , event.getSource());
                                        Log.i(TAG , "点击添加到通讯录");        
                                    }
                                if (!util.findNodesByText(event.getSource() , "发消息").isEmpty()){
                                        Log.i(TAG , "返回");   
                                        addf.GoBack(event.getSource());
                                    }
                            }
                        //如果A不可点击，B可点击 isback允许下，跳转到列表
                        if (!isclickB){
                                addf.GoBack(event.getSource());


                            }

                    }

                if (resu.contains(WC_ADDUSER)){
                        Log.i(TAG , "添加联系人<<<----位置");

                        if (isclickB){
                                addf.AddFriendly(event.getSource());
                                isclickB = false;                     
                            }
                    }  


                if (resu.contains(WC_ALLP)){
                        Log.i(TAG , "全体群友<<<----位置");
                        isclickA = true;
                        isclickB = true;
                        List<AccessibilityNodeInfo> lis = util.findNodesByViewId(event.getSource(), ID_LIST);
                        all = lis.size();
                        Log.i(TAG , lis.get(0).getViewIdResourceName() + "<<<----数量");
                        if (iscroll){
                                StartCmds();
                                i = 0;
                                RunAcest(event , server);
                                item = true;
                                Log.i(TAG, "执行Shell命令 <<<----执行结果");
                            }  
                        if (i < all && item){
                                item = false;
                                IdClick(lis , i);               
                                i++;
                                if (i == all){
                                        iscroll = true;
                                        i = 0;
                                    }else{
                                        iscroll = false; 
                                    }
                            }

                    }


            }

        //常见的微信内部通知，可自行测试并修改
        //U递邀请码525364(161)
    
        private boolean isInside(String msg){
                boolean result = false;
                if (msg.equals("已复制") || msg.equals("已分享") || msg.equals("已下载"))
                    result = true;
                if (msg.length() > 6 && (msg.substring(0, 6).equals("当前处于移动") || msg.substring(0, 6).equals("无法连接到服") || msg.substring(0, 6).equals("图片已保存至") || msg.substring(0, 6).equals("网络连接不可")))
                    result = true;
                return result;
            }

        @SuppressWarnings("static-access")
        private void setData(String data){
                Time time = new Time(); 
                time.setToNow(); 
                int hour = time.hour;
                int minute = time.minute;
                if (Config.showall){

                    }else{
                        if (!data.equals("微信：你收到了一条消息。")){
                                data = data.split(":")[0];
                                //Log.i("demo", "showall=" + StaticData.showall+" data=" + data);
                                data += " 发来一条消息。";
                            }
                    }
                data = data.format("%s     %02d:%02d", data, hour, minute);
                Config.data.add(data);
            }

        public String StartCmds(){
                String[] cmdsr = new String[]{
                        "input touchscreen swipe 310 1200 310 345 2100"

                    };

                ShellUtils.CommandResult result = ShellUtils.execCommand(cmdsr, true , true);
                return result.successMsg;
            }

        //执行点击动作，处理包含有文字的按钮
        public void WechatOnclick(String str, AccessibilityNodeInfo info , int i){
                List<AccessibilityNodeInfo> list =
                    util.findNodesByText(info, str);
                if (list.get(i).getText() != null){
                        Log.i(TAG , list.get(i).getText() + "<<<----点击");
                        util.click(list.get(i));
                    }else{
                        Log.i(TAG , "不可点击");
                    }
            }    
        //顶群友屁眼
        //父级节点，第i个
        public void IdClick(List<AccessibilityNodeInfo> list , int i){
                if (list.get(i) != null){
                        Log.i(TAG , list.get(i).getText() + "<---群友名称");
                        util.click(list.get(i));
                    }else{
                        Log.i(TAG , "指定的第" + i + "个群友不存在");
                    }
            }

        //输入消息
        //输入群发消息
        public void MessageInput(AccessibilityNodeInfo acno){
                List<AccessibilityNodeInfo> list = 
                    util.findNodesByViewId(acno , ID_MSGEDIT);          
                if (!list.isEmpty()){
                        util.Fcus(list.get(0));
                        util.paste(list.get(0));
                        SendTo(acno);
                    }
            }

        /** 返回主界面事件*/
        public static void performHome(AccessibilityService service) {
                if(service == null) {
                        return;
                    }
                service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
            }
        

        //返回上级
        public void GoBack(AccessibilityNodeInfo info){
                List<AccessibilityNodeInfo> list =
                    util.findNodesByViewId(info , ID_BACK);
                Log.i(TAG , "返回按钮");
                if (!list.isEmpty()){
                        Log.i(TAG , "存在返回按钮");
                        util.click(list.get(0));  
                    }
            }     

        //发送按钮
        public void SendTo(AccessibilityNodeInfo acno){
                List<AccessibilityNodeInfo> list = 
                    util.findNodesByViewId(acno , ID_SENDMSG);
                if (!list.isEmpty()){
                        util.click(list.get(0));
                    }else{
                        Log.i(TAG , "未能输入内容");
                    }
            }               
        //启动定时器
        //格式:任务，延迟，循环周期
        //主要用于间隔性推送消息到群
        public static String ID_TITLE = "com.tencent.mm:id/cg_";//聊天窗口标题(名称)
        public static String[] MSG_QUN = {"测试群" , "A00"};
        public void TimerTo(final AccessibilityService server){
                timer  = new Timer(true);//定时器 
                timer.schedule(
                    new java.util.TimerTask(){
                            public boolean isblock = true;   
                            int c =0;
                            public void run(){
                                    AccessibilityNodeInfo rowNode = server.getRootInActiveWindow();                
                                    List<AccessibilityNodeInfo> no = util.findNodesByViewId(rowNode , ID_TITLE);
                                    if (!no.isEmpty()){
                                            String title= no.get(0).getText() + "";                         
                                            for (int i = 0; i < MSG_QUN.length; i++){
                                                    if (title.contains(MSG_QUN[i]) && !title.isEmpty()){
                                                            send.MSGInput(rowNode);
                                                            
                                                         //  MSG_CONT = "第"+c+"条消息";
                                                            c++;
                                                            //             
                                                        }
                                                    Log.i(TAG , "<<<消息推送器");              
                                                }                     
                                        } 
                                }


                        }, 0, 1 * 2 * 1000);//每隔30秒更新一次
            }        


        /*
         打开辅助工具
         */
        public void OpenAccess(int APPType , AccessibilityService server){
                Intent accessibleIntent = 
                    new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);       
                accessibleIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                server.getApplicationContext().startActivity(accessibleIntent);
            }


        /*
         *启动微信
         */


        public static String CLASS_WX = "com.tencent.mm.ui.LauncherUI";//微信首页类名
        public static String CLASS_QQ = "com.tencent.mobileqq.activity.SplashActivity";//QQ首页类名

        public void RunWechat(AccessibilityService server){
                Intent intent = new Intent();
                intent.setClassName(PACKEG_WX , CLASS_WX);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                server.getApplicationContext().startActivity(intent); 

            }


        //其中循环的方法recycle():
        public void recycle(AccessibilityNodeInfo info){

                if (info.getChildCount() == 0){
                        //Log.i(TAG , info.getClassName()  +"类名");

                    }else{
                        for (int i = 0; i < info.getChildCount(); i++){
                                if (info.getChild(i) != null){
                                        recycle(info.getChild(i));
                                    }
                            }
                    }
            }        







    }
