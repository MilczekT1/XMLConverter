package Main;

import Entities.DataContainer;
import Entities.DbQuerriesManager;
import Entities.XmlCreator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Cleanup;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main extends Application {
    
    private static DataContainer dataContainer = new DataContainer();
    private static Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        primaryStage.setTitle("XML Converter");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> System.exit(0));
    }
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void convertXMLToDB(){
        dataContainer.getAllDataFromXml();
        DbQuerriesManager.dbAddEntities(dataContainer);
    }
    public static void exportDataFromDb(String sentNumber) {
        dataContainer = DbQuerriesManager.dbExportDataUsingSENTNumber(sentNumber);
    }
    public static void createXMLFileFromExistingDataContainer() throws IOException{
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save XML");
        File xmlFile = chooser.showSaveDialog(root.getScene().getWindow());
        if (!xmlFile.exists()){
            xmlFile.createNewFile();
        }
        @Cleanup
        FileOutputStream out = new FileOutputStream(xmlFile);
        XmlCreator.createXMLFile(dataContainer,out);
    
        repairXML(xmlFile.getPath());
    }
    private static void repairXML(String path){
        // method is adding \n between elements and fixiing attributes in first 2 lines
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            // Add \n between elements
            String src2 = "><";
            String target2 = ">\n<";
            List<String> newEndOfLines = lines.map(line -> line.replaceAll(src2, target2)).collect(Collectors.toList());
            Files.write(Paths.get(path), newEndOfLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            // Add atribute to 1st line
            String src = "<\\?xml version=\"1\\.0\" encoding=\"UTF-8\"\\?>";
            String target = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
            List<String> fixedAttr = lines.map(line-> line.replaceAll(src,target))
                                            .collect(Collectors.toList());
            Files.write(Paths.get(path), fixedAttr);
        }
        catch (IOException e){
            ;
        }
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            List<String>smth = Files.readAllLines(Paths.get(path));
            String sent = smth.get(1).substring(1,9);
            if (sent.contains("112")){
                String before = "<SENT_112>";
                String after = "<SENT_112 xmlns:ns2=\"http://www.mf.gov.pl/SENT/2017/01/18/STypes.xsd\" xmlns=\"http://www.mf.gov.pl/SENT/2017/01/18/SENT_112.xsd\">";
                List<String> fixedAttr = lines.map(line-> line.replaceAll(before,after))
                                                 .collect(Collectors.toList());
                Files.write(Paths.get(path), fixedAttr);
            }
            else if (sent.contains("110")){
                String before = "<SENT_110>";
                String after = "<SENT_110 xmlns:ns2=\"http://www.mf.gov.pl/SENT/2017/01/18/STypes.xsd\" xmlns=\"http://www.mf.gov.pl/SENT/2017/01/18/SENT_110.xsd\">";
                List<String> fixedAttr = lines.map(line-> line.replaceAll(before,after))
                                                 .collect(Collectors.toList());
                Files.write(Paths.get(path), fixedAttr);
            }
        }
        catch (IOException e){
            ;
        }
    }
}
