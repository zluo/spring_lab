package lab.zluo.servlet3;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 */
@WebServlet(urlPatterns = {"/async"}, asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    private static final Queue queue = new ConcurrentLinkedQueue();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final AsyncContext ac = request.startAsync();
        ac.setTimeout(1*60*1000);
        ac.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                System.out.println("onComplete");
            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                System.out.println("onTimeout");
            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {
                System.out.println("onError");
            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
                System.out.println("onStartAsync");
            }

        });
        ac.start(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Run Async");
                    Thread.sleep(5000);
                    System.out.println("Finish Run Async");
                    ac.getRequest().setAttribute("res", "Response: " + System.currentTimeMillis());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //if (request.getAttribute("res") != null){
        //    System.out.println(request.getAttribute("res"));
         //   response.getWriter().println("Attribute: " + request.getAttribute("res"));
        //}else{
            System.out.println("Write response");
            response.getWriter().println("Start Async Servlet");
            response.flushBuffer();
            System.out.println("Leave Servlet");
        //}
    }
}
