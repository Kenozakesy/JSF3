package Classes;


import Classes.ChatMessage;
import Enums.MessageType;
import Interfaces.OnClientMessage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gebruiker on 11-12-2017.
 */
public class Server implements Runnable
{
    private static List<ChatMessage> messageList = new ArrayList<>();


    @Override
    public void run()
    {
        try (ServerSocket s = new ServerSocket(8189))
        {
            while (!s.isClosed())
            {
                Socket incoming = s.accept();
                Runnable r = new TestServerHandler(incoming);
                Thread t = new Thread(r);
                t.start();
            }
        }
        catch (IOException e)
        {
            //messages.append("\nTestServer.run: " + e);
        }
    }

    class TestServerHandler implements Runnable
    {
        private Socket incommingSocket;

        public TestServerHandler(Socket i)
        {
            incommingSocket = i;
        }

        public void run()
        {
            try
            {
                OutputStream out = incommingSocket.getOutputStream();
                InputStream in = incommingSocket.getInputStream();

                ObjectInputStream ObjectIn = new ObjectInputStream(in);
                ObjectOutputStream ObjectOut = new ObjectOutputStream(out);

                //ObjectOut.writeObject();

                //hier gebeurt shit





                //incommingSocket.close();
            }
            catch (Exception e)
            {

            }
        }

    }
}
