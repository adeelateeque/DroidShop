package com.droidshop.api.model.geospatial;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
// JSON format for latLng-based /new and /geocenter results
// Includes formattedAddress and LatLng information from GeocodeManager
public class GeoLocation {
    private String name;
    private String formattedAddress;
    private LatLng latLng;

    public GeoLocation() {
        super();
    }

    public GeoLocation(String name, String formattedAddress, LatLng latLng) {
        super();
        this.name = name;
        this.formattedAddress = formattedAddress;
        this.latLng = latLng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public LatLng getLocation() {
        return latLng;
    }

    public void setLocation(LatLng latLng) {
        this.latLng = latLng;
    }
}
