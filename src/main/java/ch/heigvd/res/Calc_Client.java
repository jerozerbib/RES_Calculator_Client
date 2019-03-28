package ch.heigvd.res;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.String.format;

public class Calc_Client {


    public static void main(String[] args) throws IOException {
        int port = 2205;
        Scanner scanner = new Scanner(System.in);

        // Opens the socket for the `ip` on `port`
        Socket socket = new Socket("10.192.107.55", port);

        // Gets the input and output Streams.
        BufferedReader dis  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter dos = new PrintWriter(socket.getOutputStream());

        System.out.println(dis.readLine());
        while (true){

            //Prompt to ask the Client his input
            System.out.print("Please enter you operation :");
            System.out.println("Format is : REQUEST\t<operand>[[\t<operand>]{0,1}\tOP<operation>]+\n or Bye to quit");

            System.out.println("what do you want to do ?");
            String request = scanner.nextLine();

            if (request.equals("BYE")){
                String message = format("%s", request);

                dos.write(message);
                dos.flush();
                break;
            }

            System.out.println("Enter 1st operand");
            String op1 = scanner.nextLine();

            if (!isParsable(op1)){
                continue;
            }

            System.out.println("Enter operator");
            String operator = scanner.nextLine();


            System.out.println("Enter 2nd operand");
            String op2 = scanner.nextLine();

            if (!isParsable(op2)){
                continue;
            }

            String message = format("%s\t%s\t%s\t%s\n", request, op1, op2, "OP" + operator);

            dos.write(message);
            dos.flush();

            //Waiting for the response
            System.out.println("Answer = " + dis.readLine());

        }

    }

    static boolean isParsable(String operand){
        try{
            Double.parseDouble(operand);
            return true;
        } catch (NumberFormatException e){
            e.printStackTrace();
            return false;
        }
    }
}
