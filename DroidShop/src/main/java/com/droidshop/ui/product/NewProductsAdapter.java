package com.droidshop.ui.product;

import java.util.List;

import android.view.LayoutInflater;

import com.droidshop.R;
import com.droidshop.model.Product;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

public class NewProductsAdapter extends SingleTypeAdapter<Product>
{
	public NewProductsAdapter(LayoutInflater inflater, List<Product> items)
	{
		super(inflater, R.layout.product_grid_item);

		setItems(items);
	}


	@Override
	protected int[] getChildViewIds()
	{
		return new int[] { R.id.iv_product, R.id.tv_name };
	}

	@Override
	protected void update(int position, Product product)
	{
		setText(1, String.format("%1$s %2$s", product.getName(), product.getPrice().getValue()));
	}
}
