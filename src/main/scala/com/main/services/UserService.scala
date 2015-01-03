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
import java.util.{Date, Locale}
import java.text.DateFormat
import java.text.DateFormat._
import java.util.Calendar
import java.text.SimpleDateFormat
import javax.servlet.http.HttpServletResponse
import main.scala.com.main.MongoDBMain
import org.springframework.data.mongodb.core.query._
import main.scala.com.model.User
import main.scala.com.model.Counters
import main.scala.com.main.common.CommonQueries

@RestController
@RequestMapping(value= Array("/digiwallet"+"/v2"))
class UserService 
{
	var count : Int =0
			var etag : String = ""

@RequestMapping(value= Array("/users"), method=Array(RequestMethod.POST),
			produces = Array("application/json"), consumes= Array("application/json"))
@ResponseStatus(HttpStatus.CREATED)
@ResponseBody 
def createUser(@RequestBody @Valid userObj : User): ResponseEntity[User] = {
		println("Inside user")
		
		var dbCounters : Counters = CommonQueries.getUserCount();
		var count : Int = dbCounters.getSeq() + 1;
		dbCounters.setSeq(count);
		MongoDBMain.getMongoOps.save(dbCounters, DigitalWallet.getCOUNTER_COLLECTION())
		var userId = "DWS_USR-" + count;
		userObj.setUser_id(userId);
		var date:String = new SimpleDateFormat("dd-M-yyyy hh:mm:ss") format new Date()

		userObj.setCreated_at(date)
		userObj.setUpdated_at(date)

		MongoDBMain.getMongoOps().insert(userObj,DigitalWallet.getUSER_COLLECTION());
		// DigitalWallet.getUserList.put(userId,userObj);
		new ResponseEntity[User](userObj,HttpStatus.OK)
}

@RequestMapping(value= Array("/users/{user_id}"), method=Array(RequestMethod.GET),
produces = Array("application/json"))
@ResponseBody 
@ResponseStatus(HttpStatus.OK)
def viewUser(@PathVariable(value = "user_id") userId: String,
		@RequestHeader(value="ETag", required =false, defaultValue="") etag : String, 
		response : HttpServletResponse) : ResponseEntity[User] = 
	{
				var nullid: User = null
				/**
				 * Getting the user from MongoDB
				 */
				//var userObj:User = DigitalWallet.getUserList().get(userId);
				var userObj : User = CommonQueries.getUserObj(userId)
		if(userObj !=null)
		{
			var tag : String  = userObj.getEtag();
			if(etag.equals(""))
			{
			response.addHeader("ETag",tag)
			new ResponseEntity[User](userObj,HttpStatus.OK)
			}
			else if(!tag.equals(etag))
			{
			response.addHeader("ETag",tag)
			new ResponseEntity[User](userObj,HttpStatus.OK)
			}
			else
			{
			new ResponseEntity[User](nullid,HttpStatus.NOT_MODIFIED)
			}
		}
		else
		{
			new ResponseEntity[User](userObj,HttpStatus.BAD_REQUEST)  
		}

	}

		@RequestMapping(value= Array("/users/{user_id}"), method=Array(RequestMethod.PUT),
				produces = Array("application/json"),consumes = Array("application/json"))
		@ResponseBody
		@ResponseStatus(HttpStatus.CREATED)
		def updateUser(@PathVariable(value ="user_id")userId:String, 
				@RequestBody @Valid userObj: User,
				response : HttpServletResponse ): ResponseEntity[User] =
			{
				var userOrig : User = CommonQueries.getUserObj(userId);
		if(userOrig!=null)
		{
			if(userObj.getEmail_id()!=null)
			{
				userOrig.setEmail_id(userObj.getEmail_id());
			}
			if(userObj.getPassword()!=null)
			{
				userOrig.setPassword(userObj.getPassword());
			}
			if(userObj.getName()!=null)
			{
				userOrig.setName(userObj.getName());
			}
			userOrig.setEtag(computeETag())
			var date:String = new SimpleDateFormat("dd-M-yyyy hh:mm:ss") format new Date()
			userOrig.setUpdated_at(date)
			
			MongoDBMain.getMongoOps().save(userOrig,DigitalWallet.getUSER_COLLECTION())
			//DigitalWallet.getUserList.put(userId, userOrig);

			response.addHeader("ETag",etag);
			new ResponseEntity[User](userOrig,HttpStatus.CREATED)
		}
		else
		{
			new ResponseEntity[User](userOrig,HttpStatus.BAD_REQUEST)
		}
			}

		def computeETag() : String =
			{
				count = count+1;
				etag =  "usr123456"+count; 
				return etag;
			}
		def getEtagValue() : String =
			{
				return etag;
			}
		
}