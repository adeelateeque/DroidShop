package com.droidshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
 
public class MainActivity extends Activity {
	private FrameLayout flUser, flAdmin;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private MergeAdapter mAdapter;
    private ArrayAdapter aAdapter;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    private LinearLayout llMain, llCategory, llSearch, llCProduct, llUProduct;
    private Button btnSearch, btnClear, btnCreate, btnUpdate, btnReset;
    private GridView gvToday, gvRecent, gvMost, gvCategory;
    private EditText etPName, etPPrice, etPQuantity, etPDesc, etUName, etUPrice, etUQuantity, etUDesc;
    private Spinner spCategory, spUCategory, spRCategory, spRName;
    private ImageButton ibPImage, ibUImage;
    private static boolean isAdmin = false;
	private static boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        flUser = (FrameLayout) findViewById(R.id.flUser);
        flAdmin = (FrameLayout) findViewById(R.id.flAdmin);
        llMain = (LinearLayout) findViewById(R.id.llMain);
        llCategory = (LinearLayout) findViewById(R.id.llCategory);
        llSearch = (LinearLayout) findViewById(R.id.llSearch);
        llCProduct = (LinearLayout) findViewById(R.id.llCProduct);
        llUProduct = (LinearLayout) findViewById(R.id.llUProduct);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        gvToday = (GridView) findViewById(R.id.gvToday);
        gvRecent = (GridView) findViewById(R.id.gvRecent);
        gvMost = (GridView) findViewById(R.id.gvMost);
        gvCategory = (GridView) findViewById(R.id.gvCategory);
        etPName = (EditText) findViewById(R.id.etPName);
        etPPrice = (EditText) findViewById(R.id.etPPrice);
        etPQuantity = (EditText) findViewById(R.id.etPQuantity);
        etPDesc = (EditText) findViewById(R.id.etPDesc);
        spCategory = (Spinner) findViewById(R.id.spCategory);
        ibPImage = (ImageButton) findViewById(R.id.ibPImage);
        etUName = (EditText) findViewById(R.id.etUName);
        etUPrice = (EditText) findViewById(R.id.etUPrice);
        etUQuantity = (EditText) findViewById(R.id.etUQuantity);
        etUDesc = (EditText) findViewById(R.id.etUDesc);
        spRCategory = (Spinner) findViewById(R.id.spRCategory);
        spUCategory = (Spinner) findViewById(R.id.spUCategory);
        ibUImage = (ImageButton) findViewById(R.id.ibUImage);
        btnClear = (Button) findViewById(R.id.btnClear);
        
        addProductCategory();

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mAdapter = new MergeAdapter();
        
        if ((isLogin == false)&&(isAdmin == false)){
        	flUser.setVisibility(View.VISIBLE);
        	flAdmin.setVisibility(View.GONE);
        	mPlanetTitles = getResources().getStringArray(R.array.logoutArray);
        	aAdapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, mPlanetTitles);
        	mAdapter.addAdapter(new ProfileImageAdapter(this));
        	mAdapter.addAdapter(aAdapter);
        	mDrawerList.setAdapter(mAdapter);
        }
        else if ((isLogin == true)&&(isAdmin == false)){
        	flUser.setVisibility(View.VISIBLE);
        	flAdmin.setVisibility(View.GONE);
        	mPlanetTitles = getResources().getStringArray(R.array.loginArray);
        	aAdapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, mPlanetTitles);
        	mAdapter.addAdapter(new ProfileImageAdapter(this));
        	mAdapter.addAdapter(aAdapter);
        	mDrawerList.setAdapter(mAdapter);
        }
        else if ((isLogin == true)&&(isAdmin == true)){
        	flUser.setVisibility(View.GONE);
        	flAdmin.setVisibility(View.VISIBLE);
        	mPlanetTitles = getResources().getStringArray(R.array.adminArray);
        	aAdapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, mPlanetTitles);
        	mAdapter.addAdapter(new ProfileImageAdapter(this));
        	mAdapter.addAdapter(aAdapter);
        	mDrawerList.setAdapter(mAdapter);
        }
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				if (isAdmin == false){
					if(tab.getText().equals("Main")){
						llMain.setVisibility(View.VISIBLE);
						llCategory.setVisibility(View.GONE);
						llSearch.setVisibility(View.GONE);
					} else if(tab.getText().equals("Category")){
						llMain.setVisibility(View.GONE);
						llCategory.setVisibility(View.VISIBLE);
						llSearch.setVisibility(View.GONE);
					} else if(tab.getText().equals("Search")){
						llMain.setVisibility(View.GONE);
						llCategory.setVisibility(View.GONE);
						llSearch.setVisibility(View.VISIBLE);
					}
				} else if (isAdmin == true){
					if(tab.getText().equals("Create Product")){
						llCProduct.setVisibility(View.VISIBLE);
						llUProduct.setVisibility(View.GONE);
					} else if(tab.getText().equals("Update Product")){
						llCProduct.setVisibility(View.GONE);
						llUProduct.setVisibility(View.VISIBLE);
					}
				}
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
        };
        if (isAdmin == false){
        	actionBar.addTab(actionBar.newTab().setText("Main").setTabListener(tabListener));
        	actionBar.addTab(actionBar.newTab().setText("Category").setTabListener(tabListener));
        	actionBar.addTab(actionBar.newTab().setText("Search").setTabListener(tabListener));
        } else if (isAdmin == true){
        	actionBar.addTab(actionBar.newTab().setText("Create Product").setTabListener(tabListener));
        	actionBar.addTab(actionBar.newTab().setText("Update Product").setTabListener(tabListener));
        }
        
        gvToday.setAdapter(new ImageAdapter(this));
        gvToday.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        
        gvRecent.setAdapter(new ImageAdapter(this));
        gvRecent.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        
        gvMost.setAdapter(new ImageAdapter(this));
        gvMost.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        
        gvCategory.setAdapter(new ImageAdapter(this));
        gvCategory.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    public void addProductCategory() {
		List<String> list = new ArrayList<String>();
		list.add("Cloth");
		list.add("Console");
		list.add("Food");
		list.add("Mobile Phone");
		list.add("Laptop");
		list.add("Hard Disk");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spCategory.setAdapter(dataAdapter);
		spUCategory.setAdapter(dataAdapter);
		spRCategory.setAdapter(dataAdapter);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        case R.id.action_settings:
            // create intent to perform web search for this planet
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            }
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
    	
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
        
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flUser, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        if ((isLogin == false)&&(isAdmin == false)){
        	if (position == 1){
        		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(intent);
        	}
        }
        else if ((isLogin == true)&&(isAdmin == false)){
        	if (position == 1){
        		Intent intent = new Intent(MainActivity.this, UserProfile.class);
				startActivity(intent);
        	} else if (position == 2){
        		
        	} else if (position == 3){
        		
        	} else if (position == 4){
        		
        	} else if (position == 5){
        		
        	}
        }
        
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class PlanetFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";

        public PlanetFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER) - 1;
            if (i >= 0){
            	String planet = "";
            	if ((isLogin == false)&&(isAdmin == false)){
            		planet = getResources().getStringArray(R.array.logoutArray)[i];
            	}
            	else if ((isLogin == true)&&(isAdmin == false)){
            		planet = getResources().getStringArray(R.array.loginArray)[i];
            	}

            	int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                            "drawable", getActivity().getPackageName());
            	((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            }
            return rootView;
        }
    }
}