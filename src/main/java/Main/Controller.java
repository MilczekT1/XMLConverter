package Main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Controller implements Initializable {
    
    private static File xmlFile;
    private static String xmlPath;
    public static String getXmlPath() {
        return xmlPath;
    }
    
    @FXML
    private TextField fileSourceTextField = new TextField();
    @FXML
    private Button importXmlButton;
    
    public void importXmlFile(){
        Main.convertXMLToDB();
        importXmlButton.setDisable(true);
    }
    public void handleXMLSourceDragDropped(DragEvent event){
        List<File> file = event.getDragboard().getFiles();
        if (file.get(0).getPath().endsWith(".xml")) {
            xmlFile = file.get(0);
            xmlPath = xmlFile.getPath();
    
            fileSourceTextField.setText(xmlPath);
            importXmlButton.setDisable(false);
        }
    }
    public void handleXMLSourceDragOver(DragEvent event){
        if(event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }
    //---------------------------------------------------------------------------------------------
    @FXML
    private Button exportXmlFileButton;
    @FXML
    private TextField sentNumberTextField;
    @FXML
    private Label exportCommentLabel;
    
    public void exportXmlFile() throws IOException{
        String sentNumber = sentNumberTextField.getText();
        exportCommentLabel.setText("");
        Main.exportDataFromDb(sentNumber);
        Main.createXMLFileFromExistingDataContainer();
    }
    
    public void enableExportXmlFileButton(){
        if (!Pattern.matches("", sentNumberTextField.getText())) {
            if (Pattern.matches("SENT[0-9]{14}", sentNumberTextField.getText())) {
                exportXmlFileButton.setDisable(false);
                exportCommentLabel.setText("");
            } else {
                exportCommentLabel.setText("WRONG SENT, should be \"SENT\" and 14 numbers");
                exportXmlFileButton.setDisable(true);
            }
        }
    }
    //---------------------------------------------------------------------------------------------
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exportXmlFileButton.setDisable(true);
        exportCommentLabel.setText("");
    }
}
