package com.careeranna.boloanna;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.careeranna.boloanna.Adapter.CustomNavigationListAdapter;
import com.careeranna.boloanna.fragements.NewsFragment;
import com.careeranna.boloanna.fragements.VideosFragment;
import com.careeranna.boloanna.models.Category;
import com.careeranna.boloanna.models.NavItems;

import java.util.ArrayList;

public class LandingActivity extends AppCompatActivity implements
        CustomNavigationListAdapter.OnItemClickListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private RecyclerView expandableListView;
    private CustomNavigationListAdapter navAdapter;

    ArrayList<NavItems> navItems;

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    NewsFragment newsFragment;
    VideosFragment videosFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        expandableListView = (RecyclerView) findViewById(R.id.navList);

        genData();

        addDrawersItem();

        setUpDrawer();

        if(savedInstanceState == null) {
            selectedFirstItemAsDefault();
        }

        if(getSupportActionBar() != null ) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        newsFragment = new NewsFragment();
        videosFragment = new VideosFragment();

        fragmentManager = getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, videosFragment);
        fragmentTransaction.commit();

    }

    private void selectedFirstItemAsDefault() {

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void addDrawersItem() {

    }

    private void setUpDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void genData() {

        navItems = new ArrayList<>();

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category("Politics"));
        categories.add(new Category("Economy"));

        navItems.add(new NavItems("Videos", categories));

        navItems.add(new NavItems("News", categories));
        navItems.add(new NavItems("Change Language"));
        navItems.add(new NavItems("Profile"));
        navItems.add(new NavItems("Sign Out"));


        navAdapter = new CustomNavigationListAdapter(LandingActivity.this, navItems);
        expandableListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        expandableListView.setAdapter(navAdapter);
        navAdapter.setOnItemClicklistener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeTitle(int child_position, int parent_position) {

        switch (parent_position) {

            case 0:

                videosFragment = new VideosFragment();

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, videosFragment).commit();

                break;

            case 1:

                newsFragment = new NewsFragment();

                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, newsFragment).commit();

                break;


        }

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(navItems.get(parent_position).getCategories().get(child_position).getName());
        }

        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override

    public void onNavItemSelected(int position) {

        switch (position) {

            case 0:
            case 1:
                break;

            case 2:

                startActivity(new Intent(LandingActivity.this, ChooseLanguage.class));
                drawerLayout.closeDrawer(GravityCompat.START);

                break;

            case 3:

                startActivity(new Intent(LandingActivity.this, ProfileActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);

                break;

            case 4:

                startActivity(new Intent(LandingActivity.this, SignInActivity.class));
                finish();

                break;

        }

    }
}
