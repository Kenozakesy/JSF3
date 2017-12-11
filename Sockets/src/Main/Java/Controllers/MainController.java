package Controllers;

import Classes.ChatMessage;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable
{

    //Field
    @FXML
    private ListView lvChat;

    @FXML
    private TextField tfChat;

    Socket s;
    ObjectOutputStream out;
    ObjectInputStream in;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try
        {
            s = new Socket("localhost", 8189);
            try
            {
                OutputStream outStream = s.getOutputStream();
                InputStream inStream = s.getInputStream();

                // Let op: volgorde is van belang!
                out = new ObjectOutputStream(outStream);
                in = new ObjectInputStream(inStream);
            }
            finally
            {
                System.out.println("hello");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    //Operators
    @FXML
    public void btnSend(Event e)
    {
        String message = tfChat.getText();
        if(message.equals(""))
        {
            return;
        }
        ChatMessage chat = new ChatMessage(message);
        // send object to server
        try {
            out.writeObject(chat);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }




}
