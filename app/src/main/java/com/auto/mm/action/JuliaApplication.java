package com.auto.mm.action;

import android.app.Application;
import android.content.Context;

/**
 * 内容管理器
 */
public class JuliaApplication extends Application {
  private static Context context;

  @Override
  public void onCreate() {
    super.onCreate();
    context = getApplicationContext();
  }

  public static Context getAppContext() {
    return context;
  }

}
