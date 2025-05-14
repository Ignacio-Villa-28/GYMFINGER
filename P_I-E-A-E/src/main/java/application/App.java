package application;

import controllers.CrudAdminController;
import controllers.CrudEstudianteController;
import controllers.CrudExternoController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Casa;
import model.Escuela;
import model.Salon;
import utils.Paths;

import java.io.IOException;

public class App extends Application {
    public static App app;
    private Stage stageWindow;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        app = this;
        stageWindow = stage;
        // Cargar la vista INICIO_VIEW
        setScene(Paths.INICIO_VIEW);
    }

    public void setScene(String path){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        try {
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            stageWindow.setScene(scene);
            stageWindow.show();

            // Si la vista es GESTIONAR_ESTUDIANTES_VIEW, configurar el controlador con la escuela
            if (path.equals(Paths.GESTIONAR_ESTUDIANTES_VIEW)) {
                // Configuración de la escuela para el controlador CrudEstudianteController
                CrudEstudianteController controller = loader.getController();
                controller.setEscuela(new Escuela("UAQROO"));
            }
            if (path.equals(Paths.GESTIONAR_ADMIN_VIEW)) {
                // Configuración de la escuela para el controlador CrudEstudianteController
                CrudAdminController controller = loader.getController();
                controller.setEscuela(new Salon("UAQROO"));
            }
            if (path.equals(Paths.GESTIONAR_EXTERNO_VIEW)) {
                // Configuración de la escuela para el controlador CrudEstudianteController
                CrudExternoController controller = loader.getController();
                controller.setEscuela(new Casa("UAQROO"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
