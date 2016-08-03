package com.auto.mm.util;


import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import com.auto.mm.action.AccessAction;
import com.auto.mm.action.JuliaApplication;
import java.util.List;

/**
 * 实用程序类来帮助处理AccessibilityService相关操作。
 * <p/>
 * 
 */
public class AccessServiceUtil {

        private static final String TAG = "Access";

        public static void performAction(AccessibilityNodeInfo info, AccessAction accessAction) {
                switch (accessAction.actionType) {
                        case AccessibilityNodeInfo.ACTION_CLICK:
                            click(info);
                            break;
                        case AccessibilityNodeInfo.ACTION_COPY:
                            copy(info);
                            break;
                        case AccessibilityNodeInfo.ACTION_PASTE:
                            paste(info);
                            break;

                    }
                accessAction.performed = true;
            }

        //执行点击动作，处理包含有文字的按钮，不指定默认第一个
        public void Textclick(String str, AccessibilityNodeInfo info){
                List<AccessibilityNodeInfo> list =
                    findNodesByText(info, str);
                if (list.get(0).getText() != null){
                        Log.i(TAG , list.get(0).getViewIdResourceName() + "<<<----点击ID");
                        click(list.get(0));
                    }else{
                        Log.i(TAG , "不可点击");
                    }
            }    

        //查找可点击节点
        public static AccessibilityNodeInfo findClickableNode(AccessibilityNodeInfo info) {
                if (info == null) {
                        return null;
                    }
                if (info.isClickable()) {
                        return info;
                    } else {
                        return findClickableNode(info.getParent());
                    }
            }


        //
        public void IdClick(AccessibilityNodeInfo acno , int i , String resid){
                List<AccessibilityNodeInfo> list = 
                    findNodesByViewId(acno , resid);
                if (!list.isEmpty()){
                        Log.i(TAG , list.get(i).getText() + "<---群友名称");

                        click(list.get(i));
                    }else{
                        Log.i(TAG , "指定的第" + i + "个View不存在");
                    }
            }
        //点击操作
        public static void click(AccessibilityNodeInfo info) {
                AccessibilityNodeInfo clickableNode = findClickableNode(info);
                if (clickableNode != null) {
                        clickableNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
            }


        //复制操作
        public static void copy(AccessibilityNodeInfo info) {
                if (info != null) {
                        info.performAction(AccessibilityNodeInfo.ACTION_COPY);
                    }
            }

        //剪贴操作,如果没有选择需要剪贴的内容，默认将全选，剪切
        public static void cuts(AccessibilityNodeInfo info) {
                if (info != null) {
                        info.performAction(AccessibilityNodeInfo.ACTION_CUT);
                    }
            }

        //选择操作
        public static void seltAll(AccessibilityNodeInfo info) {
                if (info != null) {
                        info.performAction(AccessibilityNodeInfo.ACTION_SELECT);
                    }
            }
        //粘贴操作
        public static void paste(AccessibilityNodeInfo info) {
                if (info != null) {
                        info.performAction(AccessibilityNodeInfo.ACTION_PASTE);
                    }
            }


        //返回上级
        public static void Back(AccessibilityNodeInfo info) {
                if (info != null) {
                    info.performAction(AccessibilityService.GLOBAL_ACTION_BACK);
                    }
            }

        //回主屏幕
         public static void Home(AccessibilityNodeInfo info) {
         if (info != null) {
            info.performAction(AccessibilityService.GLOBAL_ACTION_HOME);
         }
        }
        //触摸焦点
        public static void Fcus(AccessibilityNodeInfo info) {
                if (info != null) {
                        info.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                   
                        }
                        }

        //滑动操作
        public static void Swipe(AccessibilityNodeInfo info) {
        if (info != null) {
            info.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
        }
    }


        //输入文本操作
        public static void setText(AccessibilityNodeInfo info, String text) {
                if (info != null && !TextUtils.isEmpty(text)) {
                    Bundle arguments = new Bundle();
                    arguments.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT, AccessibilityNodeInfo.MOVEMENT_GRANULARITY_LINE);
                    arguments.putBoolean(AccessibilityNodeInfo.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN, true);
                    info.performAction(AccessibilityNodeInfo.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY, arguments);
                }
            }
        



        //文本查找
        public static List<AccessibilityNodeInfo> findNodesByText(AccessibilityNodeInfo root, String text) {
                return root.findAccessibilityNodeInfosByText(text);
            }



        //界面查找
        public static List<AccessibilityNodeInfo> findNodesByViewId(AccessibilityNodeInfo root,
                                                                    String viewId) {
                return root.findAccessibilityNodeInfosByViewId(viewId);
            }



        //内容排序
        public static AccessibilityNodeInfo findNodesByContentDesc(AccessibilityNodeInfo root,
                                                                   String targetContentDesc) {
                if (root == null || TextUtils.isEmpty(targetContentDesc)) {
                        return null;
                    }

                CharSequence contentDesc = root.getContentDescription();
                Log.d(TAG, "content desc:" + contentDesc);

                if (!TextUtils.isEmpty(contentDesc) && contentDesc.equals(targetContentDesc)) {
                        return root;
                    } else {
                        for (int i = 0; i < root.getChildCount(); i++) {
                                AccessibilityNodeInfo childNode = root.getChild(i);
                                AccessibilityNodeInfo result = findNodesByContentDesc(childNode, targetContentDesc);
                                if (result != null) {
                                        return result;
                                    }
                            }
                        return null;
                    }
            }



        //获得Activity的类名
        @TargetApi(Build.VERSION_CODES.KITKAT)
        public static String getForegroundActivityBelowLollipop() {
                try {
                        ActivityManager activityManager =
                            (ActivityManager) JuliaApplication.getAppContext().getSystemService(
                            Context.ACTIVITY_SERVICE);
                        List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(1);
                        ComponentName cn = taskInfoList.get(0).topActivity;
                        return cn.getClassName();
                    } catch (AssertionError e) {
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                return null;
            }
    }
