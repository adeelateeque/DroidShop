package com.droidshop;

import com.droidshop.SimpleGestureFilter.SimpleGestureListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
 
public class MainActivity extends Activity implements SimpleGestureListener {
	
	private SimpleGestureFilter detector;
	private SlidingDrawer slide;
	private Button btnLogin, btnOrder, btnReserve, btnWish, btnLogout;
	private boolean isLogin = false;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        detector = new SimpleGestureFilter(this,this);
        slide = (SlidingDrawer)this.findViewById(R.id.slidingDrawer);
        btnLogin = (Button)this.findViewById(R.id.btnLogin);
        btnOrder = (Button)this.findViewById(R.id.btnOrder);
        btnReserve = (Button)this.findViewById(R.id.btnReserve);
        btnWish = (Button)this.findViewById(R.id.btnWish);
        btnLogout = (Button)this.findViewById(R.id.btnLogout);
        
        if (isLogin == false){
        	btnLogin.setVisibility(View.VISIBLE);
        	btnOrder.setVisibility(View.GONE);
        	btnReserve.setVisibility(View.GONE);
        	btnWish.setVisibility(View.GONE);
        	btnLogout.setVisibility(View.GONE);
        } else{
        	btnLogin.setVisibility(View.GONE);
        	btnOrder.setVisibility(View.VISIBLE);
        	btnReserve.setVisibility(View.VISIBLE);
        	btnWish.setVisibility(View.VISIBLE);
        	btnLogout.setVisibility(View.VISIBLE);
        }
        
        btnLogin.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(intent);
			}
        	
        });
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
    
    @Override 
    public boolean dispatchTouchEvent(MotionEvent me){ 
      this.detector.onTouchEvent(me);
     return super.dispatchTouchEvent(me); 
    }

	@Override
	public void onSwipe(int direction) {
		// TODO Auto-generated method stub
		if(direction == 3){
			if(slide.isOpened() == false){
				slide.animateToggle();
			}
		}
		if(direction == 4){
			if(slide.isOpened()){
				slide.animateToggle();
			}
		}
	}

	@Override
	public void onDoubleTap() {
		// TODO Auto-generated method stub
		
	}

}