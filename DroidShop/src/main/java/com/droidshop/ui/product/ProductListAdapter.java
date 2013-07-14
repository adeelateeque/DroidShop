package com.droidshop.ui.product;

import java.util.List;

import android.view.LayoutInflater;
import android.widget.ImageView;

import com.droidshop.BootstrapApplication;
import com.droidshop.R;
import com.droidshop.api.BootstrapApi;
import com.droidshop.model.Category;
import com.droidshop.model.Product;
import com.droidshop.ui.core.AlternatingColorListAdapter;
import com.squareup.picasso.Picasso;

public class ProductListAdapter extends AlternatingColorListAdapter<Product> {

	protected BootstrapApi api;

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
		int latest = api.getProductApi().getProductsList(1).size();

		List<String> loaded = ((Product) api.getProductApi().getProductsList(1)).getImages();

		for (int i = 0; i < latest; i++) {
			String url = loaded.get(i);
			Picasso.with(BootstrapApplication.getInstance())
					.load(url)
					.resizeDimen(R.dimen.list_item_image,
							R.dimen.list_item_image).centerCrop().into(view);
			setText(1, item.getName());
		}
	}

}