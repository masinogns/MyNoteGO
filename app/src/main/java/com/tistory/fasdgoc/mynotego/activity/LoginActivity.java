package com.tistory.fasdgoc.mynotego.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.tistory.fasdgoc.mynotego.MainActivity;
import com.tistory.fasdgoc.mynotego.R;
import com.tistory.fasdgoc.mynotego.adapter.IntroPagerAdapter;
import com.tistory.fasdgoc.mynotego.fragment.LoginFragment;
import com.tistory.fasdgoc.mynotego.util.ParallelxPageTransformer;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, LoginFragment.OnSignInTryListener {
    private static final String TAG = "LoginActivity";

    private static final int RC_GOOGLE_SIGN_IN = 9001;

    private boolean exitActivity = false;

    @BindView(R.id.pager)
    ViewPager mViewPager;

    @BindView(R.id.indicator)
    CircleIndicator mIndicator;

    private PagerAdapter mPagerAdapter;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mPagerAdapter = new IntroPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setPageTransformer(true, new ParallelxPageTransformer());
        mIndicator.setViewPager(mViewPager);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onBackPressed() {
        int position = mViewPager.getCurrentItem();

        if (position == 0) {
            if (exitActivity) {
                super.onBackPressed();
                return;
            }
            exitActivity = true;
            Toast.makeText(this, "종료하려면 한번 더 누르세요.", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exitActivity = false;
                }
            }, 2000);

        } else {
            mViewPager.setCurrentItem(position - 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RC_GOOGLE_SIGN_IN:
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

                if (result.isSuccess()) {
                    GoogleSignInAccount account = result.getSignInAccount();
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    mAuth.signInWithCredential(credential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "signInWithCredential", task.getException());
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                } else {
                    Log.w(TAG, "getSignInResultFromIntent");
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onSignInTry(View v) {
        switch (v.getId()) {
            case R.id.google_sign_in:
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(intent, RC_GOOGLE_SIGN_IN);
                break;

            default:
                break;
        }
    }
}
