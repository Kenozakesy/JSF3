package Classes;


import Classes.ChatMessage;
import Enums.MessageType;
import Interfaces.OnClientMessage;
import KochfractalPackage.Edge;
import KochfractalPackage.KochData;
import KochfractalPackage.KochManager;

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
    ServerSocket server;


    @Override
    public void run()
    {
        try ( ServerSocket s = new ServerSocket(8189))
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

                ObjectOut.writeObject("hello");
                ObjectOut.flush();

                // echo client Object input
                boolean done = false;
                Object inObject = null;
                while (!done) {
                    try {
                        inObject = ObjectIn.readObject();
                        if (inObject instanceof ChatMessage) {


                            // change name //hier berekenen kochfractal
                            ChatMessage message = (ChatMessage) inObject;
                            System.out.println("Persoon ontvangen: "
                                    + message.toString());

                            //hier terug geven
                            ObjectOut.writeObject(message);
                            out.flush();
                        } else if (inObject instanceof String) {
                            String woord = (String) inObject;

                            if (woord.equals("BYE")) {
                                done = true;

                                ObjectOut.writeObject(woord);
                                out.flush();

                            }

                            else {
                                int level = Integer.valueOf(woord);

                                KochManager manager = new KochManager(ObjectOut);
                                ArrayList<Edge> edges = manager.changeLevel(level);
                              //  KochData data = new KochData(edges);

                             //   ObjectOut.writeObject(data);
                             //   out.flush();




                            }

                        }
                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        System.out.println("Object type not known");
                    }
                    //
                }


                incommingSocket.close();
            }

            catch (IOException e)
            {

            }


        }

    }
}
