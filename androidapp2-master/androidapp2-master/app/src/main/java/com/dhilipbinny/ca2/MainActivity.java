package com.dhilipbinny.ca2;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//    private TextView mTextMessage;

    public static String loggedinUserId = "";
    public static BottomNavigationView navView;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.title_stationery_request:
                    fragment = new HomeFragment();
                    break;

                case R.id.navigation_dashboard:
                    fragment = new DashboardFragment();
                    break;

                case R.id.navigation_editdepartment:
                    fragment = new EditDepartmentFragment();
                    break;

                case R.id.navigation_editdelegate:
                    fragment = new EditDelegateFragment();
                    break;
            }

            return loadFragment(fragment);
        }

    };

    public static String getUserId() {

        return  loggedinUserId.split("/")[0];
    }

    public static String getUser() {

        return  loggedinUserId.split("/")[1];
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {
            Toast.makeText(MainActivity.this, "Signout clicked", Toast.LENGTH_LONG).show();
            finish();
            return true;
        }

//        if (id == R.id.action_notify) {
//            Toast.makeText(MainActivity.this, "Notify clicked", Toast.LENGTH_LONG).show();
//            return true;
//        }
//        if (id == R.id.action_home) {
//            Toast.makeText(MainActivity.this, "Home clicked", Toast.LENGTH_LONG).show();
//            View view = navView.findViewById(R.id.navigation_dashboard);
//            view.performClick();
////            loadFragment(new DashboardFragment());
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        loggedinUserId = intent.getStringExtra("userId");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle( loggedinUserId.split("/")[1] );
        myToolbar.setLogo(R.drawable.iconmale);

        setSupportActionBar(myToolbar);


        //loading the default fragment
        loadFragment(new DashboardFragment());

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void LaunchPage(Context context, Class c){
        Intent myIntent = new Intent(context, c);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
