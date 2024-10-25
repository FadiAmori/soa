package resources;

import entities.RendezVous;
import metiers.RendezVousBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;
import java.util.Optional;

@Path("rendezvous")
public class RendezVousRessources {
    public static RendezVousBusiness rdvBusiness = new RendezVousBusiness();

    // 1) Ajouter un rendez-vous
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response ajouterRendezVous(RendezVous rendezVous) {
        if (rdvBusiness.addRendezVous(rendezVous)) {
            return Response.status(Status.CREATED).build(); // 201 Created
        }
        return Response.status(Status.BAD_REQUEST).build(); // 400 Bad Request
    }

    // 2) Récupérer tous les rendez-vous
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTousLesRendezVous() {
        List<RendezVous> liste = rdvBusiness.getListeRendezVous();

        if (!liste.isEmpty()) {
            return Response.status(Status.OK).entity(liste).build(); // 200 OK
        }
        return Response.status(Status.NO_CONTENT).build(); // 204 No Content
    }

    // 3) Récupérer des rendez-vous par référence de logement
    @GET
    @Path("/logement/{ref}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRendezVousByLogementReference(@PathParam("ref") int reference) {
        List<RendezVous> liste = rdvBusiness.getListeRendezVousByLogementReference(reference);

        if (!liste.isEmpty()) {
            return Response.status(Status.OK).entity(liste).build(); // 200 OK
        }
        return Response.status(Status.NO_CONTENT).build(); // 204 No Content
    }

    // 4) Supprimer un rendez-vous par identifiant
    @DELETE
    @Path("/{id}")
    public Response supprimerRendezVous(@PathParam("id") int id) {
        boolean deleted = rdvBusiness.deleteRendezVous(id);

        if (deleted) {
            return Response.status(Status.OK).build(); // 200 OK
        }
        return Response.status(Status.NOT_FOUND).build(); // 404 Not Found
    }

    // 5) Mettre à jour un rendez-vous
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_PLAIN)
    public Response modifierRendezVous(@PathParam("id") int id, RendezVous rendezVous) {
        boolean updated = rdvBusiness.updateRendezVous(id, rendezVous);

        if (updated) {
            return Response.status(Status.OK).entity("succès").build(); // 200 OK
        }
        return Response.status(Status.NOT_FOUND).entity("échec").build(); // 404 Not Found
    }

    // 6) Récupérer un rendez-vous par identifiant
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRendezVousById(@PathParam("id") int id) {
        RendezVous rendezVous = rdvBusiness.getRendezVousById(id);

        if (rendezVous != null) {
            return Response.status(Status.OK).entity(rendezVous).build(); // 200 OK
        }
        return Response.status(Status.NOT_FOUND).build(); // 404 Not Found
    }
}
