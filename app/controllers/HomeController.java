package controllers;

import controllers.security.AuthAdmin;
import controllers.security.Secured;
import play.mvc.*;
import play.data.*;
import play.db.ebean.Transactional;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import play.api.Environment;

import views.html.*;

// Import models
import models.*;
import models.users.*;

import play.mvc.Http.*;
import play.mvc.Http.MultipartFormData.FilePart;

import java.io.File;
import javax.inject.Inject;
import play.Logger;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
     // Declare a private FormFactory instance
    private FormFactory formFactory;


    private Environment env;

    //  Inject an instance of FormFactory it into the controller via its constructor
    @Inject
    public HomeController(Environment e, FormFactory f) {
        this.env = e;
        this.formFactory = f;
    }

    // Method retuns the logged in user (or null)
    private User getUserFromSession() {
        return User.getUserById(session().get("email"));
    }
	 
    public Result index() {
       List<Category> categoriesList = Category.findAll();
       return ok(index.render(categoriesList, getUserFromSession()));
   }
	

    public Result productDetails() {
	List<Category> categoriesList = Category.findAll();
        return ok(productDetails.render(categoriesList, getUserFromSession()));
    }

  //  public Result register() {
//	return ok(register.render());
   // }

    public Result products(Long cat, String filter) {

        // Get list of all categories in ascending order
        List<Category> categoriesList = Category.findAll();
        List<Product> productsList = new ArrayList<Product>();

        if (cat == 0) {
            // Get list of all categories in ascending order
            productsList = Product.findAll(filter);
        }
        else {
            // Get products for selected category
            // Find category first then extract products for that cat.
            productsList = Product.findFilter(cat, filter);
        }

        return ok(products.render(env, categoriesList, productsList, cat, filter, getUserFromSession()));
    }


    public Result addUserSubmit(){
        Form<User> addUserForm = formFactory.form(User.class).bindFromRequest();
        User u = addUserForm.get();

        if(addUserForm.hasErrors()){
            flash("fail", "User" + u.getEmail() + "is already in our database.");
            return redirect(controllers.routes.LoginController.login());
        }

        if (u.getEmail() != null){
            u.save();
            flash("success", "User " + u.getEmail() + " has been registered.");
        }

        return redirect(controllers.routes.LoginController.login());
    }

}
