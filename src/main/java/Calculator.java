import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calculator")
public class Calculator extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
int i = 0;
        System.out.println(i);
        //        resp.getWriter().write(RESPONSE_TEMPLATE.formatted(NAME_CITY, shapeResponse(ZONE_ID, DATE_FORMAT)));
    }
}
