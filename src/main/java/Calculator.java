import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.tms.lesson26.onl30.other.Historian.writeHistoryCalculation;
import static by.tms.lesson26.onl30.other.KeeperConstants.*;
import static by.tms.lesson26.onl30.other.MyLogger.logIn;

@WebServlet("/calculator")      //http://localhost:8080/calculator?num1=7&num2=5&type=sum
public class Calculator extends HttpServlet {
    private static final String SERVLET_NAME = "Calculator";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (IS_PERFORM_LOGGING) logIn(MESSAGE_BEGINNING_WORK_TEMPLATE.formatted("Calculator"));
        String firstOperand = req.getParameter("num1");
        String secondOperand = req.getParameter("num2");
        String typeOperation = req.getParameter("type");
        if (isNotNumber(firstOperand) || isNotNumber(secondOperand)) {
            resp.getWriter().write((ERROR_TEMPLATE.formatted(ERROR_ON_OPERAND_TEMPLATE)));
            return;
        }
        String result = calculate(Double.valueOf(firstOperand), Double.valueOf(secondOperand), typeOperation);
        resp.getWriter().write(RESPONSE_TEMPLATE.formatted(result));
        if (!result.equals(INVALID_OPERATION)) {
            writeHistoryCalculation(firstOperand, secondOperand, typeOperation);
        } else {
            resp.getWriter().write((ERROR_TEMPLATE.formatted(ERROR_ON_TYPE_OPERATION_TEMPLATE)));
            return;
        }

        if (IS_PERFORM_LOGGING) logIn(MESSAGE_ENDING_WORK_TEMPLATE.formatted(SERVLET_NAME));
    }

    private String calculate(double firstOperand, double secondOperand, String typeOperation) {
        return switch (typeOperation) {
            case "sum" -> String.valueOf(firstOperand + secondOperand);
            case "diff" -> String.valueOf(firstOperand - secondOperand);
            case "mul" -> String.valueOf(firstOperand * secondOperand);
            case "div" -> String.valueOf(firstOperand / secondOperand);
            case "prc" -> String.valueOf(firstOperand * secondOperand / 100);
            default -> INVALID_OPERATION;
        };
    }

    private static boolean isNotNumber(String stringAsNumber) {
        try {
            Integer.valueOf(stringAsNumber);
        } catch (Exception e) {
            return true;
        }
        return false;
    }
}