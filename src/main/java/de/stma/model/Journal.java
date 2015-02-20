package de.stma.model;

import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Journal extends Document {

    public Journal(String title, String author) {
        super(title, author);
    }

    @Override
    public void createWatermark() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("content", "journal");
            jo.put("title", title);
            jo.put("author", author);
            watermark = jo.toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Watermark finished for Journal " + title + " from " + author + " ( " + watermark + " ) ");
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null
                && obj.getClass().equals(Journal.class)
                && Objects.equals(title, ((Document) obj).title)
                && Objects.equals(author, ((Document) obj).author);
    }
}
