package controllers

import play.api.i18n._
import play.api.data._
import play.api.data.Form._
import play.api.data.Forms._
import play.api.mvc._ 

object RegistrationController extends Controller {

	import models._
	import views._
  	
	/** 
    	Registration form backing object
    */
    val registrationForm = Form(
    	tuple(
    		"email" -> tuple (
    			"main" -> email,
    			"confirm" -> text
    		).verifying (
    			Messages("values.dont.match", "Email", "Confirm Email"), 
    			result => result match {
    				case (main, confirm) => main == confirm
    			}
    		),
    		"password" -> tuple (
      			"main" -> text.verifying(
      				Messages("error.password", 4, 12), 
      				s => { s.length >= 4 && s.length <= 12 } ),
      			"confirm" -> text
      		).verifying (
      		    Messages("values.dont.match", "Password", "Confirm Password"), 
    			result => result match {
    				case (main, confirm) => main == confirm
    			}
      		)
    	).verifying (
    		Messages("user.already.exists"), 
    		result => result match {
    			case ((email, _), (_, _)) => 
    				!User.findByEmail(email).isDefined
    		}
    		
    	)
    ) 
    
    /**
    	Show the registration form
    */
    def registration() = Action { implicit request =>
    	Ok(html.registration(registrationForm))
    }
    
    /**
    	Process the registration form
    */
    def register() = Action { implicit request => 
    	registrationForm.bindFromRequest.fold(
	    	// Form has errors, redisplay it
      		errors => BadRequest(html.registration(errors)),
      
      		// We got a valid User value, display the summary
      		user => {
      			// save the new user to the database
      			User.create(user._1._1, user._2._1)
      			Redirect(routes.Application.index)
      				.flashing("message" -> Messages("log.in.after.registration"))
      		}
    	)
    	
    }
}