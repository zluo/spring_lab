package lab.zluo.servlet3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(asyncSupported = true, name = "MyServlet", urlPatterns ={"/test"}, initParams = {@WebInitParam(name="webInitParam1", value="Hello"), @WebInitParam(name="webInitParam2", value="world")})
public class MyServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html><head><title>MyServlet</title></head><body>");
            out.write(getServletConfig().getInitParameter("webInitParam1") + " ");
            out.write(getServletConfig().getInitParameter("webInitParam2"));
            out.println("</body>");
            out.println("</html>");
        } finally {

            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
