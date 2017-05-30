package Entities;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity (name = "placeofdelivery")
@Table (name = "placeofdelivery")
@Data
class PlaceOfDeliveryEntity {
    @Id
    @GeneratedValue (generator="increment")
    @GenericGenerator (name="increment", strategy = "increment")
    @Column (name = "nr_placeofdelivery")
    private Integer nr_placeofdelivery;
    
    @Column (name = "sentNumber", unique = true)
    private String sentNumber;
    
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
    
    @Column (name = "plannedTransportDate")
    private LocalDateTime plannedTransportDate;
    
    @Column (name = "startTransportDate")
    private LocalDateTime startTransportDate;
    
    @Column (name = "endTransportDate")
    private LocalDateTime endTransportDate;
}
