/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.MovingBallsFX;


import javafx.scene.paint.Color;

/**
 *
 * @author Peter Boots
 */
public class BallRunnable implements Runnable {

    private Ball ball;
    private ReadWrite readWrite;

    public BallRunnable(Ball ball, ReadWrite readWrite) {
        this.ball = ball;
        this.readWrite = readWrite;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {

                if (ball.getColor() ==  Color.RED && ball.isEnteringCs())
                {
                    readWrite.enterReader();
                }
                else if (ball.isLeavingCs()) {
                    readWrite.exitReader();
                }
                ball.move();
                Thread.sleep(ball.getSpeed());

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
