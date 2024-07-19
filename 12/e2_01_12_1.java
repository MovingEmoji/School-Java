import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class e2_01_12_1 {
    public static void main(String[] args) throws IOException {
        int port = 8080; // ポート番号を設定

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("SimpleWebServer is running on port " + port);

        while (true) {
            try (Socket clientSocket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                // リクエストの読み取り（ヘッダー行のみ）
                String line = in.readLine();
                System.out.println("Received request: " + line);
                String path = line.split(" ")[1];
                File file = new File("." + path);

                
                // レスポンス本体
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
                break;
            }
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
/*
Received request: GET /a.txt HTTP/1.1
Received request: GET /favicon.ico HTTP/1.1
Received request: GET /img.jpg HTTP/1.1
Received request: GET /index.html HTTP/1.1
Received request: GET /img.jpg HTTP/1.1
Received request: GET /aaa HTTP/1.1
 */
