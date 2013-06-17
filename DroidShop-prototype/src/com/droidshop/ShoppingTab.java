package com.droidshop;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
 
public class ShoppingTab extends TabActivity {
    // TabSpec Names
    private static final String SHOP_SPEC = "Shop";
    private static final String CART_SPEC = "Cart";
    private static final String PROFILE_SPEC = "Profile";
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_tab);
         
        TabHost tabHost = getTabHost();
         
        // Shop Tab
        TabSpec shopSpec = tabHost.newTabSpec(SHOP_SPEC);
        // Tab Icon
        shopSpec.setIndicator(SHOP_SPEC, getResources().getDrawable(R.drawable.icon_shop));
        Intent shopIntent = new Intent(this, ShoppingListView.class);
        // Tab Content
        shopSpec.setContent(shopIntent);
         
        // Cart Tab
        TabSpec cartSpec = tabHost.newTabSpec(CART_SPEC);
        cartSpec.setIndicator(CART_SPEC, getResources().getDrawable(R.drawable.icon_cart));
        Intent cartIntent = new Intent(this, ShoppingCart.class);
        cartSpec.setContent(cartIntent);
         
        // Profile Tab
        TabSpec profileSpec = tabHost.newTabSpec(PROFILE_SPEC);
        profileSpec.setIndicator(PROFILE_SPEC, getResources().getDrawable(R.drawable.icon_user));
        Intent profileIntent = new Intent(this, UserPage.class);
        profileSpec.setContent(profileIntent);
         
        // Adding all TabSpec to TabHost
        tabHost.addTab(shopSpec); // Adding Inbox tab
        tabHost.addTab(cartSpec); // Adding Outbox tab
        tabHost.addTab(profileSpec); // Adding Profile tab
    }
}