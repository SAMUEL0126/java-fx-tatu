import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.sql.ResultSet;
import java.sql.Statement;

import conect.Conexion;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllClientes {
    String estadoCliente;
    int codigoCliente;
    Conexion con = new Conexion();
    
    @FXML private TextField txtIdentifi;
    @FXML private TextField txtName;
    @FXML private TextField txtApellido;
    

    @FXML private ImageView buscarImg;
    @FXML private TextField textfieldRespuesta;



    @FXML private Button btnDelete;
    @FXML private Button btnNew;
    @FXML private Button btnUpdate;

    @FXML private ComboBox<String> cmbGenero;

    String query;




    @FXML
    void buscarImgClick(MouseEvent event) throws SQLException {
        String iden = txtIdentifi.getText();
        if (iden==null || iden.isEmpty()) {
            textfieldRespuesta.setText("Digite una identificación valida para poder buscar");
        }
        else{
            con.conectar();
            try(Statement stm = con.getCon().createStatement()){
                int ident = Integer.parseInt(txtIdentifi.getText());
                ResultSet rta = stm.executeQuery("select * from clientes where cedula = '"+ident+"'");
                if(rta.next()){
                    codigoCliente = rta.getInt("idClientes");
                    estadoCliente = rta.getString("estado");
                    txtName.setText(rta.getString("nombre"));
                    txtApellido.setText(rta.getString("apellidos"));
                    cmbGenero.setValue(rta.getString("genero"));
                    if (estadoCliente.equalsIgnoreCase("A")) {
                        btnDelete.setText("Borrar");
                    } else {
                        btnDelete.setText("Recuperar");
                    }
                    btnDelete.setDisable(false);
                    btnUpdate.setDisable(false);
                    textfieldRespuesta.clear();
                }
                else
                    textfieldRespuesta.setText("No se encontro registro que coincida con la identificación");
            }
            con.desconectar();
        }

    }

    @FXML
    void actionUpdate(MouseEvent event) throws SQLException {        
        String ide = txtIdentifi.getText();
        String nom = txtName.getText();
        String ape = txtApellido.getText();
        String gen = cmbGenero.getValue();
        
        if(ide==null || ide.isEmpty())
            textfieldRespuesta.setText("Debe ingresar una Identificacion valida");
        else if(nom == null || nom.isEmpty())
            textfieldRespuesta.setText("Debe ingresar un Nombre valido");
        else if(ape == null || ape.isEmpty())
            textfieldRespuesta.setText("Debe ingresar un Apellido valido");
        else if(gen == null || gen.isEmpty())
            textfieldRespuesta.setText("Debe ingresar un genero valido");
        else{
            String query1 = "UPDATE clientes set cedula = '"+ide+"', nombre = '"+nom+"' , apellidos = '"+ape+"', genero = '"+gen+"' WHERE cedula = "+ide;
            con.conectar();
            System.out.println("voy bien");
            try(Statement stm = con.getCon().createStatement()){
            //Ejecuta comando de accion
            int res = stm.executeUpdate(query1);
            if(res!=0){
                textfieldRespuesta.setText("Registro Actualizado con exito");
            }else
                textfieldRespuesta.setText("Error al Actualizar registro");
            restaurarDatos();
            } 
            con.desconectar();
        }

    }

    @FXML
    void actionDelete(MouseEvent event) throws SQLException {
        //Recuperar el texto del boton
        //Preparar la consulta a utilizar (Borrar --> estado=I, recuperar--> estado =A)
        //Ejecuta la consulta
        String acc = btnDelete.getText();

        if ("Borrar".equalsIgnoreCase(acc)) {
            query = "UPDATE clientes set estado = 'I' where idClientes="+codigoCliente;
        } else {
            query = "UPDATE clientes set estado = 'A' where idClientes="+codigoCliente;
        }
        con.conectar();
        try(Statement stm = con.getCon().createStatement()){
        //Ejecuta comando de accion
        int res = stm.executeUpdate(query);
        if(res!=0){
            textfieldRespuesta.setText("Registro Recuperado/Borrado con exito");
        }else
            textfieldRespuesta.setText("Error al recuperar/Borrar registro");
        restaurarDatos();
        }
        con.desconectar();
    }
    
    private void restaurarDatos(){
        txtName.clear();
        txtApellido.clear();
        txtIdentifi.clear();
        cmbGenero.setValue("M");
        btnDelete.setDisable(true);
        btnDelete.setText("Borrar");
        btnUpdate.setDisable(true);
    }


    @FXML
    void actionNew(MouseEvent event) throws SQLException {
        //Recuperar datos del formulario
        //Validar Datos
        //Preparar la insercion
        String ide = txtIdentifi.getText();
        String nom = txtName.getText();
        String ape = txtApellido.getText();
        String gen = cmbGenero.getValue();
        if(ide==null || ide.isEmpty())
            textfieldRespuesta.setText("Debe ingresar una Identificacion valida");
        else if(nom==null || nom.isEmpty())
            textfieldRespuesta.setText("Debe ingresar un Nombre valido");
        else if(ape==null || ape.isEmpty())
            textfieldRespuesta.setText("Debe ingresar un Apellido valido");
        else if(gen==null || gen.isEmpty())
            textfieldRespuesta.setText("Debe ingresar un genero valido");
        else{
            String query1 = "insert into clientes (cedula,nombre,apellidos,genero)values ('"+ide+"','"+nom+"','"+ape+"','"+gen+"')";
            con.conectar();
            try (Statement stm = con.getCon().createStatement()){
                int rest = stm.executeUpdate(query1);
                if(rest != 0){
                    textfieldRespuesta.setText("Datos Registrados con exito");
                    restaurarDatos();
                }
                else{
                    textfieldRespuesta.setText("Error al grabar los datos por favor verifique");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            con.desconectar();
        }

    }

    @FXML
    void initialize(){ // Metodo de javafx que sirve para inicializar combox y demas en java apenas se abra una ventana
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        // Para inicializar valores de combo de genero
        cmbGenero.getItems().clear();
        cmbGenero.getItems().addAll("M", "F");
        cmbGenero.setValue("Select your gender");
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////         H E A D E R       ////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////


    @FXML private Button sales;
    @FXML private Button productos;
    @FXML private Button clientes;
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




















}
