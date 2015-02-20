package de.stma.model;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public enum Status {

    InProgress("Watermarking is still in progress"),
    Finished("Your document was watermarked and can be retrieved"),
    NoTicket("There is no ticket for your ticketnumber");

    private String message;

    private Status(String message) {
        this.message = message;
    }
}
