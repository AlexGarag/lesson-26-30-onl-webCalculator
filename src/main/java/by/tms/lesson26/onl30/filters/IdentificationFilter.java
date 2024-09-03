package by.tms.lesson26.onl30.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.tms.lesson26.onl30.other.FileProcessor.readFile;
import static by.tms.lesson26.onl30.other.KeeperConstants.*;
import static by.tms.lesson26.onl30.other.KeeperConstants.LF;

@WebFilter(servletNames = {"Calculator"})
public class IdentificationFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (identifyUser(login, password)) {
            chain.doFilter(req, res);
        } else {
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private boolean identifyUser(String login, String password) {
        String[] idArray = readFile(LOGIN_PASSWORD_STORAGE).split(LF);
        for (String line : idArray) {
            String[] lineArray = line.split(SEPARATOR);
            if (Integer.valueOf(lineArray[0]) == login.hashCode()
                    && Integer.valueOf(lineArray[1]) == password.hashCode()) {
                return true;
            }
        }
        return false;
    }
}
