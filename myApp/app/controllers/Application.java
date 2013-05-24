package controllers;



import models.*;
import play.*;
import play.data.Form;
import play.mvc.Security;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.*;

public class Application extends Controller {
    public static class Login {

        public String email;
        public String password;
        
        public String validate() {
            if (User.authenticate(email, password) == null) {
              return "Invalid user or password";
            }
            return null;
        }

    }
  
    @Security.Authenticated(Secured.class)
    public static Result index() {
        return ok(index.render(
                Project.findInvolving(request().username()), 
                Task.findTodoInvolving(request().username()),
                User.find.byId(request().username())
            ));  
    }
    
    @SuppressWarnings("rawtypes")
	public static Result login() {
    	//new Form<Login>(Login.class);
        return ok(login.render(new Form(Login.class)));
    }
    
	public static Result logout() {
	    session().clear();
	    flash("success", "You've been logged out");
	    return redirect(
	        routes.Application.login()
	    );
	}
    


	public static Result authenticate() {
        Form<Login> loginForm =(new Form(Login.class)).bindFromRequest();
        //System.out.println(loginForm.toString());
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("email", loginForm.get().email);
            return redirect(
                routes.Application.index()
            );
        }
    }
    
	public static Result javascriptRoutes() {
	    response().setContentType("text/javascript");
	    return ok(
	        Routes.javascriptRouter("jsRoutes",
	            controllers.routes.javascript.Projects.add(),
	            controllers.routes.javascript.Projects.delete(),
	            controllers.routes.javascript.Projects.rename(),
	            controllers.routes.javascript.Projects.addGroup()
	        )
	    );
	}


}
