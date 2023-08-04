import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MoonPhases
{
    public static byte[] getMoonPhases() throws IOException
    {
        // Create a URL object for the NASA Moon Phases API.
        URL url = new URL("https://api.nasa.gov/planetary/apod/?api_key=OI6Tv5awzxQt9NXM4mxfBVZPMEwUKjUt7y85zpC0");

        // Create an HTTP connection to the URL.
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the request method to GET.
        connection.setRequestMethod("GET");

        // Get the response code from the connection.
        int responseCode = connection.getResponseCode();

        // Check if the response code is 200 (OK).
        if (responseCode == 200)
        {
            // Get the input stream from the connection.
            InputStream inputStream = connection.getInputStream();

            // Read the response from the input stream and write it to the print writer.
            byte[] response = new byte[1024];

            // Read the response from the input stream and store it in the array of characters.
            int bytesRead;
            do
            {
                bytesRead = inputStream.read(response);
            } while (bytesRead != -1);

            // Return the response as a string.
            return response;
        } else {
            // The response code was not 200 (OK).
            System.out.println("The response code was " + responseCode);

            // The response code was not 200 (OK).
            return null;
        }
    }
}
