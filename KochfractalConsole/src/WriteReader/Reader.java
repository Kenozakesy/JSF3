package WriteReader;

import calculate.Edge;
import calculate.KochData;
import org.apache.commons.lang3.SerializationUtils;
import timeutil.TimeStamp;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Base64;

/**
 * Created by Gebruiker on 22-11-2017.
 */
public class Reader {

    private static final int NUMBER_OF_BYTES = 10*1024*1024; //10 MB of data
    private String mappedPath= "mapped.txt";

    FileInputStream binaryFilePath = null;
    private final String binaryPath = "C:\\Users\\Gebruiker\\Documents\\IntelliJProjects\\KochfractalConsole\\Files\\binary.ser";
    private final String textPath = "C:\\Users\\Gebruiker\\Documents\\IntelliJProjects\\KochfractalConsole\\Files\\Edges.txt";

    public Reader()
    {
        try {
            binaryFilePath = new FileInputStream("C:\\Users\\Gebruiker\\Documents\\IntelliJProjects\\KochfractalConsole\\Files\\binary.ser");
            // nonBinaryFilepath =

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //------------ Read ------------\\
    public KochData readBinaryBuffered() throws IOException, ClassNotFoundException
    {
        TimeStamp timeStamp = new TimeStamp();
        timeStamp.setBegin("Start - Read Binary Not Bufferd");

        KochData kd = null;
        byte [] buffer = null;
        File a_file = new File(binaryPath);

        if(a_file.exists() && !a_file.isDirectory()) {
            try
            {
                DataInputStream fis = new DataInputStream(binaryFilePath);
                int length = (int)a_file.length();
                buffer = new byte [length];
                fis.read(buffer);
                fis.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

            ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
            ObjectInput in = new ObjectInputStream(bis);
            kd = (KochData) in.readObject();

        }

        timeStamp.setEnd("Stop - Read Text Bufferd");
        System.out.println(timeStamp.toString());

        return kd;
    }

    public KochData readBinaryNotBuffered() throws IOException, ClassNotFoundException
    {
        TimeStamp timeStamp = new TimeStamp();
        timeStamp.setBegin("Start - Read Binary Not Bufferd");

        KochData kd = null;
        byte [] buffer =null;
        File a_file = new File(binaryPath);

        if(a_file.exists() && !a_file.isDirectory()) {
            try
            {
                FileInputStream fis = binaryFilePath;
                int length = (int)a_file.length();
                buffer = new byte [length];
                fis.read(buffer);
                fis.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
            ObjectInput in = new ObjectInputStream(bis);
            kd = (KochData)in.readObject();

        }

        timeStamp.setEnd("Stop - Read Text not Bufferd");
        System.out.println(timeStamp.toString());

        return kd;
    }

    public KochData readTextBuffered() throws IOException, ClassNotFoundException
    {
        TimeStamp timeStamp = new TimeStamp();
        timeStamp.setBegin("Start - Read Text Bufferd");

        KochData kd = null;

        File file = new File(textPath);
        try (BufferedReader reader = new BufferedReader(new FileReader(file));){

            String content = "";
            String line;
            while ((line = reader.readLine()) != null) {
                content += line;
            }
            reader.close();

             kd = (KochData) SerializationUtils.deserialize(Base64.getDecoder().decode(content));

        } catch (IOException e) {
            e.printStackTrace();
        }

        timeStamp.setEnd("Stop - Read Text Bufferd");
        System.out.println(timeStamp.toString());

        return kd;

    }

    public KochData readTextNotBuffered() throws IOException, ClassNotFoundException
    {
        TimeStamp timeStamp = new TimeStamp();
        timeStamp.setBegin("Start - Read Text Bufferd");

        KochData kd = null;
        try
        (
           InputStream inputStream = new FileInputStream(textPath)
        )
        {
            String content = "";

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            int data = inputStream.read();
            while(data != -1){
                char theChar = (char) data;
                content += theChar;
                data = inputStream.read();
            }

            inputStreamReader.close();

             kd = (KochData)SerializationUtils.deserialize(Base64.getDecoder().decode(content));

        } catch (IOException e) {
            e.printStackTrace();
        }

        timeStamp.setEnd("Stop - Read Text Bufferd");
        System.out.println(timeStamp.toString());

        return kd;
    }

    public KochData readEdgesMapped()  throws IOException, ClassNotFoundException
    {
        byte[] serialized = new byte[NUMBER_OF_BYTES];

        TimeStamp timeStamp = new TimeStamp();
        timeStamp.setBegin("Start - Read Mapped");
        KochData kd = null;
        try (
                RandomAccessFile memoryMappedFile = new RandomAccessFile(mappedPath, "rw");
                )
        {

            MappedByteBuffer out = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, NUMBER_OF_BYTES);
            out.get(serialized);
            kd = (KochData)SerializationUtils.deserialize(serialized);



        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        timeStamp.setEnd("Stop - Read Mapped");
        System.out.println(timeStamp.toString());

        return kd;
    }

}
