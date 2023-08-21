/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package dfrontend;

import com.utils.SceneChanger;
import com.utils.WindowSize;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Dfrontend extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        String mode = "Restaurant";
        Parent root;
        if(mode.equals("Admin")){root = FXMLLoader.load(getClass().getResource("adminApplicationView.fxml")); }
        else {root = FXMLLoader.load(getClass().getResource("applicationView.fxml"));}
        SceneChanger sceneChanger = SceneChanger.getInstance();
        sceneChanger.createRouteHistory();
        Scene scene = new Scene(root,WindowSize.getScreenWidth(),WindowSize.getScreenHeight());

        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}
