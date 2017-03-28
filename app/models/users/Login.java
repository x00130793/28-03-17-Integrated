package models.users;

// A view Model class to back the Login form
public class Login {

    private String email;
    private String password;

    // Validate method - invoked during error checking
    // after form based on a Login object has been submitted
    public String validate() {

        // Call the static authenticate method in User
        if (User.authenticate(email, password) == null) {
            return "Invalid user or password";
        }
        return null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}