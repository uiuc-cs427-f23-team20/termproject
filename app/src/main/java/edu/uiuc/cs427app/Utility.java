package edu.uiuc.cs427app;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class Utility {



    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.

     public static String downloadDataFromUrlWeatherBit(String myurl)  {
        InputStream is = null;
        System.out.println(" inside downloadDataFromUrl");

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000); // time in milliseconds
            conn.setConnectTimeout(15000); // time in milliseconds
            conn.setRequestMethod("GET"); // request method GET OR POST
            conn.setDoInput(true);
            // Starts the query
            conn.connect(); // calling the web address
            int response = conn.getResponseCode();

            System.out.println("Weather  myurl: " + myurl);
            System.out.println("Weather  response: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readInputStream(is);
            System.out.println("Weather contentAsString: " + contentAsString);

            String output = "Something Wrong";
            DecimalFormat df = new DecimalFormat("#.##");
            try {
                JSONObject jsonResponse = new JSONObject(contentAsString);
                JSONArray jsonArray = jsonResponse.getJSONArray("data");
                JSONObject jsonDataNode =  jsonArray.getJSONObject(0);

                if (jsonDataNode.getInt("lat")==0) return output+" !!";

                JSONObject jsonObjectWeather = jsonDataNode.getJSONObject("weather");
                String description = jsonObjectWeather.getString("description");
                double temp = jsonDataNode.getDouble("temp");
                double feelsLike = jsonDataNode.getDouble("app_temp") ;
                double pressure = jsonDataNode.getDouble("pres");
                int humidity = jsonDataNode.getInt("rh");
                double windSpeed = jsonDataNode.getDouble("wind_spd");
                String winDir = jsonDataNode.getString("wind_cdir_full");
                String clouds = jsonDataNode.getString("clouds");
                String countryName = jsonDataNode.getString("country_code");
                String cityName = jsonDataNode.getString("city_name");
                String stateName = jsonDataNode.getString("state_code");
                //String dateTime = jsonDataNode.getString("ob_time");
                int timeStamp = jsonDataNode.getInt("ts");
                String timezone = jsonDataNode.getString("timezone");
                // time can not be used as it is last observed time, and the time is not current time
                int uv = jsonDataNode.getInt("uv");
                int vis = jsonDataNode.getInt("vis");
                int aqi = jsonDataNode.getInt("aqi");

                String uvDesc="";
                if (uv<2) uvDesc=" (Low)";
                else if (uv<=5) uvDesc=" (Moderate)";
                else if (uv<=7) uvDesc=" (High)";
                else if (uv<=10) uvDesc=" (Very High)";
                else  uvDesc=" (Extreme)";

                String airQuality="";
                if (aqi<=50) airQuality=" (Good)";
                else if (aqi<=100) airQuality=" (Moderate)";
                else if (aqi<=200) airQuality=" (Unhealthy)";
                else if (aqi<=300) airQuality=" (Very Unhealthy)";
                else airQuality=" (Hazardous)";

                String location = "";
                if (cityName!=null && cityName.trim().length()>0) location+=cityName.trim();
                if (location.length()>0) {
                    if (stateName!=null && stateName.trim().length()>0) location=location+","+stateName.trim();
                }
                else
                    if (stateName!=null && stateName.trim().length()>0) location+=stateName.trim();

                if (location.length()>0) {
                    if (countryName!=null && countryName.trim().length()>0) location=location+","+countryName.trim();
                }
                else
                    if (countryName!=null && countryName.trim().length()>0) location+=countryName.trim();

                Instant instant = Instant.ofEpochSecond( timeStamp );
                ZoneId z = ZoneId.of( timezone ) ;
                ZonedDateTime zdt = instant.atZone( z );
                //System.out.println("Weather ZonedDateTime: " + zdt);

                final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss");
                String dateStr = zdt.format(DATETIME_FORMATTER);


                output = "\t\t Location : "+location
                        + "\n\t\t Local Time : "+dateStr
                        + "\n\t\t Tome-zone : "+timezone
                        + "\n\t\t Temperature: " + df.format(temp) + " °C"
                        + "\n\t\t Feels Like: " + df.format(feelsLike) + " °C"
                        + "\n\t\t Weather: " + description
                        + "\n\t\t Cloudiness: " +clouds + " %"
                        + "\n\t\t Humidity: " + df.format(humidity) + " %"
                        + "\n\t\t Wind Speed: " + df.format(windSpeed) + " m/s" //  (meters per second)
                        + "\n\t\t Wind Direction: " + winDir
                        + "\n\t\t UV Index: " + uv+uvDesc
                        + "\n\t\t Visibility: " +vis+" Km"
                        + "\n\t\t Air quality: " + aqi+airQuality;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return output;
            //return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (Exception e) {
            System.out.println("Weather  conn error : " + e.toString());
            e.printStackTrace();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            return "Unable to retrieve current weather information for this city";
        }
    }

    // Reads an InputStream and converts it to a String.
    public static String readInputStream(InputStream stream) throws IOException {
        int n = 0;
        char[] buffer = new char[1024 * 4];
        InputStreamReader reader = new InputStreamReader(stream, "UTF8");
        StringWriter writer = new StringWriter();
        while (-1 != (n = reader.read(buffer)))
            writer.write(buffer, 0, n);
        return writer.toString();
    }
}
