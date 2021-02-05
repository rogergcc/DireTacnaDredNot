package com.educaciontacna.drednot;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.educaciontacna.drednot.databinding.ActivityMainBinding;
import com.educaciontacna.drednot.ui.helpers.BottomNavigationBehavior;
import com.educaciontacna.drednot.ui.helpers.DarkModePrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
{


    ActivityMainBinding binding;
    NavHostFragment navHostFragment;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (new DarkModePrefManager(this).isNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        setSupportActionBar(binding.appBarMain.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.appBarMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        //region REGION OLD METHOD BOTTOM NAVIGATION
//        binding.appBarMain.bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) binding.appBarMain.bottomNavigationView.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationBehavior());
//        binding.appBarMain.bottomNavigationView.setSelectedItemId(R.id.navigationHome);
        //endregion

        setupNavigation();

    }

    private void setupNavigation() {
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) binding.appBarMain.bottomNavigationView.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationBehavior());

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        if (navHostFragment != null) {
            NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());
        }
    }


    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
