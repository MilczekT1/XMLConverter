package Entities;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity(name = "registeredsentinfo")
@Table(name = "registeredsentinfo")
@Data
class RegisteredSENTInfoEntity {
    @Id
    @GeneratedValue (generator="increment")
    @GenericGenerator (name="increment", strategy = "increment")
    @Column (name = "nr_registeredsentinfo")
    private Integer nr_registeredsentinfo;
    
    @Column(name = "sentNumber", unique = true)
    private String sentNumber;
    
    @Column(name = "creationDate")
    private ZonedDateTime creationDate;
    
    @Column(name = "creator")
    private String creator;
    
    @Column(name = "modificationDate")
    private ZonedDateTime modificationDate;
    
    @Column(name = "modifier")
    private String modifier;
    
    @Column(name = "sourceDocumentId")
    private String sourceDocumentId;
    
    @Column(name = "checksumOfSourceDocument")
    private String checksumOfSourceDocument;
    
    @Column(name = "sentId")
    private String sentId;
    
    @Column(name = "sentStatus")
    private String sentStatus;
}