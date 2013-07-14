package com.droidshop.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.droidshop.R;

public class PastOrderFragment extends SherlockFragment {

	private TableLayout tlPastOrder;
	private TableRow tableRow;
	private TextView textView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.past_order_view, null);
		tlPastOrder = (TableLayout) view.findViewById(R.id.tl_past_order);

		for(int i = 0; i < 5; i++){
			tlPastOrder.addView(createRow(view));
		}
		return view;
	}

	public TableRow createRow(View view){
		tableRow = new TableRow(view.getContext());

		textView = new TextView(view.getContext());
		textView.setText("Hi");
		textView.setBackgroundResource(R.drawable.cell_shape);
		textView.setTextSize(15);
		textView.setPadding(10, 10, 10, 10);
		textView.setLayoutParams(new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.2f));
		tableRow.addView(textView);

		textView = new TextView(view.getContext());
		textView.setText("Hi");
		textView.setBackgroundResource(R.drawable.cell_shape);
		textView.setTextSize(15);
		textView.setPadding(10, 10, 10, 10);
		textView.setLayoutParams(new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.2f));
		tableRow.addView(textView);

		textView = new TextView(view.getContext());
		textView.setText("Hi");
		textView.setBackgroundResource(R.drawable.cell_shape);
		textView.setTextSize(15);
		textView.setPadding(10, 10, 10, 10);
		textView.setLayoutParams(new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.6f));
		tableRow.addView(textView);

		return tableRow;
	}
}
