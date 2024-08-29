package by.tms.lesson26.onl30.other;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static by.tms.lesson26.onl30.other.KeeperConstants.*;
import static by.tms.lesson26.onl30.other.MyLogger.logIn;

public class Historian {

    public static void writeHistoryCalculation(String firstOperand, String secondOperand, String typeOperation) {
        ZonedDateTime dateTime = ZonedDateTime.now(ZoneOffset.UTC);
        String lineFileCsv = String.format(CSV_FORMAT_TEMPLATE, dateTime.toInstant().toEpochMilli(),
                firstOperand, secondOperand, typeOperation);
        Thread writeFile = new Thread(() -> {
            try {
                Files.write(Paths.get(CSV_NAME_FILE), lineFileCsv.getBytes(), StandardOpenOption.APPEND);

            } catch (IOException ex) {
                logIn(ERROR_TEMPLATE.formatted(ERROR_IO_FILE_TEMPLATE.formatted(CSV_NAME_FILE)));
            }
        });
        writeFile.start();
    }

    public static String readHistoryCalculation() {
        String historyString = null;
        try {
            historyString = Files.readString(Paths.get(CSV_NAME_FILE));
        } catch (IOException ex) {
            logIn(ERROR_TEMPLATE.formatted(ERROR_IO_FILE_TEMPLATE.formatted(CSV_NAME_FILE)));
        }
        return historyString;
    }

//    public static String readHistoryCalculation(String[] historyArray) {
//        Response response = new Response();
//        StringBuilder resultString =  new StringBuilder();
//        for (String line : historyArray) {
//            String[] lineArray = line.split(";");
//            resultString.append(convertSecondsDateTimeString(Long.valueOf(lineArray[0]))).append("\t---\t");
//            double firstOperand = Double.valueOf(lineArray[1]);
//            double secondOperand = Double.valueOf(lineArray[2]);
//            switch (lineArray[3]) {
//                case "sum" -> resultString.append(myDoubleString(firstOperand)).append("+").append(myDoubleString(secondOperand)).append("=")
//                        .append(myDoubleString(firstOperand + secondOperand)).append("\n");
//                case "diff" -> resultString.append(myDoubleString(firstOperand)).append("-").append(myDoubleString(secondOperand)).append("=")
//                        .append(myDoubleString(firstOperand - secondOperand)).append("\n");
//                case "mul" -> resultString.append(myDoubleString(firstOperand)).append("*").append(myDoubleString(secondOperand)).append("=")
//                        .append(myDoubleString(firstOperand * secondOperand)).append("\n");
//                case "div" -> resultString.append(myDoubleString(firstOperand)).append("/").append(myDoubleString(secondOperand)).append("=")
//                        .append(myDoubleString(firstOperand / secondOperand)).append("\n");
//                case "prc" -> resultString.append(myDoubleString(firstOperand)).append("%").append(myDoubleString(secondOperand)).append("=")
//                        .append(myDoubleString(firstOperand * secondOperand / 100)).append("\n");
//            };
//        }
//        response.setBodyResponse(resultString.toString());
//        return ""; //response;
//    }
}
