package com.mirea.kabanovasvetlana.lesson7;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionActivity extends AppCompatActivity {
    private TextView ipTextView, cityTextView, regionTextView, countryTextView, latitudeTextView, longitudeTextView, weatherTextView;
    private String latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_http_urlconnection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ipTextView = findViewById(R.id.ipTextView);
        cityTextView = findViewById(R.id.cityTextView);
        regionTextView = findViewById(R.id.regionTextView);
        countryTextView = findViewById(R.id.countryTextView);
        latitudeTextView = findViewById(R.id.latitudeTextView);
        longitudeTextView = findViewById(R.id.longitudeTextView);
        weatherTextView = findViewById(R.id.weatherTextView);

        new DownloadIpInfoTask().execute("https://ipinfo.io/json");
    }
    private class DownloadIpInfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return downloadUrl(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject responseJson = new JSONObject(result);
                String ip = responseJson.getString("ip");
                String city = responseJson.getString("city");
                String region = responseJson.getString("region");
                String country = responseJson.getString("country");
                String loc = responseJson.getString("loc");

                ipTextView.setText("IP: " + ip);

                String[] coords = loc.split(",");
                latitude = coords[0];
                longitude = coords[1];

                latitudeTextView.setText("Широта: " + latitude);
                longitudeTextView.setText("Долгота: " + longitude);

                regionTextView.setText("Регион: " + region);
                countryTextView.setText("\nКод региона: " + country);
                cityTextView.setText("Город: " + city);

                // Получаем погоду
                String weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude +
                        "&longitude=" + longitude + "&current_weather=true";
                new DownloadWeatherTask().execute(weatherUrl);

            } catch (Exception e) {
                e.printStackTrace();
                ipTextView.setText("Ошибка получения данных IP");
            }
        }
    }

    private class DownloadWeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return downloadUrl(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result);
                JSONObject currentWeather = json.getJSONObject("current_weather");

                double temperature = currentWeather.getDouble("temperature");
                double windspeed = currentWeather.getDouble("windspeed");
                String weather = "\tТемпература: " + temperature + "°C\n\tСкорость ветра: " + windspeed + " км/ч";

                weatherTextView.setText("\nПогода\n" + weather);
            } catch (Exception e) {
                e.printStackTrace();
                weatherTextView.setText("Ошибка загрузки погоды");
            }
        }
    }

    private String downloadUrl(String address) {
        InputStream inputStream = null;
        String data = "";
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int read;
                while ((read = inputStream.read()) != -1) {
                    bos.write(read);
                }
                bos.close();
                data = bos.toString();
            } else {
                data = "Ошибка: " + responseCode;
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (Exception ignored) {}
        }
        return data;
    }
}