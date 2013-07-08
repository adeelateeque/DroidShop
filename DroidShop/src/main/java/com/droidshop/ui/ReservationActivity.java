package com.droidshop.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.droidshop.R;
import com.droidshop.R.drawable;
import com.droidshop.ui.core.BootstrapFragmentActivity;

public class ReservationActivity extends BootstrapFragmentActivity {

	private ListView lvReservation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reservation);

		lvReservation = (ListView) findViewById(R.id.lvReservation);
		lvReservation.setAdapter(new ReservationAdapter(this));
	}

	public class ReservationAdapter extends BaseAdapter {

		private Context mContext;
		private int count = 0;

		public ReservationAdapter(Context c){
			mContext = c;
		}

		public int getCount() {
	        return count;
	    }

	    public Object getItem(int position) {
	        return null;
	    }

	    public long getItemId(int position) {
	        return 0;
	    }

	    // create a new ImageView for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
	        /*ImageView imageView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(mContext);
	            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setPadding(8, 8, 8, 8);
	        } else {
	            imageView = (ImageView) convertView;
	        }

	        imageView.setImageResource(mThumbIds[position]);
	        return imageView;*/
	    	View v = convertView;
	    	 if (v == null) {
                 LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                 v = vi.inflate(R.layout.reservation_list_row, null);
             }
             while (count != 10) {
            	 LinearLayout reservationLayout = (LinearLayout) v.findViewById(R.id.llReservation);
            	 if (count%2 == 0){
            		 reservationLayout.setBackgroundResource(R.drawable.table_background_selector);
            	 } else if (count%2 != 0){
            		 reservationLayout.setBackgroundResource(R.drawable.table_background_alternate_selector);
            	 }
                 TextView tt = (TextView) v.findViewById(R.id.toptext);
                 TextView bt = (TextView) v.findViewById(R.id.bottomtext);
                 if (tt != null) {
                       tt.setText("Name: " + Integer.toString(count));                            }
                 if(bt != null){
                      	bt.setText("Status: "+ Integer.toString(count));
                 }
                 count++;
             }
             return v;
	    }
	}
}
