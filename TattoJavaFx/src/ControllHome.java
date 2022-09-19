import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import conect.Conexion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class ControllHome {    

    @FXML private Button sales;
    @FXML private Button productos;
    @FXML private Button clientes;
    @FXML private Button signOff;

////////////////////////////////////     C E R R A R    S E S I O N      /////////////////////////
    @FXML
	void closSignOff(ActionEvent event) throws SQLException{
        try {
            ///////////////////////////////////////////////////
            Stage stage = (Stage) signOff.getScene().getWindow(); 
            stage.close(); //CERRAR LA PESTAÑA DE HOME
            Parent root = (new FXMLLoader(getClass().getResource("fxml/login.fxml"))).load();
            Scene scene =  new Scene(root);
            Stage teatro = new Stage();
            teatro.setScene(scene);
            teatro.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
	void opensVentas(ActionEvent event) throws SQLException{
        try {
            ///////////////////////////////////////////////////
            Stage stage = (Stage) sales.getScene().getWindow(); 
            stage.close(); //CERRAR LA PESTAÑA DE HOME
            Parent root = (new FXMLLoader(getClass().getResource("fxml/ventas.fxml"))).load();
            Scene scene =  new Scene(root);
            Stage teatro = new Stage();
            teatro.setTitle("Ventas");
            teatro.setScene(scene);
            teatro.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    void opensProductos(ActionEvent event) throws SQLException{
        try {
            ///////////////////////////////////////////////////
            Stage stage = (Stage) productos.getScene().getWindow(); 
            stage.close(); //CERRAR LA PESTAÑA DE HOME

            Parent root = (new FXMLLoader(getClass().getResource("fxml/gestionProductos.fxml"))).load();
            Scene scene =  new Scene(root);
            Stage teatro = new Stage();
            teatro.setTitle("Productos");
            teatro.setScene(scene);
            teatro.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    String dato, query;
    Conexion conect = new Conexion();


    
////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////        V   E   N   T   A   S        //////////////////////////////////////////

    @FXML private Button btnGuardar;

    @FXML private ComboBox<String> cmbClient;

    @FXML private ComboBox<String> cmbProduct;

    @FXML private TextField txtTatto;

    @FXML private Button home;


    @FXML
    void opensHome(ActionEvent event) throws SQLException{
        try {
            ///////////////////////////////////////////////////
            Stage stage = (Stage) home.getScene().getWindow(); 
            stage.close(); //CERRAR LA PESTAÑA DE HOME

            Parent root = (new FXMLLoader(getClass().getResource("fxml/home.fxml"))).load();
            Scene scene =  new Scene(root);
            Stage teatro = new Stage();
            teatro.setScene(scene);
            teatro.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void opensClientes(ActionEvent event) throws SQLException{
        try {
            ///////////////////////////////////////////////////
            Stage stage = (Stage) clientes.getScene().getWindow(); 
            stage.close(); //CERRAR LA PESTAÑA DE HOME

            Parent root = (new FXMLLoader(getClass().getResource("fxml/gestionClientes.fxml"))).load();
            Scene scene =  new Scene(root);
            Stage teatro = new Stage();
            teatro.setTitle("Clientes");
            teatro.setScene(scene);
            teatro.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    void clickGuardar(MouseEvent event) throws SQLException {
        String cliente = cmbClient.getValue();
        String producto = cmbProduct.getValue();
        String tipoTatto = txtTatto.getText();

        if (cliente==null || cliente.isEmpty()) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setTitle("Data validation");
            alerta.setContentText("Please select a customer");
            alerta.showAndWait();
        }
        else if(producto==null || producto.isEmpty()){
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setTitle("Data validation");
            alerta.setContentText("Please select a product");
            alerta.showAndWait();
        }
        else if(tipoTatto== null || tipoTatto.isEmpty()){ 
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Data validation");
            alerta.setContentText("Please indicate the number of tattoos to be tattooed ");
            alerta.showAndWait();
        }
        else{
            //Conectarme a la base de datos
            conect.conectar();
            try (Statement stm = conect.getCon().createStatement()){
                String[] tmp = cliente.split(" ");
                int clie = Integer.parseInt(tmp[0]);
                tmp = producto.split(" ");
                int prod = Integer.parseInt(tmp[0]);
                String cant = tipoTatto;
                System.out.println("UPDATE======================");
                query = "INSERT INTO ventas(cliente,producTatto,cantidadTatto) VALUES ("+clie+","+prod+","+cant+")";
                int resu = stm.executeUpdate(query);
                if(resu != 0){
                    System.out.println("Datos Insertados con exito");
                    txtTatto.clear();
                }
                else{
                    System.out.println("Error al registrar la venta");
                }
                
            } catch (Exception e) {
            }
            
        }
        
    }

    
    @FXML
    void initialize() throws IOException, SQLException{
        //Declaro variable
        ResultSet rst;
        //Conectarme a la base de datos        
        conect.conectar();
        System.out.println("Voy bien antes del combo");
        //Preparar para recuperar datos de la base de datos. Tabla clientes
        query = "SELECT idClientes,nombre,apellidos from clientes order by apellidos,nombre";
        try (Statement stm = conect.getCon().createStatement()){ //Preparo el area para las consultas
            rst = stm.executeQuery(query);
            System.out.println("Voy bien dentro combo");
            while (rst.next()) {
                dato = String.format("%d %s %s", rst.getInt("idClientes"), rst.getString("nombre"), rst.getString("apellidos"));
                cmbClient.getItems().add(dato);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //Preparar para recuperar datos de la base de datos. Tabla Productos
        query = "SELECT codigo,nombre from ptattos order by nombre";
        try (Statement stm = conect.getCon().createStatement()){ //Preparo el area para las consultas
            rst = stm.executeQuery(query);
            while (rst.next()) {
                dato = String.format("%d %s", rst.getInt("codigo"), rst.getString("nombre"));
                cmbProduct.getItems().add(dato);
            }
        } catch (Exception e) {
        }
        
    }
}



	




