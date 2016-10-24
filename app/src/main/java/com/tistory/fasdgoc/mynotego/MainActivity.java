package com.tistory.fasdgoc.mynotego;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.tistory.fasdgoc.mynotego.adapter.DrawerListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private boolean exitActivity = false;
    private String title;
    @BindView(R.id.activity_main)
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    @BindView(R.id.left_drawer)
    ListView mDrawerList;
    DrawerListAdapter mAdapter;

    @OnItemClick(R.id.left_drawer)
    public void onItemClick(AdapterView<?> parent, int position) {
        Toast.makeText(this, "Clicked" + position, Toast.LENGTH_SHORT).show();
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
        array.add(new DrawerListAdapter.DrawerItem(R.drawable.locked, "로그인"));
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
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }

        if(exitActivity) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
