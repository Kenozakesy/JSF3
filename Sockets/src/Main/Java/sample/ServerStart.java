package sample;

import Classes.Server;

/**
 * Created by Gebruiker on 11-12-2017.
 */
public class ServerStart {

    public static void main(String[] args)
    {
        Thread T = new Thread(new Server());
        T.start();
    }
}
