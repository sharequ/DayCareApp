package activity.rprs.com.daycaremain;

public class TecAttendmodel {

    int id,img_id;
    String name,classname;
    Boolean state,islogin;

    public TecAttendmodel(int id, int img_id, String name, Boolean state) {
        this.id = id;
        this.img_id = img_id;
        this.name = name;
        this.state = state;
    }

    public TecAttendmodel( String name, Boolean state) {
        this.name = name;
        this.state = state;
    }

    public TecAttendmodel(int id, String name, Boolean state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public TecAttendmodel(int id, String name, String classname, Boolean state,Boolean islogin) {
        this.id = id;
        this.name = name;
        this.classname = classname;
        this.state = state;
        this.islogin=islogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Boolean getIslogin() {
        return islogin;
    }

    public void setIslogin(Boolean islogin) {
        this.islogin = islogin;
    }
}
