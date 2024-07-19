import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ClientHandler implements Runnable {

    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            
            String line = in.readLine();
            //System.out.println("Received request: " + line);
            line = line.split(" ")[1];
            if(e2_01_12_4.fileMap.get(line) != null) {
                // HTTPレスポンスを送信
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Length: " + e2_01_12_4.fileMap.get(line).length);
                out.println("Content-Type: " + getContentType(line));
                out.println(); //空行 (レスポンスヘッダの終端を意味する)
                clientSocket.getOutputStream().write(e2_01_12_4.fileMap.get(line));
            } else {
                sendErrorResponse(out, 404);
            }
        } catch (IOException e) {
            System.err.println("Error in connection: " + e.getMessage());
        }
    }
    
    private static void sendErrorResponse(PrintWriter out, int statusCode) {
        String statusMessage;
        switch (statusCode) {
            case 404: statusMessage = "Not Found"; break;
            case 500: statusMessage = "Internal Server Error"; break;
            default: statusMessage = "Error";
        }
        out.println("HTTP/1.1 " + statusCode + " " + statusMessage);
        out.println("Content-Type: text/html");
        out.println();
        out.println("<html><body><h1>" + statusCode + " " + statusMessage + "</h1></body></html>");
    }

    private static String getContentType(String path) {
        if (path.endsWith(".htm") || path.endsWith(".html")) {
            return "text/html";
        } else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (path.endsWith(".png")) {
            return "image/png";
        } else {
            return "text/plain";
        }
    }
}

public class e2_01_12_4 {

    public static ConcurrentHashMap<String,byte[]> fileMap = new ConcurrentHashMap<>();
    public static void main(String[] args) throws IOException {
        int port = 8080; // ポート番号を設定
        System.out.println("SimpleWebServer is running on port " + port);
        System.err.println("Waiting for connections on accept()");
        ExecutorService executor = Executors.newFixedThreadPool(16);
        for(File file : new File("./").listFiles()) {
            fileMap.put(file.getPath().replace(".\\", "/"), Files.readAllBytes(Paths.get(file.getPath())));
        }
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.err.println("Client Accepted " + clientSocket.getInetAddress());
                ClientHandler handler = new ClientHandler(clientSocket);
                executor.submit(handler);
            }
        }
    }
}
/*
変更前...
This is ApacheBench, Version 2.3 <$Revision: 1879490 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:
Server Hostname:        localhost
Server Port:            8080

Document Path:          /img.jpg
Document Length:        502212 bytes

Concurrency Level:      5
Time taken for tests:   0.992 seconds
Complete requests:      1000
Failed requests:        0
Total transferred:      502257000 bytes
HTML transferred:       502212000 bytes
Requests per second:    1008.06 [#/sec] (mean)
Time per request:       4.960 [ms] (mean)
Time per request:       0.992 [ms] (mean, across all concurrent requests)
Transfer rate:          494438.39 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.3      0       1
Processing:     1    4  25.7      2     316
Waiting:        0    1   0.5      1       2
Total:          1    5  25.7      2     316

Percentage of the requests served within a certain time (ms)
  50%      2
  66%      3
  75%      3
  80%      3
  90%      3
  95%      3
  98%      4
  99%      4
 100%    316 (longest request)

 
変更後...
This is ApacheBench, Version 2.3 <$Revision: 1879490 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:
Server Hostname:        localhost
Server Port:            8080

Document Path:          /img.jpg
Document Length:        502212 bytes

Concurrency Level:      5
Time taken for tests:   0.381 seconds
Complete requests:      1000
Failed requests:        0
Total transferred:      502281000 bytes
HTML transferred:       502212000 bytes
Requests per second:    2627.41 [#/sec] (mean)
Time per request:       1.903 [ms] (mean)
Time per request:       0.381 [ms] (mean, across all concurrent requests)
Transfer rate:          1288767.53 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   1.1      0      15
Processing:     0    2   4.8      0      18
Waiting:        0    0   2.2      0      16
Total:          0    2   4.9      0      18

Percentage of the requests served within a certain time (ms)
  50%      0
  66%      0
  75%      0
  80%      0
  90%     15
  95%     16
  98%     17
  99%     17
 100%     18 (longest request)


 */
