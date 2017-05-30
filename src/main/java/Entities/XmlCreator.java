package Entities;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class XmlCreator {
    
    public static void createXMLFile(DataContainer dc, FileOutputStream out){
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        try {
            XMLStreamWriter writer = factory.createXMLStreamWriter(out);
            writer.writeStartDocument("UTF-8","1.0");
            
            writeXMLContent(dc,writer);
            
            writer.writeEndDocument();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
    
    private static void writeXMLContent(DataContainer dc, XMLStreamWriter writer) throws XMLStreamException{
        writer.writeStartElement(dc.getKeyNumberEntity().getSentroot());
        
        writeSentNumber(dc,writer);
        writeKeyNumber(dc,writer);
        writeRegisteredSentInfo(dc,writer);
        writeGoodsSender(dc,writer);
        writeGoodsRecipient(dc,writer);
        writeCarrier(dc,writer);
        writeMeansOfTransport(dc,writer);
        writeTransport(dc,writer);
        writeGoodsInformation(dc,writer);
        writeComments(dc,writer);
        
        writer.writeEndElement();
    }
    
    private static void writeSentNumber(DataContainer dc, XMLStreamWriter writer) throws  XMLStreamException {
        writeElement("SentNumber",dc.getKeyNumberEntity().getSentNumber(),writer);
    }
    private static void writeKeyNumber(DataContainer dc, XMLStreamWriter writer) throws  XMLStreamException {
        // calculate if entity contains useful data, instead of throwing exception for every null;
        LinkedList<String> values = new LinkedList<>();
        values.add(dc.getKeyNumberEntity().getSenderKey());
        values.add(dc.getKeyNumberEntity().getRecipientKey());
        values.add(dc.getKeyNumberEntity().getCarrierKey());
        Integer nulls = 0;
        for (String value: values){
            if (value == null){
                nulls++;
            }
        }
        if (nulls.equals(values.size())){
            return;
        }
        else {
            writer.writeStartElement("KeyNumber");
            writeElement("ns2:SenderKey",dc.getKeyNumberEntity().getSenderKey(),writer);
            writeElement("ns2:RecipientKey",dc.getKeyNumberEntity().getRecipientKey(),writer);
            writeElement("ns2:CarrierKey",dc.getKeyNumberEntity().getCarrierKey(),writer);
            writer.writeEndElement();
        }
    }
    private static void writeRegisteredSentInfo(DataContainer dc, XMLStreamWriter writer) throws  XMLStreamException {
        // calculate if entity contains useful data, instead of throwing exception for every null;
        LinkedList<String> values = new LinkedList<>();
        values.add(dc.getRegisteredSENTInfoEntity().getCreationDate().toString());
        values.add(dc.getRegisteredSENTInfoEntity().getCreator());
        values.add(dc.getRegisteredSENTInfoEntity().getModificationDate().toString());
        values.add(dc.getRegisteredSENTInfoEntity().getModifier());
        values.add(dc.getRegisteredSENTInfoEntity().getSourceDocumentId());
        values.add(dc.getRegisteredSENTInfoEntity().getChecksumOfSourceDocument());
        values.add(dc.getRegisteredSENTInfoEntity().getSentId());
        values.add(dc.getRegisteredSENTInfoEntity().getSentStatus());
        Integer nulls = 0;
        for (String value: values){
            if (value == null){
                nulls++;
            }
        }
        if (nulls.equals(values.size())){
            return;
        }
        else {
            writer.writeStartElement("RegisteredSentInfo");
            if (dc.getRegisteredSENTInfoEntity().getCreationDate() != null){
                writer.writeStartElement("ns2:CreationDate");
                writer.writeCharacters(dc.getRegisteredSENTInfoEntity().getCreationDate().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
                writer.writeEndElement();
            }
            writeElement("ns2:Creator",dc.getRegisteredSENTInfoEntity().getCreator(),writer);
            if (dc.getRegisteredSENTInfoEntity().getModificationDate() != null){
                writer.writeStartElement("ns2:ModificationDate");
                writer.writeCharacters(dc.getRegisteredSENTInfoEntity().getModificationDate().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
                writer.writeEndElement();
            }
            writeElement("ns2:Modifier",dc.getRegisteredSENTInfoEntity().getModifier(),writer);
            writeElement("ns2:SourceDocumentId",dc.getRegisteredSENTInfoEntity().getSourceDocumentId(),writer);
            writeElement("ns2:ChecksumOfSourceDocument",dc.getRegisteredSENTInfoEntity().getChecksumOfSourceDocument(),writer);
            writeElement("ns2:SentId",dc.getRegisteredSENTInfoEntity().getSentId(), writer);
            writeElement("ns2:SentStatus",dc.getRegisteredSENTInfoEntity().getSentStatus(), writer);
            writer.writeEndElement();
        }
    }
    private static void writeGoodsSender(DataContainer dc, XMLStreamWriter writer) throws  XMLStreamException{
        // calculate if entity contains useful data, instead of throwing exception for every null;
        LinkedList<String> values = new LinkedList<>();
        values.add(dc.getGoodsSenderEntity().getTraderName());
        values.add(dc.getGoodsSenderEntity().getTraderIdentityType());
        values.add(dc.getGoodsSenderEntity().getTraderIdentityNumber());
        values.add(dc.getGoodsSenderEntity().getStreet());
        values.add(dc.getGoodsSenderEntity().getHouseNumber());
        values.add(dc.getGoodsSenderEntity().getFlatNumber());
        values.add(dc.getGoodsSenderEntity().getCity());
        values.add(dc.getGoodsSenderEntity().getCountry());
        values.add(dc.getGoodsSenderEntity().getPostalCode());
        Integer nulls = 0;
        for (String value: values){
            if (value == null){
                nulls++;
            }
        }
        if (nulls.equals(values.size())){
            return;
        }
        else {
            writer.writeStartElement("GoodsSender");
            if (dc.getGoodsSenderEntity().getTraderName() == null &&
                        dc.getGoodsSenderEntity().getTraderIdentityType() == null &&
                        dc.getGoodsSenderEntity().getTraderIdentityNumber() == null){
            }
            else{
                writer.writeStartElement("ns2:TraderInfo");
                writeElement("ns2:TraderName",dc.getGoodsSenderEntity().getTraderName(),writer);
                writeElement("ns2:TraderIdentityType",dc.getGoodsSenderEntity().getTraderIdentityType(),writer);
                writeElement("ns2:TraderIdentityNumber",dc.getGoodsSenderEntity().getTraderIdentityNumber(),writer);
                writer.writeEndElement();
            }
            
            // Assuming that here at least 1 node is obligatory
            writer.writeStartElement("ns2:TraderAddress");
            writeElement("ns2:Street",dc.getGoodsSenderEntity().getStreet(),writer);
            writeElement("ns2:HouseNumber",dc.getGoodsSenderEntity().getHouseNumber(),writer);
            writeElement("ns2:FlatNumber",dc.getGoodsSenderEntity().getFlatNumber(),writer);
            writeElement("ns2:City",dc.getGoodsSenderEntity().getCity(),writer);
            writeElement("ns2:Country",dc.getGoodsSenderEntity().getCountry(),writer);
            writeElement("ns2:PostalCode",dc.getGoodsSenderEntity().getPostalCode(),writer);
            writer.writeEndElement(); // TraderAddress
            writer.writeEndElement(); // TraderInfo
        }
    }
    private static void writeGoodsRecipient(DataContainer dc, XMLStreamWriter writer) throws  XMLStreamException{
        // calculate if entity contains useful data, instead of throwing exception for every null;
        LinkedList<String> values = new LinkedList<>();
        values.add(dc.getGoodsRecipientEntity().getTraderName());
        values.add(dc.getGoodsRecipientEntity().getTraderIdentityType());
        values.add(dc.getGoodsRecipientEntity().getTraderIdentityNumber());
        values.add(dc.getGoodsRecipientEntity().getStreet());
        values.add(dc.getGoodsRecipientEntity().getHouseNumber());
        values.add(dc.getGoodsRecipientEntity().getFlatNumber());
        values.add(dc.getGoodsRecipientEntity().getCity());
        values.add(dc.getGoodsRecipientEntity().getCountry());
        values.add(dc.getGoodsRecipientEntity().getPostalCode());
        Integer nulls = 0;
        for (String value: values){
            if (value == null){
                nulls++;
            }
        }
        if (nulls.equals(values.size())){
            return;
        }
        else {
            writer.writeStartElement("GoodsRecipient");
            if (dc.getGoodsRecipientEntity().getTraderName() == null &&
                        dc.getGoodsRecipientEntity().getTraderIdentityType() == null &&
                        dc.getGoodsRecipientEntity().getTraderIdentityNumber() == null){
            }
            else{
                writer.writeStartElement("ns2:TraderInfo");
                writeElement("ns2:TraderName",dc.getGoodsRecipientEntity().getTraderName(),writer);
                writeElement("ns2:TraderIdentityType",dc.getGoodsRecipientEntity().getTraderIdentityType(),writer);
                writeElement("ns2:TraderIdentityNumber",dc.getGoodsRecipientEntity().getTraderIdentityNumber(),writer);
                writer.writeEndElement();
            }
            // Assuming that here at least 1 node is obligatory
            writer.writeStartElement("ns2:TraderAddress");
            writeElement("ns2:Street",dc.getGoodsRecipientEntity().getStreet(),writer);
            writeElement("ns2:HouseNumber",dc.getGoodsRecipientEntity().getHouseNumber(),writer);
            writeElement("ns2:FlatNumber",dc.getGoodsRecipientEntity().getFlatNumber(),writer);
            writeElement("ns2:City",dc.getGoodsRecipientEntity().getCity(),writer);
            writeElement("ns2:Country",dc.getGoodsRecipientEntity().getCountry(),writer);
            writeElement("ns2:PostalCode",dc.getGoodsRecipientEntity().getPostalCode(),writer);
            writer.writeEndElement(); // TraderAddress
            writer.writeEndElement(); // TraderInfo
        }
    }
    private static void writeCarrier(DataContainer dc, XMLStreamWriter writer) throws  XMLStreamException {
        // calculate if entity contains useful data, instead of throwing exception for every null;
        LinkedList<String> values = new LinkedList<>();
        values.add(dc.getCarrierEntity().getTraderName());
        values.add(dc.getCarrierEntity().getTraderIdentityType());
        values.add(dc.getCarrierEntity().getTraderIdentityNumber());
        values.add(dc.getCarrierEntity().getStreet());
        values.add(dc.getCarrierEntity().getHouseNumber());
        values.add(dc.getCarrierEntity().getFlatNumber());
        values.add(dc.getCarrierEntity().getCity());
        values.add(dc.getCarrierEntity().getCountry());
        values.add(dc.getCarrierEntity().getPostalCode());
        Integer nulls = 0;
        for (String value : values) {
            if (value == null) {
                nulls++;
            }
        }
        if (nulls.equals(values.size())) {
            return;
        } else {
            writer.writeStartElement("Carrier");
            if (dc.getGoodsRecipientEntity().getTraderName() == null &&
                        dc.getGoodsRecipientEntity().getTraderIdentityType() == null &&
                        dc.getGoodsRecipientEntity().getTraderIdentityNumber() == null) {
            } else {
                writer.writeStartElement("ns2:TraderInfo");
                writeElement("ns2:TraderName",dc.getCarrierEntity().getTraderName(),writer);
                writeElement("ns2:TraderIdentityType",dc.getCarrierEntity().getTraderIdentityType(),writer);
                writeElement("ns2:TraderIdentityNumber",dc.getCarrierEntity().getTraderIdentityNumber(),writer);
                writer.writeEndElement();
            }
            // Assuming that here at least 1 node is obligatory
            writer.writeStartElement("ns2:TraderAddress");
            writeElement("ns2:Street",dc.getCarrierEntity().getStreet(),writer);
            writeElement("ns2:HouseNumber",dc.getCarrierEntity().getHouseNumber(),writer);
            writeElement("ns2:FlatNumber",dc.getCarrierEntity().getFlatNumber(),writer);
            writeElement("ns2:City",dc.getCarrierEntity().getCity(),writer);
            writeElement("ns2:Country",dc.getCarrierEntity().getCountry(),writer);
            writeElement("ns2:PostalCode",dc.getCarrierEntity().getPostalCode(),writer);
            writer.writeEndElement(); // TraderAddress
            writer.writeEndElement(); // TraderInfo
        }
    }
    private static void writeMeansOfTransport(DataContainer dc, XMLStreamWriter writer) throws  XMLStreamException{
        // calculate if entity contains useful data, instead of throwing exception for every null;
        LinkedList<String> values = new LinkedList<>();
        values.add(dc.getMeanOfTransportEntity().getTruckNumber());
        values.add(dc.getMeanOfTransportEntity().getPermitRoad());
        Integer nulls = 0;
        for (String value : values) {
            if (value == null) {
                nulls++;
            }
        }
        if (nulls.equals(values.size())) {
            return;
        } else {
            writer.writeStartElement("MeansOfTransport");
            writeElement("ns2:TruckNumber",dc.getMeanOfTransportEntity().getTruckNumber(),writer);
            writeElement("ns2:PermitRoad",dc.getMeanOfTransportEntity().getPermitRoad(),writer);
            writer.writeEndElement();
        }
    }
    private static void writeTransport(DataContainer dc, XMLStreamWriter writer) throws  XMLStreamException{
        // calculate if entity contains useful data, instead of throwing exception for every null;
        LinkedList<String> values = new LinkedList<>();
        values.add(dc.getPlaceOfLoadingEntity().getStreet());
        values.add(dc.getPlaceOfLoadingEntity().getHouseNumber());
        values.add(dc.getPlaceOfLoadingEntity().getFlatNumber());
        values.add(dc.getPlaceOfLoadingEntity().getCity());
        values.add(dc.getPlaceOfLoadingEntity().getCountry());
        values.add(dc.getPlaceOfLoadingEntity().getPostalCode());
        Integer nulls = 0;
        for (String value : values) {
            if (value == null) {
                nulls++;
            }
        }
        if (nulls.equals(values.size())) {
            return;
        } else {
            writer.writeStartElement("Transport");
    
            //Assuming place of loading is obligatory
            writer.writeStartElement("ns2:PlaceOfLoading");
            writeElement("ns2:Street",dc.getPlaceOfLoadingEntity().getStreet(),writer);
            writeElement("ns2:HouseNumber",dc.getPlaceOfLoadingEntity().getHouseNumber(),writer);
            writeElement("ns2:FlatNumber",dc.getPlaceOfLoadingEntity().getFlatNumber(),writer);
            writeElement("ns2:City",dc.getPlaceOfLoadingEntity().getCity(),writer);
            writeElement("ns2:Country",dc.getPlaceOfLoadingEntity().getCountry(),writer);
            writeElement("ns2:PostalCode",dc.getPlaceOfLoadingEntity().getPostalCode(),writer);
            writer.writeEndElement();
            
            values.clear();
            values.add(dc.getPlaceOfDeliveryEntity().getStreet());
            values.add(dc.getPlaceOfDeliveryEntity().getHouseNumber());
            values.add(dc.getPlaceOfDeliveryEntity().getFlatNumber());
            values.add(dc.getPlaceOfDeliveryEntity().getCity());
            values.add(dc.getPlaceOfDeliveryEntity().getCountry());
            values.add(dc.getPlaceOfDeliveryEntity().getPostalCode());
            Boolean placeOfDeliveryExists=false;
            for (String value: values){
                if (value != null){
                    placeOfDeliveryExists=true;
                    break;
                }
            }
            
            if(placeOfDeliveryExists){
                writer.writeStartElement("ns2:PlaceOfDelivery");
                writeElement("ns2:Street",dc.getPlaceOfDeliveryEntity().getStreet(),writer);
                writeElement("ns2:HouseNumber",dc.getPlaceOfDeliveryEntity().getHouseNumber(),writer);
                writeElement("ns2:FlatNumber",dc.getPlaceOfDeliveryEntity().getFlatNumber(),writer);
                writeElement("ns2:City",dc.getPlaceOfDeliveryEntity().getCity(),writer);
                writeElement("ns2:Country",dc.getPlaceOfDeliveryEntity().getCountry(),writer);
                writeElement("ns2:PostalCode",dc.getPlaceOfDeliveryEntity().getPostalCode(),writer);
                writer.writeEndElement();
            }
            if(dc.getPlaceOfDeliveryEntity().getPlannedTransportDate() != null ){
                writer.writeStartElement("ns2:PlannedStartCarriageDate");
                writer.writeCharacters(dc.getPlaceOfDeliveryEntity().getPlannedTransportDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd+HH:mm")));
                writer.writeEndElement();
            }
            if(dc.getPlaceOfDeliveryEntity().getStartTransportDate() != null ){
                writer.writeStartElement("ns2:StartTransportDate");
                writer.writeCharacters(dc.getPlaceOfDeliveryEntity().getStartTransportDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd+HH:mm")));
                writer.writeEndElement();
            }
            if(dc.getPlaceOfDeliveryEntity().getEndTransportDate() != null ) {
                writer.writeStartElement("ns2:EndTransportDate");
                writer.writeCharacters(dc.getPlaceOfDeliveryEntity().getEndTransportDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd+HH:mm")));
                writer.writeEndElement();
            }
            writer.writeEndElement(); // Transport
        }
    }
    private static void writeGoodsInformation(DataContainer dc, XMLStreamWriter writer) throws  XMLStreamException{
        //Assuming GoodsInformation  is obligatory
        writer.writeStartElement("GoodsInformation");
        writeElement("ns2:CodePkwiuClassification",dc.getGoodsInformationEntity().getCodePKWIUClassification(),writer);
        writeElement("ns2:CodeCnClassification",dc.getGoodsInformationEntity().getCodeCNClassification(),writer);
        writeElement("ns2:GoodsName",dc.getGoodsInformationEntity().getGoodsName(),writer);
        writeElement("ns2:AmountOfGoods",dc.getGoodsInformationEntity().getAmountOfGoods(),writer);
        writeElement("ns2:UnitOfMeasure",dc.getGoodsInformationEntity().getUnitOfMeasure(),writer);
        writeElement("ns2:TypeOfTransportDocument",dc.getGoodsInformationEntity().getTypeOfTransportDocument(),writer);
        writeElement("ns2:GrossWeightOfGoods",dc.getGoodsInformationEntity().getGrossWeightOfGoods(),writer);
        writeElement("ns2:NumberOfTransportDocument",dc.getGoodsInformationEntity().getNumberOfTransportDocument(),writer);
        writer.writeEndElement();
    }
    private static void writeComments(DataContainer dc, XMLStreamWriter writer) throws  XMLStreamException{
        writeElement("Comments",dc.getKeyNumberEntity().getComments(),writer);
    }
    
    private static void writeElement(String elementName, Object entityToWrite, XMLStreamWriter writer) throws XMLStreamException {
        if (entityToWrite != null){
            writer.writeStartElement(elementName);
            writer.writeCharacters(entityToWrite.toString());
            writer.writeEndElement();
        }
    }
}