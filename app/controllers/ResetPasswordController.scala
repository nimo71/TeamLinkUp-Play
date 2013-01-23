package controllers

import play.api.i18n._
import play.api.data._
import play.api.data.Form._
import play.api.data.Forms._
import play.api.mvc._ 

object ResetPasswordController extends Controller {

	import models._
	import views._
	
	/**
  		Log in form backing object
  	*/
  	val forgottenPasswordForm = Form(
    		"email" -> email
  	)    
    
    /**
    	Show the forgotten password form
     */
    def forgottenPassword() = Action { implicit request => 
    	Ok(html.forgottenPassword(forgottenPasswordForm))
    }
    
    /**
    	Process the forgotten password form
     */
    def resetPassword() = Action { implicit request => 
    	forgottenPasswordForm.bindFromRequest.fold(
	    	// Form has errors, redisplay it
      		errors => BadRequest(html.forgottenPassword(errors)),
      
      		// We got a valid User value, display the summary
      		user => {
      			// save the new user to the database
      			Redirect(routes.Application.index)
      				.flashing("message" -> Messages("password.reset"))
      		}
      	)
    }

}