package databasemanagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author  Kyle Williams
 * @since   Version 3
 */
public final class LogFileHelper {

    public static String logFilePath = "Hours_Worked.txt";

    private LogFileHelper(){}

    /**
     * Reads in the text from the log file,
     * and returns it as a string
     *
     * @return String representing the text in the log file
     */
    public static String getLogFileText() {

        createLogFileIfNotExists();

        try (
                FileInputStream fileInputStream = new FileInputStream(logFilePath);
                Scanner scanner = new Scanner(fileInputStream)
        ) {
            StringBuilder logFileText = new StringBuilder();
            while (scanner.hasNextLine()) {
                logFileText.append(scanner.nextLine());
                if (scanner.hasNextLine()){
                    // Add the newline which scanner removed
                    logFileText.append(System.lineSeparator());
                }
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
        createLogFileIfNotExists();
        try (FileWriter fileWriter = new FileWriter(LogFileHelper.logFilePath)) {
            fileWriter.write(newLogFileText);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks to see if the log file exists
     *
     * @return true if the log file exists or false if it does not
     */
    public static boolean logFileExists() {
        File logFile = new File(LogFileHelper.logFilePath);
        return (logFile.exists() && !logFile.isDirectory());
    }

    /**
     * Creates a new log file if, and only if,
     * one does not currently exist
     */
    private static void createLogFileIfNotExists() {
        try {
            File logFile = new File(LogFileHelper.logFilePath);
            logFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
