package sample.MovingBallsFX;

import javafx.scene.paint.Color;

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
        // empty constructor just for initializing
        // this is required
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

    public void fixBalls(Ball ball) {
        monLock.lock();

        try {
            if (ball.getColor() == Color.BLUE) {
                if (ball.isEnteringCs()) {
                    writersWaiting--;
                } else if (ball.isInsideCS()) {
                    writersActive--;
                }
            } else if (ball.getColor() == Color.RED) {
                if (ball.isEnteringCs()) {
                    readersWaiting--;
                } else if (ball.isInsideCS()) {
                    readersActive--;
                }
            }

            if (writersWaiting == 0 && writersActive == 0) {
                okToRead.signalAll();
            } else if (writersWaiting > 0) {
                okToWrite.signal();
            }
        } finally {
            monLock.unlock();
        }
    }

}


