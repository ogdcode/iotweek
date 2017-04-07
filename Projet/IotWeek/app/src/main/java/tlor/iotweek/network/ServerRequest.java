package tlor.iotweek.network;

import android.util.Log;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class ServerRequest {

    public final static String API_URL = "api.mlab.com/api/1/databases/iotweek-db/collections/";
    public final static String API_KEY = "gUBeJJih5LDEcH9jLV3duXYRmx-Rw7dJ";

    public final static int DEFAULT_READ_TIMEOUT = 5000; // 5 seconds
    public final static int DEFAULT_CONNECT_TIMEOUT = 5000; // 5 seconds
    public final static String GET = "GET";
    public final static String POST = "POST";
    public final static String PUT = "PUT";
    public final static String DELETE = "DELETE";
    private int readTimeout = DEFAULT_READ_TIMEOUT;
    private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    private Map<String, String> headers;
    private Map<String, String> parameters;
    private String body;
    private String type;
    private String url;
    private boolean secure = true;
    private HttpURLConnection connection;
    private ServerRequest() {}
    public ServerRequest(String type, String url) {
        this(type, url, null, new HashMap<String, String>());
    }
    public ServerRequest(String type, String url, String body) {
        this(type, url, body, new HashMap<String, String>());
    }
    public ServerRequest(String type, String url, String body, Map<String, String> headers) {
        this(type, url, body, headers, new HashMap<String, String>());
    }
    public ServerRequest(String type, String url, String body, Map<String, String> headers, Map<String, String> parameters) {
        this.parameters = parameters;
        this.headers = headers;
        this.body = body;
        this.type = type;
        this.url = url;
    }
    /**
     * Send the request and retrieve the response
     * @return the server's reponse or null
     */
    public ServerResponse send() {
        ServerResponse serverResponse = null;
        try {
            // url
            String baseUrl = (secure ? "https://" : "http://") + url;
            if(parameters != null) {
                int i = 0;
                for (Map.Entry<String, String> entry : parameters.entrySet()) {
                    baseUrl += (i == 0) ? "?" : "&";
                    baseUrl += URLEncoder.encode(entry.getKey(), "utf-8") + "=" + URLEncoder.encode(entry.getValue(), "utf-8");
                    i++;
                }
            }
            URL formattedUrl = new URL(baseUrl);
            Log.d(getClass().getName(), "Request " + type + " to " + formattedUrl.toString() + " with body : " + body);
            // connection init
            connection = (HttpURLConnection) formattedUrl.openConnection();
            connection.setReadTimeout(readTimeout);
            connection.setConnectTimeout(connectTimeout);
            connection.setRequestMethod(type);
            // headers
            if(headers != null)
                for(Map.Entry<String, String> entry : headers.entrySet())
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
            // body
            if((type == POST || type == PUT) && body != null) {
                connection.setDoOutput(true);
                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes(body);
                outputStream.flush();
                outputStream.close();
            }
            // response
            try {
                InputStream inputStream = connection.getInputStream();
                if (inputStream != null) {
                    serverResponse = new ServerResponse(connection.getResponseCode(), readResponse(inputStream));
                }
            }
            catch (IOException inputStreamException) {
                try {
                    InputStream errorStream = connection.getErrorStream();
                    if (errorStream != null) {
                        serverResponse = new ServerResponse(connection.getResponseCode(), readResponse(errorStream));
                    }
                }
                catch (IOException errorStreamException) {
                    throw errorStreamException;
                }
            }
        } catch (Exception e) {
        }
        return serverResponse;
    }
    /**
     * Read the InputStream and returns the server's response as a string
     * @param in the InputStream to read from
     * @return the server's reponse
     * @throws IOException
     */
    private String readResponse(InputStream in) throws IOException {
        if (in == null)
            return null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder builder = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } finally {
            in.close();
            reader.close();
        }
        return builder.toString();
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
    public void addHeader(String header, String value) {
        this.headers.put(header, value);
    }
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
    public void addParameter(String parameter, String value) {
        this.parameters.put(parameter, value);
    }
    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public void setSecure(boolean secure) {
        this.secure = secure;
    }
    // Utility methods
    /**
     * Transforms a basic Map into a valid json object
     * @param map the Map to turn into json
     * @return a valid json string
     */
    public static String mapToJson(Map<String, String> map) {
        String str = "{";
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, String> argument : map.entrySet()) {
                str += "\"" + argument.getKey() + "\":\"" + argument.getValue() + "\",";
            }
            str = str.substring(0, str.length()-1); // remove the last ','
        }
        str += "}";
        return str;
    }
    /**
     * Transforms a basic Map into a valid form data string
     * @param map the Map to turn into a form data compliant string
     * @return a string containing the form data (may be empty)
     */
    public static String mapToFormData(Map<String, String> map) {
        String str = "";
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, String> argument : map.entrySet()) {
                str += argument.getKey() + "=" + argument.getValue() + "&";
            }
            str = str.substring(0, str.length()-1); // remove the last '&'
        }
        return str;
    }
}
