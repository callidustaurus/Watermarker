package de.stma;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import de.stma.model.Book;
import de.stma.model.Document;
import de.stma.model.Journal;
import de.stma.model.Topic;

public class DocumentFactoryTest {

    private DocumentFactory factory;

    @Before
    public void initFactory() {
        factory = new DocumentFactory();
    }

    @Test
    public void factoryReturnsCorrectImplementationOfDocument() {
        String bookAsJson = getJsonForDocument("myTitle", "myAuthor", Topic.Science);
        Document book = factory.getDocument(bookAsJson);
        assertEquals(Book.class, book.getClass());

        String journalAsJson = getJsonForDocument("myTitle", "myAuthor");
        Document journal = factory.getDocument(journalAsJson);
        assertEquals(Journal.class, journal.getClass());
    }

    @Test
    public void factoryReturnsNullIfJsonIsNotCorrect() {
        assertNull(factory.getDocument("incorrect json"));
    }

    // ****************************************************************************
    // ****************************************************************************
    // ****************************************************************************

    private String getJsonForDocument(String title, String author) {
        return getJsonForDocument(title, author, null);
    }

    private String getJsonForDocument(String title, String author, Topic topic) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("title", title);
            jo.put("author", author);
            if (topic != null) {
                jo.put("topic", topic);
            }
            return jo.toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
