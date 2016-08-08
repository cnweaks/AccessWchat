package com.auto.mm.util;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

//服务处理接口
public interface IAccessServiceImpl {
  void Acest(AccessibilityEvent event, AccessibilityService service);

  void onInterrupt();

  boolean isCompleted();
}
