package dsa.models;

public class Posicion {
    private double lat;
    private double lon;

    public Posicion() {}
    public Posicion(double lat, double lon) //variables
    {
        this.lat = lat;
        this.lon = lon;
    }

    //getters y setters
    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }
    public double getLon() { return lon; }
    public void setLon(double lon) { this.lon = lon; }
}
