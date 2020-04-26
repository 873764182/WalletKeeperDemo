package com.example.walletkeeper.packages;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Database extends SQLiteOpenHelper {

    /*建表语句  用sql写*/
    public static final String CREATE_ACCOUNT = "create table account("
            + "id integer PRIMARY KEY AUTOINCREMENT,"  //ID 主键
            + "year integer,"  //时间
            + "month integer,"
            + "day integer,"
            + "hour integer,"
            + "minute integer,"
            + "second integer,"
            + "money intger,"  //金额
            + "type integer,"    //类别
            + "comment text)";  //备注

    /*建表语句  用sql写 收入记录*/
    public static String CREATE_INCOME = "create table income(" +
            "id integer PRIMARY KEY AUTOINCREMENT, " +
            "insTime text, " +  // 插入时间
            "updTime text, " +  // 最后更新时间
            "money integer, " + // 金额
            "comment text " +   // 备注
            ")";

    /*建表语句  用sql写 心愿记录*/
    public static String CREATE_WISH = "create table wish(" +
            "id integer PRIMARY KEY AUTOINCREMENT, " +
            "insTime text, " +  // 插入时间
            "money integer, " + // 目标金额
            "comment text " +   // 备注
            ")";

    private Context mContext;

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    /*第一次创建数据库的时候会执行这个函数
    所以在这个函数里建表
    db.execSQL() 函数 这个函数经常要用的
    用来执行sql命令  接收一个String参数
    把刚刚写的sql语句传进去 就能创建表了  */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNT);
        db.execSQL(CREATE_INCOME);
        db.execSQL(CREATE_WISH);
        Toast.makeText(mContext, "创建数据库功", Toast.LENGTH_LONG).show();
    }


    /*升级数据库的时候会执行这个函数
    比如要新增字段 新增加表  就在这个函数里操作*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
