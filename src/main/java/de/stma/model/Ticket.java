package de.stma.model;

import java.util.Objects;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Ticket {
    private final int ticketNumber;

    public Ticket(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ticketNumber;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null
                && obj.getClass().equals(Ticket.class)
                && Objects.equals(ticketNumber, ((Ticket) obj).ticketNumber);
    }

}
