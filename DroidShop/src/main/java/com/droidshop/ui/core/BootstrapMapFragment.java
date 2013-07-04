package com.droidshop.ui.core;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Watson.OnCreateOptionsMenuListener;
import android.support.v4.app.Watson.OnOptionsItemSelectedListener;
import android.support.v4.app.Watson.OnPrepareOptionsMenuListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.internal.view.menu.MenuItemWrapper;
import com.actionbarsherlock.internal.view.menu.MenuWrapper;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.droidshop.R;

public class BootstrapMapFragment extends SupportMapFragment implements OnCreateOptionsMenuListener,
		OnPrepareOptionsMenuListener, OnOptionsItemSelectedListener, OnMarkerClickListener
{
	private SherlockFragmentActivity mActivity;
	List<Marker> markers;
	Marker selectedMarker;

	public SherlockFragmentActivity getSherlockActivity()
	{
		return mActivity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.markers = new ArrayList<Marker>();
	}

	@Override
	public void onAttach(Activity activity)
	{
		if (!(activity instanceof SherlockFragmentActivity))
		{
			throw new IllegalStateException(getClass().getSimpleName() + " must be attached to a SherlockFragmentActivity.");
		}
		mActivity = (SherlockFragmentActivity) activity;

		super.onAttach(activity);
	}

	@Override
	public void onDetach()
	{
		mActivity = null;
		super.onDetach();
	}

	@Override
	public final void onCreateOptionsMenu(android.view.Menu menu, android.view.MenuInflater inflater)
	{
		onCreateOptionsMenu(new MenuWrapper(menu), mActivity.getSupportMenuInflater());
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// Nothing to see here.
	}

	@Override
	public final void onPrepareOptionsMenu(android.view.Menu menu)
	{
		onPrepareOptionsMenu(new MenuWrapper(menu));
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu)
	{
		// Nothing to see here.
	}

	@Override
	public final boolean onOptionsItemSelected(android.view.MenuItem item)
	{
		return onOptionsItemSelected(new MenuItemWrapper(item));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Nothing to see here.
		return false;
	}

	protected void addMarkerToMap(MarkerOptions options)
	{
		Marker marker = getMap().addMarker(options);
		markers.add(marker);
	}

	protected void highLightMarker(Marker marker)
	{
		for (Marker foundMarker : this.markers)
		{
			if (!foundMarker.equals(marker))
			{
				foundMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			}
			else
			{
				foundMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
				foundMarker.showInfoWindow();
			}
		}

		this.selectedMarker = marker;
	}

	/**
	 * Highlight the marker by index
	 */
	protected void highLightMarker(int index)
	{
		highLightMarker(markers.get(index));
	}

	protected void clearMarkers()
	{
		getMap().clear();
		markers.clear();
	}

	protected void removeSelectedMarker()
	{
		this.markers.remove(this.selectedMarker);
		this.selectedMarker.remove();
	}

	public Marker getSelectedMarker()
	{
		return selectedMarker;
	}

	public void setSelectedMarker(Marker selectedMarker)
	{
		this.selectedMarker = selectedMarker;
	}

	public List<Marker> getMarkers()
	{
		return markers;
	}

	public class IconizedWindowAdapter implements InfoWindowAdapter
	{
		LayoutInflater inflater = null;

		public IconizedWindowAdapter(LayoutInflater inflater)
		{
			this.inflater = inflater;
		}

		@Override
		public View getInfoWindow(Marker marker)
		{
			return (null);
		}

		@Override
		public View getInfoContents(Marker marker)
		{
			View popup = inflater.inflate(R.layout.marker_info_window, null);
			TextView tv = (TextView) popup.findViewById(R.id.title);
			tv.setText(marker.getTitle());
			tv = (TextView) popup.findViewById(R.id.snippet);
			tv.setText(marker.getSnippet());

			return (popup);
		}
	}

	@Override
	public boolean onMarkerClick(Marker marker)
	{
		this.selectedMarker = marker;
		return false;
	}
}