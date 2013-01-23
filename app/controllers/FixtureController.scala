package controllers

import play.api._
import play.api.mvc._
import play.api.i18n._
import play.api.libs._
import play.api.libs.json._
import play.api.libs.json.Json._
import com.codahale.jerkson.Json._
import scala.collection._
import java.util.Calendar
import java.text.SimpleDateFormat

object FixtureController extends Controller {
  
	import views._
	import models.Fixture
	import services.Cypher
  
	/**
    	List fixtures
    */
    def fixtures() = Action { implicit request =>
      	Logger.info("fixtures()"+ request.session.toString)
      	val userId = request.session("userId")
      	Ok(html.fixtures(Cypher.encode(userId)))
    }
    
    /**
     * Create a new selection
     */
    def create = Action(parse.json) { implicit request =>
      	Logger.info("create()")
      	val errors = mutable.Map.empty[String, String]
      	
      	val home = (request.body \ "home").asOpt[String] match {
      	    case None => 
      	      	errors += ("home" -> Messages("fixture.home.not.none"))
      	      	None
      	    case Some("") => 
      	      	errors += ("home" -> Messages("fixture.home.not.blank"))
      	    	None
      	    case Some(home) => Some(home) 
      	}
      	val away = (request.body \ "away").asOpt[String] match {
      	  	case None => 
      	  	  	errors += ("away" -> Messages("fixture.away.not.none"))
      	  	  	None
      	  	case Some("") => 
      	  	  	errors += ("away" -> Messages("fixture.away.not.blank"))
      	  		None
      	  	case Some(away) => Some(away)
      	}
      	var dateRegExp = """(dd)/(dd)/(dddd)""".r
      	val date = (request.body \ "date").asOpt[String] match {
      	  	case None => 
      	  	  	errors += ("date" -> Messages("fixture.date.not.none"))
      	  	  	None
      	  	case Some("") => 
      	  	  	errors += ("date" -> Messages("fixture.date.not.blank"))
      	  		None
      	  	case Some(dateRegExp(dd,mm,yyyy)) => Some("%s/%s/%s".format(dd, mm, yyyy))
      	  	case Some(badDateFormat) => 
      	  	  	errors += ("date" -> Messages("fixture.date.bad.format"))
      	  	  	None
      	}
      	val time = (request.body \ "time").asOpt[String] match {
      	  	case None => 
      	  	  	errors += ("time" -> Messages("fixture.time.not.none"))
      	  	  	None
      	  	case Some("") => 
      	  	  	errors += ("time" -> Messages("fixture.time.not.blank"))
      	  	  	None
      	  	case Some(time) => Some(time)
      	}
      	
      	if (errors.size > 0) {
      		// TODO: use more efficient play 2.x json manipulation libraries when next version comes out.
      		val res = Map(
      		    "status" -> "formErrors", 
      		    "errors" -> errors)
      		    
      		Ok(generate(res))
      	}
      	else {
      		val cal = Calendar.getInstance
      		// TODO: retrieve the date format from the locale sent in the request if possible
      		val dateFormat = new SimpleDateFormat("dd/MM/yyyy")
      		val d = dateFormat.parse(date.get)
      		cal.setTime(d);
      		val hh = time.get.split(":")(0);
      		val mm = time.get.split(":")(1);
      		cal.set(Calendar.HOUR_OF_DAY, hh.toInt);
      		cal.set(Calendar.MINUTE, mm.toInt);
      		
      		val fixture = Fixture.create(home.get, away.get, cal.getTime)
      		val res = fixture match {
      		  	case Some(f) => 
      				Map(
      					"status" -> "saved", 
      					"fixture" -> Map(
      							"home" -> f.home, 
      							"away" -> f.away, 
      							"datetime" -> f.datetime) )
      		  	case None => 
      				Map("status" -> "save error")
      		}
      		Ok(generate(res))
      	}
    }  	
}