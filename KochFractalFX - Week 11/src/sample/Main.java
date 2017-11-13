package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.calculate.Edge;
import sample.calculate.KochFractal;

import java.util.Observable;
import java.util.Observer;

public class Main extends Application implements Observer {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        KochFractal koch = new KochFractal();
       koch.setLevel(1);
        koch.generateRightEdge();
        koch.generateLeftEdge();
     //   koch.generateBottomEdge();

        Main observer = new Main();

        koch.addObserver(observer);

    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void update(Observable o, Object arg) {
        Edge e = (Edge)arg;
    }
}
