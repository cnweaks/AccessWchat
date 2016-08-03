package com.auto.mm.assist;
import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.auto.mm.util.AccessServiceUtil;
import com.auto.mm.util.IAccessServiceImpl;
import com.auto.mm.util.ShellUtils;
import java.util.List;

public class QQLitesAccess implements IAccessServiceImpl{

        @Override
        public void onInterrupt(){
                
            }

        @Override
        public boolean isCompleted(){
                
                return false;
            }
        


        //QQLite界面段，类名
        public static String QQ_MSGTABLE = "SplashActivity";//消息栏目
        public static String QQ_LIST = "TroopAssistantActivity";//群列表栏目
        public static String QQ_DEX = "ChatSettingForTroop";//详细资料
        public static String QQ_ROOM = "com.tencent.mobileqq.activity.ChatActivity";//群聊天界面
        public static String QQ_SEER = "TroopMemberListActivity";//全部群友
        public static String QQ_USER = "TroopMemberCardActivity";//用户详细资料页
        public static String QQ_ADDU = "AddFriendVerifyActivity";//添加用户界面
        public static String QQ_AUTORM = "AutoRemarkActivity";//备注页
        public static String QQ_AEDIT = "EditText";//添加编辑框
        public static AccessServiceUtil util = new AccessServiceUtil();
        private static final String TAG = "Access";
        public static String ID_LSTINDEX = "com.tencent.qqlite:id/relativeItem";//群对列Item
        public static String ID_EDIT = "com.tencent.qqlite:id/input";//输入框
        public static String ID_SEND = "com.tencent.qqlite:id/fun_btn";//发送按钮
        public static String ID_SENDYZ = "com.tencent.qqlite:id/ivTitleBtnRightText";//验证发送按钮
        public static String ID_LISTVIEW = "com.tencent.qqlite:id/tv_name";//群成员列表单个View
        public static String ID_ADD = "com.tencent.qqlite:id/txt";//群成员添加
        public static String ID_ISFRIENDY = "com.tencent.mm:id/dq";//是否允许查看朋友圈
        public static String ID_TOQUN = "com.tencent.mm:id/dn";//进入群信息
        public static String ID_NEXT = "com.tencent.qqlite:id/ivTitleBtnRightText";//编辑框，添加时
        public static String ID_BACK = "com.tencent.mm:id/ez";

        private static boolean isclickB = false , isclickA = false;


        public static String STR_TOLIST = "群助手";
        public static String STR_DET = "";//进入群信息


        private static int all = 0 , i = 0;

    
    
