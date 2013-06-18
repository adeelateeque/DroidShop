package com.droidshop.api.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.droidshop.api.manager.GeocodeManager;
import com.droidshop.api.model.geo.GeoLocation;
import com.droidshop.api.model.geo.LatLng;
import com.droidshop.api.model.geo.LocationQuery;
import com.droidshop.api.model.geo.MongoConnection;
import com.droidshop.api.model.geo.SpatialQuery;
import com.google.gson.Gson;

@Controller
@RequestMapping("/locations")
public class LocationController
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private MongoConnection mongoConnection = new MongoConnection();

	@RequestMapping(value = "/new", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Response addLocation(@RequestBody LocationQuery location)
	{
		try
		{
			logger.debug("addLocation: Name = " + location.getName());
			logger.debug("addLocation: AddressString = " + location.getAddressString());

			GeocodeManager geocoder = new GeocodeManager();

			if (geocoder.callGeocoder(location))
			{
				// Create a LatLng object from GeocoderResponse
				Gson gson = new Gson();
				String json = gson.toJson(geocoder.getGeocoderResponse().getResults().get(0).getGeometry().getLocation());
				LatLng theLocation = gson.fromJson(json, LatLng.class);

				// Get formatted address string from GeocoderResponse
				String formattedAddress = geocoder.getGeocoderResponse().getResults().get(0).getFormattedAddress();

				// GeoLocation is stored in Mongo and returned to the client
				GeoLocation geoLocation = new GeoLocation(location.getName(), formattedAddress, theLocation);
				mongoConnection.connect();
				mongoConnection.newLocation(geoLocation);

				// Return 200 OK plus GeoLocation data
				return Response.status(Response.Status.OK).entity(new Gson().toJson(geoLocation)).build();
			}
			else
			{
				return Response.status(geocoder.getErrorCode()).build();
			}
		}
		catch (Exception e)
		{
			logger.error("addLocation()", (Object[]) e.getStackTrace());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		finally
		{
			mongoConnection.getMongo().close();
		}
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Response removeLocation(@RequestBody LocationQuery location)
	{
		try
		{
			logger.debug("removeLocation: Name = " + location.getName());

			mongoConnection.connect();
			mongoConnection.deleteLocationByName(location.getName());

			return Response.status(Response.Status.OK).build();
		}
		catch (Exception e)
		{
			logger.error("removeLocation()", (Object[]) e.getStackTrace());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		finally
		{
			mongoConnection.getMongo().close();
		}
	}

	@RequestMapping(value = "/lookup", method = RequestMethod.POST, consumes = "application/json", produces = "text/plain")
	@ResponseBody
	public Response checkLocation(@RequestBody LocationQuery location)
	{
		try
		{
			logger.debug("checkLocation: Name = " + location.getName());
			logger.debug("checkLocation: AddressString = " + location.getAddressString());

			GeocodeManager geocoder = new GeocodeManager();

			if (geocoder.callGeocoder(location))
			{
				String geoResult = geocoder.getGeocoderResponse().getResults().get(0).toString();
				logger.debug("Geocoder Result: " + geoResult);

				return Response.status(Response.Status.OK).entity(geoResult.toString()).build();
			}
			else
			{
				return Response.status(geocoder.getErrorCode()).build();
			}
		}
		catch (Exception e)
		{
			logger.error("getLatLng()", (Object[]) e.getStackTrace());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Response getLocations()
	{
		StatusType statusCode = null;
		String result = null;

		try
		{
			mongoConnection.connect();
			result = mongoConnection.getLocations();
			statusCode = Response.Status.OK;
		}
		catch (Exception e)
		{
			logger.error("getLocations()" + e.getStackTrace());
			statusCode = Response.Status.INTERNAL_SERVER_ERROR;
		}
		finally
		{
			mongoConnection.getMongo().close();
		}

		if (statusCode != Response.Status.OK)
			return Response.status(statusCode).build();
		else
			return Response.status(statusCode).entity(result).build();
	}

	@RequestMapping(value = "/geocenter", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Response getGeoSpatialCenter(@RequestBody SpatialQuery query)
	{
		StatusType statusCode = null;
		String result = null;

		try
		{
			mongoConnection.connect();
			result = mongoConnection.getLocationsByGeoCenter(query.getCenter(), query.getRadius());
			statusCode = Response.Status.OK;
		}
		catch (Exception e)
		{
			logger.error("getGeoSpatialCenter", (Object[]) e.getStackTrace());
			statusCode = Response.Status.INTERNAL_SERVER_ERROR;
		}
		finally
		{
			mongoConnection.getMongo().close();
		}

		if (statusCode != Response.Status.OK)
			return Response.status(statusCode).build();
		else
			return Response.status(statusCode).entity(result).build();
	}
}
