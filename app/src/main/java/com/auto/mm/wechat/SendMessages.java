package com.auto.mm.wechat;
import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.auto.mm.database.Config;
import com.auto.mm.util.AccessServiceUtil;
import java.util.List;
public class SendMessages{


        private int mode = 1;//微信通知模式：1.详细通知2.非详细通知 
        private AccessibilityNodeInfo editText = null;





        public static String WC_ALLL = "ui.contact.ChatroomContactUI";//全部列表
        public static String ID_BACK = "com.tencent.mm:id/c69";//返回按钮

        public static String ID_SENDMSG = "com.tencent.mm:id/yw";//发消息
        public AccessServiceUtil util = new AccessServiceUtil();
        public static String ID_MSGEDIT = "com.tencent.mm:id/yq";//消息编辑框
        private String TAG = "Access"; 
        public static boolean state = true , 
        isclickA = true ,
        isclickB = true ,//
        isback = true ,//返回
        item = true ,//群列表
        isclickHome = true,
        isroom = true;
        public static int i = 0 , all = 0;;//序列指针
        public static String ID_QUNLIST = "com.tencent.mm:id/gd";//群列表 
        public static String WC_ROOM = "ChattingUI";//聊天窗口  






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
        public void ToQunList(AccessibilityNodeInfo info){
                if (!util.findNodesByText(info , "群聊").isEmpty()){
                        util.Textclick("群聊" , info);
                    }
            }


        public void GetList(AccessibilityEvent event , String idlist){
                isclickA = true;
                isclickB = true;
                List<AccessibilityNodeInfo> lis = util.findNodesByViewId(event.getSource(), idlist);
                all = lis.size();
                Log.i(TAG , all + "<<<----当页数量");
                if (i <= all && item){
                        item = false;
                        util.IdClick(event.getSource() , i , idlist);    
                        i++;
                    }
                /*
                 if (all > i){
                 //StartCmds();
                 i = 0;
                 StartAccess(event);
                 item = true;
                 Log.i(TAG, "执行Shell命令 <<<----执行结果");
                 }  

             */}      

        //输入消息
        //输入群发消息
        public void MessageInput(AccessibilityNodeInfo acno){
                List<AccessibilityNodeInfo> list = 
                    util.findNodesByViewId(acno , ID_MSGEDIT);

                if (list.isEmpty()){

                        util.Fcus(list.get(0));
                        util.paste(list.get(0));
                        SendTo(acno);
                        Log.i(TAG , "找到了哦…………");  

                    }     

            }
        //发送按钮
        public void SendTo(AccessibilityNodeInfo acno){
                List<AccessibilityNodeInfo> list = 
                    util.findNodesByViewId(acno , ID_SENDMSG);
                if (!list.isEmpty()){
                        //Log.i(TAG , list.get(0).getViewIdResourceName() + "发送按钮<---位置");
                        util.click(list.get(0));

                    }else{
                        Log.i(TAG , "未能输入内容");
                    }
            }  

        public boolean MSGInput(AccessibilityNodeInfo nodeInfo){


                AccessibilityNodeInfo targetNode = null;

                //判断是否群聊以及mode=2时是否匹配好友
                List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(Config.qunId);
                if (!list.isEmpty()){
                        targetNode = list.get(0);

                        String temp = targetNode.getText().toString();
                        if (temp.matches(".*\\(([3-9]|[1-9]\\d+)\\)") || (mode == 2 && Config.isfriend && (!temp.equals(Config.friend)))){
                               // performBack();
                                // wakeAndUnlock(false);
                                return false;
                            }
                            
                    }

                //查找文本编辑框
                if (editText == null){
                        Log.i("demo", "正在查找编辑框...");
                        //第一种查找方法
                        List<AccessibilityNodeInfo> list1 = nodeInfo.findAccessibilityNodeInfosByViewId(ID_MSGEDIT);
                        if (!list1.isEmpty())
                            editText = list1.get(0);
                        //第二种查找方法
                        if (editText == null)
                            findNodeInfosByName(nodeInfo, "android.widget.EditText");

                    }

                targetNode = editText;

                //粘贴回复信息
                if (targetNode != null){

                        //Log.i("demo", "设置粘贴板");
                        //焦点 （n是AccessibilityNodeInfo对象） 
                        targetNode.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                        //Log.i("demo", "获取焦点");
                        //粘贴进入内容 
                        targetNode.performAction(AccessibilityNodeInfo.ACTION_PASTE);
                        //Log.i("demo", "粘贴内容");
                    }

                //查找发送按钮
                if (targetNode != null){ //通过组件查找
                        Log.i("demo", "查找发送按钮...");
                        targetNode = null;
                        List<AccessibilityNodeInfo> list2 = nodeInfo.findAccessibilityNodeInfosByViewId(Config.sendId);
                        if (!list2.isEmpty())
                            targetNode = list2.get(0);
                        //第二种查找方法
                        if (targetNode == null)
                            targetNode = findNodeInfosByText(nodeInfo, "发送");
                    }

                //点击发送按钮
                if (targetNode != null){
                        Log.i("demo", "点击发送按钮中...");
                        final AccessibilityNodeInfo n = targetNode;
                        performClick(n);
                        Config.replaied++;

                    }
                //恢复锁屏状态
                // wakeAndUnlock(false);
                return true;
            }
        /** 点击事件*/
        public void performClick(AccessibilityNodeInfo nodeInfo){
                if (nodeInfo == null){
                        return;
                    }
                if (nodeInfo.isClickable()){
                        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }else{
                        performClick(nodeInfo.getParent());
                    }
            }

        /** 返回事件*/
        public  void performBack(AccessibilityService service){
                if (service == null){
                        return;
                    }
                service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
            }

        /** 通过文本查找*/
        public  AccessibilityNodeInfo findNodeInfosByText(AccessibilityNodeInfo nodeInfo, String text){
                List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
                if (list == null || list.isEmpty()){
                        return null;
                    }
                return list.get(0);
            }

        //通过组件名递归查找编辑框
        private void findNodeInfosByName(AccessibilityNodeInfo nodeInfo, String name){
                if (name.equals(nodeInfo.getClassName())){
                        editText = nodeInfo;
                        return;
                    }
                for (int i = 0; i < nodeInfo.getChildCount(); i++){
                        findNodeInfosByName(nodeInfo.getChild(i), name);
                    }
            }


    }
