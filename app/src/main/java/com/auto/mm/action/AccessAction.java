package com.auto.mm.action;
/**
 *构造器
 * 
 *
 */
public class AccessAction {
  public enum KeyType {
    TEXT,
    VIEWID,
    CONTENT_DESCRIPTION;
  }

  public KeyType keyType;
  public String keyValue;
  public int actionType;
  public String actionValue;
  /* 登录节点 */
  public boolean performed = false;

  public AccessAction(KeyType keyType, String keyValue, int actionType, String actionValue) {
    this.keyType = keyType;
    this.keyValue = keyValue;
    this.actionType = actionType;
    this.actionValue = actionValue;
  }
}
