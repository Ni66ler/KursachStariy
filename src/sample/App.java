package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.WorkWithList;

//JavaFX App
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //заполняем список картин данными из файла
        WorkWithList.loadListFromFile();
        //загружаем окно главного меню
        Parent root = FXMLLoader.load(getClass().getResource("/forms/sample.fxml"));
        primaryStage.setTitle("Справочник живописи");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.setResizable(false);//размеры окна будет невозможно поменять
        primaryStage.show(); //вывод окна главного меню на экран
    }

    //метод, который отвечает за действия при закрытии программы
    //данные из списка запишутся в файл
    public void stop(){
        WorkWithList.loadListInFile();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
