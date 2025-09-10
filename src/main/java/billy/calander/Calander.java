package billy.calander;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;

import billy.task.Events;

public class Calander {
    private TreeSet<Events> events;

    private static class eventComparator implements Comparator<Events> {
        public int compare(Events event1, Events event2) {
            if (event1.getEventStartTime().isBefore(event2.getEventStartTime())) {
                return -1;
            }
            return 1;
        }
    }

    public Calander() {
        this.events = new TreeSet<>(new eventComparator());
    }

    public boolean hasOverlap(Events event1, Events event2) {
        return (event1.getEventStartTime().isBefore(event2.getEventEndTime())
            && event2.getEventStartTime().isBefore(event1.getEventEndTime()));
    }

    public ArrayList<Events> add(Events event) {
        ArrayList<Events> overlappedEvents = new ArrayList<>();

        for (Events e : this.events) {
            if (hasOverlap(e, event)) {
                overlappedEvents.add(e);
            }
        }
        return overlappedEvents;
    }

    public LocalDateTime getEarliestFreeTime(int hours, LocalDateTime currentTime) {
        int index = 0;
        ArrayList<Events> filteredEvents = new ArrayList<>();

        for (Events e : this.events) {
            if (e.getEventEndTime().isAfter(currentTime)) {
                filteredEvents.add(e);
            }
        }

        if (filteredEvents.size() == 0) {
            return currentTime;
        }

        LocalDateTime earliestStartTime = filteredEvents.stream()
                .filter(e -> e.getEventStartTime().isBefore(currentTime))
                .max((e1, e2) -> e1.getEventEndTime().compareTo(e2.getEventEndTime()))
                .map(e -> e.getEventEndTime())
                .orElse(currentTime);

        ArrayList<Events> upcomingEvents = filteredEvents.stream()
                .filter(e -> e.getEventStartTime().isAfter(earliestStartTime)
                        || e.getEventStartTime().isEqual(earliestStartTime))
                .collect(Collectors.toCollection(ArrayList::new));

        if (upcomingEvents.size() == 0) {
            return earliestStartTime;
        }

        Events firstUpcomingEvent = upcomingEvents.get(0);
        LocalDateTime proposedEndTime = earliestStartTime.plusHours(hours);

        if (proposedEndTime.isBefore(firstUpcomingEvent.getEventStartTime()) ||
                proposedEndTime.equals(firstUpcomingEvent.getEventStartTime())) {
            return earliestStartTime;
        }

        for (int i = 0; i < upcomingEvents.size() - 1; i++) {
            Events currentEvent = upcomingEvents.get(i);
            Events nextEvent = upcomingEvents.get(i + 1);

            LocalDateTime gapStart = currentEvent.getEventEndTime();
            LocalDateTime gapEnd = nextEvent.getEventStartTime();
            LocalDateTime requiredEndTime = gapStart.plusHours(hours);

            // Check if the required duration fits in this gap
            if (requiredEndTime.isBefore(gapEnd) || requiredEndTime.equals(gapEnd)) {
                return gapStart;
            }
        }

        // If no gap found, return time after the last upcoming event
        Events lastEvent = upcomingEvents.get(upcomingEvents.size() - 1);
        return lastEvent.getEventEndTime();


    }
}
