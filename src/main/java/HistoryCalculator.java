import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.tms.lesson26.onl30.other.Historian.readHistoryCalculation;
import static by.tms.lesson26.onl30.other.KeeperConstants.*;
import static by.tms.lesson26.onl30.other.MyLogger.logIn;

@WebServlet("/history_calculator")
public class HistoryCalculator extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if (IS_PERFORM_LOGGING) logIn(MESSAGE_BEGINNING_WORK_TEMPLATE.formatted("HistoryCalculator"));
        String[] historyArray = readHistoryCalculation().split("\n");
        int i = 0;
//        Response response = prepareResponseHistory(historyArray);
//        exchangeAll(exchange, response)
        if (IS_PERFORM_LOGGING) logIn(MESSAGE_ENDING_WORK_TEMPLATE.formatted("HistoryCalculator"));
    }
}
