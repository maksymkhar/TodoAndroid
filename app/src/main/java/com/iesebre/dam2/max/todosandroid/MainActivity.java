package com.iesebre.dam2.max.todosandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String SHARED_PREFERENCE_TODOS = "SP_TODOS";
    private static final String TODO_LIST = "todo_list";
    private Gson gson;

    public TodoArrayList tasks;

    private Snackbar snackbar;


    public static final int LENGTH_LONG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new MaterialDialog.Builder(MainActivity.this)
                        .title("Title")
                        .customView(R.layout.form_add_task, true)
                        .positiveText("ADD")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                Log.v("DIALOG", "Positive");
                            }
                        })
                        .negativeText("CANCEL")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                Log.v("DIALOG", "Negative");
                            }
                        })
                        .show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*
            SHARED PREFERENCES
         */
        SharedPreferences todos = getSharedPreferences(SHARED_PREFERENCE_TODOS, 0);
        String todoList = todos.getString(TODO_LIST, null);

        if (todoList == null)
        {
            String initialJson =    "[" +
                                        "{\"name\":\"Comprar llet\", \"done\": true, \"priority\": 2}," +
                                        "{\"name\":\"Comprar pa\", \"done\": false, \"priority\": 1},"  +
                                        "{\"name\":\"Comprar ous\", \"done\": false, \"priority\": 3}"  +
                                    "]";

            SharedPreferences.Editor editor = todos.edit();
            editor.putString(TODO_LIST, initialJson);
            editor.commit();

            todoList = todos.getString(TODO_LIST, null);
        }


        Log.v("JSON",todoList);

        /*
        snackbar = Snackbar.make(findViewById(android.R.id.content), todoList, Snackbar.LENGTH_LONG).setAction("Action", null);
        snackbar.show();
        Toast.makeText(this, "LOOOONG..", Toast.LENGTH_LONG).show();
        */

        gson = new Gson();

        // Mapejem el JSON
        Type arrayTodoList = new TypeToken<TodoArrayList>() {}.getType();
        TodoArrayList temp = gson.fromJson(todoList, arrayTodoList);

        if(temp != null)
        {
            tasks = temp;

            for (int i=0; i<tasks.size(); i++)
            {
                Log.v("TASK", tasks.get(i).getName());
            }
        }
        else
        {
            //Error TODO
        }

        ListView todoslv = (ListView) findViewById(R.id.todoListView);

        TodoListAdapter adapter = new TodoListAdapter(this, R.layout.list_item, tasks);
        todoslv.setAdapter(adapter);

        todoslv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.v("Click", String.valueOf(position));

            }
        });


        /*

        [
            {"name":"Comprar llet", "done": true, "priority": 2},
            {"name":"Comprar pa", "done": false, "priority": 1},
            {"name":"Comprar ous", "done": false, "priority": 3}
        ]


         */




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
}
