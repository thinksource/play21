package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.QueryIterator;

import play.data.validation.Constraints.Email;
import play.db.ebean.Model;

@Entity
public class User extends Model {

    @Id

    public String email;
    public String name;
    public String password;
    
    public User(String email, String name, String password) {
      this.email = email;
      this.name = name;
      this.password = password;
    }

    public static Finder<String,User> find = new Finder<String,User>(String.class, User.class); 
    
    public static User authenticate(String email, String password) {
    	ExpressionList<User> listu=find.where();
//    	System.out.println(listu.findRowCount());
//    	for(QueryIterator<User> it=listu.findIterate();it.hasNext();){
//    		User tmpu=it.next();
//    		System.out.println("email:"+tmpu.email+", name="+tmpu.name);
//    	}
        return find.where().eq("email", email)
            .eq("password", password).findUnique();
    }
    
    public static List<User> findAll() {
        return find.all();
    }
}
