import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Test;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import static org.junit.gen5.api.Assertions.assertEquals;

/**
 * This tutorial will demonstrate how to use HttpClient
 */
class HttpClientTutorial {

    @Test
    @DisplayName("A test demonstration get http status code")
    public void testHttpSSL() throws IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true).build();

        CloseableHttpClient client = HttpClients.custom().setSSLContext(sslContext).setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        CloseableHttpResponse response = client.execute(new HttpGet("https://backendplatform1:8443/aws/rest/api-docs"));
        assertEquals(response.getStatusLine().getStatusCode(), 200);
        String body = EntityUtils.toString(response.getEntity());
        System.out.println("Http Body: " + body);
    }

    @Test
    @DisplayName("A test demonstration get http status code")
    public void testHttpClient() throws IOException {
        CloseableHttpClient client = HttpClients.custom().build();
        CloseableHttpResponse response = client.execute(new HttpGet("http://www.google.com"));
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        /**Get Http Mime Type*/
        String contentMimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        assertEquals(contentMimeType, ContentType.TEXT_HTML.getMimeType());
        System.out.println("Http MimeType: " + contentMimeType);

        /** Get body*/
        String body = EntityUtils.toString(response.getEntity());
        System.out.println("Http Body: " + body);
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        HttpClientTutorial tutorial = new HttpClientTutorial();
        //tutorial.testHttpClient();
        tutorial.testHttpSSL();
    }
}