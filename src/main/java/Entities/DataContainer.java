package Entities;

import Main.Controller;
import lombok.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ToString
@Data
public class DataContainer {
    
    protected String xmlSENTHeader;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd+HH:mm");
    
    // Entities
    private KeyNumberEntity keyNumberEntity;
    private RegisteredSENTInfoEntity registeredSENTInfoEntity;
    private GoodsSenderEntity goodsSenderEntity;
    private GoodsRecipientEntity goodsRecipientEntity;
    private PlaceOfLoadingEntity placeOfLoadingEntity;
    private PlaceOfDeliveryEntity placeOfDeliveryEntity;
    private CarrierEntity carrierEntity;
    private GoodsInformationEntity goodsInformationEntity;
    private MeanOfTransportEntity meanOfTransportEntity;
    
    public void getAllDataFromXml(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            File xmlFile = new File(Controller.getXmlPath());
            Document xmlDocument = builder.parse(xmlFile);
            
            XPathFactory xpfactory = XPathFactory.newInstance();
            XPath xPath = xpfactory.newXPath();
            Node node = null;
            
            xmlSENTHeader = setXmlSENTHeader(); // "/SENT_XXX"
            
            importDataToKeyNumberEntity(node, xPath, xmlDocument);
            importDataToRegisteredSENTInfoEntity(node, xPath, xmlDocument);
            importDataToGoodsSenderEntity(node, xPath, xmlDocument);
            importDataToGoodsRecipientEntity(node, xPath, xmlDocument);
            importDataToPlaceOfLoadingEntity(node, xPath, xmlDocument);
            importDataToPlaceOfDeliveryEntity(node, xPath, xmlDocument);
            importDataToCarrierEntity(node, xPath, xmlDocument);
            importDataToGoodsInformationEntity(node, xPath, xmlDocument);
            importDataToMeanOfTransportEntity(node, xPath, xmlDocument);
            
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (SAXException e){
            e.printStackTrace();
        }
    }
    
    private String setXmlSENTHeader() throws IOException {
        Path path = Paths.get(Controller.getXmlPath());
        List<String> list = Files.readAllLines(path);
        String lastRow = list.get(list.size()-1);
        return "/" + lastRow.substring(lastRow.length()-9,lastRow.length()-1); // "/SENT_XXX"
    }
    private static String getSingleStringFromXml(Node node, XPath xpath, Document xmlDocument, String what){
        try {
            node = (Node) xpath.evaluate(what, xmlDocument, XPathConstants.NODE);
            return node.getTextContent();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            return null;
        }
        return null;
    }
    
