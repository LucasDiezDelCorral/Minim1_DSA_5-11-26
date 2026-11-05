package dsa.services;

import dsa.*;
import dsa.models.Dron;
import dsa.models.PlanVuelo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/flota", description = "Flota de drones")
@Path("/flota")
public class DronService {

    private DronManager dm;

    public DronService() {
        this.dm = DronManagerImpl.getInstance();
        if (dm.numDrones() == 0) {
            dm.addDron("d1", "Halcon", "DJI", "Mavic 3");
            dm.addDron("d2", "Aguila", "Parrot", "Anafi");
            dm.addDron("d67", "Buho", "DJI", "Mini 4");
            dm.addPiloto("p1", "Lucas", "Diez");
            dm.addPiloto("p2", "Marta", "Puig");
        }
    }

    @GET
    @Path("/drones/horas")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Drones ordenados por horas de vuelo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Dron.class, responseContainer = "List")
    })
    public Response dronesPorHoras() {
        List<Dron> list = dm.dronesPorHorasVuelo();
        GenericEntity<List<Dron>> entity = new GenericEntity<List<Dron>>(list) {};
        return Response.status(200).entity(entity).build();
    }

    @POST
    @Path("/planes")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Crear un plan de vuelo")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Plan creado"),
            @ApiResponse(code = 404, message = "Dron o piloto no existe"),
            @ApiResponse(code = 409, message = "Dron en el almacen u ocupado")
    })
    public Response crearPlan(PlanVuelo plan) {
        try {
            dm.crearPlanVuelo(plan);
            return Response.status(201).build();
        } catch (DronException e) {
            return Response.status(e.getCodigo()).build();
        }
    }

    @PUT
    @Path("/almacen/{idDron}")
    @ApiOperation(value = "Meter un dron en el almacen")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Dron en el almacen"),
            @ApiResponse(code = 404, message = "Dron no existe"),
            @ApiResponse(code = 409, message = "Ya esta en el almacen")
    })
    public Response guardarEnAlmacen(@PathParam("idDron") String idDron) {
        try {
            dm.Reparar(idDron);
            return Response.status(201).build();
        } catch (DronException e) {
            return Response.status(e.getCodigo()).build();
        }
    }

    @PUT
    @Path("/almacen/reparar")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Reparar el dron mas cerca de la puerta")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dron reparado", response = Dron.class),
            @ApiResponse(code = 404, message = "Almacen vacio")
    })
    public Response reparar() {
        Dron d = dm.repararDron();
        if (d == null) return Response.status(404).build();
        return Response.status(200).entity(d).build();
    }
}
