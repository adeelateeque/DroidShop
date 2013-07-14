package com.droidshop.model;


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
