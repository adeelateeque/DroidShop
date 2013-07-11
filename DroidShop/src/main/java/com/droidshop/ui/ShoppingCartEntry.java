package com.droidshop.ui;

import com.droidshop.model.Product;

public class ShoppingCartEntry {

	private Product mProduct;
	private int mQuantity;

	public ShoppingCartEntry(Product product, int quantity) {
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
