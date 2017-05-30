package Entities;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity (name = "carrier")
@Table (name = "carrier")
@Data
class CarrierEntity {
    @Id
    @GeneratedValue (generator="increment")
    @GenericGenerator (name="increment", strategy = "increment")
    @Column (name = "nr_carrier")
    private Integer nr_carrier;
    
    @Column (name = "sentNumber", unique = true)
    private String sentNumber;
    
    @Column (name = "idSisc") // TODO:  w kazdym htmlu, w zadnym xmlu
    private String idSisc;
    
    @Column (name = "traderName")
    private String traderName;
    
    @Column (name = "traderIdentityType")
    private String traderIdentityType;
    
    @Column (name = "traderIdentityNumber")
    private String traderIdentityNumber;
    
    @Column (name = "street")
    private String street;
    
    @Column (name = "houseNumber")
    private String houseNumber;
    
    @Column (name = "flatNumber")
    private String flatNumber; //TODO: dodac do sql
    
    @Column (name = "city")
    private String city;
    
    @Column (name = "country")
    private String country;
    
    @Column (name = "postalCode")
    private String postalCode;
}
