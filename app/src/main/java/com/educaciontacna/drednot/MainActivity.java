package com.educaciontacna.drednot;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.educaciontacna.drednot.databinding.ActivityMainBinding;
import com.educaciontacna.drednot.ui.utils.MyUtilsApp;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
{


    ActivityMainBinding binding;
    NavHostFragment navHostFragment;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        setSupportActionBar(binding.appBarMain.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.appBarMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        setupNavigation();

        MyUtilsApp.ShowDialog(MainActivity.this,"Bienvenido a DredNot","Active su ubicacion para continuar");
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
