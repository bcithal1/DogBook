package dogbook.model;

import java.io.IOException;
import java.util.Date;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String type;
    @Column
    private byte[] data;
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    public Photo(){
        
    }

    public Photo(MultipartFile file) throws IOException {
        this.name = StringUtils.cleanPath(file.getOriginalFilename());
        this.type = file.getContentType();
        this.data = file.getBytes();
        this.createdAt = new Date();
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name= name;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type= type;
    }

    public byte[] getData(){
        return data;
    }

    public void setData(byte[] data){
        this.data = data;
    }

    public Date getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(Date createdAt){
        this.createdAt = createdAt;
    }
}
