package de.stma;

import org.json.JSONException;
import org.json.JSONObject;

import de.stma.model.Book;
import de.stma.model.Document;
import de.stma.model.Journal;
import de.stma.model.Topic;

public class DocumentFactory {

    /**
     * Creates a {@link de.stma.model.Document} object from JSON. It will either creates a {@link de.stma.model.Book} or a {@link de.stma.model.Journal},
     * depending on values within JSON
     *
     * @param json
     *            the JSON-string which is used to generate the document-object.
     * @return {@link de.stma.model.Book} if JSON contains value "topic" otherwise {@link de.stma.model.Journal}. It will return {@code null} if
     *         {@link org.json.JSONException} is thrown during parsing.
     */
    public Document getDocument(String json) {
        Document document = null;
        JSONObject jo = null;
        try {
            jo = new JSONObject(json);
            if (jo.has("topic")) {
                document = new Book(
                        (String) jo.get("title"),
                        (String) jo.get("author"),
                        Topic.valueOf((String) jo.get("topic")));
            } else {
                document = new Journal(
                        (String) jo.get("title"),
                        jo.getString("author"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return document;
    }
}
