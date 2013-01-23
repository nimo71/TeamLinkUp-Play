package services

object Cypher {
	
	//val SHA1 = java.security.MessageDigest.getInstance("SHA-1")
	
	def encode(cleartext: String) : String = {
		new sun.misc.BASE64Encoder().encode( cleartext.getBytes )
	}
	
	def decode(cyphertext: String) : String = {
		new String(new sun.misc.BASE64Decoder().decodeBuffer(cyphertext))
	}
	
}