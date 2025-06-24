import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);
            new ReadThread(socket).start();
            new WriteThread(socket).start();
        } catch (IOException e) {
            System.out.println("Unable to connect to server.");
        }
    }
}

class ReadThread extends Thread {
    private BufferedReader in;

    public ReadThread(Socket socket) {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Error reading from server.");
        }
    }

    public void run() {
        String response;
        try {
            while ((response = in.readLine()) != null) {
                System.out.println(response);
            }
        } catch (IOException e) {
            System.out.println("Connection closed.");
        }
    }
}

class WriteThread extends Thread {
    private PrintWriter out;
    private BufferedReader console;

    public WriteThread(Socket socket) {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            console = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            System.out.println("Error writing to server.");
        }
    }

    public void run() {
        String userInput;
        try {
            while ((userInput = console.readLine()) != null) {
                out.println(userInput);
            }
        } catch (IOException e) {
            System.out.println("Error sending message.");
        }
    }
}
