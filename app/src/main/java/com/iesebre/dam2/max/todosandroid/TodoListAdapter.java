package com.iesebre.dam2.max.todosandroid;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 20/11/15.
 */
public class TodoListAdapter extends BaseAdapter {

    private Activity activity;
    private int resource;
    private ArrayList<TodoItem> items;

    public TodoListAdapter(Activity activity, int resource, ArrayList listData)
    {
        this.activity = activity;
        this.resource = resource;
        this.items = listData;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {

            convertView = activity.getLayoutInflater().inflate(resource, null);

            TodoItem item = items.get(position);

            if (item != null) {
                TextView tv1 = (TextView) convertView.findViewById(R.id.id1);
                TextView tv2 = (TextView) convertView.findViewById(R.id.id2);

                tv1.setText(item.getName());
                tv2.setText(String.valueOf(item.getPriority()));
            }
        }

        return convertView;

    }
}
