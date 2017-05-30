package Entities;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity (name = "meanoftransport")
@Table (name = "meanoftransport")
@Data
class MeanOfTransportEntity {
    @Id
    @GeneratedValue (generator="increment")
    @GenericGenerator (name="increment", strategy = "increment")
    @Column (name = "nr_meanoftransport")
    private Integer nr_meanoftransport;
    
    @Column (name = "sentNumber", unique = true)
    private String sentNumber;
    
    @Column (name = "truckNumber")
    private String truckNumber;
    
    @Column (name = "permitRoad")
    private String permitRoad;
}
