package main.scala.com.main
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

@Configuration
@EnableAutoConfiguration
@ComponentScan
class Services
{

	/*@ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    def handleException(exception: MethodArgumentNotValidException ) : Error = {
        return new ApiErrors(exception.getBindingResult())
    }
  */
	
	
	/*@RequestMapping(value= Array("/users/{user_id}/idcards"), method=Array(RequestMethod.POST))
	@ResponseBody 
    def createIDCard(@PathVariable("user_id")userId:String, 
        @RequestParam("card_name") card_name: String,
        @RequestParam("card_number") card_number: String): IDCard = {
	  
	var count : Int = DigitalWallet.getIdCardCount()+1  
    var card : IDCard = new IDCard();
	var card_id:String  = "DWS_IDC-" + count
    card.setCard_id(card_id)
    DigitalWallet.setIdCardCount(count)    
    card.setCard_name(card_name)
    card.setCard_number(card_number)
    
    var user_id : User = DigitalWallet.getUserList().get(userId);
    user_id.getId_cardList().put(card_id,card);
    card
     }
	*/
	
}

