package sample;

import Classes.ChatMessage;
import KochfractalPackage.Edge;
import KochfractalPackage.KochData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Client extends Application  {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = null;

        root = fxmlLoader.load();
        primaryStage.setTitle("Sockets");

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args)
    {
        try
        {
            Socket s = new Socket("localhost", 8189);
            try
            {
                OutputStream outStream = s.getOutputStream();
                InputStream inStream = s.getInputStream();

                // Let op: volgorde is van belang!
                ObjectOutputStream out = new ObjectOutputStream(outStream);
                ObjectInputStream in = new ObjectInputStream(inStream);
                //
                // Simulatie clientsessie
                //
                // ontvang welkomsboodschap
                String result = (String)in.readObject();
                System.out.println(result.toString());


                // send Level
                String level = "3";

                System.out.println(level);
                out.writeObject(level);
                out.flush();

                // get edges
                Edge data = (Edge)in.readObject();

                System.out.println("generated edges: "+ data.X1);

//                // send object
//                ChatMessage message = new ChatMessage("Hello");
//                System.out.println("sending ME: "+ message.toString());
//                out.writeObject(message);
//
//                ChatMessage reply = (ChatMessage)in.readObject();
//                System.out.println("ontvangen antwoord: "+ reply.toString());

                // close
                out.writeObject("BYE");
                out.flush();
                result = (String)in.readObject();
                System.out.println("ontvangen antwoord: "+ result.toString());


            }
            finally
            {
                s.close();

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException nfe){
            nfe.printStackTrace();
        }
    }
}

//    public static void main(String[] args) {
//        launch(args);
//    }

