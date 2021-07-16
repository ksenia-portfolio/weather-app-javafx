package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Button get_weather;

    @FXML
    private Text temp;

    @FXML
    private Text feels_like;

    @FXML
    private Text temp_min;

    @FXML
    private Text temp_max;

    @FXML
    private Text pressure;

    @FXML
    private Text humidity;

    @FXML
    void initialize() {
        get_weather.setOnAction(actionEvent -> {
            // get data from text field
            String  getUserCity = city.getText().trim();

            // if city is not empty, get weather from open weather map site
            if(!getUserCity.equals("")){
                String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=07e138552d73b19d5c8d37601724bd73&units=metric");

                // if no error was found and such city exists, getting data from json file
                if(!output.isEmpty()){

                    // System.out.println(output);

                    JSONObject obj = new JSONObject(output);

                    temp.setText("Temperature: "+obj.getJSONObject("main").getDouble("temp"));
					feels_like.setText("Feels like: "+obj.getJSONObject("main").getDouble("feels_like"));
					temp_min.setText("Min: "+obj.getJSONObject("main").getDouble("temp_min"));
					temp_max.setText("Max: "+obj.getJSONObject("main").getDouble("temp_max"));
					pressure.setText("Pressure: "+obj.getJSONObject("main").getDouble("pressure"));
                    humidity.setText("Humidity: "+obj.getJSONObject("main").getDouble("humidity"));
                }
            }
        });
    }

    private static String getUrlContent(String urlAddress){
        StringBuffer content =  new StringBuffer();

        try{
            URL url =  new URL(urlAddress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }catch (Exception e) {
            System.out.println("City could not be found!");
        }
        return content.toString();
    }
}
