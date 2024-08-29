import by.tms.lesson26.onl30.other.Historian;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


import static by.tms.lesson26.onl30.other.Historian.readHistoryCalculation;
import static by.tms.lesson26.onl30.other.KeeperConstants.*;
import static by.tms.lesson26.onl30.other.MyLogger.logIn;

@WebServlet("/history_calculator")
public class HistoryCalculator extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (IS_PERFORM_LOGGING) logIn(MESSAGE_BEGINNING_WORK_TEMPLATE.formatted("HistoryCalculator"));
        String[] historyArray = readHistoryCalculation().split("\n");
        resp.getWriter().write(prepareResponseHistory(historyArray));
        if (IS_PERFORM_LOGGING) logIn(MESSAGE_ENDING_WORK_TEMPLATE.formatted("HistoryCalculator"));
    }

   private String prepareResponseHistory(String[] historyArray) {
        StringBuilder resultString =  new StringBuilder();
        for (String line : historyArray) {
            String[] lineArray = line.split(";");
            resultString.append(convertSecondsToStringDateTime(Long.valueOf(lineArray[0]))).append("\t---\t");
            double firstOperand = Double.valueOf(lineArray[1]);
            double secondOperand = Double.valueOf(lineArray[2]);
            switch (lineArray[3]) {
                case "sum" -> resultString.append(myDoubleString(firstOperand)).append("+").append(myDoubleString(secondOperand)).append("=")
                        .append(myDoubleString(firstOperand + secondOperand)).append("\n");
                case "diff" -> resultString.append(myDoubleString(firstOperand)).append("-").append(myDoubleString(secondOperand)).append("=")
                        .append(myDoubleString(firstOperand - secondOperand)).append("\n");
                case "mul" -> resultString.append(myDoubleString(firstOperand)).append("*").append(myDoubleString(secondOperand)).append("=")
                        .append(myDoubleString(firstOperand * secondOperand)).append("\n");
                case "div" -> resultString.append(myDoubleString(firstOperand)).append("/").append(myDoubleString(secondOperand)).append("=")
                        .append(myDoubleString(firstOperand / secondOperand)).append("\n");
                case "prc" -> resultString.append(myDoubleString(firstOperand)).append("%").append(myDoubleString(secondOperand)).append("=")
                        .append(myDoubleString(firstOperand * secondOperand / 100)).append("\n");
                default -> resultString.append(""); // незнакомая операция пропускается в выдаче
            };
        }
        return resultString.toString();
    }

    String convertSecondsToStringDateTime(long quantitySeconds){
        Date dateLong = new Date(quantitySeconds);
        Instant instant = dateLong.toInstant();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(defaultZoneId).toLocalDateTime();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_LOGGER_TEMPLATE);
        return localDateTime.format(dateTimeFormatter);
    }

    String myDoubleString(double d){
        return String.format(DOUBLE_2_STRING,d);
    }
}
