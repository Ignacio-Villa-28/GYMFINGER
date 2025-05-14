package controllers;

import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import model.Escuela;
import model.Estudiante;
import utils.Paths;

import java.util.Date;

public class CrudEstudianteController {
    @FXML
    private TableColumn<Estudiante, Integer> colEdad;

    @FXML
    private TableColumn<Estudiante, Date> colFecha;
    @FXML

    private TableColumn<Estudiante, String> colLicenciatura;

    @FXML
    private TableColumn<Estudiante, String> colMaterno;

    @FXML
    private TableColumn<Estudiante, String> colMatricula;

    @FXML
    private TableColumn<Estudiante, String> colNombre;

    @FXML
    private TableColumn<Estudiante, String> colPais;

    @FXML
    private TableColumn<Estudiante, String> colPaterno;

    @FXML
    private TableColumn<Estudiante, String> colSeguro;

    @FXML
    private TableColumn<Estudiante, String> colSexo;

    @FXML
    private ImageView imgHuellaTactil;

    @FXML
    private TableView<Estudiante> tblEstudiantes;

    @FXML
    private TextField textApMaterno;

    @FXML
    private TextField textApPaterno;

    @FXML
    private TextField txtEdad;

    @FXML
    private TextField txtLicenciatura;

    @FXML
    private TextField txtMatricula;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPais;

    @FXML
    private TextField txtSeguro;

    @FXML
    private TextField txtSexo;


    private Escuela escuela;

    @FXML
    void cambiarInicio(ActionEvent event) {
        App.app.setScene(Paths.INICIO_VIEW);
    }

    @FXML
    void actualizarEstudiante(ActionEvent event) {

        Estudiante estudiante = new Estudiante();

        estudiante.setNombre(txtNombre.getText());
        estudiante.setApellidoPaterno(textApPaterno.getText());
        estudiante.setApellidoMaterno(textApMaterno.getText());
        estudiante.setEdad(Integer.parseInt(txtEdad.getText()));
        estudiante.setSexo(txtSexo.getText());
        estudiante.setMatricula(txtMatricula.getText());
        estudiante.setSeguroFacultativo(txtSeguro.getText());
        estudiante.setPais(txtPais.getText());
        estudiante.setLicenciatura(txtLicenciatura.getText());


        escuela.actualizarEstudiante(estudiante);
        limpiarCampos();
        actualizarTabla();
    }

    @FXML
    void eliminarEstudiantes(ActionEvent event) {
      eliminarEstudiantes();
    }

    public void eliminarEstudiantes() {
        Estudiante estudiante = tblEstudiantes.getSelectionModel().getSelectedItem();
        escuela.eliminarEstudiante(estudiante);

        actualizarTabla();
    }
    @FXML
    void guardarEstudiante(ActionEvent event) {
        guardarEstudiante();
    }

    public void guardarEstudiante() {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(txtNombre.getText());
        estudiante.setApellidoPaterno(textApPaterno.getText());
        estudiante.setApellidoMaterno(textApMaterno.getText());
        estudiante.setEdad(Integer.parseInt(txtEdad.getText()));
        estudiante.setSexo(txtSexo.getText());
        estudiante.setMatricula(txtMatricula.getText());
        estudiante.setSeguroFacultativo(txtSeguro.getText());
        estudiante.setPais(txtPais.getText());
        estudiante.setLicenciatura(txtLicenciatura.getText());
        estudiante.setFechaRegistro(new Date());

        escuela.agregarEstudiante(estudiante);

        actualizarTabla();
        limpiarCampos();
    }

    private void actualizarTabla() {
        tblEstudiantes.getItems().clear();
        tblEstudiantes.getItems().addAll(escuela.getListaEstudiantes());
        tblEstudiantes.refresh();
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        textApPaterno.setText("");
        textApMaterno.setText("");
        txtEdad.setText("");
        txtSexo.setText("");
        txtMatricula.setText("");
        txtSeguro.setText("");
        txtPais.setText("");
        txtLicenciatura.setText("");


        txtMatricula.setEditable(true);
    }

    @FXML
    void initialize() {
    colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    colPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
    colMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
    colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
    colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
    colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
    colSeguro.setCellValueFactory(new PropertyValueFactory<>("seguroFacultativo"));
    colPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
    colLicenciatura.setCellValueFactory(new PropertyValueFactory<>("licenciatura"));
    colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaRegistro"));


    tblEstudiantes.setOnMouseClicked(mouseEvent -> {
        if (tblEstudiantes.getSelectionModel().getSelectedItem() != null) cargarCampos();

    });
    }

    private void cargarCampos() {
        Estudiante estudiante = tblEstudiantes.getSelectionModel().getSelectedItem();

        txtNombre.setText(estudiante.getNombre());
        textApPaterno.setText(estudiante.getApellidoPaterno());
        textApMaterno.setText(estudiante.getApellidoMaterno());
        txtEdad.setText(Integer.toString(estudiante.getEdad()));
        txtSexo.setText(estudiante.getSexo());
        txtMatricula.setText(estudiante.getMatricula());
        txtSeguro.setText(estudiante.isSeguroFacultativo());
        txtPais.setText(estudiante.getPais());
        txtLicenciatura.setText(estudiante.getLicenciatura());


            txtMatricula.setEditable(false);


    }

    public Escuela getEscuela() {
        return escuela;
    }

    public void setEscuela(Escuela escuela) {
        this.escuela = escuela;
    }

    public ImageView getImgHuellaTactil() {
        return imgHuellaTactil;
    }

    public void setImgHuellaTactil(ImageView imgHuellaTactil) {
        this.imgHuellaTactil = imgHuellaTactil;
    }
}
