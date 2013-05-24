package controllers;

import java.util.ArrayList;

import models.Project;
import play.mvc.Controller;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;
import play.data.Form;
import views.html.*;
import views.html.projects.*;
@Security.Authenticated(Secured.class)
public class Projects extends Controller {
	public static Result add() {
	    Project newProject = Project.create(
	        "New project",
	        Form.form().bindFromRequest().get("group"),
	        request().username()
	    );
	    return ok(item.render(newProject));
	}
	
	public static boolean isMemberOf(Long project) {
	    return Project.isMember(
	        project,
	        Context.current().request().username()
	    );
	}
	
	public static Result rename(Long project) {
	    if (Secured.isMemberOf(project)) {
	        return ok(
	            Project.rename(
	                project,
	                Form.form().bindFromRequest().get("name")
	            )
	        );
	    } else {
	        return forbidden();
	    }
	}
	
	public static Result delete(Long project) {
	    if(Secured.isMemberOf(project)) {
	        Project.find.ref(project).delete();
	        return ok();
	    } else {
	        return forbidden();
	    }
	}
	
	public static Result addGroup() {
	    return ok(
	        group.render("New group", new ArrayList<Project>())
	    );
	}
	

}