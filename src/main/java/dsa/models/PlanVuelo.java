package dsa.models;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlanVuelo {
    private String idDron;
    private String idPiloto;
    private String fecha;          //"año-mes-diaThoras:minutos"
    private double reserva;
    private List<Posicion> posiciones = new ArrayList<>();

    public PlanVuelo() {}
    public PlanVuelo(String idDron, String idPiloto, String fecha, double reserva)  //variables
    {
        this.idDron = idDron;
        this.idPiloto = idPiloto;
        this.fecha = fecha;
        this.reserva = reserva;
    }

    public void addPosicion(double lat, double lon) {
        posiciones.add(new Posicion(lat, lon));
    }

    public LocalDateTime inicio() { return LocalDateTime.parse(fecha); }
    public LocalDateTime fin() { return inicio().plusMinutes(Math.round(reserva * 60)); }

    public boolean solapaCon(PlanVuelo otro) {
        return this.inicio().isBefore(otro.fin()) && otro.inicio().isBefore(this.fin());
    }

    //getters y setters
    public String getIdDron() { return idDron; }
    public void setIdDron(String idDron) { this.idDron = idDron; }
    public String getIdPiloto() { return idPiloto; }
    public void setIdPiloto(String idPiloto) { this.idPiloto = idPiloto; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public double getReserva() { return reserva; }
    public void setReserva(double reserva) { this.reserva = reserva; }
    public List<Posicion> getPosiciones() { return posiciones; }
    public void setPosiciones(List<Posicion> posiciones) { this.posiciones = posiciones; }
}
