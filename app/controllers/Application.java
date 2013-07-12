package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    public static Result segments() {
    	
    	return TODO;
    }
  
    public static Result segmentsTopN() {
    	
    	return TODO;
    }
    
    public static Result SegnameNumHouseScore() {
    	
    	return TODO;
    }
    public static Result IPSegnameScoreMore() {
    	
    	return TODO;
    }
    
    public static Result IPSegnameScoreless() {
    	
    	return TODO;
    }
}
