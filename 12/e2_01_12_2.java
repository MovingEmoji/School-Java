import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;

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
            System.out.println("Received request: " + line);
            String path = line.split(" ")[1];
            File file = new File("." + path);

            if(file.exists()) {
                // HTTPレスポンスを送信
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Length: " + Files.readAllBytes(Paths.get(file.getPath())).length);
                out.println("Content-Type: " + getContentType(path));
                out.println(); //空行 (レスポンスヘッダの終端を意味する)
                FileInputStream fileIn = new FileInputStream(file);
                byte[] buffer = new byte[4096];
                int length;
                while ((length = fileIn.read(buffer)) > 0) {
                    clientSocket.getOutputStream().write(buffer, 0, length);
                }
                fileIn.close();
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

public class e2_01_12_2 {
    public static void main(String[] args) throws IOException {
        int port = 8080; // ポート番号を設定
        System.out.println("SimpleWebServer is running on port " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                System.err.println("Waiting for connections on accept()");
                Socket clientSocket = serverSocket.accept();
                System.err.println("Client Accepted " + clientSocket.getInetAddress());
                ClientHandler handler = new ClientHandler(clientSocket);
                new Thread(handler).start();
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
Time taken for tests:   2.439 seconds
Complete requests:      1000
Failed requests:        0
Total transferred:      502257000 bytes
HTML transferred:       502212000 bytes
Requests per second:    410.01 [#/sec] (mean)
Time per request:       12.195 [ms] (mean)
Time per request:       2.439 [ms] (mean, across all concurrent requests)
Transfer rate:          201101.75 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.3      0       1
Processing:     9   12   1.4     12      23
Waiting:        3   10   1.3     10      21
Total:          9   12   1.4     12      23

Percentage of the requests served within a certain time (ms)
  50%     12
  66%     12
  75%     13
  80%     13
  90%     14
  95%     14
  98%     16
  99%     17
 100%     23 (longest request)

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
Time taken for tests:   1.000 seconds
Complete requests:      1000
Failed requests:        0
Total transferred:      502257000 bytes
HTML transferred:       502212000 bytes
Requests per second:    999.79 [#/sec] (mean)
Time per request:       5.001 [ms] (mean)
Time per request:       1.000 [ms] (mean, across all concurrent requests)
Transfer rate:          490381.39 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.3      0       1
Processing:     1    5  19.4      3     317
Waiting:        0    1   0.9      1       9
Total:          2    5  19.4      3     317

Percentage of the requests served within a certain time (ms)
  50%      3
  66%      4
  75%      4
  80%      4
  90%      5
  95%      6
  98%      8
  99%     14
 100%    317 (longest request)
 */
