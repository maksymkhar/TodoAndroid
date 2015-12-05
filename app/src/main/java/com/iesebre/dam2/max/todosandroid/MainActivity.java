package com.iesebre.dam2.max.todosandroid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iesebre.dam2.max.todosandroid.adapters.TodoListAdapter;
import com.iesebre.dam2.max.todosandroid.models.TodoItem;
import com.iesebre.dam2.max.todosandroid.utils.Constants;

import java.lang.reflect.Type;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnClickListener {

    private Gson gson;
    public TodoArrayList tasks;
    private TodoListAdapter adapter;
    private SharedPreferences todoSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Add listeners to fab buttons.
        findViewById(R.id.fabAdd).setOnClickListener(this);
        findViewById(R.id.fabRemove).setOnClickListener(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Load tasks from SharedPreferences
        tasks = loadTasks();
        //if (tasks == null) { return; }

        // Initialize ListView
        ListView todoListView = (ListView) findViewById(R.id.todoListView);

        // Initialitze ListView adapter
        adapter = new TodoListAdapter(this, R.layout.list_item, tasks);
        todoListView.setAdapter(adapter);

        todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.v("s","a");
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.fabAdd:
                displayAddTaskDialog();
                break;
            case R.id.fabRemove:
                removeTask();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        saveTasks();
    }

    private TodoArrayList loadTasks()
    {
        todoSharedPreference = getSharedPreferences(Constants.SHARED_PREFERENCE_TODOS, 0);
        String todoList = todoSharedPreference.getString(Constants.SHARED_PREFERENCE_TODO_LIST, null);

        if (todoList == null)
        {
            SharedPreferences.Editor editor = todoSharedPreference.edit();
            editor.putString(Constants.SHARED_PREFERENCE_TODO_LIST, Constants.INITIAL_TASKS_JSON);
            editor.apply();

            todoList = todoSharedPreference.getString(Constants.SHARED_PREFERENCE_TODO_LIST, null);
        }

        gson = new Gson();

        // Mapejem el JSON
        Type arrayTodoList = new TypeToken<TodoArrayList>() {}.getType();
        TodoArrayList allTasks = gson.fromJson(todoList, arrayTodoList);

        if(allTasks == null) { return null; }

        return allTasks;
    }

    private void saveTasks()
    {
        if (tasks == null) { return; }

        String tasksToSave = gson.toJson(tasks);

        SharedPreferences.Editor editor = todoSharedPreference.edit();
        editor.putString(Constants.SHARED_PREFERENCE_TODO_LIST, tasksToSave);
        editor.apply();
    }

    private void removeTask()
    {
        Log.v("CLICK", "REMOVE");
        //TODO
    }

    private void displayAddTaskDialog()
    {
        new MaterialDialog.Builder(MainActivity.this)
                .title(getResources().getString(R.string.add_task_dialog_title))
                .customView(R.layout.form_add_task, true)
                .positiveText(getResources().getString(R.string.add).toUpperCase())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        EditText editTextName = (EditText) dialog.findViewById(R.id.etName);
                        String taskName = editTextName.getText().toString();

                        addTask(taskName);
                    }
                })
                .negativeText(getResources().getString(R.string.cancel).toUpperCase())
                .show();
    }

    private void addTask(String name)
    {
        // TODO

        TodoItem todoItem = new TodoItem();
        todoItem.setName(name);
        todoItem.setDone(true);
        todoItem.setPriority(1);

        tasks.add(todoItem);
        adapter.notifyDataSetChanged();
    }
}
