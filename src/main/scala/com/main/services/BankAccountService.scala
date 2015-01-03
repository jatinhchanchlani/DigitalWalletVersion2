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
import main.scala.com.model.BankAccount
import main.scala.com.model.BankAccount
import main.scala.com.model.BankAccount
import main.scala.com.main.MongoDBMain
import org.springframework.web.client.RestTemplate
import main.scala.com.model.RoutingLookup
import main.scala.com.model.RoutingLookup
import main.scala.com.model.RoutingLookup
import org.springframework.http.HttpEntity
import main.scala.com.model.RoutingLookup
import org.springframework.http.HttpHeaders
import main.scala.com.model.RoutingLookup
import main.scala.com.model.RoutingLookup
import main.scala.com.model.RoutingLookup
import java.util.Arrays
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import main.scala.com.model.RoutingLookup
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import main.scala.com.model.RoutingLookup
import java.util.List
import main.scala.com.main.common.CommonQueries
import main.scala.com.model.Counters

@RestController
@RequestMapping(value = Array("/digiwallet" + "/v2"))
class BankAccountService {

  @RequestMapping(value = Array("/users/{user_id}/bankaccounts"), method = Array(RequestMethod.POST),
    produces = Array("application/json"), consumes = Array("application/json"))
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  def createBankAccount(@PathVariable("user_id") userId: String, @RequestBody @Valid bankAccnt: BankAccount): ResponseEntity[BankAccount] = {

    /**
     *  Getting the user from MongoDB
     */
    //var user_id : User = DigitalWallet.getUserList().get(userId);
    var user_id: User = CommonQueries.getUserObj(userId);

    var nullId: BankAccount = null;
    if (user_id != null) {
      var dbCounters: Counters = CommonQueries.getBankAccountCount();
      var count: Int = dbCounters.getSeq() + 1;
      dbCounters.setSeq(count);
      MongoDBMain.getMongoOps.save(dbCounters, DigitalWallet.getCOUNTER_COLLECTION())
      var aacntId: String = "DWS_ACC-" + count
      bankAccnt.setBa_id(aacntId)

      var customer_name: String = getCustomerNameFromLookUpService(bankAccnt.getRouting_number());
      System.out.print("Customer name from lookup : " + customer_name);
      bankAccnt.setAccount_name(customer_name);
      
      user_id.getAccnt().put(aacntId, bankAccnt)

      MongoDBMain.getMongoOps().insert(bankAccnt, DigitalWallet.getBANK_COLLECTION());
      MongoDBMain.getMongoOps().save(user_id, DigitalWallet.getUSER_COLLECTION())

      return new ResponseEntity[BankAccount](bankAccnt, HttpStatus.CREATED)
    } else {
      return new ResponseEntity[BankAccount](nullId, HttpStatus.BAD_REQUEST)
    }
  }

  @RequestMapping(value = Array("/users/{user_id}/bankaccounts"), method = Array(RequestMethod.GET),
    produces = Array("application/json"))
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  def lisAllBankAccnts(@PathVariable("user_id") userId: String): ResponseEntity[HashMap[String, BankAccount]] = {

    /**
     * Getting user from MongoDB
     */
    var user_id: User = CommonQueries.getUserObj(userId);
    //var user_id : User = DigitalWallet.getUserList().get(userId);
    var nullid: HashMap[String, BankAccount] = null;
    if (user_id != null) {

      return new ResponseEntity[HashMap[String, BankAccount]](user_id.getAccnt(), HttpStatus.OK)
    } else {
      return new ResponseEntity[HashMap[String, BankAccount]](nullid, HttpStatus.BAD_REQUEST)
    }
  }
  @RequestMapping(value = Array("/users/{user_id}/bankaccounts/{ba_id}"), method = Array(RequestMethod.DELETE))
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def deleteBankAccnt(@PathVariable("user_id") userId: String,
    @PathVariable("ba_id") ba_id: String): ResponseEntity[String] = {

    /**
     * Getting user from MongoDB
     */
    var user_id: User = CommonQueries.getUserObj(userId);
    //var user_id : User = DigitalWallet.getUserList().get(userId);
    if (user_id != null) {
      var accnt: BankAccount = user_id.getAccnt().get(ba_id)
      if (accnt != null) {

        CommonQueries.deleteBankAccount(ba_id);
        user_id.getAccnt().remove(ba_id)
        MongoDBMain.getMongoOps().save(user_id, DigitalWallet.getUSER_COLLECTION())

        return new ResponseEntity[String]("Bank Account Deleted", HttpStatus.NO_CONTENT);
      } else {
        return new ResponseEntity[String]("Invalid Bank Account Id.. Please enter correct bank account details", HttpStatus.BAD_REQUEST);

      }
    } else {
      return new ResponseEntity[String]("Invalid User.. Please enter correct user details", HttpStatus.BAD_REQUEST);

    }
  }

  def getCustomerNameFromLookUpService(routing_num: String): String =
    {

      var restTemplate: RestTemplate = new RestTemplate();
      restTemplate.setMessageConverters(getMessageConverters());
      
      var response : ResponseEntity[RoutingLookup] = restTemplate.getForEntity(DigitalWallet.getLookupURL(), classOf[RoutingLookup], routing_num);
      var routingLookupObj : RoutingLookup = response.getBody();
      
      var status: Int = routingLookupObj.getCode();
      if(status == 200)
      {
       return routingLookupObj.getCustomer_name(); 
      }
      return "Unknown";
    }
  
  /**
   * This method adds the text/plain mediatype in MappingJackson2HttpMessageConverter
   * Essential to get the response in java object: RoutingLookup in our case
   */
  def getMessageConverters() : ArrayList[HttpMessageConverter[_]]= 
  {
      var jsonConverter: MappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
      var jsonTypes: List[MediaType] = new ArrayList[MediaType](jsonConverter.getSupportedMediaTypes());
      jsonTypes.add(MediaType.TEXT_PLAIN);
      jsonConverter.setSupportedMediaTypes(jsonTypes);
      
      var messageConverters = new ArrayList[HttpMessageConverter[_]]()
      messageConverters.add(jsonConverter);
      
      return messageConverters;
  }
}