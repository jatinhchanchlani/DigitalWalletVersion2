package main.scala.com.main.services
import org.springframework.context.annotation.Configuration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation._
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestParam
import main.scala.com.model.User
import java.util.ArrayList
import java.util.HashMap
import main.scala.com.model.WebLogin
import main.scala.com.model.BankAccount
import main.scala.com.model.IDCard
import javax.validation.Valid
import org.springframework.validation.BindingResult
import main.scala.com.model.IDCard
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import main.scala.com.model.User
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.validation._
import java.util.Date
import main.scala.com.main.DigitalWallet
import main.scala.com.model.WebLogin
import main.scala.com.main.MongoDBMain
import main.scala.com.main.common.CommonQueries
import main.scala.com.model.Counters

@RestController
@RequestMapping(value= Array("/digiwallet"+"/v2"))
class WebLoginService {

	@RequestMapping(value= Array("/users/{user_id}/weblogins"), method=Array(RequestMethod.POST),
	    produces = Array("application/json"), consumes = Array("application/json"))
	@ResponseBody 
	@ResponseStatus(HttpStatus.CREATED)
    def createWebLogin(@PathVariable("user_id") userId : String,
        @RequestBody @Valid login:WebLogin ): ResponseEntity[WebLogin] = {
	  
	 /**
	  * Getting user from MongoDB
	  */
	 var user_id : User = CommonQueries.getUserObj(userId);  
	 //var user_id : User = DigitalWallet.getUserList().get(userId);
	var nullid : WebLogin = null;
	if(user_id!=null)
	{
	  
	  var dbCounters : Counters = CommonQueries.getWebLoginCount();
	  var count : Int = dbCounters.getSeq() +1 ;
	  dbCounters.setSeq(count);
	  MongoDBMain.getMongoOps.save(dbCounters, DigitalWallet.getCOUNTER_COLLECTION()) 

	  var login_id :String  = "DWS_WEB-" + count
      login.setLogin_id(login_id)
      user_id.getSso().put(login_id, login)
      
        MongoDBMain.getMongoOps().insert(login,DigitalWallet.getWEB_COLLECTION());
	 	MongoDBMain.getMongoOps().save(user_id,DigitalWallet.getUSER_COLLECTION());
      
      new ResponseEntity[WebLogin](login,HttpStatus.CREATED)
	}
	else
	{
	  new ResponseEntity[WebLogin](nullid,HttpStatus.BAD_REQUEST)
	}
	
   }
	
	@RequestMapping(value= Array("/users/{user_id}/weblogins"), method=Array(RequestMethod.GET),
	    produces = Array("application/json"))
	@ResponseBody 
	@ResponseStatus(HttpStatus.OK)
    def lisAllWebLogins(@PathVariable("user_id")userId:String): ResponseEntity[HashMap[String,WebLogin]] = {
	  
    /**
	  * Getting user from MongoDB
	  */
	 var user_id : User = CommonQueries.getUserObj(userId);  
	 //var user_id : User = DigitalWallet.getUserList().get(userId);
    var nullId : HashMap[String,WebLogin] = null;
    if(user_id!=null)
    {
      return new ResponseEntity[HashMap[String,WebLogin]](user_id.getSso(),HttpStatus.OK)
    }
    else
    {
      return new ResponseEntity[HashMap[String,WebLogin]](nullId,HttpStatus.BAD_REQUEST)
    }
    
    
     }
	@RequestMapping(value= Array("/users/{user_id}/weblogins/{login_id}"), method=Array(RequestMethod.DELETE),
	    produces = Array("application/json"))
	@ResponseBody 
	@ResponseStatus(HttpStatus.NO_CONTENT)
    def deleteWebLogin(@PathVariable("user_id")userId:String,
        @PathVariable("login_id") loginId : String): ResponseEntity[String] = {
	  
     /**
	  * Getting user from MongoDB
	  */
	 var user_id : User = CommonQueries.getUserObj(userId);  
	 //var user_id : User = DigitalWallet.getUserList().get(userId);
    if(user_id!=null)
    {
    	if(user_id.getSso().get(loginId)!=null)
    	{
    	   CommonQueries.deleteWebLogin(loginId);
    	  user_id.getSso().remove(loginId);
    	  MongoDBMain.getMongoOps().save(user_id,DigitalWallet.getUSER_COLLECTION())
    	  
    	  return new ResponseEntity[String]("Web Login Deleted",HttpStatus.NO_CONTENT)
    	}
    	else
    	{
    	  return new ResponseEntity[String]("Invalid Web Login details.. Please enter correct login details",HttpStatus.BAD_REQUEST)
    	}
    }
    else
    {
      return new ResponseEntity[String]("Invalid User.. Please enter correct user details",HttpStatus.BAD_REQUEST)
    }
   }
	
}