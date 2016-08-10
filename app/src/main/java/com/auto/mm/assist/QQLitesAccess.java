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
