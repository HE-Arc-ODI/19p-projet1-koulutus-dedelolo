package ch.hearc.odi.koulutus.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Program")
@XmlRootElement(name = "Program")
public class Program {

    private Long id;
    private String name;
    private String richDescription;
    private String field;
    private BigDecimal price;
    private List<Course> courses;

    public Program(){
        courses = new ArrayList<>();
    }

    public Program(Long id, String name, String richDescription, String field, BigDecimal price) {
        this();
        this.id = id;
        this.name = name;
        this.richDescription = richDescription;
        this.field = field;
        this.price = price;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRichDescription() {
        return richDescription;
    }

    public void setRichDescription(String richDescription) {
        this.richDescription = richDescription;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @OneToMany(targetEntity = Course.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "courses")
    @OrderColumn(name = "order_courses")
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
