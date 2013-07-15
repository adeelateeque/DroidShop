package com.droidshop.ui.product;

import java.util.List;

import android.view.LayoutInflater;
import android.widget.ImageView;

import com.droidshop.BootstrapApplication;
import com.droidshop.R;
import com.droidshop.api.BootstrapApi;
import com.droidshop.model.AbstractEntity;
import com.droidshop.model.Product;
import com.droidshop.ui.core.AlternatingColorListAdapter;
import com.squareup.picasso.Picasso;

public class ProductListAdapter extends AlternatingColorListAdapter<Product> {

	protected BootstrapApi<AbstractEntity> api;

	public ProductListAdapter(LayoutInflater inflater, List<Product> items,
			boolean selectable) {
		super(R.layout.product_list_item, inflater, items, selectable);
	}

	public ProductListAdapter(LayoutInflater inflater, List<Product> items) {
		super(R.layout.product_list_item, inflater, items);
	}

	@Override
	protected int[] getChildViewIds() {
		return new int[] { R.id.image, R.id.product_name, R.id.price };
	}

	@Override
	protected void update(int position, Product item) {
		super.update(position, item);
		ImageView view = imageView(0);
		if (view == null) {
			view = new ImageView(BootstrapApplication.getInstance());
		}
		Picasso.with(BootstrapApplication.getInstance()).load(item.getImages().get(0))
				.resizeDimen(R.dimen.list_item_image, R.dimen.list_item_image)
				.centerCrop().into(view);
		setText(1, item.getName(), "$" + item.getPrice());
	}

}