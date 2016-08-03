package com.auto.mm.action;
import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.auto.mm.action.AccessAction;
import com.auto.mm.util.AccessServiceUtil;
import com.auto.mm.util.IAccessServiceImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * App包管理器
 * 
 */
public class OpenApp implements IAccessServiceImpl{

        private String pkgName;
        private List<AccessAction> accessActionList;




        //打开App
        public OpenApp(String pkgName, List<AccessAction> accessActionList){
                this.pkgName = pkgName;
                this.accessActionList = accessActionList;
            }

        @Override
        public void Acest(AccessibilityEvent event, AccessibilityService service){
                if (!event.getPackageName().equals(pkgName)){
                        return;
                    }
                AccessibilityNodeInfo rootNode = service.getRootInActiveWindow();
                if (rootNode == null){
                        return;
                    }
                for (AccessAction accessAction : accessActionList){
                        List<AccessibilityNodeInfo> targetNodes = null;
                        switch (accessAction.keyType){
                                case TEXT:
                                    targetNodes = AccessServiceUtil.findNodesByText(rootNode, accessAction.keyValue);
                                    break;
                                case VIEWID:
                                    targetNodes = AccessServiceUtil.findNodesByViewId(rootNode, accessAction.keyValue);
                                    break;
                                case CONTENT_DESCRIPTION:
                                    AccessibilityNodeInfo node =
                                        AccessServiceUtil.findNodesByContentDesc(rootNode, accessAction.keyValue);
                                    if (node != null){
                                            targetNodes = new ArrayList<AccessibilityNodeInfo>();
                                            targetNodes.add(node);
                                        }
                                    break;
                            }
                        if (targetNodes != null && !accessAction.performed){
                                for (AccessibilityNodeInfo targetNode : targetNodes){
                                        AccessServiceUtil.performAction(targetNode, accessAction);
                                        targetNode.recycle();
                                    }
                            }
                    }

                rootNode.recycle();
            }

        @Override
        public void onInterrupt(){

            }

        @Override
        public boolean isCompleted(){
                if (accessActionList != null){
                        for (AccessAction accessAction : accessActionList){
                                if (!accessAction.performed){
                                        return false;
                                    }
                            }
                    }
                return true;
            }

    }
