package DParser;

import SupportData.DataMeeting;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Set;

public class JsonParser implements DParser<DataMeeting> {

    private Properties props = new Properties();

    {
        try {
            props.load(this.getClass().getResourceAsStream("/metadata.properties"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void reader(Set<DataMeeting> set) {

        try {
            JSONObject JsonObject1 = (JSONObject) JSONValue.parseWithException(readJsonFromFile());

            int size = Integer.valueOf(JsonObject1.get("size").toString());
            String workTime = JsonObject1.get("workTime").toString();

            for (int i = 1; i <= size; i++) {
                JSONObject JsonObject2 = (JSONObject) JSONValue.parseWithException(JsonObject1.get(String.valueOf(i)).toString());
                DataMeeting dataMeeting = new DataMeeting();

                DateTimeFormatter f_reqSubTime = DateTimeFormatter.ofPattern(props.getProperty("reqSubTimePattern"));
                DateTimeFormatter f_workTime = DateTimeFormatter.ofPattern(props.getProperty("workTimePattern"));
                DateTimeFormatter f_meetStartTime = DateTimeFormatter.ofPattern(props.getProperty("meetStartTimePattern"));

                dataMeeting.setOpenOffice(LocalTime.parse(workTime.split(" ")[0], f_workTime));
                dataMeeting.setCloseOffice(LocalTime.parse(workTime.split(" ")[1], f_workTime));

                dataMeeting.setRequestSubmissionTime(LocalDateTime.parse(JsonObject2.get("requestSubmissionTime").toString(), f_reqSubTime));
                dataMeeting.setEmployeeID(JsonObject2.get("employeeID").toString());
                dataMeeting.setMeetingStartTime(LocalDateTime.parse(JsonObject2.get("meetingStartTime").toString(), f_meetStartTime));
                dataMeeting.setMeetingEndTime(LocalDateTime.parse(JsonObject2.get("meetingStartTime").toString(), f_meetStartTime)
                        .plusHours(Long.valueOf(JsonObject2.get("duration").toString())));

                set.add(dataMeeting);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void writer(Set<DataMeeting> set) {
        JSONObject jsonObject = new JSONObject();
        int count = 0;
        for (DataMeeting dm : set) {
            JSONObject tempJsonObject = new JSONObject();
            tempJsonObject.put("meetingStartDate", dm.getMeetingStartTime().toLocalDate()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            tempJsonObject.put("meetingStartTime",
                    dm.getMeetingStartTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")) +
                    dm.getMeetingEndTime().toLocalTime().format(DateTimeFormatter.ofPattern(" HH:mm")));
            tempJsonObject.put("employeeID", dm.getEmployeeID());

            jsonObject.put(count++, tempJsonObject);
        }
        writeJsonToFile(jsonObject.toString());
    }

    private String readJsonFromFile() {
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader in = new BufferedReader(new FileReader(props.getProperty("inputJsonFile")))) {
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private void writeJsonToFile(String jsonObject) {
        try (FileWriter file = new FileWriter("out.json")) {
            file.write(jsonObject);
            file.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
