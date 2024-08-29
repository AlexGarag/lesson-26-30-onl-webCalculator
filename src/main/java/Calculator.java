import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.tms.lesson26.onl30.other.KeeperConstants.*;

@WebServlet("/calculator")      //http://localhost:8080/calculator?num1=7&num2=5&type=sum
public class Calculator extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (IS_PERFORM_LOGGING) {
            System.out.println(MESSAGE_BEGINNING_WORK_TEMPLATE.formatted("'Calculator'"));
        }
        String operand1 = req.getParameter("num1");
        String operand2 = req.getParameter("num2");
        String typeOperation = req.getParameter("type");

        double num1 = Double.valueOf(operand1);
        double num2 = Double.valueOf(operand2);
        String result = calculate(num1, num2, typeOperation);

        resp.getWriter().write(RESPONSE_TEMPLATE.formatted(result));
// todo сделать историю операций
int i = 0;
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
}
