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
import java.util.List;

/**
 * Created by Gebruiker on 20-11-2017.
 */
public class Writer {

    private static final int NUMBER_OF_BYTES = 10*1024*1024; //10 MB of data
    private String mappedPath= "mapped.txt";

    FileOutputStream binaryFilePath = null;
    FileWriter textFilePath = null;
    //private final String binaryPath = "C:\\Users\\Gebruiker\\Documents\\IntelliJProjects\\KochfractalConsole\\Files\\binary.ser";
    private final String textPath = "C:\\Users\\Gebruiker\\Documents\\IntelliJProjects\\KochfractalConsole\\Files\\Edges.txt";
    //File nonBinaryFilepath

    public Writer() {
//        try {
//            binaryFilePath = new FileOutputStream("C:\\Users\\Gebruiker\\Documents\\IntelliJProjects\\KochfractalConsole\\Files\\binary.ser");
//            textFilePath = new FileWriter("C:\\Users\\Gebruiker\\Documents\\IntelliJProjects\\KochfractalConsole\\Files\\Edges.txt");
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    //------------ Write ------------\\
    public void writeEdgesToBinaryBufferd(ArrayList<Edge> edges)
    {
        KochData kd = new KochData(edges);
        checkSame(kd);

        TimeStamp timeStamp = new TimeStamp();
        timeStamp.setBegin("Start - Write Binary Buffered");

        //serialize the List
        try (
                OutputStream file = binaryFilePath;
                OutputStream buffer = new BufferedOutputStream(file);
                ObjectOutput output = new ObjectOutputStream(buffer);
        )
        {
            output.writeObject(kd);
        }
        catch(IOException ex){
            System.err.println("Cannot perform output." + ex);
        }

        timeStamp.setEnd("Stop - Write Binary Buffered");
        System.out.println(timeStamp.toString());
    }

    public void writeEdgesToBinaryNotBufferd(ArrayList<Edge> edges)
    {
        KochData kd = new KochData(edges);
        checkSame(kd);

        TimeStamp timeStamp = new TimeStamp();

        timeStamp.setBegin("Start - Write Binary Not Bufferd");
        //serialize the List
        try (
                OutputStream file = binaryFilePath;
                ObjectOutput output = new ObjectOutputStream(file);
        ){
            output.writeObject(kd);
        }
        catch(IOException ex){
            System.err.println("Cannot perform output." + ex);
        }
        timeStamp.setEnd("Stop - Write Binary Not Bufferd");

        System.out.println(timeStamp.toString());
    }

    public void writeEdgesToTextBuffered(ArrayList<Edge> edges)
    {
        KochData kd = new KochData(edges);
        checkSame(kd);

        byte[] serialized = SerializationUtils.serialize(kd);
        String s = Base64.getEncoder().encodeToString(serialized);
        TimeStamp timeStamp = new TimeStamp();


        timeStamp.setBegin("Start - Write Text Buffered");
        File file = new File(textPath);
        try (FileWriter fw = new FileWriter(file.getAbsoluteFile());)
        {
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }


            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(s);

            bw.close();
            fw.close();
            //System.out.println("Wrote buffered to text.");
        } catch (IOException ex) {
            System.out.println("you fucked something up");
        }


        timeStamp.setEnd("Stop - Write Text Buffered");
        System.out.println(timeStamp.toString());
    }

    public void writeEdgesToTextNotBuffered(ArrayList<Edge> edges)
    {
        KochData kd = new KochData(edges);
        checkSame(kd);

        byte[] serialized = SerializationUtils.serialize(kd);
        String s = Base64.getEncoder().encodeToString(serialized);

        TimeStamp timeStamp = new TimeStamp();

        timeStamp.setBegin("Start - Write Text Not Bufferd");
        File file = new File(textPath);
        try (FileWriter w = new FileWriter(file))
        {
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            w.write(s);
            w.close();

            //System.out.println("Wrote Not buffered to text.");
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }

        timeStamp.setEnd("Stop - Write Text Not Bufferd");
        System.out.println(timeStamp.toString());
    }

    public void writeEdgesMapped(ArrayList<Edge> edges)
    {
        KochData kd = new KochData(edges);
        checkSame(kd);

        byte[] serialized = SerializationUtils.serialize(kd);

        TimeStamp timeStamp = new TimeStamp();
        timeStamp.setBegin("Start - Write Mapped");
        try (
                RandomAccessFile memoryMappedFile = new RandomAccessFile(mappedPath, "rw")
                )
        {

            MappedByteBuffer out = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, NUMBER_OF_BYTES);
            out.put(serialized);

        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        timeStamp.setEnd("Stop - Write Mapped");
        System.out.println(timeStamp.toString());

    }

    private void checkSame(KochData kd)
    {
        System.out.println
                (
                        String.valueOf(kd.getEdges().get(0).X1) + "\n" +
                        String.valueOf(kd.getEdges().get(2).X1) +  "\n" +
                        String.valueOf(kd.getEdges().get(kd.getEdges().size() - 1).X1)
                );
    }

}
