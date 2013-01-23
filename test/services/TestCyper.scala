package test.services

import org.specs2.mutable.Specification
import services.Cypher

class CypherSpec extends Specification {

	import services.Cypher

	"encrypt" should {
	
		"hide the value of a long" in {
			val longVal = 1L
			Cypher.encode(longVal.toString) must not be equalTo(longVal.toString)
		}
	}

	"decrypt" should {
	
		"reveal the value of an encrypted long" in {
			val longVal = 1L
			val encoded = Cypher.encode(longVal.toString) 
			val decoded = Cypher.decode(encoded)
			
			decoded must equalTo(longVal.toString)
		}
		
		"reveal the value of the encrypted maximum long" in {
			val encoded = Cypher.encode(Long.MaxValue.toString)
			val decoded = Cypher.decode(encoded)
			
			decoded must equalTo(Long.MaxValue.toString)
		}
		
		"reveal the value of encrypted zero" in {
			val encoded = Cypher.encode(0.toString)
			val decoded = Cypher.decode(encoded)
			
			decoded must equalTo(0.toString)
		}
	}
}
  