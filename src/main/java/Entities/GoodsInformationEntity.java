package Entities;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity (name = "goodsinformation")
@Table (name = "goodsinformation")
@Data
class GoodsInformationEntity {
    
    @Id
    @GeneratedValue (generator="increment")
    @GenericGenerator (name="increment", strategy = "increment")
    @Column (name = "nr_goodsinformation")
    private Integer nr_goodsinformation;
    
    @Column (name = "sentNumber", unique = true)
    private String sentNumber;
    
    @Column (name = "codeCNClassification")
    private String codeCNClassification;
    
    @Column (name = "codePKWIUClassification")
    private String codePKWIUClassification;
    
    @Column (name = "goodsName")
    private String goodsName;
    
    @Column (name = "amountOfGoods")
    private String amountOfGoods;
    
    @Column (name = "unitOfMeasure")
    private String unitOfMeasure;
    
    @Column (name = "grossWeightOfGoods")
    private String grossWeightOfGoods; //brutto
    
    @Column (name = "typeOfTransportDocument")
    private String typeOfTransportDocument;
    
    @Column (name = "numberOfTransportDocument")
    private String numberOfTransportDocument;
}
