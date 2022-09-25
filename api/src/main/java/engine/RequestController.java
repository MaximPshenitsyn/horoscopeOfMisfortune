package engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class RequestController {

    private static HttpURLConnection getConnection(String serverUrl) throws ConnectException {
        try {
            URL url = new URL(serverUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            return connection;
        } catch (MalformedURLException e) {
            throw new ConnectException("Invalid url");
        } catch (IOException e) {
            throw new ConnectException("Failed connect to server");
        }
    }

    private static String performGetRequest(HttpURLConnection connection) throws ConnectException {
        try {
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new ConnectException("Bad status");
            }

            String line;
            StringBuilder response = new StringBuilder();
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((line = br.readLine()) != null) {
                response.append(line).append("\n");
            }
            br.close();
            return response.toString();

        } catch (ProtocolException e) {
            throw new ConnectException("Can't perform GET request");
        } catch (IOException e) {
            throw new ConnectException("Failed to connect or read");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static String performPostRequest(HttpURLConnection connection, Map<String, String> postParams)
            throws ConnectException {
        try {
            try {
                connection.setRequestMethod("POST");
                connection.connect();
            } catch (ProtocolException e) {
                throw new ConnectException("Can't perform POST request");
            } catch (IOException e) {
                throw new ConnectException("Failed to connect");
            }

            byte[] out = postParams.toString().getBytes();
            OutputStream os = null;
            try {
                os = connection.getOutputStream();
                os.write(out);
            } catch (IOException e) {
                throw new ConnectException("Failed to write");
            } finally {
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    response.append(line).append("\n");
                }
                br.close();
                return response.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            connection.disconnect();
        }
        return null;
    }

    public static String postRequest(String url, Map<String, String> postParams) throws ConnectException {
        return performPostRequest(getConnection(url), postParams);
    }

    public static String getRequest(String url) throws ConnectException {
        return performGetRequest(getConnection(url));
    }
}
