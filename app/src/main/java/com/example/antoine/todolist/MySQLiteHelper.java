
package com.example.antoine.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by antoine on 28/01/2017.
 */


public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_COMMENTS = "comments";
    public static final String TABLE_TASKS = "tasks";
    public static final String ID_COMMENTS = "id_comments";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ID_TASK = "id_task";
    public static final String COLUMN_COMMENT = "comment";
    public static final String COLUMN_TASK = "task";

    private static final String DATABASE_NAME = "comments.db";
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_COMMENTS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_COMMENT
            + " text not null);";

    private static final String DATABASE_CREATE2 = "create table "
            + TABLE_TASKS + "(" + COLUMN_ID_TASK
            + " integer primary key autoincrement, " + COLUMN_TASK
            + " text not null, " + ID_COMMENTS + " integer not null);";

        public MySQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
            "Upgrading database from version " + oldVersion + " to"
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS + ";");
        onCreate(db);
    }
}