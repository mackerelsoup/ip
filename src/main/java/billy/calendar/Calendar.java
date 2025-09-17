package billy.calendar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;

import billy.task.Events;

/**
 * Manages a calendar of events with functionality to detect overlaps and find free time slots.
 * Events are stored in a TreeSet ordered by their start time for efficient searching.
 */
public class Calendar {
    private TreeSet<Events> events;

    /**
     * Comparator for sorting events by their start time.
     */
    private static class EventComparator implements Comparator<Events> {
        /**
         * Compares two events by their start time.
         *
         * @param event1 the first event to compare
         * @param event2 the second event to compare
         * @return negative if event1 starts before event2, positive if after, 0 if equal
         */
        @Override
        public int compare(Events event1, Events event2) {
            if (event1.getEventStartTime().isBefore(event2.getEventStartTime())) {
                return -1;
            } else if (event1.getEventStartTime().isAfter(event2.getEventStartTime())) {
                return 1;
            }
            return 0;
        }
    }

    /**
     * Constructs a new Calendar with an empty set of events.
     */
    public Calendar() {
        this.events = new TreeSet<>(new EventComparator());
    }

    /**
     * Checks if two events have overlapping time periods.
     *
     * @param event1 the first event
     * @param event2 the second event
     * @return true if the events overlap, false otherwise
     */
    public boolean hasOverlap(Events event1, Events event2) {
        return (event1.getEventStartTime().isBefore(event2.getEventEndTime())
            && event2.getEventStartTime().isBefore(event1.getEventEndTime()));
    }

    /**
     * Adds an event to the calendar and returns any overlapping events.
     *
     * @param event the event to add
     * @return list of events that overlap with the added event
     */
    public ArrayList<Events> add(Events event) {
        ArrayList<Events> overlappedEvents = new ArrayList<>();

        for (Events e : this.events) {
            if (hasOverlap(e, event)) {
                overlappedEvents.add(e);
            }
        }
        this.events.add(event);
        return overlappedEvents;
    }

    private ArrayList<Events> getEventsAfterTime(LocalDateTime currentTime) {
        ArrayList<Events> filteredEvents = new ArrayList<>();
        for (Events e : this.events) {
            if (e.getEventEndTime().isAfter(currentTime)) {
                filteredEvents.add(e);
            }
        }
        return filteredEvents;
    }

    private LocalDateTime getSearchStartTime(LocalDateTime currentTime, ArrayList<Events> relevantEvents) {
        return relevantEvents.stream()
                .filter(e -> e.getEventStartTime().isBefore(currentTime) || e.getEventStartTime().isEqual(currentTime))
                .filter(e -> e.getEventEndTime().isAfter(currentTime))
                .map(Events::getEventEndTime)
                .max(LocalDateTime::compareTo)
                .orElse(currentTime);
    }

    private ArrayList<Events> getUpcomingEvents(LocalDateTime startTime, ArrayList<Events> relevantEvents) {
        return relevantEvents.stream()
                .filter(e -> e.getEventStartTime().isAfter(startTime) || e.getEventStartTime().isEqual(startTime))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private LocalDateTime findFirstAvailableSlot(LocalDateTime startTime, int duration, ArrayList<Events> upcomingEvents) {
        if (upcomingEvents.isEmpty()) {
            return startTime;
        }

        if (fitsBeforeFirstEvent(startTime, duration, upcomingEvents.get(0))) {
            return startTime;
        }

        LocalDateTime gapSlot = findSlotInGaps(duration, upcomingEvents);
        if (gapSlot != null) {
            return gapSlot;
        }

        return getTimeAfterLastEvent(upcomingEvents);
    }

    private boolean fitsBeforeFirstEvent(LocalDateTime startTime, int duration, Events firstEvent) {
        LocalDateTime proposedEndTime = startTime.plusHours(duration);
        return proposedEndTime.isBefore(firstEvent.getEventStartTime())
                || proposedEndTime.equals(firstEvent.getEventStartTime());
    }

    private LocalDateTime findSlotInGaps(int duration, ArrayList<Events> upcomingEvents) {
        for (int i = 0; i < upcomingEvents.size() - 1; i++) {
            Events currentEvent = upcomingEvents.get(i);
            Events nextEvent = upcomingEvents.get(i + 1);

            if (gapCanAccommodateDuration(currentEvent, nextEvent, duration)) {
                return currentEvent.getEventEndTime();
            }
        }
        return null;
    }

    private boolean gapCanAccommodateDuration(Events currentEvent, Events nextEvent, int duration) {
        LocalDateTime gapStart = currentEvent.getEventEndTime();
        LocalDateTime gapEnd = nextEvent.getEventStartTime();
        LocalDateTime requiredEndTime = gapStart.plusHours(duration);

        return requiredEndTime.isBefore(gapEnd) || requiredEndTime.equals(gapEnd);
    }

    private LocalDateTime getTimeAfterLastEvent(ArrayList<Events> upcomingEvents) {
        Events lastEvent = upcomingEvents.get(upcomingEvents.size() - 1);
        return lastEvent.getEventEndTime();
    }

    /**
     * Finds the earliest available time slot that can accommodate the specified duration.
     * Searches from the current time onwards for a gap between events that is large enough.
     *
     * @param currentTime the starting time to search from
     * @param duration the required duration in hours
     * @return the earliest available start time for the requested duration
     */
    public LocalDateTime getEarliestFreeTime(LocalDateTime currentTime, int duration) {
        ArrayList<Events> relevantEvents = getEventsAfterTime(currentTime);

        if (relevantEvents.isEmpty()) {
            return currentTime;
        }

        LocalDateTime searchStartTime = getSearchStartTime(currentTime, relevantEvents);
        ArrayList<Events> upcomingEvents = getUpcomingEvents(searchStartTime, relevantEvents);

        return findFirstAvailableSlot(searchStartTime, duration, upcomingEvents);

    }
}
