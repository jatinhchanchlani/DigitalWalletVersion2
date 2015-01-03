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
import main.scala.com.model.IDCard
import java.util.{Date, Locale}
import java.text.DateFormat
import java.text.DateFormat._
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.GregorianCalendar
import main.scala.com.main.MongoDBMain
import main.scala.com.main.common.CommonQueries
import main.scala.com.model.Counters


@RestController
@RequestMapping(value= Array("/digiwallet"+"/v2"))
class IDCardService {
  
	@RequestMapping(value= Array("/users/{user_id}/idcards"), method=Array(RequestMethod.POST),
	    produces = Array("application/json"), consumes = Array("application/json"))
	@ResponseBody 
    def createIDCard(@PathVariable("user_id")userId:String, 
       @RequestBody @Valid idcard: IDCard): ResponseEntity[IDCard] = {
	 var nullId : IDCard = null; 
	 
	 /**
	  * Getting user from MongoDB
	  */
	 var user_id : User = CommonQueries.getUserObj(userId);  
	 //var user_id : User = DigitalWallet.getUserList().get(userId);
	 if(user_id!=null)
	 {
	   var dbCounters : Counters = CommonQueries.getIdCardCount();
	   var count : Int = dbCounters.getSeq() +1 ;
	   dbCounters.setSeq(count);
	   MongoDBMain.getMongoOps.save(dbCounters, DigitalWallet.getCOUNTER_COLLECTION())  
	   
	   var card_id:String  = "DWS_IDC-" + count
	   idcard.setCard_id(card_id)
	   
	   var date:String = new SimpleDateFormat("dd-M-yyyy hh:mm:ss") format new Date()
	   idcard.setExpiration_date(date)
	   
	   	user_id.getId_cardList().put(card_id,idcard);
	   
	    MongoDBMain.getMongoOps().insert(idcard,DigitalWallet.getCARD_COLLECTION());
	 	MongoDBMain.getMongoOps().save(user_id,DigitalWallet.getUSER_COLLECTION());
	 	
	   return new ResponseEntity[IDCard](idcard,HttpStatus.CREATED);
	 }
	 else
	 {
	   return new ResponseEntity[IDCard](nullId,HttpStatus.BAD_REQUEST);
	 }
     }
	
	@RequestMapping(value= Array("/users/{user_id}/idcards"), method=Array(RequestMethod.GET),
	    produces = Array("application/json"))
	@ResponseBody 
	@ResponseStatus(HttpStatus.OK)
    def lisAllIDCards(@PathVariable("user_id")userId:String): ResponseEntity[HashMap[String,IDCard]] = {
	  
	var nullId : HashMap[String,IDCard] = null;  
   /**
	  * Getting user from MongoDB
	  */
	 var user_id : User = CommonQueries.getUserObj(userId);  
	 //var user_id : User = DigitalWallet.getUserList().get(userId);
    if(user_id !=null)
    {
    	return new ResponseEntity[HashMap[String,IDCard]](user_id.getId_cardList(),HttpStatus.OK)  
    }
    else
    {
    	return new ResponseEntity[HashMap[String,IDCard]](nullId,HttpStatus.BAD_REQUEST)
    }
    
    
     }
	@RequestMapping(value= Array("/users/{user_id}/idcards/{card_id}"), method=Array(RequestMethod.DELETE),
	    produces = Array("application/json"))
	@ResponseBody 
	@ResponseStatus(HttpStatus.NO_CONTENT)
    def deleteIDCard(@PathVariable("user_id")userId:String,
        @PathVariable("card_id") cardId : String): ResponseEntity[String] = {
	  
    /**
	  * Getting user from MongoDB
	  */
	 var user_id : User = CommonQueries.getUserObj(userId);  
	 //var user_id : User = DigitalWallet.getUserList().get(userId);
    if(user_id!=null)
    {
    	var card :IDCard = user_id.getId_cardList().get(cardId);
     	if(card!=null)
     	{
       
     		CommonQueries.deleteCard(cardId);
     		user_id.getId_cardList().remove(cardId)
     		MongoDBMain.getMongoOps().save(user_id,DigitalWallet.getUSER_COLLECTION())
     		return new ResponseEntity[String]("Card Deleted",HttpStatus.NO_CONTENT)
     	}
     	else
     	{
     		return new ResponseEntity[String]("Invalid Card.. Please enter correct card details",HttpStatus.BAD_REQUEST)
     	}
    }
    else
    {
      return new ResponseEntity[String]("Invalid User.. Please enter correct user details",HttpStatus.BAD_REQUEST)
    }
    
   }
	
}