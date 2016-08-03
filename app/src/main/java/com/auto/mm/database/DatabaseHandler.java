package com.auto.mm.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

        // 静态参数
        // 数据库版本
        private static final int DATABASE_VERSION = 1;

        // 数据库名
        private static final String DATABASE_NAME = "wechat_data";

        // 表名
        private static final String TABLE_CONTACTS = "wechat_table";

        // 列名
        private static final String KEY_ID = "id";
        private static final String KEY_NAME = "name";
        private static final String KEY_SIX = "wcsix";
        
        private final ArrayList<Contact> contact_list = new ArrayList<Contact>();

        public DatabaseHandler(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

        // 建表
        @Override
        public void onCreate(SQLiteDatabase db) {
                String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                    + KEY_SIX + " TEXT" + ")";
                db.execSQL(CREATE_CONTACTS_TABLE);
            }

        // 更新数据库
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // 如果旧表则删除
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

                // 开始更新
                onCreate(db);
            }

        /**
         * 所有操作 CRUD(创建, 读取, 更新, 删除) Operations
         */

        // 添加数据
        public void Add_Contact(Contact contact) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_NAME, contact.getName()); // 名称
                values.put(KEY_SIX, contact.getWechatSix()); // 性别
                
                // 执行导入
                db.insert(TABLE_CONTACTS, null, values);
                db.close();// 关闭数据库
            }

        // 获得连接
        Contact Get_Contact(int id) {
                SQLiteDatabase db = this.getReadableDatabase();

                Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                                                 KEY_NAME, KEY_SIX }, KEY_ID + "=?",
                                         new String[] { String.valueOf(id) }, null, null, null);
                if (cursor != null)
                    cursor.moveToFirst();

                Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                                              cursor.getString(1), cursor.getString(2));
                // return contact
                cursor.close();
                db.close();

                return contact;
            }

        // Getting All Contacts
        public ArrayList<Contact> Get_Contacts() {
                try {
                        contact_list.clear();

                        // Select All Query
                        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

                        SQLiteDatabase db = this.getWritableDatabase();
                        Cursor cursor = db.rawQuery(selectQuery, null);

                        // looping through all rows and adding to list
                        if (cursor.moveToFirst()) {
                                do {
                                        Contact contact = new Contact();
                                        contact.setID(Integer.parseInt(cursor.getString(0)));
                                        contact.setName(cursor.getString(1));
                                        contact.setWechatSix(cursor.getString(2));
                                        
                                        // Adding contact to list
                                        contact_list.add(contact);
                                    } while (cursor.moveToNext());
                            }

                        // return contact list
                        cursor.close();
                        db.close();
                        return contact_list;
                    } catch (Exception e) {
                        // TODO: handle exception
                        Log.e("all_contact", "" + e);
                    }

                return contact_list;
            }

        // Updating single contact
        public int Update_Contact(Contact contact) {
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(KEY_NAME, contact.getName());
                values.put(KEY_SIX, contact.getWechatSix());
                

                // updating row
                return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                                 new String[] { String.valueOf(contact.getID()) });
            }

        // Deleting single contact
        public void Delete_Contact(int id) {
                SQLiteDatabase db = this.getWritableDatabase();
                db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                          new String[] { String.valueOf(id) });
                db.close();
            }

        // Getting contacts Count
        public int Get_Total_Contacts() {
                String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor cursor = db.rawQuery(countQuery, null);
                cursor.close();

                // return count
                return cursor.getCount();
            }

    }
