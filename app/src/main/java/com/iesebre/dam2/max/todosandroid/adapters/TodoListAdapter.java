package com.iesebre.dam2.max.todosandroid.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hanks.library.AnimateCheckBox;
import com.iesebre.dam2.max.todosandroid.R;
import com.iesebre.dam2.max.todosandroid.models.TodoItem;

import java.util.ArrayList;

/**
 * Created by max on 20/11/15.
 */
public class TodoListAdapter extends BaseAdapter {

    private Activity activity;
    private int resource;
    private ArrayList<TodoItem> list;

    public TodoListAdapter(Activity activity, int resource, ArrayList listData)
    {
        this.activity = activity;
        this.resource = resource;
        this.list = listData;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {



        // TODO, this is not the best solution...
        convertView = null;



        final TodoItem item = list.get(position);

        if (convertView == null)
        {
            convertView = activity.getLayoutInflater().inflate(resource, null);

            if (item != null)
            {
                final TextView tv1 = (TextView) convertView.findViewById(R.id.id1);
                tv1.setText(item.getName());

                final AnimateCheckBox cbCompletedTask = (AnimateCheckBox) convertView.findViewById(R.id.cbCompletedTask);
                setPriorityColor(cbCompletedTask, item.getPriority());

                cbCompletedTask.setChecked(item.isDone());
                strikethroughName(tv1, item.isDone());

                cbCompletedTask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!cbCompletedTask.isChecked())
                        {
                            cbCompletedTask.setChecked(true);
                            strikethroughName(tv1, true);
                            list.get(position).setDone(true);
                        }
                        else
                        {
                            cbCompletedTask.setChecked(false);
                            strikethroughName(tv1, false);
                            list.get(position).setDone(false);
                        }


                    }
                });

            }
        }

        // Item click event listener
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO

            }
        });

        // Item checkBox click event listener
        /*cbCompletedTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO

            }
        });*/

        return convertView;

    }

    private void setPriorityColor(AnimateCheckBox checkBox, int priority)
    {
        switch (priority)
        {
            case 1:
                checkBox.setCircleColor(ContextCompat.getColor(activity, R.color.green));
                checkBox.setUnCheckColor(ContextCompat.getColor(activity, R.color.green));
                break;
            case 2:
                checkBox.setCircleColor(ContextCompat.getColor(activity, R.color.orange));
                checkBox.setUnCheckColor(ContextCompat.getColor(activity, R.color.orange));
                break;
            case 3:
                checkBox.setCircleColor(ContextCompat.getColor(activity, R.color.red));
                checkBox.setUnCheckColor(ContextCompat.getColor(activity, R.color.red));
                break;
            default:
                checkBox.setCircleColor(ContextCompat.getColor(activity, R.color.green));
                checkBox.setUnCheckColor(ContextCompat.getColor(activity, R.color.green));
                break;
        }
    }

    private void strikethroughName (TextView name, boolean isDone)
    {
        if (isDone)
        {
            name.setPaintFlags(name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else
        {
            name.setPaintFlags(name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }
}
