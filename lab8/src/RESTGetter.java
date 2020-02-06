import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class RESTGetter
{
    private URL kayneRestApiURL;

    public RESTGetter()
    {
        try
        {
            this.kayneRestApiURL = new URL("https://api.kanye.rest/");
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    private String getData()
    {
        try
        {
            URLConnection conn = kayneRestApiURL.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder response = new StringBuilder();
            return reader.lines().collect(Collectors.joining("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // error
        return "";
    }

    public String getQuoteSync()
    {
        String response = getData();
        String responseLeftStripped = response.substring(10);
        return responseLeftStripped.substring(0, responseLeftStripped.length() - 3);
    }

    public CompletableFuture<String> getQuoteAsync(ExecutorService executor)
    {
        return CompletableFuture.supplyAsync(() ->
        {
            String data = getData();
            data = data.substring(10);
            data = data.substring(0, data.length() - 3);
            return data;
        }, executor);
    }
}
