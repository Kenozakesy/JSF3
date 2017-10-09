package sample.MovingBallsFX;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Gebruiker on 9-10-2017.
 */
public class ReadWrite
{
    Lock monLock = new ReentrantLock();
    int readersActive = 0;
    int writersActive = 0;
    Condition okToRead = monLock.newCondition();
    Condition okToWrite = monLock.newCondition();

    public ReadWrite() {}

   public void enterReader() throws InterruptedException
   {
       monLock.lock();
       try
       {
           while (writersActive > 0) {
               okToRead.await();
           }
           readersActive++;
       }
       finally
       {
           monLock.unlock();
       }
   }

   public void exitReader()
   {
       monLock.lock();
       try {
           readersActive--;
           if (readersActive == 0) {
               okToWrite.signal();
           }
       }
       finally
       {
           monLock.unlock();
       }
   }

   public void enterWriter()
   {
       monLock.lock();
       try
       {
           while (writersActive > 0 || readersActive > 0) {
               try {
                   okToWrite.await();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               writersActive++;
           }
       }
       finally
       {
           monLock.unlock();
       }
   }

   public void exitWriter()
   {
       monLock.lock();
       try {
           writersActive--;
           if (writersActive == 0) {
               okToRead.signal();
           }
       }
       finally
       {
           monLock.unlock();
       }
   }

}
