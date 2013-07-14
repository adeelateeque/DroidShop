package com.droidshop.ui.category;

import java.util.List;

import android.view.LayoutInflater;
import android.widget.ImageView;

import com.droidshop.BootstrapApplication;
import com.droidshop.R;
import com.droidshop.model.Category;
import com.droidshop.ui.core.AlternatingColorListAdapter;
import com.squareup.picasso.Picasso;

public class CategoryListAdapter extends AlternatingColorListAdapter<Category>
{
	public CategoryListAdapter(LayoutInflater inflater, List<Category> items, boolean selectable)
	{
		super(R.layout.category_list_item, inflater, items, selectable);
	}

	public CategoryListAdapter(LayoutInflater inflater, List<Category> items)
	{
		super(R.layout.category_list_item, inflater, items);
	}

	@Override
	protected int[] getChildViewIds()
	{
		return new int[] { R.id.image, R.id.category_name };
	}

	@Override
	protected void update(int position, Category item)
	{
		super.update(position, item);
		ImageView view = imageView(0);
		if (view == null)
		{
			view = new ImageView(BootstrapApplication.getInstance());
		}
		String url = "http://factspy.net/wp-content/uploads/2013/03/iphone5black.jpg";
		Picasso.with(BootstrapApplication.getInstance()).load(url).resizeDimen(R.dimen.list_item_image, R.dimen.list_item_image).centerCrop().into(view);
		setText(1, item.getName());
	}

}