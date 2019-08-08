package com.example.antoine.todolist;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

/* Created by antoine on 27/01/2017. */


//acceder/modifier notes
    public class display_title extends ListActivity {
        Button ok;
        String id;
        private String note;
        private DataSource datasource = new DataSource(this);
        ArrayAdapter<Task> _adapter;
        Comment comment;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.title);
            Intent intent = getIntent();

            if (intent != null)
                id = intent.getStringExtra("id");
            ok = (Button) findViewById(R.id.button);
            datasource.open();
            List<Task> _values = datasource.getTasksbyIDComment(id);
            datasource.close();
           _adapter = new ArrayAdapter<Task>(this,
                    android.R.layout.simple_list_item_1, _values);
            setListAdapter(_adapter);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    note = ((EditText) findViewById(R.id.note)).getText().toString();
                    if (note.isEmpty())
                        return;
                    datasource.open();
                    datasource.createTask(note, id);
                    datasource.close();
                    _adapter =  (ArrayAdapter<Task>) getListAdapter();
                    display_title.this.finish();
                }
            });
        }
}