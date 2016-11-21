package fiuba.tallerdeproyectos2.Activities;

import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.InputStream;
import java.util.HashMap;

import fiuba.tallerdeproyectos2.Fragments.CertificatesFragment;
import fiuba.tallerdeproyectos2.Fragments.CoursesFragment;
import fiuba.tallerdeproyectos2.Fragments.ExitDialogFragment;
import fiuba.tallerdeproyectos2.Fragments.HomeFragment;
import fiuba.tallerdeproyectos2.Notifications.Config;
import fiuba.tallerdeproyectos2.Notifications.NotificationUtils;
import fiuba.tallerdeproyectos2.Notifications.RegistrationService;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Utilities.Utilities;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ExitDialogFragment.ExitDialogListener {

    Fragment certificateFragment, homeFragment;
    FragmentManager fragmentManager;
    boolean doubleBackToExitPressedOnce = false;
    SessionManagerActivity session;
    HashMap<String, String> user;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        Boolean showCertificateFragment = intent.getBooleanExtra("showCertificateFragment", false);

        if(showCertificateFragment){
            certificateFragment = new CertificatesFragment();
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, certificateFragment).commit();
            setTitle("Certificados");
        } else {
            homeFragment = new HomeFragment();
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
            setTitle(R.string.title_activity_main);
        }


        session = new SessionManagerActivity(getApplicationContext());

        View header = navigationView.getHeaderView(0);
        TextView userName = (TextView) header.findViewById(R.id.name);
        TextView userSurname = (TextView) header.findViewById(R.id.surname);
        TextView userEmail = (TextView) header.findViewById(R.id.email);
        CircleImageView userProfilePhoto = (CircleImageView) header.findViewById(R.id.imageView);

        session.checkLogin();
        user = session.getUserDetails();

        new DownloadImageTask(userProfilePhoto).execute(user.get(SessionManagerActivity.KEY_IMAGE));
        userName.setText(user.get(SessionManagerActivity.KEY_NAME));
        userSurname.setText(user.get(SessionManagerActivity.KEY_SURNAME));
        userEmail.setText(user.get(SessionManagerActivity.KEY_EMAIL));

        Intent i = new Intent(this, RegistrationService.class);
        startService(i);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
        } else if (id == R.id.nav_cursos) {
            fragment = new CoursesFragment();
        } else if (id == R.id.nav_certificates) {
            fragment = new CertificatesFragment();
        } else if (id == R.id.nav_exit) {


            fragment = new HomeFragment();
            showExitDialog();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

        item.setChecked(true);
        setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                finish();
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, R.string.double_click_to_exit, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if (dialog.getTag().equals("exit")) {
            Log.i("User", "exit");
            Utilities.appendToInfoLog("User", "exit");

            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            if(accessToken != null){
                LoginManager.getInstance().logOut();
            }
            session.logoutUser();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    public void showExitDialog() {
        DialogFragment dialog = new ExitDialogFragment();
        dialog.show(getFragmentManager(), "exit");
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

}
