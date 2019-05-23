package Logic;

import DParser.JsonParser;
import SupportData.DataMeeting;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.TreeSet;

public class BookingBoardroom {

    private static JsonParser jsonParser = new JsonParser();

    //Sorted by createDate
    private static Set<DataMeeting> inputRequests = new TreeSet<>();

    //Sort DataMeeting by StartOfMeetingTime
    private static Set<DataMeeting> bookingTable = new TreeSet<>((DataMeeting o1, DataMeeting o2) -> {
        if (o1.getMeetingStartTime().isBefore(o2.getMeetingStartTime())) return -1;
        else if (o1.getMeetingStartTime().isEqual(o2.getMeetingStartTime()) &
                o1.getMeetingEndTime().isEqual(o2.getMeetingEndTime()) &
                o1.getRequestSubmissionTime().isEqual(o2.getRequestSubmissionTime())) return 0;
        return 1;
    });

    // For single requests
    public static void getCurrentBookingTable() {
        if (bookingTable.size() == 0) {
            System.out.println("BookingTable is empty. You can choose any date.");
        } else {
            jsonParser.writer(bookingTable);
        }
    }

    public static void BookingProcessing() {
        jsonParser.reader(inputRequests);

        if (inputRequests.size() == 0) {
            System.out.println("Input request is empty. Try again.");
        } else {
            for (DataMeeting dm : inputRequests) {
                if (isBookingInWorkTime(dm) & isBookingNotIntersect(dm)) {
                    bookingTable.add(dm);
                }
            }
            jsonParser.writer(bookingTable);
        }
    }

    private static boolean isBookingInWorkTime(DataMeeting dm) {

        LocalTime startMeeting = dm.getMeetingStartTime().toLocalTime();
        LocalTime endMeeting = dm.getMeetingEndTime().toLocalTime();
        LocalTime openOffice = dm.getOpenOffice();
        LocalTime closeOffice = dm.getCloseOffice();

        return ((startMeeting.isAfter(openOffice) || startMeeting.equals(openOffice)) &
                (startMeeting.isBefore(closeOffice) || startMeeting.equals(closeOffice)) &
                (endMeeting.isBefore(closeOffice) || endMeeting.equals(closeOffice)));
    }

    private static boolean isBookingNotIntersect(DataMeeting dm) {
        if (bookingTable.size() == 0) {
            return true;
        } else {
            int count = 0;
            for (DataMeeting temp : bookingTable) {
                if(count > 0) return false;
                LocalDateTime startCreatedMeeting = temp.getMeetingStartTime();
                LocalDateTime endCreatedMeeting = temp.getMeetingEndTime();
                LocalDateTime currentStartMeeting = dm.getMeetingStartTime();
                LocalDateTime currentEndMeeting = dm.getMeetingEndTime();

                if ((startCreatedMeeting.isAfter(currentStartMeeting) || startCreatedMeeting.isEqual(currentStartMeeting)) &
                        (startCreatedMeeting.isBefore(currentEndMeeting) || startCreatedMeeting.isEqual(currentEndMeeting)) &
                        (endCreatedMeeting.isBefore(currentEndMeeting) || endCreatedMeeting.isEqual(currentEndMeeting))) {
                    count++;
                }
            }
            return true;
        }
    }
}
