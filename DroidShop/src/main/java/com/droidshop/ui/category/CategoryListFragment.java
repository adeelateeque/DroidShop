package com.droidshop.ui.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.droidshop.R;
import com.droidshop.ui.product.ProductListActivity;

public class CategoryListFragment extends SherlockFragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = (View) inflater.inflate(R.layout.fragment_category, null);

		GridView gvCategory = (GridView) view.findViewById(R.id.gvCategory);

		gvCategory.setAdapter(new ImageAdapter(this.getSherlockActivity()));
		gvCategory.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View v, int position, long id)
			{
				// Toast.makeText(CategoryListFragment.this.getSherlockActivity(),
				// "" + position, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getActivity(), ProductListActivity.class);
				startActivity(intent);
			}
		});
		return view;
	}

	public class ImageAdapter extends BaseAdapter
	{

		private Context mContext;

		public ImageAdapter(Context c)
		{
			mContext = c;
		}

		public int getCount()
		{
			return mThumbIds.length;
		}

		public Object getItem(int position)
		{
			return null;
		}

		public long getItemId(int position)
		{
			return 0;
		}

		// create a new ImageView for each item referenced by the Adapter
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ImageView imageView;
			if (convertView == null)
			{ // if it's not recycled, initialize some
				// attributes
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(8, 8, 8, 8);
			}
			else
			{
				imageView = (ImageView) convertView;
			}

			imageView.setImageResource(mThumbIds[position]);
			return imageView;
		}

		// link to database for images
		private Integer[] mThumbIds = { R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher };

	}
}
