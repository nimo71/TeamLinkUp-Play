package controllers

import play.api._
import play.api.libs._
import play.api.mvc._

object UserController extends Controller {
  
  	import views._
  	import models._
  	import services.Cypher
  	
  	def notices() = Action { implicit request => 
  		Logger.info(request.session.toString)
  		val userId = request.session("userId")
  		Ok(html.notices(Cypher.encode(userId)))
  	}
}