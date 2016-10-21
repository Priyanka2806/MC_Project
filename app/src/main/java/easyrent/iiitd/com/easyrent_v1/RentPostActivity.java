package easyrent.iiitd.com.easyrent_v1;



import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;


public class RentPostActivity extends AppCompatActivity{

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    private Button postPropertyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_post);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.nav_bar_icon);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);

        setupDrawerContent(nvDrawer);

        postPropertyButton = (Button)findViewById(R.id.btn_post);
        postPropertyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PostPropertyActivity.class);
                startActivity(intent);

            }
        });
    }

        private void setupDrawerContent(NavigationView navigationView) {
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            selectDrawerItem(menuItem);
                            return true;
                        }
                    });
        }
        public void selectDrawerItem(MenuItem menuItem) {

            Fragment fragment = null;
            Class fragmentClass;
            switch(menuItem.getItemId()) {
                case R.id.myProfileView:
                    fragmentClass = MyProfileFragment.class;
                    break;
                case R.id.propertiesView:
                    fragmentClass = ListedPropertiesFragment.class;
                    break;
                case R.id.wishlistView:
                    fragmentClass = WishlistFragment.class;
                    break;
                default:
                    fragmentClass = MyProfileFragment.class;
            }

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

            // Highlight the selected item has been done by NavigationView
            menuItem.setChecked(true);
            // Set action bar title
            setTitle(menuItem.getTitle());
            // Close the navigation drawer
            mDrawer.closeDrawers();
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // The action bar home/up action should open or close the drawer.
            switch (item.getItemId()) {
                case android.R.id.home:
                    mDrawer.openDrawer(GravityCompat.START);
                    return true;
            }

            return super.onOptionsItemSelected(item);
        }


        @Override
        protected void onPostCreate(Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);
        }




}