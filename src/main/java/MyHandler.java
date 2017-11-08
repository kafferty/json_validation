import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MyHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        String string = br.readLine();
        GsonBuilder gb = new GsonBuilder();
        gb.setPrettyPrinting().serializeNulls();
        Gson gson = gb.create();
        String str = null;
        try {
            Object object = gson.fromJson(string, Object.class);
            str = gson.toJson(object);
        } catch (JsonSyntaxException ex) {
            str = ex.getMessage();
        }
        System.out.println(str);
        exchange.sendResponseHeaders(200, str.length());
        OutputStream os = exchange.getResponseBody();
        os.write(str.getBytes());
        os.close();
    }
}
