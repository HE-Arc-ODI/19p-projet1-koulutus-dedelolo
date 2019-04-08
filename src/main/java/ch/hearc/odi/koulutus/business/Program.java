package ch.hearc.odi.koulutus.business;

public class Program {

    private Integer id;
    private String name;
    private String richDescription;
    private String field;
    private Number price;


    public Program(Integer id, String name, String richDescription, String field, Number price) {
        this.id = id;
        this.name = name;
        this.richDescription = richDescription;
        this.field = field;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }
}
