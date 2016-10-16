package com.example.stuxnet.schmidt_schadensprotokoll;

import android.content.Context;
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
import android.widget.Button;
import android.widget.TextView;

import static com.example.stuxnet.schmidt_schadensprotokoll.Login.PREFS_NAME;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final boolean NAVIGATION_DRAWER  = false;
    private static final String  debugMode          = "Mainactivity";

    private static final int STATUS_CHANGE_SCHADENAUFNAHME    = 1;
    private static final int STATUS_CHANGE_SCHADENLISTE       = 2;

    Context context = MainActivity.this;

    TextView welcome;
    Button schadenaufnehmen;
    Button schadensliste;

    String lastname;
    String name;
    String vertrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSchaden);
        setSupportActionBar(toolbar);

        welcome = (TextView)findViewById(R.id.tv_welcome);
        schadenaufnehmen = (Button)findViewById(R.id.btn_schadenaufnehmen);
        schadensliste = (Button)findViewById(R.id.btn_schadensliste);

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        name           = settings.getString("name", "");
        lastname       = settings.getString("lastname", "");
        vertrag        = settings.getString("vertrag", "");



        /**
         * Macht den Welcome Text huebsch mit grossem Anfangsbuchstaben
         *  "Hallo Max Mustermann"
         */
        welcome.setText("Hallo " + name.substring(0,1).toUpperCase() + name.substring(1)
                            + " " + lastname.substring(0,1).toUpperCase() + lastname.substring(1));

        /**
         * Abhaengig wer sich anmeldet, werden nicht alle buttons angezeigt.
         */
        if(vertrag.equals("werkstatt")){
            schadensliste.setVisibility(View.VISIBLE);
        }else{
            schadensliste.setVisibility(View.GONE);
        }

        /**
         * Navigation Drawer ist noch vorhanden aber disabled.
         * Sollet er in Zukunft hinzugenommen werden reicht es die Variable
         * NAVIGATION_DRAWER auf true zu setzen.
         */
        Log.d("Activity", NAVIGATION_DRAWER + "");
        if (NAVIGATION_DRAWER) {

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        }else{

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            fab.setVisibility(View.GONE);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

        schadenaufnehmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(STATUS_CHANGE_SCHADENAUFNAHME);
            }
        });

        schadensliste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(STATUS_CHANGE_SCHADENLISTE);
            }
        });

    }

    public void startActivity(int status){
        switch (status){
            case STATUS_CHANGE_SCHADENAUFNAHME:
                startActivity(new Intent(this, Schadenaufnahme.class));
                break;
            case STATUS_CHANGE_SCHADENLISTE:
                Log.d(debugMode, "Start schadensliste");
                break;
            default:
                Log.d(debugMode, "Activity nicht gefunden");
                break;
        }
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_camera) {
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
