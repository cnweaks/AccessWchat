package com.auto.mm.wechat;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.auto.mm.database.DatabaseHandler;
import com.auto.mm.util.AccessServiceUtil;
import com.auto.mm.util.ShellUtils;
import java.util.List;
import com.auto.mm.database.Contact;

public class AddQunFriends {

        private static final String TAG = "Access";

        public static boolean state = true , 
        isclickA = true ,
        isclickB = true ,//
        isback = true ,//返回
        item = true ,//群列表
        isclickHome = true,
        isroom = true;
        
        public Context context;
        public static String ID_EDIT = "com.tencent.mm:id/nc";//编辑框，添加时
        public static String ID_BACK = "com.tencent.mm:id/c69";//返回按钮




        public static String ID_VOIDESAY = "com.tencent.mm:id/a7r";//视频聊天
        public static String ID_SEND = "com.tencent.mm:id/cf8";//发送
        public static String ID_LIST = "com.tencent.mm:id/au_";//群成员列表
        public static String ID_ISFRIENDY = "com.tencent.mm:id/dq";//是否允许查看朋友圈
        public static String ID_TOQUN = "com.tencent.mm:id/dn";//进入群信息

        public static AccessServiceUtil util = new AccessServiceUtil();
        


        public String StartCmds(){
                String[] cmdsr = new String[]{
                        "input touchscreen swipe 310 1200 310 345 2100"
                        //,"input touchscreen text \"test\""
                        //从屏幕坐标310,1200滑动到坐标310,300 基于720x1280分辨率
                    };

                ShellUtils.CommandResult result = ShellUtils.execCommand(cmdsr, true , true);
                return result.successMsg;
            }










        //执行点击动作，处理包含有文字的按钮
        public void WechatOnclick(String str, AccessibilityNodeInfo info , int i){
                List<AccessibilityNodeInfo> list =
                    util.findNodesByText(info, str);
                if (list.get(i).isClickable()){
                        util.click(list.get(i));
                    }else{
                        Log.i(TAG , "不可点击");
                    }
            }    
        //执行点击动作，处理包含有文字的按钮，不指定默认第一个
        public void Textclick(String str, AccessibilityNodeInfo info){
                List<AccessibilityNodeInfo> list =
                    util.findNodesByText(info, str);
                if (list.get(0).isClickable()){
                    util.click(list.get(0));
                    }else{
                        Log.i(TAG , "不可点击");
                    }
            }    



        private void findNodeInfosByName(AccessibilityNodeInfo nodeInfo, String name) {
                if(name.equals(nodeInfo.getClassName())) {
                        return;
                    }
                for(int i = 0; i < nodeInfo.getChildCount(); i++) {
                        findNodeInfosByName(nodeInfo.getChild(i), name);
                    }
            }


        public void AddFriendly(AccessibilityNodeInfo acno){
                
                List<AccessibilityNodeInfo> list = 
                    util.findNodesByViewId(acno , ID_EDIT);
                if (!list.isEmpty()){
                    util.Fcus(list.get(0));
                    util.paste(list.get(0));
                        Log.i(TAG, list.get(0).getInputType() + "<---编辑框");
                        List<AccessibilityNodeInfo> li = util.findNodesByText(acno, "发送");
                        if (!li.isEmpty()){
                                Log.i(TAG , li.get(1).getText() + "<---发送"); 
                                util.click(li.get(1));
                           
                            }   
                    }else{
                        Log.i(TAG , "没有找到编辑框, 跳过内容编辑");
                    }
                    
                

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





    }
