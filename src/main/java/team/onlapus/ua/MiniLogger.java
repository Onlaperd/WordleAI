package team.onlapus.ua;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static team.onlapus.ua.Data.LOGS_PATH;


public class MiniLogger {

    private static int loggerCount = 1;
    private final String filepath;

    public MiniLogger(){
        this.filepath = LOGS_PATH + getFormattedTimeForLogs() + "-" + loggerCount + ".log";
        loggerCount++;
    }

    public void log(String message){ displayAndSave(message, "INFO"); }
    public void warn(String message){ displayAndSave(message, "WARN"); }
    public void error(String message){ displayAndSave(message, "ERROR"); }

    // Formats message, log it into console and file. Message example: "[INFO] [2010-08-21:10:37:00] Onlapus spawned"
    private void displayAndSave(String message, String messageType){
        String formatedMessage = "[" + messageType + "] " + "[" + getFormattedTime() + "] " + message;
        System.out.println(formatedMessage);

        new Thread(() -> {
            try (FileWriter writer = new FileWriter(filepath, true)) {
                writer.write(formatedMessage + System.lineSeparator());
            } catch (IOException e) {
                System.out.println("[ERROR] Couldn't access the log file: " + e.getMessage());
            }
        }).start();

    }

    public static String getFormattedTimeForLogs() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
        return LocalDateTime.now().format(formatter);
    }
    public static String getFormattedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:hh:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

}
