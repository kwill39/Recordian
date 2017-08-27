package databasemanagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public final class LogFileHelper {

    public static String logFilePath = "Hours_Worked.txt";

    private LogFileHelper(){}

    /**
     * Reads in the text from the log file
     *
     * @return String representing the text in the log file
     */
    public static String getLogFileText() {
        File logFile = new File(logFilePath);
        if (!logFile.exists()) {
            return "";
        }
        try (
                FileInputStream fileInputStream = new FileInputStream(logFilePath);
                Scanner scanner = new Scanner(fileInputStream)
        ) {
            StringBuilder logFileText = new StringBuilder();
            while (scanner.hasNextLine()) {
                logFileText.append(scanner.nextLine());
                // Add the newline which scanner removed
                logFileText.append(System.lineSeparator());
            }
            return logFileText.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Replaces the current log file text with <code>newLogFileText</code>
     *
     * @param newLogFileText the string that will replace the current log file text
     */
    public static void setLogFileText(String newLogFileText) {
        try (FileWriter fileWriter = new FileWriter(LogFileHelper.logFilePath)) {
            File logFile = new File(LogFileHelper.logFilePath);
            // Creates a new log file if one does not exist
            logFile.createNewFile();
            fileWriter.write(newLogFileText);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
