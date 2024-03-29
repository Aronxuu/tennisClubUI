/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import model.Club;
import static model.Club.getInstance;
import model.ClubDAO;
import model.ClubDAOException;
import model.Member;

/**
 *
 * @author jsoler
 */
public class FXMLEditController implements Initializable {
    //========================================================
    // objects defined into FXML file with fx:id 
    @FXML
    private Button register;
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField nickField;
    @FXML
    private TextField telephoneField;
    private PasswordField passwordField;
    private PasswordField passwordField2;
    @FXML
    private TextField cardField;
    @FXML
    private TextField numberField;
    @FXML
    private Label nickError;
    @FXML
    private Label pasError;
    @FXML
    private Label telError;
    @FXML
    private Label cardError;
    @FXML
    private Label cardError1;
    private Image im;
    private Club club;
    @FXML
    private PasswordField passwordFiled2;
    @FXML
    private TextField nameField3;
    @FXML
    private PasswordField passwordFiled3;
    @FXML
    private Label nameError1;
    @FXML
    private Label passwordError;
    @FXML
    private Label surnameError;
    @FXML
    private ImageView userImage;
    
    private boolean hasValidCard = false;
    @FXML
    private Button backButton;
    @FXML
    private Label imError;
    public String loggeduser;
    public String pwd;
    private Member user;
    //=========================================================
    // event handler, fired when button is clicked or 
    //                      when the button has the focus and enter is pressed
    @FXML
    private void handleRegister(ActionEvent event) throws IOException {
        
        boolean nerror = true;
        nickError.setVisible(false);
        pasError.setVisible(false);
        telError.setVisible(false);
        cardError.setVisible(false);
        cardError1.setVisible(false);
        surnameError.setVisible(false);
        nameError1.setVisible(false);
        passwordError.setVisible(false);
        
        Image image = chooseImage(); 
        String name = nameField3.getText();
        String surname = surnameField.getText();
        String nick = nickField.getText();
        String tel = telephoneField.getText();
        String password = passwordFiled3.getText();
        String password2 = passwordFiled2.getText();
        String card = cardField.getText();
        String number = numberField.getText();
        String as = "";
         try {
            club = getInstance();
        } catch (ClubDAOException ex) {
            Logger.getLogger(FXMLSignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }

       
        if(!password.equals(password2)){
            pasError.setVisible(true);
            nerror = false;
        }
        if(tel.length() != 9 || !tel.matches("[0-9]+")){
            telError.setVisible(true);
            nerror = false;
        }
        if(!card.equals(as) && !number.equals(as)){
        if(((card.length() != 16 && !card.equals(as)) || !card.matches("[0-9]+")) || ((number.length() != 3 && !number.equals(as))  || !number.matches("[0-9]+"))){
            cardError.setVisible(true);
            nerror = false;
            cardError1.setVisible(true);
            nerror = false;
        }
        }else{
            if(card.equals(as) && !number.equals(as)){
                cardError.setVisible(true);
            nerror = false;
            cardError1.setVisible(true);
            nerror = false;
            }
            
            if(!card.equals(as) && number.equals(as)){
                cardError.setVisible(true);
            nerror = false;
            cardError1.setVisible(true);
            nerror = false;
            }
        }
        if(as.equals(name)){
            nameError1.setVisible(true);
            nerror = false;
        }
        if(as.equals(surname)){
            surnameError.setVisible(true);
            nerror = false;
        }
        if(as.equals(password)){
            passwordError.setVisible(true);
            nerror = false;
        }
        int num = 0;
        try{
            if(!number.equals("")) num = Integer.parseInt(number);
        }catch(NumberFormatException e){
            cardError1.setVisible(true);
            nerror = false;
        }
        if(nerror){
                user.setName(name);
                user.setSurname(surname);
                user.setNickName(nick);
                user.setPassword(password);
                user.setCreditCard(card);
                user.setSvc(num);
                user.setImage(image);
                loggeduser = nick;
                pwd = password;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Modificación Exitosa");
                alert.setHeaderText("Datos actualizados correctamente");
                alert.show();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/archivosfxml/pistas.fxml"));
        loader.setControllerFactory(controllerClass -> {
        PistasController controller = new PistasController();
        controller.setLogin(loggeduser,pwd);
        return controller;
        
        });
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        }}


    
    private Image chooseImage() throws IOException {
        if(im != null){ return im;}
        Image image;
        image = new Image("/images/default.png");
        return image;
    }
    FileChooser fileChooser = new FileChooser();
    @FXML
    private void getImage(ActionEvent Event) throws IOException{
        try{
        File file = fileChooser.showOpenDialog(new Stage());
        BufferedImage image = ImageIO.read(file);
        Image image1 = SwingFXUtils.toFXImage(image, null);
        im = image1;
        userImage.setImage(image1);
        imError.setVisible(false);
        }catch(Exception e){
            imError.setVisible(true);
        }
    }

    
    //=========================================================
    // you must initialize here all related with the object 
    public void setLogin(String login,String pass){
        loggeduser = login;
        pwd = pass;
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            club = getInstance();
        } catch (ClubDAOException | IOException ex) {
            Logger.getLogger(PistasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String a = loggeduser;
        String b = pwd;
        userImage.setImage(club.getMemberByCredentials(a, b).getImage());
        im = club.getMemberByCredentials(a, b).getImage();
        user = club.getMemberByCredentials(loggeduser, pwd);
        nameField3.setText(user.getName());
        surnameField.setText(user.getSurname());
        nickField.setText(a);
        nickField.setDisable(true);
        telephoneField.setText(user.getTelephone());
        passwordFiled3.setText(b);
        passwordFiled2.setText(b);
        cardField.setText(user.getCreditCard());
        if(user.getSvc()!=0){numberField.setText(String.valueOf(user.getSvc()));}
        loggeduser = a;
        pwd = b;
    }    

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/archivosfxml/pistas.fxml"));
    loader.setControllerFactory(controllerClass -> {

        PistasController controller = new PistasController();
        controller.setLogin(loggeduser,pwd);
        return controller;
        
        });
    Parent root = loader.load();
    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    }

  



}
