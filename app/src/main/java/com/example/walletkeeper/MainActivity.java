package com.example.walletkeeper;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.walletkeeper.packages.Database;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    //数据库创建
    private Database database;
    private SQLiteDatabase db;

    public MenuItem a, b, c;

    NavController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new Database(this, "account", null, 4);
        db = database.getReadableDatabase();

        /*
        a=(MenuItem)findViewById(R.id.navigation_home);
        b=(MenuItem)findViewById(R.id.navigation_dashboard);
        c=(MenuItem)findViewById(R.id.navigation_notifications);
        a.setIcon(R.drawable.addition1);
        b.setIcon(R.drawable.bill1);
        c.setIcon(R.drawable.diagram2);
        */
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_wishList).build();
        controller = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, controller, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, controller);
        controller.setGraph(R.navigation.mobile_navigation);
        controller.navigate(R.id.navigation_dashboard);
        /*
        controller = Navigation.findNavController(this, R.id.nav_host_fragment);
        controller.setGraph(R.navigation.mobile_navigation);
        controller.navigate(R.id.navigation_dashboard); //直接跳转到threeFragment
         */

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the aaction bar if it is present
        getMenuInflater().inflate(R.menu.top_menu, menu);
        //getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_return:
                Intent intent = new Intent(this, SplashActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }


}
