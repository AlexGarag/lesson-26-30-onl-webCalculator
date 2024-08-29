import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static by.tms.lesson26.onl30.other.KeeperConstants.*;
import static by.tms.lesson26.onl30.other.MyLogger.logIn;

@WebServlet("/calculator")      //http://localhost:8080/calculator?num1=7&num2=5&type=sum
public class Calculator extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (IS_PERFORM_LOGGING) logIn(MESSAGE_BEGINNING_WORK_TEMPLATE.formatted("Calculator"));
        String firstOperand = req.getParameter("num1");
        String secondOperand = req.getParameter("num2");
        String typeOperation = req.getParameter("type");
        String result = calculate(Double.valueOf(firstOperand), Double.valueOf(secondOperand), typeOperation);
        resp.getWriter().write(RESPONSE_TEMPLATE.formatted(result));
        if (!result.equals(INVALID_OPERATION)) {
            writeHistoryCalculation(firstOperand, secondOperand, typeOperation);
        }
        if (IS_PERFORM_LOGGING) logIn(MESSAGE_ENDING_WORK_TEMPLATE.formatted("Calculator"));
    }

    private String calculate(double num1, double num2, String typeOperation) {
        return  switch (typeOperation) {
            case "sum" -> String.valueOf(num1 + num2);
            case "diff" -> String.valueOf(num1 - num2);
            case "mul" -> String.valueOf(num1 * num2);
            case "div" -> String.valueOf(num1 / num2);
            case "prc" -> String.valueOf(num1 * num2 / 100);
            default -> INVALID_OPERATION;
        };
    }

    private void writeHistoryCalculation(String firstOperand, String secondOperand, String typeOperation) {
        ZonedDateTime dateTime = ZonedDateTime.now(ZoneOffset.UTC);
        String lineFileCsv = String.format(CSV_FORMAT_TEMPLATE, dateTime.toInstant().toEpochMilli(),
                firstOperand, secondOperand, typeOperation);
        try {
// todo сделать поток

                Files.write(Paths.get(CSV_NAME_FILE), lineFileCsv.getBytes(), StandardOpenOption.APPEND);

        } catch (IOException ex) {
            logIn(ERROR_TEMPLATE.formatted(ERROR_WRITE_FILE_TEMPLATE.formatted(CSV_NAME_FILE)));
        }
    }
}
