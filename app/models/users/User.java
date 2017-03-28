package models.users;

import java.util.*;
import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.*;

// Product entity managed by Ebean
@Entity
// specify mapped table name
@Table(name = "user")
// Map inherited classes to a single table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// Discriminator column used to define user type
@DiscriminatorColumn(name = "role")
// This user type is user
@DiscriminatorValue("user")
public class User extends Model {

    @Id
    @Constraints.Required
    private String email;

    @Column(insertable=false, updatable=false)
    private String role;

    @Constraints.Required
    private String fName;

    @Constraints.Required
    private String lName;

    @Constraints.Required
    private String password;

    public User(String email, String role, String fName, String lName, String password) {
        this.email = email;
        this.role = role;
        this.fName = fName;
        this.lName = lName;
        this.password = password;
    }

    //Generic query helper for entity User with unique id String
    public static Finder<String, User> find = new Finder<String, User>(User.class);

    // Find all Users in the database
    public static List<User> findAll() {
        return User.find.all();
    }

    // Static method to authenticate based on username and password
    // Returns user object if found, otherwise NULL
    public static User authenticate(String email, String password) {
        // If found return the user object with matching username and password
        return find.where().eq("email", email).eq("password", password).findUnique();
    }

    // Check if a user is logged in (by id - email address)
    public static User getUserById(String id) {
        if (id == null)
            return null;
        else
            // Find user by id and return object
            return find.byId(id);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
