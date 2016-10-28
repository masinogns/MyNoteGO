package com.tistory.fasdgoc.mynotego;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.tistory.fasdgoc.mynotego.activity.LoginActivity;
import com.tistory.fasdgoc.mynotego.activity.WriteNoteActivity;
import com.tistory.fasdgoc.mynotego.adapter.DrawerListAdapter;
import com.tistory.fasdgoc.mynotego.util.MyLocationManager;
import com.tistory.fasdgoc.mynotego.util.MySensorManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    private MyLocationManager mMyLocationManager;
    private MySensorManager mMySensorManager;

    private boolean exitActivity = false;
    private String title;
    @BindView(R.id.activity_main)
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    @BindView(R.id.left_drawer)
    ListView mDrawerList;
    DrawerListAdapter mAdapter;

    @OnClick(R.id.write_note_button)
    public void OnWriteNoteClicked() {
        Location location = mMyLocationManager.getCurrentLocation();

        if (location != null) {
            Intent intent = new Intent(this, WriteNoteActivity.class);
            intent.putExtra("Location", location.toString());
            startActivity(intent);
        } else {
            Toast.makeText(this, "GPS를 수신하기 전까지는\n쪽지를 쓸 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnItemClick(R.id.left_drawer)
    public void onItemClick(AdapterView<?> parent, int position) {
        switch (position) {
            case 0: // Sign Out
                new AlertDialog.Builder(this)
                        .setTitle("로그아웃")
                        .setMessage("로그아웃을 하면\n앱을 이용할 수 없습니다.")
                        .setIcon(R.drawable.exit)
                        .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();

                                Toast.makeText(MainActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
                break;

            default:
                Toast.makeText(this, "Clicked" + position, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public MyLocationManager getmMyLocationManager() {
        return mMyLocationManager;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ArrayList<DrawerListAdapter.DrawerItem> array = new ArrayList<>();
        array.add(new DrawerListAdapter.DrawerItem(R.drawable.unlocked, "로그아웃"));
        array.add(new DrawerListAdapter.DrawerItem(R.drawable.like, "오늘의 핫한 장소"));
        array.add(new DrawerListAdapter.DrawerItem(R.drawable.megaphone, "공지사항"));
        array.add(new DrawerListAdapter.DrawerItem(R.drawable.settings, "환경설정"));
        array.add(new DrawerListAdapter.DrawerItem(R.drawable.fax, "문의 및 건의"));

        mAdapter = new DrawerListAdapter(this, array, R.layout.draweritem);
        mDrawerList.setAdapter(mAdapter);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                title = getTitle().toString();
                String user = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                setTitle(user);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                setTitle(title);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mMyLocationManager = new MyLocationManager(this);
        mMySensorManager = new MySensorManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMyLocationManager.register();
        mMySensorManager.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMyLocationManager.unregister();
        mMySensorManager.unregister();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mMyLocationManager.OnRequestPermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }

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
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
