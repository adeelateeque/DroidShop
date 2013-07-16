package com.droidshop.ui.product;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.actionbarsherlock.app.SherlockFragment;
import com.droidshop.R;
import com.droidshop.model.MonetaryAmount;
import com.droidshop.model.Product;

public class ProductManagerFragment extends SherlockFragment
{

	private EditText name, price, quantity, desc;
	private RadioGroup status;
	private RadioButton rgStatus;
	private ImageButton image;
	private Button next;
	private List<String> images;

	private static int RESULT_LOAD_IMAGE = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		final List c = new ArrayList();
		final ScrollView view = (ScrollView) inflater.inflate(R.layout.fragment_product_manager, null);
		image = (ImageButton) view.findViewById(R.id.ibPImage);
		next = (Button) view.findViewById(R.id.btnNext);
		image.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}

		});
		next.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
				builder.setTitle("Select Category(s)")
						.setMultiChoiceItems(R.array.testing, null, new DialogInterface.OnMultiChoiceClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int which, boolean isChecked)
							{
								if (isChecked)
								{
									// If the user checked the item, add it to the selected items
									c.add(which);
								}
								else if (c.contains(which))
								{
									// Else, if the item is already in the array, remove it
									c.remove(Integer.valueOf(which));
								}
							}
						}).setPositiveButton(R.string.create, new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int id)
							{
								Product p = createProduct(view, c);
							}
						}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int id)
							{

							}
						});
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
		return view;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == getSherlockActivity().RESULT_OK && null != data)
		{
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getSherlockActivity().getContentResolver()
					.query(selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			Bitmap b = BitmapFactory.decodeFile(picturePath);
			image.setImageBitmap(Bitmap.createScaledBitmap(b, image.getWidth(), image.getHeight(), false));
			images = new ArrayList<String>();
			images.add(BitMapToString(b));
		}
	}

	public Product createProduct(View v, List category)
	{
		name = (EditText) v.findViewById(R.id.etPName);
		price = (EditText) v.findViewById(R.id.etPPrice);
		quantity = (EditText) v.findViewById(R.id.etPQuantity);
		desc = (EditText) v.findViewById(R.id.etPDesc);
		status = (RadioGroup) v.findViewById(R.id.rgStatus);

		int selectedId = status.getCheckedRadioButtonId();
		rgStatus = (RadioButton) v.findViewById(selectedId);

		String name = this.name.getText().toString();
		String desc = this.desc.getText().toString();
		Double bd = Double.parseDouble(price.getText().toString());
		Currency c = Currency.getInstance("SGD");
		MonetaryAmount price = new MonetaryAmount(c, bd);
		int quantity = Integer.parseInt(this.quantity.getText().toString());
		String status = rgStatus.getText().toString();

		Product p = new Product();
		p.setName(name);
		p.setQuantity(quantity);
		p.setPrice(price);
		p.setCategories(category);
		p.setDescription(desc);
		p.setImages(images);
		p.setStatus(Product.Status.valueOf(status));
		return p;
	}

	public String BitMapToString(Bitmap bitmap)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] b = baos.toByteArray();
		String temp = Base64.encodeToString(b, Base64.DEFAULT);
		return temp;
	}
}