       /* 
        public void onAccessibilityEvent(AccessibilityEvent event){     
                if (event.getPackageName().equals(PACKEG_WX) ||
                    event.getPackageName().equals(PACKEG_QQ)){             
                        String eventType = "";
                        switch (event.getEventType()){          
                                case event.TYPE_NOTIFICATION_STATE_CHANGED:
                                    eventType = " 状态栏变化";
                                    List<CharSequence> texts = event.getText();
                                    if (!texts.isEmpty()){
                                            String message = texts.get(0).toString();

                                            //过滤微信内部通知消息
                                            if (isInside(message)){
                                                    return;
                                                }

                                            StaticData.total++;
                                            setData(message);

                                            //Log.i("demo", "收到通知栏消息:" + message);
                                            //收到信息发送更新锁屏界面广
                                            //  Intent i = new Intent("com.auto.mm.SHOW_ACTION");
                                            //  sendBroadcast(i);

                                            if (!StaticData.auto)
                                                return;

                                            //微信的两种通知消息类型，mode=1为详细内容，mode=2为通用类型
                                            if (message.equals("微信：你收到了一条消息。"))
                                                mode = 2;
                                            else
                                                mode = 1;
                                            //判断是否指定好友并进行过滤
                                            if (StaticData.isfriend && (mode == 1) && (!message.split(":")[0].equals(StaticData.friend))){
                                                    Log.i("demo", "不匹配"); 
                                                    return;
                                                }

                                            //模拟打开通知栏消息  
                                            if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification){
                                                    Log.i("demo", "标题栏canReply=true");
                                                    canReply = true;
                                                    //   sende.wakeAndUnlock(true);
                                                    SetClipData("你好，你是哪位?");
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
                                    AccessibilityNodeInfo ifd =  event.getSource();
                                    List<AccessibilityNodeInfo> nodes = util.findNodesByText(event.getSource() , ".心知道");
                                    if (!nodes.isEmpty()){
                                            Log.i(TAG, nodes.get(0).getViewIdResourceName() + "找到的资源ID");   
                                        }
                                    if (ifd != null){
                                            CharSequence ids = ifd .getViewIdResourceName(); 
                                            Log.i(TAG , ids + "==点击的ID");
                                        }
                                    break;
                                case event.TYPE_VIEW_LONG_CLICKED:
                                    eventType = " 长安视图";
                                    List<AccessAction> acs = new ArrayList<AccessAction>();
                                    new AccessAction(AccessAction.KeyType.TEXT, "微信" , AccessibilityEvent.TYPE_ANNOUNCEMENT , "你好");
                                    new OpenApp("com.tencen2.mm", acs);
                                    break;
                                case event.TYPE_VIEW_SELECTED:
                                    eventType = " 选择视图";
                                    break;
                                case event.TYPE_VIEW_FOCUSED:
                                    eventType = " 视图变化";
                                    if (event.getPackageName().equals(PACKEG_QQ)){
                                            lite.Acest(event , this);
                                        }else{
                                            wechat.Acest(event , this);
                                            SetClipData(SENDCONTEXT);     
                                        }
                                    break;
                                case event.TYPE_VIEW_TEXT_CHANGED:
                                    eventType = " 文字改变";
                                    break;
                                case event.TYPE_WINDOW_STATE_CHANGED:
                                    if (event.getPackageName().equals(PACKEG_QQ)){

                                            lite.Acest(event , this);
                                        }else{
                                            wechat.Acest(event , this);
                                        }
                                    eventType = " 窗口变化";
                                    break;

                                case event.TYPE_VIEW_HOVER_ENTER:
                                    eventType = " 键盘弹出";
                                    break;
                                case event.TYPE_VIEW_HOVER_EXIT:                         
                                    eventType = " 输入法退出";
                                    break;
                                case event.TYPE_TOUCH_EXPLORATION_GESTURE_START:
                                    eventType = " 开始触摸";
                                    break;
                                case event.TYPE_TOUCH_EXPLORATION_GESTURE_END:
                                    eventType = " 触摸结束";
                                    break;
                                case event.TYPE_WINDOW_CONTENT_CHANGED:
                                    if (event.getPackageName().equals(PACKEG_WX)){
                                            wechat.Acest(event , this);
                                            Log.i(TAG , "窗口内容吊起微信");
                                        }
                                    if (canReply){
                                            canReply = false;
                                            sende.MessageInput(event.getSource());
                                            //    sende.performBack(this);
                                        }
                                    Log.i(TAG , event.getText() + "<<<结果查询");    
                                    //   if(event.getClassName().toString().contains("SeeRoomMemberUI")){

                                    //     List<AccessibilityNodeInfo> list1 = event.getSource().findAccessibilityNodeInfosByText("");
                                    //  if( !list1.isEmpty() )
                                    //Log.i(TAG , list1.get(0).getText()+"<<<结果查询");
                                    //       }     


                                    eventType = " 窗口内容改变";
                                    break;
                                case event.TYPE_VIEW_SCROLLED:
                                    eventType = " 创建视图";
                                    CharSequence chat =  event.getPackageName(); 
                                    if (chat.length() > 1){

                                            if (chat.equals(PACKEG_WX)){
                                                    wechat.Acest(event , this); 
                                                }
                                        }
                                    break;
                                case event.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                                    eventType = " 选择改变";
                                    break;
                                case event.TYPE_ANNOUNCEMENT:
                                    eventType = " 网络连接";

                                    break;
                                case event.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY:
                                    eventType = " 文本移动";
                                    break;
                                case event.TYPE_GESTURE_DETECTION_START:
                                    eventType = " 访客开始";
                                    break;
                                case event.TYPE_GESTURE_DETECTION_END:
                                    eventType = " 访客结束";
                                    break;
                                case event.TYPE_TOUCH_INTERACTION_START:
                                    eventType = " 开始联网";
                                    break;
                                case event.TYPE_TOUCH_INTERACTION_END:
                                    eventType = " 联网结束";     
                                    break;
                                case event.TYPE_VIEW_CONTEXT_CLICKED:
                                    eventType = " 未知活动";
                                    break;
                            }           
                        Log.i(TAG, "事件类型: " + eventType);
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
        
      */  
        
