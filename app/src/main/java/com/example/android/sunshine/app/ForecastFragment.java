package com.example.android.sunshine.app;

/**
 * Created by newma on 4/3/2016.
 */

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ForecastFragment extends Fragment {

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchWeatherTask weatherTask = new FetchWeatherTask();
            weatherTask.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayAdapter<String> mForecastData;

        ArrayList<String> forecastEntry = new ArrayList<String>();
        forecastEntry.add("Forecast 1");
        forecastEntry.add("Forecast 2");
        forecastEntry.add("Forecast 3");
        forecastEntry.add("Forecast 4");
        forecastEntry.add("Forecast 5");
        forecastEntry.add("Forecast 6");
        forecastEntry.add("Forecast 7");
        forecastEntry.add("Forecast 8");
        forecastEntry.add("Forecast 9");
        forecastEntry.add("Forecast 10");
        forecastEntry.add("Forecast 11");
        forecastEntry.add("Forecast 12");
        forecastEntry.add("Forecast 13");
        forecastEntry.add("Forecast 14");
        forecastEntry.add("Forecast 15");
        forecastEntry.add("Forecast 16");
        forecastEntry.add("Forecast 17");
        forecastEntry.add("Forecast 18");

        mForecastData = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, forecastEntry);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastData);
        return rootView;
    }
    public class FetchWeatherTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String forecastJsonStr = null;

            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=76513,us&mode=json&units=metric&cnt=16&appid=47ea246c4dfef9b274ccf58913d2ced7");

                Uri.Builder builder = new Uri.Builder();

                //builder.scheme("http").authority("")

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                Log.e("FF", buffer.toString());
                return  forecastJsonStr = buffer.toString();

            } catch(IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try{
                        reader.close();
                    } catch(final IOException e) {
                        Log.e("PlaceholderFragment", "Error ", e);
                    }
                }
            }
        }
    }
}
