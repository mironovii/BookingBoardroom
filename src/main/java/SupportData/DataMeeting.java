package SupportData;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class DataMeeting implements Comparable<DataMeeting> {

    private LocalTime openOffice;
    private LocalTime closeOffice;
    private LocalDateTime requestSubmissionTime;
    private String employeeID;
    private LocalDateTime meetingStartTime;
    private LocalDateTime meetingEndTime;

    public LocalTime getOpenOffice() {
        return openOffice;
    }

    public void setOpenOffice(LocalTime openOffice) {
        this.openOffice = openOffice;
    }

    public LocalTime getCloseOffice() {
        return closeOffice;
    }

    public void setCloseOffice(LocalTime closeOffice) {
        this.closeOffice = closeOffice;
    }

    public LocalDateTime getRequestSubmissionTime() {
        return requestSubmissionTime;
    }

    public void setRequestSubmissionTime(LocalDateTime requestSubmissionTime) {
        this.requestSubmissionTime = requestSubmissionTime;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public LocalDateTime getMeetingStartTime() {
        return meetingStartTime;
    }

    public void setMeetingStartTime(LocalDateTime meetingStartTime) {
        this.meetingStartTime = meetingStartTime;
    }

    public LocalDateTime getMeetingEndTime() {
        return meetingEndTime;
    }

    public void setMeetingEndTime(LocalDateTime meetingEndTime) {
        this.meetingEndTime = meetingEndTime;
    }

    @Override
    public String toString() {
        return "DataMeeting{" +
                "openOffice=" + openOffice +
                ", closeOffice=" + closeOffice +
                ", requestSubmissionTime=" + requestSubmissionTime +
                ", employeeID='" + employeeID + '\'' +
                ", meetingStartTime=" + meetingStartTime +
                ", meetingEndTime=" + meetingEndTime +
                '}';
    }

    @Override
    public int compareTo(DataMeeting o) {
        if (this.getRequestSubmissionTime().isBefore(o.getRequestSubmissionTime())) return -1;
        else if (this.getRequestSubmissionTime().isEqual(o.getRequestSubmissionTime())) return 0;
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataMeeting that = (DataMeeting) o;
        return openOffice.equals(that.openOffice) &&
                closeOffice.equals(that.closeOffice) &&
                requestSubmissionTime.equals(that.requestSubmissionTime) &&
                employeeID.equals(that.employeeID) &&
                meetingStartTime.equals(that.meetingStartTime) &&
                meetingEndTime.equals(that.meetingEndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(openOffice, closeOffice, requestSubmissionTime, employeeID, meetingStartTime, meetingEndTime);
    }
}
