package controllers

import play.api._
import play.api.i18n._
import play.api.libs._
import play.api.mvc._
import play.api.data._
import play.api.data.Form._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
  	

object Application extends Controller {
  
  	import views._
  	import models._
  
  	/**
  		Log in form backing object
  	*/
  	val logInForm = Form(
    	tuple(
      		"email" -> email,
      		"password" -> text
    	)
    	verifying (
    		Messages("invalid.login.credentials"), 
    		result => result match {
      			case (email, password) => 
      				User.findByEmail(email).exists { _.password == Crypto.sign(password) }
    	})
  	)
  
  	/**
  		Show the log in page
  	*/
  	def index() = Action { implicit request =>
		Ok(html.index(logInForm))
    }
    
    /**
    	Process the login form
    */
    def logIn() = Action { implicit request =>
    	logInForm.bindFromRequest.fold(
      		formWithErrors => BadRequest(html.index(formWithErrors)),
      		result => 
      			User.findByEmail(result._1) match {
      				case Some(u) => 
      					Redirect(routes.UserController.notices())
      						.withSession("userId" -> u.id.toString())
      						.flashing("message" -> Messages("log.in.successful", u.email))
      					
      				case None => 
      					Redirect(routes.Application.index())
      						.flashing("message" -> Messages("log.in"))		
      		}
  		)
    }  
    
}