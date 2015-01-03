package main.scala.com.model

import scala.beans.BeanProperty
import java.util.ArrayList
import java.util.HashMap
import java.util.Date
import javax.validation.constraints._
import org.hibernate.validator.constraints._
import org.springframework.data.annotation.Id
class User {

  
  @Id
  @BeanProperty
  var user_id : String =_
  
  @NotEmpty(message ="Pleae enter your Email ID")
  @NotNull(message = "Please enter your Email ID")
  @Email(message="Please enter a valid Email ID")
  @BeanProperty
  var email_id : String  =_
  
  @NotEmpty(message ="Pleae enter your Password")
  @NotNull(message = "Please enter your Password")
  @Length(min = 6,max = 10, message="Password length should be between 6 - 10 characters")
  @BeanProperty
  var password : String =_
  
  @BeanProperty
  var name : String =_  
  
  @BeanProperty
  var created_at : String =_
  
  @BeanProperty
  var updated_at : String =_
  
  @BeanProperty
  var etag : String="";
  
  @BeanProperty
  var id_cardList : HashMap[String,IDCard] = new HashMap[String,IDCard]()
  
  @BeanProperty
  var sso : HashMap[String,WebLogin] = new HashMap[String,WebLogin]()
  
  @BeanProperty
  var accnt : HashMap[String,BankAccount] = new HashMap[String,BankAccount]() 
  
  def computeEtag() : Int =
  {
    return this.hashCode();
  }
}