package main.scala.com.model

import scala.beans.BeanProperty
import javax.validation.constraints._
import org.hibernate.validator.constraints._
import org.springframework.data.annotation.Id

class BankAccount {
 
  @Id
  @BeanProperty
  var ba_id : String=_

  @NotEmpty(message ="Please enter your Routing Number")
  @NotNull(message = "Please enter your Routing Number")
  @BeanProperty
  var routing_number : String=_
  
  
  @BeanProperty
  var account_name : String=_
  
  @BeanProperty
  @NotEmpty(message ="Please enter your Account Number")
  @NotNull(message = "Please enter your Account Number")
  var account_number : String=_

}