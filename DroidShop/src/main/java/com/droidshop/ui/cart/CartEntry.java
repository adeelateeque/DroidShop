package com.droidshop.ui.cart;

import com.droidshop.model.Product;

public class CartEntry {

	private Product mProduct;
	private int mQuantity;

	public CartEntry(Product product, int quantity) {
		mProduct = product;
		mQuantity = quantity;
	}

	public Product getProduct() {
		return mProduct;
	}

	public int getQuantity() {
		return mQuantity;
	}

	public void setQuantity(int quantity) {
		mQuantity = quantity;
	}

}
