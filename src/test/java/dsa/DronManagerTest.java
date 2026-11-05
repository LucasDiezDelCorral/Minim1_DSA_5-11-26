package dsa;

import dsa.models.Dron;
import dsa.models.Piloto;
import dsa.models.PlanVuelo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DronManagerTest {

    DronManager dm;

    @Before
    public void setUp() {
        dm = DronManagerImpl.getInstance();
        dm.addDron("d1", "Halcon", "DJI", "Mavic 3");
        dm.addDron("d2", "Aguila", "Parrot", "Anafi");
        dm.addDron("d3", "Buho", "DJI", "Mini 4");
        dm.addPiloto("p1", "Lucas", "Diez");
        dm.addPiloto("p2", "Marta", "Puig");
    }

    @After
    public void tearDown() {
        dm.clear();
    }

    @Test
    public void testAlmacenPila() throws Exception {
        dm.Reparar("d1");
        dm.Reparar("d2");
        Assert.assertEquals(2, dm.numDronesEnAlmacen());

        Dron reparado = dm.repararDron();
        Assert.assertEquals("d2", reparado.getId());
        Assert.assertFalse(reparado.isDronlisiado());
        Assert.assertEquals(1, dm.numDronesEnAlmacen());
    }

    @Test
    public void testCrearPlanVuelo() throws Exception {
        PlanVuelo plan = new PlanVuelo("d1", "p1", "2026-11-05T10:00", 2);
        plan.addPosicion(41.27, 1.98);
        plan.addPosicion(41.28, 1.99);
        dm.crearPlanVuelo(plan);

        Assert.assertEquals(1, dm.planesDeDron("d1").size());
        Assert.assertEquals(1, dm.planesDePiloto("p1").size());
        Assert.assertEquals(2, dm.planesDeDron("d1").get(0).getPosiciones().size());
    }

    @Test(expected = DronException.class)
    public void testPlanDronEnAlmacen() throws Exception {
        dm.Reparar("d1");
        dm.crearPlanVuelo(new PlanVuelo("d1", "p1", "2026-11-05T10:00", 2));
    }

    @Test(expected = DronException.class)
    public void testPlanSolapado() throws Exception {
        dm.crearPlanVuelo(new PlanVuelo("d1", "p1", "2026-11-05T10:00", 2));
        dm.crearPlanVuelo(new PlanVuelo("d2", "p1", "2026-11-05T11:00", 1));
    }

    @Test
    public void testPlanesSeguidosSinSolape() throws Exception {
        dm.crearPlanVuelo(new PlanVuelo("d1", "p1", "2026-11-05T10:00", 2));
        dm.crearPlanVuelo(new PlanVuelo("d1", "p1", "2026-11-05T12:00", 1));
        Assert.assertEquals(2, dm.planesDeDron("d1").size());
    }

    @Test
    public void testListadosPorHoras() throws Exception {
        dm.crearPlanVuelo(new PlanVuelo("d2", "p2", "2026-11-05T10:00", 5));
        dm.crearPlanVuelo(new PlanVuelo("d1", "p1", "2026-11-05T10:00", 1));

        List<Dron> ds = dm.dronesPorHorasVuelo();
        Assert.assertEquals("d2", ds.get(0).getId());
        List<Piloto> ps = dm.pilotosPorHorasVuelo();
        Assert.assertEquals("p2", ps.get(0).getId());
    }

    @Test(expected = DronException.class)
    public void testDronInexistente() throws Exception {
        dm.Reparar("noExiste");
    }
}
