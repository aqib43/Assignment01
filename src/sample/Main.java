package sample;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.TreeMap;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
/*import task3.vehicles.Person;*/

public class Main extends Application {

    public static void main(String[] args) throws FileNotFoundException {
        launch(args);

    }
    @Override
    public void start(Stage stage) throws Exception {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(stage);

        ArrayList<TestFile> files=new ArrayList();
        Controller c=new Controller();
        files=c.Training();
        BorderPane border=new BorderPane();

        TableView table = new TableView();

        TableColumn file = new TableColumn("File Name");
        TableColumn actualClass = new TableColumn("Actual Class");
        TableColumn spam = new TableColumn("Spam");


        file.setCellValueFactory(
                new PropertyValueFactory<TestFile, String>("fileName"));

        actualClass.setCellValueFactory(
                new PropertyValueFactory<TestFile, String>("actualClass"));
        actualClass.setEditable(true);
        spam.setCellValueFactory(
                new PropertyValueFactory<TestFile, String>("spamProbability"));
        spam.setEditable(true);
        final ObservableList<TestFile> data =FXCollections.observableArrayList(files);
        table.setItems(data);
        file.setMinWidth(300);
        spam.setMinWidth(150);
        table.getColumns().addAll(file, actualClass, spam);


        Text acc=new Text("Accuracy :");
        TextField accField=new TextField();
        accField.setEditable(false);

        Text pr=new Text("Precision :");
        TextField prField=new TextField();
        prField.setEditable(false);

        table.getSelectionModel().selectedItemProperty().addListener(cl->
        {
            accField.setText(spam.getText());
            prField.setText(spam.getText());

        });


        Scene sc=new Scene(border, 500,600);
        stage.setScene(sc);
        stage.show();
    }

}
