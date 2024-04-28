package com.app.mtis;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainFrame extends AppCompatActivity {
    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainframe);
        BottomNavigationView navBar = findViewById(R.id.mainframe_bottnavview_barmenu);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainframe_frame_navhost, new StoryActivity()).commit();

        navBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.bottom_menu_journal){
                    Fragment fragment = new JournalActivity();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainframe_frame_navhost, fragment).commit();
                    return true;
                }else if (menuItem.getItemId() == R.id.bottom_menu_story){
                    Fragment fragment = new StoryActivity();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainframe_frame_navhost, fragment).commit();
                    return true;
                }else if (menuItem.getItemId() == R.id.bottom_menu_entrygroups){
                    Fragment fragment = new EntryGroupsListActivity();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainframe_frame_navhost, fragment).commit();
                    return true;
                }else if (menuItem.getItemId() == R.id.bottom_menu_profile){
                    Fragment fragment = new ProfileActivity();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainframe_frame_navhost, fragment).commit();
                    return true;
                }else {
                    return false;
                }
            }
        });

    }
}
