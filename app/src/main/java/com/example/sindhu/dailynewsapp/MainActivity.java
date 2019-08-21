package com.example.sindhu.dailynewsapp;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sindhu.dailynewsapp.adapters.TabAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import cn.pedant.SweetAlert.SweetAlertDialog;
import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class MainActivity extends AppCompatActivity {

    public static final int RequestSignInCode = 7;
    public static FirebaseAuth firebaseAuth;
    public static GoogleApiClient googleApiClient;
    private static int m_item_id = 123;
    ViewPager pager;
    TabLayout layout;
    NewsViewModel viewModel;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    LinearLayout signInLayout;
    boolean isItemAdded = false;
    ConnectivityManager connectivityManager;
    NetworkInfo activeNetworkInfo;
    com.google.android.gms.common.SignInButton signInButton;
    FlipperLayout flipper;
    String url[] = {
            "http://blog.eap.ucop.edu/wp-content/uploads/2016/01/Julie-Huang-27.jpg",
            "https://source.unsplash.com/random",
            "https://acaudillo1.files.wordpress.com/2015/01/innovation-technology.jpg",
            "http://1.bp.blogspot.com/-5lS9VinHhs8/Up5Od-ZXMcI/AAAAAAAAAtA/DeSFzPLLV5g/s1600/New+York+City+time+square+HD+Wallpaper.jpg",
            "https://www.bing.com/th?id=OGC.55131e1a7ff3345be6978f1960b3433c&pid=1.7&rurl=http%3a%2f%2fwww.doori.at%2" +
                    "fblog%2fwp-content%2fuploads%2fNY-Night-Cinemagraph.gif&ehk=VVbsbyO4hW3pEZjqVj2g0w"
            , "https://thenypost.files.wordpress.com/2017/08/visualhouse_lab_detail-8-0_3.jpg?quality=90&strip=all&strip=all"
    };
    private String favorite = "fav";
    private String noInternet = "No Internet Connection";
    private String somethingWrong = "Something Went Wrong";
    private String closeCon = "Do you want to close app";
    private String confirmText = "Yes,close app!";
    private String cancle = "Cancel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = findViewById(R.id.v_pager);
        layout = findViewById(R.id.tablayout);
        signInLayout = findViewById(R.id.sign_in_layout);
        signInButton = findViewById(R.id.sign_in_button);
        pager.setAdapter(new TabAdapter(getSupportFragmentManager()));
        layout.setupWithViewPager(pager);
        flipper = findViewById(R.id.flipView);
        setSlider();
        viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            signInLayout.setVisibility(View.GONE);
            pager.setVisibility(View.VISIBLE);
            layout.setVisibility(View.VISIBLE);
        }
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    UserSignInMethod();
                } else {
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(getString(R.string.alert_text_title))
                            .setContentText(noInternet).show();
                }
            }
        });
    }

    private void setSlider() {
        //used to set flipper view to flipper layout
        int n = url.length;
        for (int i = 1; i <= n; i++) {
            FlipperView view = new FlipperView(getBaseContext());
            view.setImageUrl(url[i - 1]).setDescription(i + "/" + n)
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            flipper.addFlipperView(view);
        }
    }

    public void UserSignInMethod() {

        Intent AuthIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);

        startActivityForResult(AuthIntent, RequestSignInCode);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            signInLayout.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
            pager.setVisibility(View.VISIBLE);
            pager.setAdapter(new TabAdapter(getSupportFragmentManager()));
            layout.setupWithViewPager(pager);
            isItemAdded = true;
            invalidateOptionsMenu();
        }
    }

    @SuppressLint("ResourceType")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestSignInCode) {
            isItemAdded = false;
            invalidateOptionsMenu();
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (googleSignInResult.isSuccess()) {
                GoogleSignInAccount googleSignInAccount =
                        googleSignInResult.getSignInAccount();
                FirebaseUserAuth(googleSignInAccount);
            }
        }

    }

    public void FirebaseUserAuth(GoogleSignInAccount googleSignInAccount) {

        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task AuthResultTask) {

                        if (AuthResultTask.isSuccessful()) {

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                            signInLayout.setVisibility(View.GONE);
                            isItemAdded = true;
                            invalidateOptionsMenu();
                            layout.setVisibility(View.VISIBLE);
                            pager.setVisibility(View.VISIBLE);
                            pager.setAdapter(new TabAdapter(getSupportFragmentManager()));
                            layout.setupWithViewPager(pager);
                        } else {
                            Toast.makeText(getApplicationContext(), somethingWrong, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void UserSignOutFunction() {

        firebaseAuth.signOut();

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        Toast.makeText(getApplicationContext(), R.string.logut, Toast.LENGTH_LONG).show();
                    }
                }
        );
        signInLayout.setVisibility(View.VISIBLE);
        layout.setVisibility(View.GONE);
        pager.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {

        new SweetAlertDialog(this)
                .setTitleText(getString(R.string.exit_title))
                .setContentText(closeCon)
                .setConfirmText(confirmText)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        //sDialog.dismissWithAnimation();
                        finish();
                    }
                })
                .setCancelButton(cancle, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        if (isItemAdded) {
            if (menu.findItem(m_item_id) == null) {
                MenuItem menuItem = menu.add(Menu.NONE, m_item_id, 2, R.string.signout);
                menuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER);
                menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        UserSignOutFunction();
                        disableButton();
                        return true;
                    }
                });
            }
        } else {
            menu.removeItem(m_item_id);
        }
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    public void disableButton() {
        isItemAdded = false;
        invalidateOptionsMenu();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.fav) {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(getString(R.string.detail_intent), favorite);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}


