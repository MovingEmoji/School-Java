//Naoya Iida
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) //未知のプロパティは無視する
class WeatherResponse {
    @JsonProperty("forecasts")
    private List<Forecast> forecasts;

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    @JsonIgnoreProperties(ignoreUnknown = true) //未知のプロパティは無視する
    public static class Forecast {
        private String telop;

        public String getTelop() {
            return telop;
        }
    }
}

public class e2_01_9_8 {
    public static void main(String[] args) {
        String apiUrl = "https://weather.tsukumijima.net/api/forecast/city/130010"; //お天気API
        ObjectMapper mapper = new ObjectMapper();
        try {
            //readValue()で、URL宛にHTTPリクエストを送信し、レスポンスとして受け取ったJSONデータを読み込む
            WeatherResponse response = mapper.readValue(new URL(apiUrl), WeatherResponse.class);
            List<WeatherResponse.Forecast> forecasts = response.getForecasts();

            if (!forecasts.isEmpty()) {
                for(WeatherResponse.Forecast f : forecasts){ 
                    System.out.println(f.getTelop()); //3日間の天気予報を表示
                }
            } else {
                System.out.println("No forecast data available.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
雨時々止む
曇のち一時雨
曇一時雨
 */