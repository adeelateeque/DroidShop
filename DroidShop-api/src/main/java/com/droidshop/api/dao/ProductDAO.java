package com.droidshop.api.dao;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.droidshop.api.model.Product;

@Component
public class ProductDAO extends AbstractDAO<Product>
{
	public ProductDAO()
	{
		super(Product.class);
	}

	public Product add(Product product) throws NoSuchAlgorithmException
	{
		System.out.println("ProductDAO: add");
		System.out.println("ProductDAO: START - adding product to the database");

		product.setCreatedAt(new Date());
		product.setUpdatedAt(new Date());

		beginTransaction();
		save(product);
		commitAndCloseTransaction();

		System.out.println("ProductDAO: END - adding product to the database");

		return product;
	}

	public Product update(Long productID, Product product) throws NoSuchAlgorithmException
	{
		System.out.println("ProductDAO: update");
		System.out.println("ProductDAO: START - updating product to the database");

		beginTransaction();
		Product fetchedProduct = fetch(productID);

		fetchedProduct.setUpdatedAt(new Date());

		update(fetchedProduct);
		commitAndCloseTransaction();

		System.out.println("ProductDAO: END - updating product to the database");

		return fetchedProduct;
	}

	public Product fetch(Long productID)
	{
		System.out.println("ProductDAO: fetch");
		System.out.println("ProductDAO: START - fetching product from the database by product ID");

		Product fetchedProduct = find(productID);
		System.out.println("ProductDAO: END - fetching product from the database by product ID");

		return fetchedProduct;
	}

	public Product fetchByProductName(String productName)
	{
		System.out.println("ProductDAO: fetchByProductName");
		System.out.println("ProductDAO: START - fetching product from the database by productName");

		beginTransaction();
		Product fetchedProduct = (Product) session.createCriteria(Product.class)
				.add(Restrictions.eq("productName", productName)).uniqueResult();
		System.out.println("ProductDAO: END - fetching product from the database by productName");
		closeTransaction();

		return fetchedProduct;
	}

	public List<Product> fetchAll(boolean includeAll)
	{
		System.out.println("ProductDAO: fetchAll");
		System.out.println("ProductDAO: START - fetching all products from the database");

		beginTransaction();
		List<Product> fetchedProducts = getAll();

		System.out.println("DEBUG: includeAll [" + includeAll + "]");
		if (includeAll == false)
		{
			System.out.println("ProductDAO: removing non-active products");
			Iterator<Product> iterator = fetchedProducts.iterator();
			while (iterator.hasNext())
			{
				Product currentProduct = (Product) iterator.next();
				Product.Status productStatus = currentProduct.getStatus();
				System.out.println("DEBUG: Product Status Code [" + productStatus + "]");
				if (!productStatus.equals(Product.Status.INSTOCK))
				{
					System.out.println("DEBUG: Removing Product [" + currentProduct.getName() + "]");
					iterator.remove();
				}
			}
		}
		else
		{
			System.out.println("ProductDAO: including all products");
		}
		System.out.println("ProductDAO: END - fetching all products from the database");

		return fetchedProducts;
	}

	public Product delete(Long productID)
	{
		System.out.println("ProductDAO: delete");
		System.out.println("ProductDAO: START - setting product status to delete in the database");

		beginTransaction();
		Product fetchedProduct = fetch(productID);
		fetchedProduct.setUpdatedAt(new Date());
		update(fetchedProduct);
		commitAndCloseTransaction();
		System.out.println("ProductDAO: END - setting product status to delete in the database");

		return fetchedProduct;
	}
}