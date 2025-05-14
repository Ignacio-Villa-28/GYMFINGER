package controllers;

import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import model.Casa;
import model.Externo;
import utils.Paths;

import java.util.Date;

public class CrudExternoController {
    @FXML
    private TableColumn<Externo, Integer> colEdad;

    @FXML
    private TableColumn<Externo, Date> colFecha;
    @FXML

    private TableColumn<Externo, String> colLicenciatura;

    @FXML
    private TableColumn<Externo, String> colMaterno;

    @FXML
    private TableColumn<Externo, String> colMatricula;

    @FXML
    private TableColumn<Externo, String> colNombre;

    @FXML
    private TableColumn<Externo, String> colPais;

    @FXML
    private TableColumn<Externo, String> colPaterno;

    @FXML
    private TableColumn<Externo, String> colSeguro;

    @FXML
    private TableColumn<Externo, String> colSexo;

    @FXML
    private ImageView imgHuellaTactil;

    @FXML
    private TableView<Externo> tblEstudiantes;

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


    private Casa casa;

    @FXML
    void cambiarInicio(ActionEvent event) {
        App.app.setScene(Paths.INICIO_VIEW);
    }

    @FXML
    void actualizarEstudiante(ActionEvent event) {

        Externo externo = new Externo();

        externo.setNombre(txtNombre.getText());
        externo.setApellidoPaterno(textApPaterno.getText());
        externo.setApellidoMaterno(textApMaterno.getText());
        externo.setEdad(Integer.parseInt(txtEdad.getText()));
        externo.setSexo(txtSexo.getText());
        externo.setMatricula(txtMatricula.getText());
        externo.setSeguroFacultativo(txtSeguro.getText());
        externo.setPais(txtPais.getText());
        externo.setLicenciatura(txtLicenciatura.getText());


        casa.actualizarEstudiante(externo);
        limpiarCampos();
        actualizarTabla();
    }

    @FXML
    void eliminarEstudiantes(ActionEvent event) {
      eliminarEstudiantes();
    }

    public void eliminarEstudiantes() {
        Externo externo = tblEstudiantes.getSelectionModel().getSelectedItem();
        casa.eliminarEstudiante(externo);

        actualizarTabla();
    }
    @FXML
    void guardarEstudiante(ActionEvent event) {
        guardarEstudiante();
    }

    public void guardarEstudiante() {
        Externo externo = new Externo();
        externo.setNombre(txtNombre.getText());
        externo.setApellidoPaterno(textApPaterno.getText());
        externo.setApellidoMaterno(textApMaterno.getText());
        externo.setEdad(Integer.parseInt(txtEdad.getText()));
        externo.setSexo(txtSexo.getText());
        externo.setMatricula(txtMatricula.getText());
        externo.setSeguroFacultativo(txtSeguro.getText());
        externo.setPais(txtPais.getText());
        externo.setLicenciatura(txtLicenciatura.getText());
        externo.setFechaRegistro(new Date());

        casa.agregarEstudiante(externo);

        actualizarTabla();
        limpiarCampos();
    }

    private void actualizarTabla() {
        tblEstudiantes.getItems().clear();
        tblEstudiantes.getItems().addAll(casa.getListaEstudiantes());
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
        Externo externo = tblEstudiantes.getSelectionModel().getSelectedItem();

        txtNombre.setText(externo.getNombre());
        textApPaterno.setText(externo.getApellidoPaterno());
        textApMaterno.setText(externo.getApellidoMaterno());
        txtEdad.setText(Integer.toString(externo.getEdad()));
        txtSexo.setText(externo.getSexo());
        txtMatricula.setText(externo.getMatricula());
        txtSeguro.setText(externo.isSeguroFacultativo());
        txtPais.setText(externo.getPais());
        txtLicenciatura.setText(externo.getLicenciatura());


            txtMatricula.setEditable(false);


    }

    public Casa getEscuela() {
        return casa;
    }

    public void setEscuela(Casa casa) {
        this.casa = casa;
    }

    public ImageView getImgHuellaTactil() {
        return imgHuellaTactil;
    }

    public void setImgHuellaTactil(ImageView imgHuellaTactil) {
        this.imgHuellaTactil = imgHuellaTactil;
    }
}
