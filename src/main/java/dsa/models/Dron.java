package dsa.models;
import java.util.ArrayList;
import java.util.List;

public class Dron {
    private String id;
    private String nom;
    private String fab;
    private String mod;
    private double horasVuelo;
    private boolean dronlisiado; //funcionamiento
    private List<PlanVuelo> planes = new ArrayList<>();

    public Dron() {}
    public Dron(String id, String nom, String fab, String mod) //variables del dron
    {
        this.id = id;
        this.nom = nom;
        this.fab = fab;
        this.mod = mod;
    }

    public void addPlan(PlanVuelo p)
    {
        planes.add(p);
        horasVuelo += p.getReserva();
    }

    //los getters y los setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getFab() { return fab; }
    public void setFab(String fab) { this.fab = fab; }
    public String getMod() { return mod; }
    public void setMod(String mod) { this.mod = mod; }
    public double getHorasVuelo() { return horasVuelo; }
    public void setHorasVuelo(double horasVuelo) { this.horasVuelo = horasVuelo; }

    //almacen
    public boolean isDronlisiado() { return dronlisiado; }
    public void setDronlisiado(boolean dronlisiado) { this.dronlisiado = dronlisiado; }

    public List<PlanVuelo> getPlanes() { return planes; }
}
