package fiuba.tallerdeproyectos2.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import fiuba.tallerdeproyectos2.Models.ServerResponse;
import fiuba.tallerdeproyectos2.R;
import fiuba.tallerdeproyectos2.Rest.ApiClient;
import fiuba.tallerdeproyectos2.Rest.ApiInterface;
import fiuba.tallerdeproyectos2.Utilities.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private CallbackManager callbackManager;
    public GoogleApiClient googleApiClient;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    private ProgressDialog progressDialog;
    SessionManagerActivity session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        session = new SessionManagerActivity(getApplicationContext());

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    Profile profile = Profile.getCurrentProfile();
                                    session.createLoginSession(object.getString("email"), profile.getFirstName() + profile.getMiddleName(),
                                            profile.getLastName(),profile.getProfilePictureUri(200, 200).toString());

                                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                                    Call<ServerResponse> call = apiService.postStudentData(object.getString("email"), profile.getFirstName() + profile.getMiddleName(),
                                            profile.getLastName(), "Facebook");
                                    call.enqueue(new Callback<ServerResponse>() {
                                        @Override
                                        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                                            Boolean success = response.body().getSuccess();
                                            if (success.equals(true)) {
                                                Toast.makeText(getApplicationContext(), R.string.facebook_success_login, Toast.LENGTH_LONG).show();
                                                navigateToMainActivity();
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<ServerResponse> call, Throwable t) {
                                            Log.e(TAG, t.toString());
                                        }
                                    });

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "email");
                request.setParameters(parameters);
                request.executeAsync();
                Log.i(TAG, "Facebook");
                Utilities.appendToInfoLog(TAG, "Facebook");
            }

            @Override
            public void onCancel() {
                Log.i(TAG, String.valueOf(R.string.facebook_cancel_login));
                Utilities.appendToInfoLog(TAG, String.valueOf(R.string.facebook_cancel_login));
                Toast.makeText(getApplicationContext(), R.string.facebook_cancel_login, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {
                Log.e(TAG, "Facebook error:" + e.getMessage());
                Utilities.appendToErrorLog(TAG, "Facebook error:" + e.getMessage());
                Toast.makeText(getApplicationContext(), R.string.facebook_login_error, Toast.LENGTH_LONG).show();
            }
        });

        Button facebookButton = (Button)findViewById(R.id.facebook_button);
        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email"));
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(LoginActivity.this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        Button googleButton = (Button) findViewById(R.id.google_button);
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.i(TAG, "Google");
            Utilities.appendToInfoLog(TAG, "Google");
            session.createLoginSession(acct.getEmail(), acct.getGivenName(),acct.getFamilyName(), acct.getPhotoUrl().toString());

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ServerResponse> call = apiService.postStudentData(acct.getEmail(), acct.getGivenName(),
                    acct.getFamilyName(), "Google");
            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    Boolean success = response.body().getSuccess();
                    if (success.equals(true)) {
                        Toast.makeText(getApplicationContext(), R.string.google_success_login, Toast.LENGTH_LONG).show();
                        navigateToMainActivity();
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        } else {
            Log.d(TAG, "Google Failed");
            Utilities.appendToDebugLog(TAG, "Google Failed");
            Toast.makeText(getApplicationContext(), R.string.google_login_failed, Toast.LENGTH_LONG).show();
        }
    }

    private void navigateToMainActivity(){
        Intent homeIntent = new Intent(this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
        finish();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Google connection failed:" + connectionResult);
        Utilities.appendToDebugLog(TAG, "Google connection failed:" + connectionResult);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
