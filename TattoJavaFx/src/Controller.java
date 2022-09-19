
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import conect.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {    

    @FXML private TextField loginUser;
    @FXML private PasswordField loginPassword;
    @FXML private Label loginMensage;
    @FXML private Button loginSingUp;
    

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

	@FXML
	void ingresarBtn(ActionEvent event) throws SQLException{
		String login = loginUser.getText();
        String clave = loginPassword.getText();
        Conexion conect = new Conexion();
        conect.conectar();
        if(conect.isConectado()){
            String query = "SELECT codigo from usuarios where login = '"+login+"' AND clave = '"+clave+"'";
            try (Statement stm = conect.getCon().createStatement()){
                System.out.println("Estoy dentro try");
                ResultSet rst = stm.executeQuery(query);
                if(rst.next()){;
                    Stage stage = (Stage) loginSingUp.getScene().getWindow();
                    stage.close();
                    //Crear la nueva ventana
                    System.out.println("Estoy dentro home");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/home.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    stage = new Stage();
                    //stage.close();
                    stage.setTitle("Home");
                    stage.setScene(scene);
                    
                    stage.show();
                    conect.desconectar();
                    
                }
                else
                loginMensage.setText("Invalid User or Password");
                
            } catch (Exception e) {
                System.out.println("ERROR: Aborting...");
                e.printStackTrace();
    
        }
        }
	}
	
	
}



