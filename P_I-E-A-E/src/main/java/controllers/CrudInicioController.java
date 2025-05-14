package controllers;

import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import utils.Paths;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.util.Duration;

public class CrudInicioController {

    @FXML
    private Button btnAdmin;

    @FXML
    private Button btnEstudiante;

    @FXML
    private Button btnExterno;

    @FXML
    private ImageView imgAdmin;

    @FXML
    private ImageView imgEstudiante;

    @FXML
    private ImageView imgExterno;

   @FXML
    private AnchorPane miAnchorPane;


    @FXML
    void cambiarEstudiante(ActionEvent event) {
        App.app.setScene(Paths.GESTIONAR_ESTUDIANTES_VIEW);
    }

   @FXML
    void cambiarAdmin(ActionEvent event) {
        App.app.setScene(Paths.GESTIONAR_ADMIN_VIEW);
    }

    @FXML
    void cambiarExterno(ActionEvent event) {
        App.app.setScene(Paths.GESTIONAR_EXTERNO_VIEW);
    }
    @FXML
    public void initialize() {
        //Anchor Pane_Primer degradado: negro, gris, blanco (de arriba a abajo)

        LinearGradient degradado1 = new LinearGradient(0, 0, 0, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, javafx.scene.paint.Color.BLACK),  // Color en la parte superior
                new Stop(0.5, javafx.scene.paint.Color.GRAY),  // Color medio
                new Stop(1, javafx.scene.paint.Color.WHITE)    // Color en la parte inferior
        );

        // Segundo degradado: verde, gris, gris, blanco (de arriba a abajo)
        LinearGradient degradado2 = new LinearGradient(0, 0, 0, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, javafx.scene.paint.Color.GREEN),  // Color en la parte superior
                new Stop(0.33, javafx.scene.paint.Color.GRAY),  // Color medio
                new Stop(0.66, javafx.scene.paint.Color.GRAY),  // Color medio
                new Stop(1, javafx.scene.paint.Color.WHITE)    // Color en la parte inferior
        );

        // Crear una Timeline para animar el cambio de degradado
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> miAnchorPane.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(degradado1, null, null)))),
                new KeyFrame(Duration.seconds(5), e -> miAnchorPane.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(degradado2, null, null))))
        );

        // Ciclar la animación
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);  // Hace que la animación se invierta
        timeline.play();

        //Animación de las Imagenes
        imgEstudiante.setOnMouseEntered(e -> {
            imgEstudiante.setScaleX(1.2);  // Aumenta el tamaño horizontalmente
            imgEstudiante.setScaleY(1.2);  // Aumenta el tamaño verticalmente
            imgEstudiante.setOpacity(0.8); // Cambia la opacidad
        });
        imgEstudiante.setOnMouseExited(e -> {
            imgEstudiante.setScaleX(1);  // Restaura el tamaño original
            imgEstudiante.setScaleY(1);  // Restaura el tamaño original
            imgEstudiante.setOpacity(1); // Restaura la opacidad original
        });
        imgAdmin.setOnMouseEntered(e -> {
            imgAdmin.setScaleX(1.2);  // Aumenta el tamaño horizontalmente
            imgAdmin.setScaleY(1.2);  // Aumenta el tamaño verticalmente
            imgAdmin.setOpacity(0.8); // Cambia la opacidad
        });
        imgAdmin.setOnMouseExited(e -> {
            imgAdmin.setScaleX(1);  // Restaura el tamaño original
            imgAdmin.setScaleY(1);  // Restaura el tamaño original
            imgAdmin.setOpacity(1); // Restaura la opacidad original
        });
        imgExterno.setOnMouseEntered(e -> {
            imgExterno.setScaleX(1.2);  // Aumenta el tamaño horizontalmente
            imgExterno.setScaleY(1.2);  // Aumenta el tamaño verticalmente
            imgExterno.setOpacity(0.8); // Cambia la opacidad
        });
        imgExterno.setOnMouseExited(e -> {
            imgExterno.setScaleX(1);  // Restaura el tamaño original
            imgExterno.setScaleY(1);  // Restaura el tamaño original
            imgExterno.setOpacity(1); // Restaura la opacidad original
        });

        // Cambiar color al pasar el mouse
        btnEstudiante.setOnMouseEntered(e -> btnEstudiante.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;"));
        btnEstudiante.setOnMouseExited(e -> btnEstudiante.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;"));

        btnAdmin.setOnMouseEntered(e -> btnAdmin.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;"));
        btnAdmin.setOnMouseExited(e -> btnAdmin.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;"));

        btnExterno.setOnMouseEntered(e -> btnExterno.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;"));
        btnExterno.setOnMouseExited(e -> btnExterno.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;"));
    }

}
