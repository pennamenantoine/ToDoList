package com.example.antoine.todolist;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import static com.example.antoine.todolist.R.attr.commitIcon;
import static com.example.antoine.todolist.R.attr.title;
import static com.example.antoine.todolist.R.id.note;
import static com.example.antoine.todolist.R.layout.main;
import static com.example.antoine.todolist.R.layout.addtitle;

/**
  *  Created by antoine on 28/01/2017.
  */


public class TestDatabaseActivity extends ListActivity  {
    private DataSource datasource;
    TextView mTitle;
    TextView mDate;
    private String title;
    private String date;
    ArrayAdapter<Comment> adapter;
    Comment comment = null;
    Task task = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(main);

        mDate = (TextView) findViewById(R.id.Enter_date);
        mTitle = (TextView) findViewById(R.id.Enter_title);

        datasource = new DataSource(this);
        datasource.open();

        List<Comment> values = datasource.getAllComments();
        final ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TestDatabaseActivity.this, display_title.class);

                intent.putExtra("id",Integer.toString(adapter.getItem(position).getId()));
                startActivity(intent);
            }
        });
    }

    public void onClick(View view) {
        adapter = (ArrayAdapter<Comment>) getListAdapter();
        switch (view.getId()) {
            case R.id.add:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(R.layout.addtitle)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                System.out.println(mTitle);
                                title = ((EditText) ((AlertDialog) dialog).findViewById(R.id.title)).getText().toString();
                                System.out.println(title);
                                if (title.isEmpty())
                                    return;
                                System.out.println(mDate);
                                date = ((EditText) ((AlertDialog) dialog).findViewById(R.id.date)).getText().toString();
                                System.out.println(date);
                                if (date.isEmpty())
                                    return;
                                comment = datasource.createComment(title + "         " + date);
                                adapter.add(comment);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.show();

                break;

            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    comment = (Comment) getListAdapter().getItem(0);
                    datasource.deleteComment(comment);
                    adapter.remove(comment);
                }
                break;

        }
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}
