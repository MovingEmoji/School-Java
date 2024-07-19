//Naoya Iida
/**  
  EchoServer.java
  >javac -encoding utf-8 EchoServer.java
  >java EchoServer <port number>
 */
import java.net.*;
import java.io.*;

public class e2_01_9_3 {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }
        int portNumber = Integer.parseInt(args[0]);

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
                        String[] content = inputLine.split(" ");
                        if (content.length == 3) {
                            try {
                                Integer num1 = Integer.parseInt(content[0]);
                                Integer num2 = Integer.parseInt(content[2]);
                                switch(content[1]) {
                                    case "+":
                                        clientMsg.append(num1 + num2);
                                        break;
                                    case "-":
                                        clientMsg.append(num1 - num2);
                                        break;
                                    case "*":
                                        clientMsg.append(num1 * num2);
                                        break;
                                    case "/":
                                        clientMsg.append(num1 / num2);
                                        break;
                                    case "%":
                                        clientMsg.append(num1 % num2);
                                        break;
                                    default:
                                        clientMsg.append("Unknown Opcode");
                                        break;
                                }
                            } catch (Exception e) {
                                clientMsg.append("Invalid Number");
                            }
                        } else {
                            clientMsg.append("The number of arguments  must be 3");
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
1 + 1
echo: 2
1 - 1
echo: 0
1 * 2
echo: 2
4 / 2
echo: 2
5 % 2
echo: 1
1
echo: The number of arguments  must be 3
1 a 2
echo: Unknown Opcode
e + 2
echo: Invalid Number
quit
 */