package Shared;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Concert implements Serializable {
    String venue;
    Calendar scheduledDay;
    List<Integer> groups;
    List<Integer> songs;

    public Concert(String venue, Calendar scheduledDay) {
        this.venue = venue;
        this.scheduledDay = scheduledDay;
    }

    public static Concert newConcert() {
        String venue = inputUtil.promptStr("Venue: ");
        Calendar scheduledDay = inputUtil.promptDate("Scheduled day: ", false);
        return new Concert(venue, scheduledDay);
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Calendar getScheduledDay() {
        return scheduledDay;
    }

    public void setScheduledDay(Calendar scheduledDay) {
        this.scheduledDay = scheduledDay;
    }

    public List<Integer> getGroups() {return this.groups;}

    public List<Integer> getSongs() {return this.songs;}
}
