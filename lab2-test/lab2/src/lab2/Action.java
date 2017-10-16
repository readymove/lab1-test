package lab2;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

public class Action {
    private Pattern p = Pattern.compile("\\?");
    /*private String sql1="insert into author(authorid,name,age,country) values(?,?,?,?)"; 
    private String sql2="insert into book(isbn,title,authorid,publisher,publishdate,price) values(?,?,?,?,?,?)";
    */
    private List<String> list = new LinkedList<String>();
    private List<String> list0 = new LinkedList<String>();
    ServletRequest request = ServletActionContext.getRequest();
    HttpServletRequest req = (HttpServletRequest) request;
    HttpSession session = req.getSession();
    private String username;
    private String title;
    private String isbn;
    private String name;
    private String publisher;
    private String publishdate;
    private String price;
    private String age;
    private String country;
    private String authorid;
    static int flag;
    public String getUsername(){
        return username;
    }
    public String getIsbn(){
        return isbn;
    }
    public String getTitle(){
        return title;
    }
    public String getName(){
        return name;
    }
    public String getAuthorid(){
        return authorid;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setName(String name) {
        this.name =name;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public void setPublishdate(String publishdate) {
        this.publishdate = publishdate;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public void setCountry(String country) {
        this.country=country;
    }
    // all struts logic here
    public String execute() {
        String sql0 ="select AuthorID from Author where Name=?";
        Matcher m1 = p.matcher(sql0);
        String sql1 = m1.replaceFirst('"'+username+'"');
        DBConnection connect = new DBConnection();
        list0=connect.select(sql1);
        if(list0.size() == 0)
            return "FAILED";
        String sql2 ="select Title from Book where AuthorID=?";
        Matcher m2 = p.matcher(sql2);
        String sql = m2.replaceFirst('"'+list0.get(0)+'"');
        list=connect.select(sql);
        session.setAttribute("list", list);
        session.setAttribute("username",username);
        if(list.size() == 0)
            return "FAILED";
        else
            return "SUCCESS";
    }
    public String detail(){
        String sql0 ="select * from Author where Name=?";
        Matcher m1 = p.matcher(sql0);
        String sql1 = m1.replaceFirst('"'+username+'"');
        DBConnection connect = new DBConnection();
        list0=connect.select(sql1);
        session.setAttribute("list0", list0);
        String sql2 ="select * from Book where Title=?";
        Matcher m2 = p.matcher(sql2);
        String sql3 = m2.replaceFirst('"'+title+'"');
        list=connect.select(sql3);
        session.setAttribute("list", list);
        if(list.size()==0)
            return "FAILED";
        else
            return "SUCCESS";
    }
    public String gotoedit(){
        return "SUCCESS";
    }
    public String edit(){
        flag = 0;
        String[] sql=new String[6];
        sql[0]="update Book set AuthorID=?,Publisher=?,PublishDate=?,Price=? where ISBN=?";
        String sql0="select * from Author where Name=?";
        Matcher m1 = p.matcher(sql0);
        String sql1 = m1.replaceFirst('"'+name+'"');
        DBConnection connect = new DBConnection();
        list=connect.select(sql1);
        list0=connect.select("select AuthorID from Author");
        m1=p.matcher(sql[0]);
        if(list.size()==0){
            if(list0.size()==0)
                authorid="1";
            else
                authorid=String.valueOf(Integer.parseInt(Collections.max(list0))+1);
        }
        else
            authorid=list.get(0);
        if(list.size()==0)
            return "ADD";
        sql[1] = m1.replaceFirst('"'+authorid+'"');
        m1=p.matcher(sql[1]);
        sql[2] = m1.replaceFirst('"'+publisher+'"');
        m1=p.matcher(sql[2]);
        sql[3] = m1.replaceFirst('"'+publishdate+'"');
        m1=p.matcher(sql[3]);
        sql[4] = m1.replaceFirst('"'+price+'"');
        m1=p.matcher(sql[4]);
        sql[5] = m1.replaceFirst('"'+isbn+'"');
        int signal=connect.update(sql[5]);
        if(signal==1)
            return "SUCCESS";
        else
            return "FAILED";
    }
    public String addauthor(){
        String[] sql=new String[5];
        sql[0]="insert into Author(AuthorID,Name,Age,Country) values(?,?,?,?)";
        Matcher m1 = p.matcher(sql[0]);
        sql[1] = m1.replaceFirst('"'+authorid+'"');
        m1 = p.matcher(sql[1]);
        sql[2] = m1.replaceFirst('"'+name+'"');
        m1 = p.matcher(sql[2]);
        sql[3] = m1.replaceFirst('"'+age+'"');
        m1 = p.matcher(sql[3]);
        sql[4] = m1.replaceFirst('"'+country+'"');
        DBConnection connect = new DBConnection();
        int signal=connect.insert(sql[4]);
        if(signal==1){
            if(flag==0)
                return "SUCCESS0";
            else
                return "SUCCESS1";
        }
        else
            return "FAILED";
    }
    public String delete(){
        String sql0 ="delete from Book where ISBN=?";
        Matcher m1 = p.matcher(sql0);
        String sql1 = m1.replaceFirst('"'+isbn+'"');
        DBConnection connect = new DBConnection();
        int signal=connect.delete(sql1);
        if(signal==1)
            return "SUCCESS";
        else
            return "FAILED";
    }
    public String addbook(){
        flag=1;
        String sql0="select * from Author where Name=?";
        Matcher m1 = p.matcher(sql0);
        String sql1 = m1.replaceFirst('"'+name+'"');
        DBConnection connect = new DBConnection();
        list=connect.select(sql1);
        list0=connect.select("select AuthorID from Author");
        if(list.size()==0){
            if(list0.size()==0)
                authorid="1";
            else
                authorid=String.valueOf(Integer.parseInt(Collections.max(list0))+1);
        }
        else
            authorid=list.get(0);
        if(list.size()==0)
            return "ADD";
        String sql="insert into Book(ISBN,Title,AuthorID,Publisher,PublishDate,Price) values(?,?,?,?,?,?)";
        m1 = p.matcher(sql);
        sql = m1.replaceFirst('"'+isbn+'"');
        m1 = p.matcher(sql);
        sql = m1.replaceFirst('"'+title+'"');
        m1 = p.matcher(sql);
        sql = m1.replaceFirst('"'+authorid+'"');
        m1 = p.matcher(sql);
        sql = m1.replaceFirst('"'+publisher+'"');
        m1 = p.matcher(sql);
        sql = m1.replaceFirst('"'+publishdate+'"');
        m1 = p.matcher(sql);
        sql = m1.replaceFirst('"'+price+'"');
        int signal=connect.insert(sql);
        if(signal==1)
            return "SUCCESS";
        else
            return "FAILED";
    }
}