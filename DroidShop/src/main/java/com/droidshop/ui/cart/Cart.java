package com.droidshop.ui.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.droidshop.model.CartEntry;
import com.droidshop.model.Product;

public class Cart
{
	public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

	private static Map<Product, CartEntry> cartMap = new HashMap<Product, CartEntry>();

	public static void addProduct(Product product, int quantity)
	{
		// Get the current cart entry
		CartEntry curEntry = cartMap.get(product);

		// If the quantity is zero or less, remove the products
		if (quantity <= 0)
		{
			if (curEntry != null)
				removeProduct(product);
			return;
		}

		// If a current cart entry doesn't exist, create one
		if (curEntry == null)
		{
			curEntry = new CartEntry(product, quantity);
			cartMap.put(product, curEntry);
			return;
		}

		// Update the quantity
		curEntry.setQuantity(quantity);
	}

	public static int getProductQuantity(Product product)
	{
		// Get the current cart entry
		CartEntry curEntry = cartMap.get(product);

		if (curEntry != null)
			return curEntry.getQuantity();

		return 0;
	}

	public static void removeProduct(Product product)
	{
		cartMap.remove(product);
	}

	public static List<Product> getCartList()
	{
		List<Product> cartList = new Vector<Product>(cartMap.keySet().size());
		for (Product p : cartMap.keySet())
		{
			cartList.add(p);
		}

		return cartList;
	}
}
