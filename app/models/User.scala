package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.libs._
import play.api.Play.current

case class User(id: Long, email: String, password: String)

object User {

	val user = {
  		get[Long]("userId") ~ 
  		get[String]("email") ~
  		get[String]("password") map {
    		case userId~email~password => User(userId, email, password)
  		}
	}

	/**
		Retrieve a user by email address.
	 */
	def findByEmail(email: String): Option[User] = DB.withConnection { implicit c => 
		SQL("select * from User where email = {email}")
			.on('email -> email)
			.as(user *)
			.headOption
	}
	
	/**
		Add a new user to the database
	 */	
	def create(email: String, password: String) = DB.withConnection { implicit c =>
   		SQL("insert into User (email, password) values ({email}, {password})")
   			.on(('email -> email), ('password -> Crypto.sign(password)))
   			.executeUpdate()
	}
	
}