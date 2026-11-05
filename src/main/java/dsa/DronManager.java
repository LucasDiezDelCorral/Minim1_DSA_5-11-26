package dsa;
import dsa.models.Dron;
import dsa.models.Piloto;
import dsa.models.PlanVuelo;
import java.util.List;

public interface DronManager {

    void addDron(String id, String nombre, String fabricante, String modelo);
    void addPiloto(String id, String nombre, String apellidos);

    List<Dron> dronesPorHorasVuelo();
    List<Piloto> pilotosPorHorasVuelo();

    // pila
    void Reparar(String idDron) throws DronException;
    Dron repararDron();

    void crearPlanVuelo(PlanVuelo plan)  throws DronException;

    List<PlanVuelo> planesDePiloto(String idPiloto) throws DronException;
    List<PlanVuelo> planesDeDron(String idDron) throws DronException;

    // Variables
    int numDrones();
    int numDronesEnAlmacen();
    void clear();
}
