package com.auto.mm.database;
import java.util.List;
import java.util.ArrayList;

public class Config{
        //微信6.3.18相关组件的id，微信版本更新后随之修改即可
        public static String qunId = "com.tencent.mm:id/ei";
       public static String editId = "com.tencent.mm:id/c6v";
       public static String sendId = "com.tencent.mm:id/yq";
        //是否指定好友
        public static boolean isfriend = true;
        //默认指定的好友昵称
        public static String friend = "A00";
        //是否开启自动回复
        public static boolean auto = true;
        //锁屏界面是否显示消息详细内容
        public static boolean showall = true;
        //是否来电或正在通话，用于是否显示锁屏界面
        public static boolean iscalling = false;
        //消息总数
        public static int total = 0;
        //已自动回复的消息总数
      public  static int replaied = 0;
        //收到的微信消息列表
        public static List<String> data = new ArrayList<String>();
        
    
    
    
}
