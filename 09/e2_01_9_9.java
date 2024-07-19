//Naoya Iida
/**  
  EchoServer.java
  >javac -encoding utf-8 EchoServer.java
  >java EchoServer <port number>
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.io.*;

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

public class e2_01_9_9 {

    public static HashMap<String, String> stringMap = new HashMap<>();

    public static void loadValues(String filepath) {
        File file = new File(filepath);

        try (
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
        ){
            String data;
            while((data = br.readLine()) != null) {
                String[] stringArray = data.split(":");
                stringMap.put(stringArray[0], stringArray[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        loadValues("chat.txt");

        String apiUrl = "https://weather.tsukumijima.net/api/forecast/city/400040";
        int portNumber = Integer.parseInt(args[0]);
        ObjectMapper mapper = new ObjectMapper();

        //1. socket(): ServerSocketのインスタンスを作成することで、サーバのソケットが開かれる。
        //2. bind(): 上記コンストラクタにポート番号を渡すことで、サーバのソケットがそのポートにバインドされる。
        //3. listen(): ソケット接続準備をする。
        //   2.と3.は、ServerSocketが作成されたときに自動的に処理される。
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {

            while (true) {
                System.err.println("Waiting for connections on accept()");
                //4. accept(): サーバがクライアントからの接続要求を待つ。接続要求が来るまでブロックされる。
                //             接続要求が来ると、クライアントとの通信用に新しいSocketインスタンスを返す。
                //5. 3-Way Handshake: TCPにおけるコネクション確立のための3-wayハンドシェイクを行う。
                //                    これは自動的に行われ、Javaのコードでは明示的には現れない。
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    
                    System.err.println("Client Accepted " + clientSocket.getInetAddress());
                    String inputLine;
                    //6. send()/recv(): サーバとクライアントはストリームを介してデータの送受信を行う。
                    while ((inputLine = in.readLine()) != null) {
                        if (inputLine.equals("bye") || inputLine.equals("close") || inputLine.equals("quit")) {
                            clientSocket.close();
                        }
                        StringBuilder serverMsg = new StringBuilder();
                        StringBuilder clientMsg = new StringBuilder();
                        serverMsg.append(inputLine);
                        if (inputLine.equals("reload")) {
                            stringMap = new HashMap<>();
                            clientMsg.append("reloaded values");
                            loadValues("chat.txt");
                        } else if (inputLine.equals("weather")) {
                            try {
                                //readValue()で、URL宛にHTTPリクエストを送信し、レスポンスとして受け取ったJSONデータを読み込む
                                WeatherResponse response = mapper.readValue(new URL(apiUrl), WeatherResponse.class);
                                List<WeatherResponse.Forecast> forecasts = response.getForecasts();
                    
                                if (!forecasts.isEmpty()) {
                                    clientMsg.append(forecasts.get(0).getTelop());
                                } else {
                                    System.out.println("No forecast data available.");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            String msg = stringMap.get(inputLine);
                            if (msg == null) 
                                clientMsg.append("Unknown");
                            else
                                clientMsg.append(msg);
                            }
                        System.err.println(serverMsg.toString());
                        out.println(clientMsg.toString());
                        out.flush();
                    }
                } catch (IOException e) {
                    System.err.println("Exception caught when trying to listen on port " + portNumber +
                     " or listening for a connection");
                    System.err.println(e.getMessage());
                }
                //7. close(): 通信が終了したらサーバとクライアントの両方でソケットを閉じ、リソースを解放する。
                //            今回はtry-with-resources文を使っているため自動でcloseされる。
                System.err.println("Client Closed");
            }
        }

    }
}
/*
hello
echo: Hello master
weather
echo: 曇時々雨
quit
 */