package de.stma.model;

import java.util.Objects;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, isGetterVisibility = Visibility.NONE)
public abstract class Document {
    protected final String author;
    protected final String title;
    protected String watermark;

    public Document(String title, String author) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("The title must not be null or emtpy!");
        }
        if (author == null || author.isEmpty()) {
            throw new IllegalArgumentException("The author must not be null or emtpy!");
        }

        this.title = title;
        this.author = author;
    }

    /**
     * Creates a watermark for the document.
     */
    public abstract void createWatermark();

    public boolean isWatermarked() {
        return watermark != null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author);
    }

}
