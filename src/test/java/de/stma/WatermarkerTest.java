package de.stma;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import de.stma.model.Book;
import de.stma.model.Document;
import de.stma.model.Journal;
import de.stma.model.Status;
import de.stma.model.Ticket;
import de.stma.model.Topic;

public class WatermarkerTest {
    Book book = new Book("bookTitle", "bookAuthor", Topic.Science);
    Journal journal = new Journal("journalTitle", "journalAuthor");

    Watermarker watermarker;

    @Before
    public void createWatermarker() {
        watermarker = new Watermarker();
    }

    @Test
    public void watermarkerResponseAUniqueTicketNumberWhenSendingDocument() {
        Ticket expectedTicket1 = new Ticket(1);
        Ticket expectedTicket2 = new Ticket(2);

        Ticket actualTicket1 = watermarker.watermarkDocument(createJsonForBook("myTitle", "myAuthor", Topic.Business));
        Ticket actualTicket2 = watermarker.watermarkDocument(createJsonForJournal("myTitle", "myAuthor"));

        assertEquals(expectedTicket1, actualTicket1);
        assertEquals(expectedTicket2, actualTicket2);
    }

    @Test
    public void itIsPossibleToWatermarkADocument() throws Exception {
        Document book = new Book("myTitle", "myAuthor", Topic.Media);

        watermarker.watermarkDocument(getJsonFromDocument(book));

        book = watermarker.getDocument(1);
        assertTrue(book.isWatermarked());

        Field field = Document.class.getDeclaredField("watermark");
        field.setAccessible(true);
        String watermark = (String) field.get(book);

        assertTrue(watermark.contains("myTitle"));
        assertTrue(watermark.contains("myAuthor"));
        assertTrue(watermark.contains("Media"));
    }

    @Test
    public void whenPollingForANotExistingTicketNoTicketStatusIsReturned() {
        assertEquals(Status.NoTicket, watermarker.getStatus(99999));
    }

    @Test
    public void whenPollingforAWatermarkedDocumentFinishStatusIsReturned() {
        String journal = createJsonForJournal("myTitle", "myAuthor");
        watermarker.watermarkDocument(journal);

        assertEquals(Status.Finished, watermarker.getStatus(1));
    }

    @Test
    public void itIsPossibleToGetADocumentWithATicketNumber() {
        Journal expectedDoc = new Journal("expectedTitle", "expectedAuthor");

        watermarker.watermarkDocument(getJsonFromDocument(expectedDoc));
        assertEquals(expectedDoc, watermarker.getDocument(1));
    }

    @Test
    public void retrievingADocumentWithNotExistingTicketNumberReturnsNull() {
        assertNull(watermarker.getDocument(999999));
    }

    // *******************************************************
    // *******************************************************
    // *******************************************************

    private String getJsonFromDocument(Document document) {
        if (document.getClass().isAssignableFrom(Book.class)) {
            return createJsonForBook(
                    (String) getPrivateField(document, Document.class, "title"),
                    (String) getPrivateField(document, Document.class, "author"),
                    (Topic) getPrivateField(document, Book.class, "topic"));
        } else {
            return createJsonForJournal(
                    (String) getPrivateField(document, Document.class, "title"),
                    (String) getPrivateField(document, Document.class, "author"));
        }
    }

    private String createJsonForBook(String title, String author, Topic topic) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("content", "book");
            jo.put("title", title);
            jo.put("author", author);
            jo.put("topic", topic);
            return jo.toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private String createJsonForJournal(String title, String author) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("content", "journal");
            jo.put("title", title);
            jo.put("author", author);
            return jo.toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object getPrivateField(Object instance, Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(instance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
