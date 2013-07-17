package com.droidshop.ui.product;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;

import com.droidshop.BootstrapApplication;
import com.droidshop.R;
import com.droidshop.api.ApiProvider;
import com.droidshop.api.BootstrapApi;
import com.droidshop.model.AbstractEntity;
import com.droidshop.model.Category;
import com.droidshop.model.Product;
import com.droidshop.ui.core.ItemGridFragment;
import com.squareup.picasso.Picasso;

import android.accounts.AccountsException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.OperationCanceledException;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailsActivity extends Activity {

	protected BootstrapApi<AbstractEntity> api;
	@Inject
	protected ApiProvider apiProvider;
	ImageView my_img;
	Bitmap mybitmap;
	ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_description);
		Bundle bundle = this.getIntent().getExtras();
		final CharSequence name = bundle.getCharSequence("name");
		final CharSequence price = bundle.getCharSequence("price");
		final CharSequence img = bundle.getCharSequence("img");
		final CharSequence description = bundle.getCharSequence("description");
		final CharSequence quantity = bundle.getCharSequence("quantity");
		String url = img.toString();
		url = url.substring(1, url.length() - 1);

		new DisplayImageFromURL((ImageView) findViewById(R.id.ImageViewProduct))
				.execute(url);

		// Set the proper text
		TextView productTitleTextView = (TextView) findViewById(R.id.TextViewProductTitle);
		productTitleTextView.setText(name);
		TextView productPriceTextView = (TextView) findViewById(R.id.TextViewProductPrice);
		TextView productDescriptionTextView = (TextView) findViewById(R.id.TextViewProductDescription);
		productDescriptionTextView.setText(description.toString());

		Button addToCartButton = (Button) findViewById(R.id.ButtonBuyNow);
		if (quantity.equals("0")) {
			addToCartButton.setText("Reserve Now");
			String next = "<font color='#EE0000'>OUT OF STOCK</font>";
			productPriceTextView.setText(Html.fromHtml(next));

			addToCartButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(ProductDetailsActivity.this, "Item reserved.", Toast.LENGTH_SHORT).show();
				}
			});
		} else {

			productPriceTextView.setText(price.toString());

			addToCartButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(ProductDetailsActivity.this,
							receipt.class);
					Bundle b = new Bundle();
					b.putCharSequence("name", name);
					b.putCharSequence("price", price);
					intent.putExtras(b);
					startActivity(intent);
				}
			});
		}
	}

	private class DisplayImageFromURL extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(ProductDetailsActivity.this);
			pd.setMessage("Loading...");
			pd.show();
		}

		public DisplayImageFromURL(ImageView bmImage) {
			this.bmImage = bmImage;
			bmImage.setMaxHeight(200);
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return mIcon11;

		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
			pd.dismiss();
		}
	}
}
