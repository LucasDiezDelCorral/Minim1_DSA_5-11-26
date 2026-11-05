package dsa;
import dsa.models.Dron;
import dsa.models.Piloto;
import dsa.models.PlanVuelo;
import org.apache.log4j.Logger;

import java.util.*;

public class DronManagerImpl implements DronManager {
    final static Logger logger = Logger.getLogger(DronManagerImpl.class);

    //Singleton
    private static DronManagerImpl instance;

    private DronManagerImpl() { }

    public static DronManagerImpl getInstance() {
        if (instance == null) {
            instance = new DronManagerImpl();
            logger.info("getInstance -> creada");
        }
        return instance;
    }

    //estructura
    private final Map<String, Dron> drones = new HashMap<>();
    private final Map<String, Piloto> pilotos = new HashMap<>();
    private final Deque<Dron> almacen = new ArrayDeque<>();

    @Override
    public void addDron(String id, String nombre, String fabricante, String modelo) {
        logger.info("addDron(" + id + ", " + nombre + ", " + fabricante + ", " + modelo + ")");
        drones.put(id, new Dron(id, nombre, fabricante, modelo));
        logger.info("addDron -> total drones: " + drones.size());
    }

    @Override
    public void addPiloto(String id, String nombre, String apellidos) {
        logger.info("addPiloto(" + id + ", " + nombre + ", " + apellidos + ")");
        pilotos.put(id, new Piloto(id, nombre, apellidos));
        logger.info("addPiloto -> total pilotos: " + pilotos.size());
    }

    @Override
    public List<Dron> dronesPorHorasVuelo() {
        logger.info("dronesPorHorasVuelo()");
        List<Dron> list = new ArrayList<>(drones.values());
        list.sort((a, b) -> Double.compare(b.getHorasVuelo(), a.getHorasVuelo()));
        logger.info("dronesPorHorasVuelo -> " + list.size());
        return list;
    }

    @Override
    public List<Piloto> pilotosPorHorasVuelo() {
        logger.info("pilotosPorHorasVuelo()");
        List<Piloto> list = new ArrayList<>(pilotos.values());
        list.sort((a, b) -> Double.compare(b.getHorasVuelo(), a.getHorasVuelo()));
        logger.info("pilotosPorHorasVuelo -> " + list.size());
        return list;
    }

    @Override
    public void Reparar(String idDron) throws DronException {
        logger.info("Reparar(" + idDron + ")");
        Dron dron = drones.get(idDron);
        if (dron == null) {
            logger.error("no encontrado: " + idDron);
            throw new DronException(404, "No encontrado: " + idDron);
        }
        if (dron.isDronlisiado()) {
            logger.error("Dron " + idDron + " en el almacen");
            throw new DronException(409, "Dron en el almacen: " + idDron);
        }
        dron.setDronlisiado(true);
        almacen.push(dron);
        logger.info("Reparar -> en almacen: " + almacen.size());
    }

    @Override
    public Dron repararDron() {
        logger.info("repararDron(" + almacen.size() + ")");
        Dron dron = almacen.poll();
        if (dron == null) {
            logger.warn("almacen vacio");
            return null;
        }
        dron.setDronlisiado(false);
        logger.info("repararDron -> " + dron.getId());
        return dron;
    }

    @Override
    public void crearPlanVuelo(PlanVuelo plan) throws DronException {
        logger.info("crearPlanVuelo(dron=" + plan.getIdDron() + ", piloto=" + plan.getIdPiloto()
                + ", fecha=" + plan.getFecha() + ", reserva=" + plan.getReserva()
                + ", posiciones=" + plan.getPosiciones().size() + ")");

        Dron dron = drones.get(plan.getIdDron());
        if (dron == null) {
            logger.error("dron no encontrado: " + plan.getIdDron());
            throw new DronException(404, "No encontrado: " + plan.getIdDron());
        }
        Piloto piloto = pilotos.get(plan.getIdPiloto());
        if (piloto == null) {
            logger.error("piloto no encontrado: " + plan.getIdPiloto());
            throw new DronException(404, "No encontrado: " + plan.getIdPiloto());
        }
        if (dron.isDronlisiado()) {
            logger.error("Dron " + dron.getId() + " en el almacen");
            throw new DronException(409, "Dron en el almacen: " + dron.getId());
        }
        for (PlanVuelo p : dron.getPlanes()) {
            if (p.solapaCon(plan)) {
                logger.error("Dron " + dron.getId() + " ocupado");
                throw new DronException(409, "Dron ocupado: " + dron.getId());
            }
        }
        for (PlanVuelo p : piloto.getPlanes()) {
            if (p.solapaCon(plan)) {
                logger.error("Piloto " + piloto.getId() + " ocupado");
                throw new DronException(409, "Piloto ocupado: " + piloto.getId());
            }
        }
        dron.addPlan(plan);
        piloto.addPlan(plan);
        logger.info("crearPlanVuelo -> dron " + dron.getPlanes().size() + ", piloto " + piloto.getPlanes().size());
    }

    @Override
    public List<PlanVuelo> planesDePiloto(String idPiloto) throws DronException {
        logger.info("planesDePiloto(" + idPiloto + ")");
        Piloto piloto = pilotos.get(idPiloto);
        if (piloto == null) {
            logger.error("piloto no encontrado: " + idPiloto);
            throw new DronException(404, "No encontrado: " + idPiloto);
        }
        List<PlanVuelo> list = new ArrayList<>(piloto.getPlanes());
        logger.info("planesDePiloto -> " + list.size());
        return list;
    }

    @Override
    public List<PlanVuelo> planesDeDron(String idDron) throws DronException {
        logger.info("planesDeDron(" + idDron + ")");
        Dron dron = drones.get(idDron);
        if (dron == null) {
            logger.error("dron no encontrado: " + idDron);
            throw new DronException(404, "No encontrado: " + idDron);
        }
        List<PlanVuelo> list = new ArrayList<>(dron.getPlanes());
        logger.info("planesDeDron -> " + list.size());
        return list;
    }

    @Override
    public int numDrones() {
        logger.info("numDrones()");
        int n = drones.size();
        logger.info("numDrones -> " + n);
        return n;
    }

    @Override
    public int numDronesEnAlmacen() {
        logger.info("numDronesEnAlmacen()");
        int n = almacen.size();
        logger.info("numDronesEnAlmacen -> " + n);
        return n;
    }

    @Override
    public void clear() {
        logger.info("clear()");
        drones.clear();
        pilotos.clear();
        almacen.clear();
        logger.info("clear -> vacio");
    }
}
