package resources;
import entities.Logement;
import metiers.LogementBusiness;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("logements")
public class LogementRessources {
    public static LogementBusiness logb = new LogementBusiness();

    // 1) Ajouter un logement
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public Response ajouterLogement(Logement logement) {
        if (logb.addLogement(logement)) {
            return Response.status(Status.CREATED).build(); // 201 Created
        }
        return Response.status(Status.NOT_FOUND).build(); // 400 Bad Request
    }

    // 2) Récupérer tous les logements


    // 3) Récupérer des logements par délégation
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogementsByDelegation(@QueryParam(value="delegation") String delegation,@QueryParam(value = "refrence")String ref) {
        List liste = new ArrayList<Logement>() ;

        if (delegation == null && ref == null) {
            liste = logb.getLogements();// 400 Bad Request
        }

        if (delegation != null && ref == null) {
            liste = logb.getLogementsByDeleguation(delegation);
        }

        if (delegation == null && ref != null) {
            liste = logb.getLogementsByDeleguation(delegation);
        }


        if (liste.size()!=0)
            return Response.status(Status.OK).entity(liste).build(); // 200 OK
        return Response.status(Status.NO_CONTENT).build(); // 204 No Content

    }

    // 4) Supprimer un logement par identifiant
    @DELETE
    @Path("/{id}")
    //@Produces(MediaType.APPLICATION_JSON)
    public Response supprimerLogement(@PathParam("id") int id) {
        boolean deleted = logb.deleteLogement(id);

        if (deleted) {
            return Response.status(Status.OK).build(); // 200 No Content
        }

        return Response.status(Status.NOT_FOUND).build(); // 404 Not Found si l'identifiant n'existe pas
    }

    // 5) Mettre à jour un logement
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces("text/plain")
    public Response modiferLogement(@PathParam("id") int id, Logement logement) {
        boolean updated = logb.updateLogement(id, logement);

        if (updated) {
            return Response.status(Response.Status.OK).entity("sucess").build(); // Retourne le logement mis à jour
        }

        return Response.status(Response.Status.NOT_FOUND).entity("echec").build(); // 404 si l'identifiant n'existe pas
    }

    // 6) Récupérer un logement par référence
    @GET
    @Path("/reference/{ref}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogementByReference(@PathParam("ref") int reference) {
        Optional<Logement> logement = Optional.ofNullable(logb.getLogementsByReference(reference));

        if (logement.isPresent()) {
            return Response.status(Status.OK).entity(logement.get()).build(); // 200 OK, retourne le logement
        }

        return Response.status(Status.NOT_FOUND).build(); // 404 si la référence n'existe pas
    }
}
