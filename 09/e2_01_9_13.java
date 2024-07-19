/**  
  EchoServer.java
  >javac -encoding utf-8 EchoServer.java
  >java EchoServer <port number>
 */

import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            System.err.println("Client Accepted " + clientSocket.getInetAddress());
            String inputLine;
            //6. send()/recv(): サーバとクライアントはストリームを介してデータの送受信を行う。
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equals("bye") || inputLine.equals("close") || inputLine.equals("quit")) {
                    clientSocket.close();
                }
                out.println(inputLine);
                out.flush();
                System.err.println(inputLine);
            }

        } catch (IOException e) {
            System.err.println("Exception caught when trying to listen on port " + " or listening for a connection");
            System.err.println(e.getMessage());
        }
        System.err.println("Client Closed");
    }
}

public class e2_01_9_13 {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }
        int portNumber = Integer.parseInt(args[0]);
        ExecutorService executor = Executors.newFixedThreadPool(10);

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

                Socket clientSocket = serverSocket.accept();
                System.err.println("Client Accepted " + clientSocket.getInetAddress());
                ClientHandler handler = new ClientHandler(clientSocket);
                executor.submit(handler);
            }
        }

    }
}
/*
Server...
Waiting for connections on accept()
Client Accepted /127.0.0.1
Waiting for connections on accept()
Client Accepted /127.0.0.1
aaa
Client Accepted /127.0.0.1
Waiting for connections on accept()
Client Accepted /127.0.0.1
aaa
Client Accepted /127.0.0.1
Waiting for connections on accept()
Client Accepted /127.0.0.1
aaa
Client Accepted /127.0.0.1
Waiting for connections on accept()
Client Accepted /127.0.0.1
aaa
Client Accepted /127.0.0.1
Waiting for connections on accept()
Client Accepted /127.0.0.1
aaa
Client Accepted /127.0.0.1
Waiting for connections on accept()
Client Accepted /127.0.0.1
aaaa
Client Accepted /127.0.0.1
Waiting for connections on accept()
Client Accepted /127.0.0.1
aaa
Client Accepted /127.0.0.1
Waiting for connections on accept()
Client Accepted /127.0.0.1
aaaa
Client Accepted /127.0.0.1
Waiting for connections on accept()
Client Accepted /127.0.0.1
aaa
Client Accepted /127.0.0.1
Waiting for connections on accept()
Client Accepted /127.0.0.1
aaa
Client Accepted /127.0.0.1
Waiting for connections on accept()
Exception caught when trying to listen on port  or listening for a connection
Connection reset
Client Closed
Client Accepted /127.0.0.1
Exception caught when trying to listen on port  or listening for a connection
Connection reset
Client Closed
Exception caught when trying to listen on port  or listening for a connection
Connection reset
Client Closed
Exception caught when trying to listen on port  or listening for a connection
Connection reset
Client Closed
Exception caught when trying to listen on port  or listening for a connection
Connection reset
Client Closed
Exception caught when trying to listen on port  or listening for a connection
Connection reset
Client Closed
Exception caught when trying to listen on port  or listening for a connection
Connection reset
Client Closed
Exception caught when trying to listen on port  or listening for a connection
Connection reset
Client Closed
Exception caught when trying to listen on port  or listening for a connection
Connection reset
Client Closed
Exception caught when trying to listen on port  or listening for a connection
Connection reset
Client Closed
Exception caught when trying to listen on port  or listening for a connection
Connection reset
Client Closed

Client1 ~ Client10...
aaa
echo: aaa

Client11...
aaa

*/