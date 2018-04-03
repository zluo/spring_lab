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
 * This example demonstrate how to async servlet works.
 * 1. Create a AsyncContext from request.
 * 2. Add a AsyncListener to AsyncContext
 * 3.
 */
@WebServlet(urlPatterns = {"/async"}, asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    private static final Queue queue = new ConcurrentLinkedQueue();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("---------------------- Async Servlet Diagram ---------------------------");
        System.out.println("Client->AsyncServlet:doGet(Request, Response)");
        System.out.println("Request->*AsyncContext:startAsync()");

        final AsyncContext ac = request.startAsync();
        ac.setTimeout(1*60*1000);
        ac.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                //System.out.println("-> onComplete");
            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                //System.out.println("-> onTimeout");
            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {
                //System.out.println("-> onError");
            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
                //System.out.println("-> onStartAsync");
            }

        });

        System.out.println("AsyncContext->New Thread:start()");
        ac.start(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("New Thread->+Job:doJob()");
                    Thread.sleep(5000);
                    System.out.println("Job->New Thread");

                    ac.getRequest().setAttribute("res", "Response: " + System.currentTimeMillis());
                    ServletResponse resp =ac.getResponse();
                    resp.getWriter().println("Async Response Content");
                    resp.flushBuffer();
                    System.out.println("AsyncServlet->Client:Async Response");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
            System.out.println("note handle request: return doGet()");
    }
}
