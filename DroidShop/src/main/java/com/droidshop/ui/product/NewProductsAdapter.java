package com.droidshop.ui.product;

import java.util.List;

import android.view.LayoutInflater;
import android.widget.ImageView;

import com.droidshop.BootstrapApplication;
import com.droidshop.R;
import com.droidshop.model.Product;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.squareup.picasso.Picasso;

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
		ImageView view = imageView(0);
		if (view == null)
		{
			view = new ImageView(BootstrapApplication.getInstance());
		}
		String url = "";

		if(!product.getImages().isEmpty())
		{
			url = product.getImages().get(0);
		}
		Picasso.with(BootstrapApplication.getInstance()).load(url).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(view);
		setText(1, String.format("%1$s %2$s", product.getName(), product.getPrice().getValue()));
	}
}
