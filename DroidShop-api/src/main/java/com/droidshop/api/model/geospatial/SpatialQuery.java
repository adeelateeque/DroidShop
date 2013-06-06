package com.droidshop.api.model.geospatial;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
// JSON format for /geocenter spatial queries
public class SpatialQuery {

    private LatLng center;
    private double radius;

    public SpatialQuery() {
        super();
    }

    public SpatialQuery(LatLng theCenter, double theRadius) {
        super();
        this.center = theCenter;
        this.radius = theRadius;
    }

    public LatLng getCenter() {
        return center;
    }

    public void setCenter(LatLng center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