        public void Acest(AccessibilityEvent event , AccessibilityService services){
                String eventType = "";
                switch (event.getEventType()){          
            case event.TYPE_VIEW_FOCUSED:
                eventType = " 视图变化";
                RunAcest(event);      
                break;
            case event.TYPE_VIEW_TEXT_CHANGED:
                eventType = " 文字改变";
                break;
            case event.TYPE_WINDOW_STATE_CHANGED:
                RunAcest(event);
                eventType = " 窗口变化";
                break;
            }
            Log.i(TAG , eventType);
      }
        //QQ群(基于QQlite版)调度协调器
            public void RunAcest(AccessibilityEvent event){
                CharSequence res = event.getClassName();
                    String resu = "";
                if(res.length() < 1){
                    return;
                    }else{
              resu = res.toString();         
                        
                    }
                if (resu.contains(QQ_MSGTABLE)){
                        Log.i(TAG ,  "消息栏目<<<----位置");
          //ToList(event.getSource());
                    }
                if (resu.contains(QQ_LIST)){
                        Log.i(TAG ,  "群列表界面<<<----位置");
       
                        
                        isclickA = true;
                        isclickB = true;
                        List<AccessibilityNodeInfo> lis = util.findNodesByViewId(event.getSource(), ID_LSTINDEX);
                        all = lis.size();
                        Log.i(TAG , all + "<<<----数量");
                        if (i < all){
                                AllQQClick(event.getSource() , i);
                                i++;
   
                                }
                                
                    }
                if (resu.contains(QQ_ROOM)){
                        Log.i(TAG , "群聊天界面<<<----位置");

                        QQInput(event.getSource());
                    }
                if (resu.contains(QQ_DEX)){
                        Log.i(TAG , "群详细资料页<<<----位置");
                    }

                if (resu.contains(QQ_SEER)){
                        Log.i(TAG , "全体群成员<<<----位置");
                    }

                if (resu.contains(QQ_USER)){
                        Log.i(TAG , "单用户界面<<<----位置");

                    }

                if (resu.contains(QQ_ADDU)){
                        Log.i(TAG , "添加用户<<<----位置");

                    }    
                if (resu.contains(QQ_AEDIT)){
                        Log.i(TAG , "编辑框<<<----位置");


                    }                       
                if (resu.contains(QQ_AUTORM)){
                        Log.i(TAG , "自动备注页面<<<----位置");

                    }     
            }


        //输入消息
        //输入群发消息
        public void QQInput(AccessibilityNodeInfo acno){
                List<AccessibilityNodeInfo> list = 
                    util.findNodesByViewId(acno , ID_EDIT);
                if (!list.isEmpty()){

                        util.Fcus(list.get(0));       
                        util.paste(list.get(0));
                        Log.i(TAG , list.get(0).getViewIdResourceName() + "输入内容<---位置");
                        SendTo(acno);
                    }       


            }

        //进入群列表
        //及点击群助手
        public void ToList(AccessibilityNodeInfo acno){
                List<AccessibilityNodeInfo> list = 
                    util.findNodesByViewId(acno , STR_TOLIST);
                if (list.isEmpty()){
                        util.click(list.get(0)); 
                        Log.i(TAG , list.get(0).getViewIdResourceName() + "进入群列表<---位置");
                        SendTo(acno);
                    }       


            }     
        //发送按钮
        public void SendTo(AccessibilityNodeInfo acno){
                List<AccessibilityNodeInfo> list = 
                    util.findNodesByViewId(acno , ID_SEND);
                if (!list.isEmpty()){
                        Log.i(TAG , list.get(0).getViewIdResourceName() + "发送按钮<---位置");
                        util.click(list.get(0));

                    }else{
                        Log.i(TAG , "未能输入内容");
                    }
            }     

        //顶群友屁眼
        //父级节点，第i个
        public void AllQQClick(AccessibilityNodeInfo acno , int i){
                List<AccessibilityNodeInfo> list = 
                    util.findNodesByViewId(acno , ID_LSTINDEX);
                if (!list.isEmpty()){
                        Log.i(TAG , list.get(i).getText() + "<---群名称");
                        util.click(list.get(i));
                    }else{
                        Log.i(TAG , "指定的第" + i + "个群不存在");
                    }
            }       
        public void StartCmds(){
                String[] cmdsr = new String[]{
                        "input touchscreen swipe 310 1200 310 345 2100"
                        //从屏幕坐标310,1200滑动到坐标310,300 基于720x1280分辨率
                    };             
                ShellUtils.execCommand(cmdsr, true , true);

            }
    }
