package de.stma;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.spi.resource.Singleton;

import de.stma.model.Document;
import de.stma.model.Status;
import de.stma.model.Ticket;

@Singleton
@Path("/Watermarker")
@Produces(MediaType.APPLICATION_JSON)
public class Watermarker {
    private Integer ticketCounter = new Integer(0);
    private Map<Integer, Document> ticketMatcher = new HashMap<>();
    private DocumentFactory documentFactory = new DocumentFactory();

    /**
     * Returns the status of watermarking-process.
     *
     * @param ticketNumber
     *            determines the corresponding {@link de.stma.model.Ticket} for the {@link de.stma.model.Document}.
     * @return {@link de.stma.model.Status}.
     */
    @GET
    @Path("ticketStatus/{ticketNumber}")
    public Status getStatus(@PathParam("ticketNumber") int ticketNumber) {
        System.out.println("Polling status with ticketnumber " + ticketNumber);
        Document document = ticketMatcher.get(ticketNumber);
        Status status;
        if (document == null) {
            status = Status.NoTicket;
        } else if (document.isWatermarked()) {
            status = Status.Finished;
        } else {
            status = Status.InProgress;
        }

        return status;
    }

    /**
     * Returns a {@link de.stma.model.Document} if there is a corresponding ticket.
     *
     * @param ticketNumber
     * @return A document for the given ticketnumber. If there is no corresponding ticketnumber or the document is not watermarked yet {@code null} is returned
     */
    @GET
    @Path("retrieve/{ticketNumber}")
    public Document getDocument(@PathParam("ticketNumber") int ticketNumber) {
        System.out.println("Retrieving document with ticketnumber " + ticketNumber);
        Document document = ticketMatcher.get(ticketNumber);
        if (document == null || !document.isWatermarked()) {
            return null;
        }
        return document;
    }

    /**
     * Starts watermarking for a given document
     *
     * @param document
     *            the document that should be watermarked
     * @return a ticketnumber which could be used to poll the watermarking-status or to retriev the watermarked document. Return -1 in case of exception
     */
    @GET
    @Path("/watermark")
    public synchronized Ticket watermarkDocument(@QueryParam("document") String documentJSON) {
        System.out.println("Request for watermarking a document ( " + documentJSON + " )");
        Document document = documentFactory.getDocument(documentJSON);
        if (document == null) {
            return new Ticket(-1);
        }

        document.createWatermark();
        ticketCounter++;
        ticketMatcher.put(ticketCounter, document);
        return new Ticket(ticketCounter);
    }

}
