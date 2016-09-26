package fiuba.tallerdeproyectos2.Activities;

import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

import fiuba.tallerdeproyectos2.Fragments.CertificatesFragment;
import fiuba.tallerdeproyectos2.Fragments.CoursesFragment;
import fiuba.tallerdeproyectos2.Fragments.ExitFragment;
import fiuba.tallerdeproyectos2.Fragments.HomeFragment;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Utilities.Utilities;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ExitFragment.ExitDialogListener {

    Fragment fragment;
    FragmentManager fragmentManager;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    boolean doubleBackToExitPressedOnce = false;
    private GoogleApiClient googleApiClient;
    SessionManagerActivity session;
    HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragment = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        setTitle(R.string.title_activity_main);

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
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

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
            if (googleApiClient != null && googleApiClient.isConnected()) {
                signOut();
                googleApiClient.disconnect();
            }

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
        DialogFragment dialog = new ExitFragment();
        dialog.show(getFragmentManager(), "exit");
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback( new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
            }
        });
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

}
