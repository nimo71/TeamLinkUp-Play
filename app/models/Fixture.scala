package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

import java.util.Date

case class Fixture(id: Long, home: String, away: String, datetime: Date)

object Fixture {
  
	val fixture = {
		get[Long]("fixtureId") ~
		get[String]("home") ~
		get[String]("away") ~
		get[Date]("datetime") map {
		  case fixtureId~home~away~datetime  => Fixture(fixtureId, home, away, datetime)
		}
	}
	
	def create(home: String, away: String, datetime: Date): Option[Fixture] = DB.withConnection { implicit c =>
	  	try {
		  	c.setAutoCommit(false)
		  	
		    SQL("insert into Fixture (home, away, datetime) values ({home}, {away}, {datetime})")
		    	.on(('home -> home), ('away -> away), ('datetime -> datetime))
		    	.executeUpdate
		    	
		    val f = SQL("select * from Fixture where fixtureId = currval('fixture_id_seq')")
		    	.as(fixture *)
		    	.headOption
		    	
		    c.commit
		    return f
	  	}
	  	catch {
	  	  	case e: Exception  => c.rollback
	  	  	return None
	  	}
	  	finally {
	  		c.setAutoCommit(true)
	  	}
	}
}