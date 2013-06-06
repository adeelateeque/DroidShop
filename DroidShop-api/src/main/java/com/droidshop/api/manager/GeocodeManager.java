package com.droidshop.api.manager;

import java.util.Iterator;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.droidshop.api.model.geospatial.LocationQuery;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderAddressComponent;
import com.google.code.geocoder.model.GeocoderRequest;

public class GeocodeManager
{
	private final Logger logger = LoggerFactory.getLogger(GeocodeManager.class);
	private final Geocoder geocoder = new Geocoder();
	private Response.StatusType errorCode = null;
	private GeocodeResponse geocoderResponse = null;

	public GeocodeManager()
	{
		super();
	}

	public Response.StatusType getErrorCode()
	{
		return errorCode;
	}

	private void setErrorCode(Response.StatusType errorCode)
	{
		this.errorCode = errorCode;
	}

	public GeocodeResponse getGeocoderResponse()
	{
		return geocoderResponse;
	}

	private void setGeocoderResponse(GeocodeResponse geocoderResponse)
	{
		this.geocoderResponse = geocoderResponse;
	}

	private boolean callGoogleGeocoder(String queryAddress)
	{
		try
		{
			GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(queryAddress).setLanguage("en")
					.getGeocoderRequest();
			setGeocoderResponse(geocoder.geocode(geocoderRequest));

			switch (geocoderResponse.getStatus())
			{

				case ZERO_RESULTS:
					logger.info("No matches found for address: " + queryAddress);
					setErrorCode(Response.Status.NO_CONTENT);
					return false;

				case OK:
					if (geocoderResponse.getResults().size() > 1)
					{
						logger.info("Ambiguous: more than one match for address: " + queryAddress);
						setErrorCode(Response.Status.NO_CONTENT);
						return false;
					}
					else
					{
						// We have a match
						logGeocoderResult(geocoderResponse);
						return true;
					}

					// There was a problem with the Google Geocoder Service
				case ERROR:
				case INVALID_REQUEST:
				case OVER_QUERY_LIMIT:
				case REQUEST_DENIED:
				case UNKNOWN_ERROR:
				default:
					logger.info("Google Geocoding API returned" + geocoderResponse.getStatus());
					setErrorCode(Response.Status.INTERNAL_SERVER_ERROR);
					return false;
			}
		}
		catch (Exception e)
		{
			logger.error("Exception processing location request", e);
			errorCode = Response.Status.INTERNAL_SERVER_ERROR;
			return false;
		}
	}

	private void logGeocoderResult(GeocodeResponse geocoderResponse)
	{
		logger.info("formattedAddress = " + geocoderResponse.getResults().get(0).getFormattedAddress());
		logger.info("LatLng = " + geocoderResponse.getResults().get(0).getGeometry().getLocation());

		if (logger.isDebugEnabled())
		{
			Iterator<GeocoderAddressComponent> iterator = geocoderResponse.getResults().get(0).getAddressComponents()
					.iterator();
			while (iterator.hasNext())
			{
				GeocoderAddressComponent addressComponent = iterator.next();
				logger.debug("Address Component [" + addressComponent.getTypes().get(0) + "] = "
						+ addressComponent.getShortName());
			}
		}
	}

	public boolean callGeocoder(LocationQuery location)
	{
		logger.debug("Name = " + location.getName());
		logger.debug("AddressString = " + location.getAddressString());

		return (callGoogleGeocoder(location.getAddressString()));
	}
}
