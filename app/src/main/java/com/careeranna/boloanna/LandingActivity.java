package com.careeranna.boloanna;

import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.careeranna.boloanna.Adapter.CustomNavigationListAdapter;
import com.careeranna.boloanna.fragements.NewsFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LandingActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] items;

    private ExpandableListView expandableListView;
    private CustomNavigationListAdapter navAdapter;
    private List<String> listTitle;
    private Map<String, List<String>> listChild;

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    NewsFragment newsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        expandableListView = (ExpandableListView) findViewById(R.id.navList);

        initItems();

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

        fragmentManager = getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, newsFragment);
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

        navAdapter = new CustomNavigationListAdapter(this, listTitle, listChild);
        expandableListView.setAdapter(navAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(listTitle.get(groupPosition).toString());
                }
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        LandingActivity.this,
                        listChild.get(listTitle.get(groupPosition)).get(childPosition).toString(),
                        Toast.LENGTH_SHORT)
                        .show();
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
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

        List<String> title = Arrays.asList("QAndA", "Videos", "News", "ChangeLanguage", "Profile");
        List<String> childrenItem = Arrays.asList("Cat1", "Cat2", "Cat3");

        listChild = new TreeMap<>();
        listChild.put(title.get(0), childrenItem);
        listChild.put(title.get(1), childrenItem);
        listChild.put(title.get(2), childrenItem);

        listTitle = new ArrayList<>(listChild.keySet());
    }

    private void initItems() {

        items = new String[]{"QAndA", "Videos", "News", "ChangeLanguage", "Profile"};

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
