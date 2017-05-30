package Entities;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity (name = "placeofloading")
@Table (name = "placeofloading")
@Data
class PlaceOfLoadingEntity {
    @Id
    @GeneratedValue (generator="increment")
    @GenericGenerator (name="increment", strategy = "increment")
    @Column (name = "nr_placeofloading")
    private Integer nr_placeofloading;
    
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
}
