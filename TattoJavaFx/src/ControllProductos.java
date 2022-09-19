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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllProductos {
    String estadoProductos;
    int codeProduct;
    Conexion con = new Conexion();
    
    @FXML private TextField txtCod;
    @FXML private TextField txtNameT;
    @FXML private TextField txtPrecio;
    

    @FXML private ImageView buscarImg;
    @FXML private TextField textfieldRespuesta;


    @FXML private Button btnNew;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;

    String query;




    @FXML
    void buscarImgClick(MouseEvent event) throws SQLException {
        String iden = txtCod.getText();
        if (iden==null || iden.isEmpty()) {
            textfieldRespuesta.setText("Digite una identificación valida para poder buscar");
        }
        else{
            con.conectar();
            try(Statement stm = con.getCon().createStatement()){
                int cods = Integer.parseInt(txtCod.getText());
                ResultSet rta = stm.executeQuery("select * from ptattos where codigo = '"+cods+"'");
                if(rta.next()){
                    codeProduct = rta.getInt("codigo");
                    estadoProductos = rta.getString("estado");
                    txtNameT.setText(rta.getString("nombre"));
                    txtPrecio.setText(rta.getString("pVenta"));

                    if (estadoProductos.equalsIgnoreCase("A")) {
                        btnDelete.setText("Borrar");
                    } else {
                        btnDelete.setText("Recuperar");
                    }
                    btnDelete.setDisable(false);
                    btnUpdate.setDisable(false);
                    textfieldRespuesta.clear();
                }
                else
                    textfieldRespuesta.setText("No se encontro registro que coincida con ese CODIGO");
            }
            con.desconectar();
        }

    }

    @FXML
    void actionUpdate(MouseEvent event) throws SQLException {

        String nom = txtNameT.getText();
        String pre = txtPrecio.getText();
    
        if(nom == null || nom.isEmpty())
            textfieldRespuesta.setText("Debe ingresar un Nombre valido");
        else if(pre == null || pre.isEmpty())
            textfieldRespuesta.setText("Debe ingresar un Precio valido");
        else{
            String query1 = "UPDATE ptattos set nombre = '"+nom+"' , pVenta = '"+pre+ "'WHERE codigo = "+codeProduct;
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
        String acc = btnDelete.getText();

        if ("Borrar".equalsIgnoreCase(acc)) {
            query = "UPDATE ptattos set estado = 'I' where codigo="+ codeProduct;
        } else {
            query = "UPDATE ptattos set estado = 'A' where codigo="+ codeProduct;
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
        txtNameT.clear();
        txtPrecio.clear();
        txtCod.clear();
        btnDelete.setDisable(true);
        btnDelete.setText("Borrar");
        btnUpdate.setDisable(true);
    }


    @FXML
    void actionNew(MouseEvent event) throws SQLException {
        String codde = txtCod.getText();
        String nom = txtNameT.getText();
        String precio = txtPrecio.getText();
        if(codde==null || codde.isEmpty())
            textfieldRespuesta.setText("Debe ingresar una Identificacion valida");
        else if(nom==null || nom.isEmpty())
            textfieldRespuesta.setText("Debe ingresar un Nombre valido");
        else if(precio==null || precio.isEmpty())
            textfieldRespuesta.setText("Debe ingresar un precio valido");
        else{
            String query1 = "insert into ptattos(codigo,nombre,pVenta)values ('"+codde+"','"+nom+"','"+precio+"')";
            con.conectar();
            try (Statement stm = con.getCon().createStatement()){
                int rest = stm.executeUpdate(query1);
                if(rest != 0){
                    textfieldRespuesta.setText("Datos Registrados con exito");
                    restaurarDatos(); //SE VACIAN LOS CAMPOS 
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






    //////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////         H E A D E R      E X O T I C O    ////////////////////
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
