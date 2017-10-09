package sample.MovingBallsFX;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Gebruiker on 9-10-2017.
 */
public class ReadWrite {
    Lock monLock = new ReentrantLock();
    int readersActive = 0;
    int writersActive = 0;
    int readersWaiting = 0;
    int writersWaiting = 0;


    Condition okToRead = monLock.newCondition();
    Condition okToWrite = monLock.newCondition();

    public ReadWrite() {
    }

    public void enterReader() throws InterruptedException {
        monLock.lock();
        try {
            while (writersActive != 0) {
                readersWaiting++;
                okToRead.await();
                readersWaiting--;
            }
            readersActive++;
        } finally {
            monLock.unlock();
        }
    }

    public void exitReader() {
        monLock.lock();
        try {
            readersActive--;
            if (readersActive == 0) {
                okToWrite.signal();
            }
        } finally {
            monLock.unlock();
        }
    }

    public void enterWriter() throws InterruptedException {
        monLock.lock();
        try {
            while (writersActive > 0 || readersActive > 0) {
                writersWaiting++;
                okToWrite.await();
                writersWaiting--;
            }
            writersActive++;
        } finally {
            monLock.unlock();
        }
    }

    public void exitWriter() {
        monLock.lock();
        try {
            writersActive--;
            if (writersWaiting > 0 && writersActive == 0) {
                okToWrite.signal();
            } else {
                okToRead.signalAll();
            }
        } finally {
            monLock.unlock();
        }
    }

    }