    private void importDataToKeyNumberEntity(Node node, XPath xpath, Document xmlDocument){
        keyNumberEntity = new KeyNumberEntity();
        //------------------------------- encja keynumber
        keyNumberEntity.setSentNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/SentNumber"));
        keyNumberEntity.setSenderKey(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/KeyNumber/SenderKey"));
        keyNumberEntity.setRecipientKey(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/KeyNumber/RecipientKey"));
        keyNumberEntity.setCarrierKey(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/KeyNumber/CarrierKey"));
        keyNumberEntity.setComments(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Comments"));
        // usefull info to generate root
        keyNumberEntity.setSentroot(getXmlSENTHeader().substring(1));
    }
    private void importDataToRegisteredSENTInfoEntity(Node node, XPath xpath, Document xmlDocument){
        registeredSENTInfoEntity = new RegisteredSENTInfoEntity();
        registeredSENTInfoEntity.setSentNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/SentNumber"));
        registeredSENTInfoEntity.setCreationDate(ZonedDateTime.parse(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/RegisteredSentInfo/CreationDate")));
        registeredSENTInfoEntity.setCreator(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/RegisteredSentInfo/Creator"));
        registeredSENTInfoEntity.setModificationDate(ZonedDateTime.parse(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/RegisteredSentInfo/ModificationDate")));
        registeredSENTInfoEntity.setChecksumOfSourceDocument(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/RegisteredSentInfo/ChecksumOfSourceDocument"));
        registeredSENTInfoEntity.setSentId(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/RegisteredSentInfo/SentId"));
        registeredSENTInfoEntity.setSentStatus(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/RegisteredSentInfo/SentStatus"));
        registeredSENTInfoEntity.setModifier(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/RegisteredSentInfo/Modifier"));
        registeredSENTInfoEntity.setSourceDocumentId(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/RegisteredSentInfo/SourceDocumentId"));
    }
    private void importDataToGoodsSenderEntity(Node node, XPath xpath, Document xmlDocument){
        goodsSenderEntity = new GoodsSenderEntity();
        goodsSenderEntity.setSentNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/SentNumber"));
        goodsSenderEntity.setIdSisc(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsSender/TraderInfo/IdSisc"));
        goodsSenderEntity.setTraderName(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsSender/TraderInfo/TraderName"));
        goodsSenderEntity.setTraderIdentityType(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsSender/TraderInfo/TraderIdentityType"));
        goodsSenderEntity.setTraderIdentityNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsSender/TraderInfo/TraderIdentityNumber"));
        
        goodsSenderEntity.setStreet(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsSender/TraderAddress/Street"));
        goodsSenderEntity.setHouseNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsSender/TraderAddress/HouseNumber"));
        goodsSenderEntity.setFlatNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsSender/TraderAddress/FlatNumber"));
        
        goodsSenderEntity.setCity(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsSender/TraderAddress/City"));
        goodsSenderEntity.setCountry(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsSender/TraderAddress/Country"));
        goodsSenderEntity.setPostalCode(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsSender/TraderAddress/PostalCode"));
    }
    private void importDataToGoodsRecipientEntity(Node node, XPath xpath, Document xmlDocument){
        goodsRecipientEntity = new GoodsRecipientEntity();
        goodsRecipientEntity.setSentNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/SentNumber"));
        goodsRecipientEntity.setIdSisc(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsRecipient/TraderInfo/IdSisc"));
        goodsRecipientEntity.setTraderName(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsRecipient/TraderInfo/TraderName"));
        goodsRecipientEntity.setTraderIdentityType(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsRecipient/TraderInfo/TraderIdentityType"));
        goodsRecipientEntity.setTraderIdentityNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsRecipient/TraderInfo/TraderIdentityNumber"));
        
        goodsRecipientEntity.setStreet(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsRecipient/TraderAddress/Street"));
        goodsRecipientEntity.setHouseNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsRecipient/TraderAddress/HouseNumber"));
        goodsRecipientEntity.setFlatNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsRecipient/TraderAddress/FlatNumber"));
        
        goodsRecipientEntity.setCity(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsRecipient/TraderAddress/City"));
        goodsRecipientEntity.setCountry(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsRecipient/TraderAddress/Country"));
        goodsRecipientEntity.setPostalCode(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsRecipient/TraderAddress/PostalCode"));
    }
    private void importDataToPlaceOfLoadingEntity(Node node, XPath xpath, Document xmlDocument){
        placeOfLoadingEntity = new PlaceOfLoadingEntity();
        placeOfLoadingEntity.setSentNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/SentNumber"));
        placeOfLoadingEntity.setStreet(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Transport/PlaceOfLoading/Street"));
        placeOfLoadingEntity.setHouseNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Transport/PlaceOfLoading/HouseNumber"));
        placeOfLoadingEntity.setFlatNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Transport/PlaceOfLoading/FlatNumber"));
        placeOfLoadingEntity.setCity(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Transport/PlaceOfLoading/City"));
        placeOfLoadingEntity.setCountry(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Transport/PlaceOfLoading/Country"));
        placeOfLoadingEntity.setPostalCode(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Transport/PlaceOfLoading/PostalCode"));
    }
    private void importDataToPlaceOfDeliveryEntity(Node node, XPath xpath, Document xmlDocument){
        placeOfDeliveryEntity = new PlaceOfDeliveryEntity();
        placeOfDeliveryEntity.setSentNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/SentNumber"));
        try {
            placeOfDeliveryEntity.setPlannedTransportDate(LocalDateTime.parse(getSingleStringFromXml(node, xpath, xmlDocument, xmlSENTHeader + "/Transport/PlannedStartCarriageDate"), formatter));
        }
        catch( NullPointerException e){ placeOfDeliveryEntity.setPlannedTransportDate(null); }
        try {
            placeOfDeliveryEntity.setStartTransportDate(LocalDateTime.parse(getSingleStringFromXml(node, xpath, xmlDocument, xmlSENTHeader + "/Transport/StartTransportDate"), formatter));
        }
        catch(NullPointerException e){ placeOfDeliveryEntity.setStartTransportDate(null); }
        try {
            placeOfDeliveryEntity.setEndTransportDate(LocalDateTime.parse(getSingleStringFromXml(node, xpath, xmlDocument, xmlSENTHeader + "/Transport/EndTransportDate"), formatter));
        }catch (NullPointerException e){ placeOfDeliveryEntity.setEndTransportDate(null); }
        
        placeOfDeliveryEntity.setStreet(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Transport/PlaceOfDelivery/Street"));
        placeOfDeliveryEntity.setHouseNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Transport/PlaceOfDelivery/HouseNumber"));
        placeOfDeliveryEntity.setCity(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Transport/PlaceOfDelivery/City"));
        placeOfDeliveryEntity.setCountry(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Transport/PlaceOfDelivery/Country"));
        placeOfDeliveryEntity.setPostalCode(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Transport/PlaceOfDelivery/PostalCode"));
    }
    private void importDataToCarrierEntity(Node node, XPath xpath, Document xmlDocument){
        carrierEntity = new CarrierEntity();
        carrierEntity.setSentNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/SentNumber"));
        carrierEntity.setIdSisc(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Carrier/TraderInfo/IdSisc"));
        carrierEntity.setTraderName(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Carrier/TraderInfo/TraderName"));
        carrierEntity.setTraderIdentityType(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Carrier/TraderInfo/TraderIdentityType"));
        carrierEntity.setTraderIdentityNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Carrier/TraderInfo/TraderIdentityNumber"));
        
        carrierEntity.setStreet(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Carrier/TraderAddress/Street"));
        carrierEntity.setHouseNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Carrier/TraderAddress/HouseNumber"));
        carrierEntity.setCity(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Carrier/TraderAddress/City"));
        carrierEntity.setCountry(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Carrier/TraderAddress/Country"));
        carrierEntity.setPostalCode(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/Carrier/TraderAddress/PostalCode"));
    }
    private void importDataToGoodsInformationEntity(Node node, XPath xpath, Document xmlDocument){
        goodsInformationEntity = new GoodsInformationEntity();
        goodsInformationEntity.setSentNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/SentNumber"));
        goodsInformationEntity.setCodeCNClassification(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsInformation/CodeCnClassification"));
        goodsInformationEntity.setCodePKWIUClassification(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsInformation/CodePkwiuClassification"));
        goodsInformationEntity.setGoodsName(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsInformation/GoodsName"));
        goodsInformationEntity.setAmountOfGoods(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsInformation/AmountOfGoods"));
        goodsInformationEntity.setUnitOfMeasure(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsInformation/UnitOfMeasure"));
        goodsInformationEntity.setGrossWeightOfGoods(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsInformation/GrossWeightOfGoods"));
        goodsInformationEntity.setTypeOfTransportDocument(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsInformation/TypeOfTransportDocument"));
        goodsInformationEntity.setNumberOfTransportDocument(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/GoodsInformation/NumberOfTransportDocument"));
    }
    private void importDataToMeanOfTransportEntity(Node node, XPath xpath, Document xmlDocument) {
        meanOfTransportEntity = new MeanOfTransportEntity();
        meanOfTransportEntity.setSentNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/SentNumber"));
        meanOfTransportEntity.setPermitRoad(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/MeansOfTransport/PermitRoad"));
        meanOfTransportEntity.setTruckNumber(getSingleStringFromXml(node,xpath,xmlDocument,xmlSENTHeader + "/MeansOfTransport/TruckNumber"));
    }
}
