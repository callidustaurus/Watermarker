package de.stma.model;

import java.util.Objects;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.json.JSONException;
import org.json.JSONObject;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, isGetterVisibility = Visibility.NONE)
public class Book extends Document {
    private final Topic topic;

    public Book(String title, String author, Topic topic) {
        super(title, author);
        if (topic == null) {
            throw new IllegalArgumentException("The topic must not be null!");
        }
        this.topic = topic;
    }

    @Override
    public void createWatermark() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("content", "book");
            jo.put("title", title);
            jo.put("author", author);
            jo.put("topic", topic);
            watermark = jo.toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Watermark finished for Book " + title + " from " + author + " ( " + watermark + " ) ");
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null
                && obj.getClass().equals(Book.class)
                && Objects.equals(title, ((Document) obj).title)
                && Objects.equals(author, ((Document) obj).author);
    }
}
