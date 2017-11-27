package lab.zluo.servlet3;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.xml.ws.WebFault;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@WebFilter(urlPatterns = {"/test"}, initParams = {@WebInitParam(name="simpleParam", value="paramValue")} , asyncSupported = true)
public class TestFilter implements Filter {
    FilterConfig filterConfig;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.getWriter().print("Filter request!");
        servletResponse.getWriter().println("======================");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
