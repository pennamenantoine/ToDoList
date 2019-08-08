package com.example.antoine.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import static com.example.antoine.todolist.MySQLiteHelper.COLUMN_TASK;
import static com.example.antoine.todolist.MySQLiteHelper.TABLE_COMMENTS;

/**
 * Created by antoine on 28/01/2017.
 */


public class DataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
/*    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
        MySQLiteHelper.COLUMN_COMMENT, MySQLiteHelper.COLUMN_TASK }; */
    private String[] allComments = { MySQLiteHelper.COLUMN_ID,
        MySQLiteHelper.COLUMN_COMMENT };
    private String[] allTask = { MySQLiteHelper.COLUMN_ID_TASK,
        MySQLiteHelper.COLUMN_TASK, MySQLiteHelper.ID_COMMENTS };

    public DataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Comment createComment(String comment) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
        long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
                allComments, MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteComment(Comment comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public void deleteCommentByID(String id) {

        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID + " = " + id, null);
    }


    public void createTask(String task, String id) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TASK, task);
        values.put(MySQLiteHelper.ID_COMMENTS, id);
        database.insert(MySQLiteHelper.TABLE_TASKS, null, values);
    }

    public void deleteTask(Task task) {
        long id = task.getId();
        System.out.println("Task deleted with id : " + id);
        database.delete(MySQLiteHelper.TABLE_TASKS, MySQLiteHelper.COLUMN_ID_TASK + " = " + id, null);
    }

    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<Comment>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS, allComments, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    public List<Task> getTasksbyIDComment(String id) {
        List<Task> tasks = new ArrayList<Task>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_TASKS, allTask, MySQLiteHelper.ID_COMMENTS + " LIKE \"" + id  +"\"", null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return tasks;
    }


    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getInt(0));
        comment.setComment(cursor.getString(1));
        return comment;
    }

    private Task cursorToTask(Cursor cursor) {
        Task task = new Task();
        task.setId(cursor.getLong(0));
        task.setTask(cursor.getString(1));
        return task;
    }
}