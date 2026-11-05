package dsa.models;
import java.util.ArrayList;
import java.util.List;

public class Piloto {
    private String id;
    private String nom;
    private String cognom;
    private double horasVuelo;
    private List<PlanVuelo> planes = new ArrayList<>();

    public Piloto() {}
    public Piloto(String id, String nom, String cognom) {
        this.id = id;
        this.nom = nom;
        this.cognom = cognom;
    }

    public void addPlan(PlanVuelo p) {
        planes.add(p);
        horasVuelo += p.getReserva();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getCognom() { return cognom; }
    public void setCognom(String cognom) { this.cognom = cognom; }
    public double getHorasVuelo() { return horasVuelo; }
    public void setHorasVuelo(double horasVuelo) { this.horasVuelo = horasVuelo; }

    public List<PlanVuelo> getPlanes() { return planes; }
}
