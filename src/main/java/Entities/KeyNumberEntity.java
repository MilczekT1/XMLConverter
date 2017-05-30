package Entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity (name = "keynumber")
@Table (name = "keynumber")
@Data
class KeyNumberEntity {
    @Id
    @GeneratedValue (generator="increment")
    @GenericGenerator (name="increment", strategy = "increment")
    @Column (name = "nr_keynumber")
    private Integer nr_keynumber;
    
    @Column (name = "sentnumber", unique = true)
    private String sentNumber;
    
    @Column (name = "sentroot")
    private String sentroot;
    
    @Column (name = "senderkey")
    private String senderKey;
    
    @Column (name = "recipientkey")
    private String recipientKey;
    
    @Column (name = "carrierkey")
    private String carrierKey;
    
    @Column (name = "comments")
    private String comments;
}
