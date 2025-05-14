package controllers;

import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import model.Salon;
import model.Admin;
import utils.Paths;

import java.util.Date;

public class CrudAdminController {
    @FXML
    private TableColumn<Admin, Integer> colEdad;

    @FXML
    private TableColumn<Admin, Date> colFecha;
    @FXML

    private TableColumn<Admin, String> colLicenciatura;

    @FXML
    private TableColumn<Admin, String> colMaterno;

    @FXML
    private TableColumn<Admin, String> colMatricula;

    @FXML
    private TableColumn<Admin, String> colNombre;

    @FXML
    private TableColumn<Admin, String> colPais;

    @FXML
    private TableColumn<Admin, String> colPaterno;

    @FXML
    private TableColumn<Admin, String> colSeguro;

    @FXML
    private TableColumn<Admin, String> colSexo;

    @FXML
    private ImageView imgHuellaTactil;

    @FXML
    private TableView<Admin> tblEstudiantes;

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


    private Salon salon;

    @FXML
    void cambiarInicio(ActionEvent event) {
        App.app.setScene(Paths.INICIO_VIEW);
    }

    @FXML
    void actualizarEstudiante(ActionEvent event) {

        Admin admin = new Admin();

        admin.setNombre(txtNombre.getText());
        admin.setApellidoPaterno(textApPaterno.getText());
        admin.setApellidoMaterno(textApMaterno.getText());
        admin.setEdad(Integer.parseInt(txtEdad.getText()));
        admin.setSexo(txtSexo.getText());
        admin.setMatricula(txtMatricula.getText());
        admin.setSeguroFacultativo(txtSeguro.getText());
        admin.setPais(txtPais.getText());
        admin.setLicenciatura(txtLicenciatura.getText());


        salon.actualizarEstudiante(admin);
        limpiarCampos();
        actualizarTabla();
    }

    @FXML
    void eliminarEstudiantes(ActionEvent event) {
      eliminarEstudiantes();
    }

    public void eliminarEstudiantes() {
        Admin admin = tblEstudiantes.getSelectionModel().getSelectedItem();
        salon.eliminarEstudiante(admin);

        actualizarTabla();
    }
    @FXML
    void guardarEstudiante(ActionEvent event) {
        guardarEstudiante();
    }

    public void guardarEstudiante() {
        Admin admin = new Admin();
        admin.setNombre(txtNombre.getText());
        admin.setApellidoPaterno(textApPaterno.getText());
        admin.setApellidoMaterno(textApMaterno.getText());
        admin.setEdad(Integer.parseInt(txtEdad.getText()));
        admin.setSexo(txtSexo.getText());
        admin.setMatricula(txtMatricula.getText());
        admin.setSeguroFacultativo(txtSeguro.getText());
        admin.setPais(txtPais.getText());
        admin.setLicenciatura(txtLicenciatura.getText());
        admin.setFechaRegistro(new Date());

        salon.agregarEstudiante(admin);

        actualizarTabla();
        limpiarCampos();
    }

    private void actualizarTabla() {
        tblEstudiantes.getItems().clear();
        tblEstudiantes.getItems().addAll(salon.getListaEstudiantes());
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
        Admin admin = tblEstudiantes.getSelectionModel().getSelectedItem();

        txtNombre.setText(admin.getNombre());
        textApPaterno.setText(admin.getApellidoPaterno());
        textApMaterno.setText(admin.getApellidoMaterno());
        txtEdad.setText(Integer.toString(admin.getEdad()));
        txtSexo.setText(admin.getSexo());
        txtMatricula.setText(admin.getMatricula());
        txtSeguro.setText(admin.isSeguroFacultativo());
        txtPais.setText(admin.getPais());
        txtLicenciatura.setText(admin.getLicenciatura());


            txtMatricula.setEditable(false);


    }

    public Salon getEscuela() {
        return salon;
    }

    public void setEscuela(Salon salon) {
        this.salon = salon;
    }

    public ImageView getImgHuellaTactil() {
        return imgHuellaTactil;
    }

    public void setImgHuellaTactil(ImageView imgHuellaTactil) {
        this.imgHuellaTactil = imgHuellaTactil;
    }
}
