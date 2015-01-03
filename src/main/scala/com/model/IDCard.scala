package main.scala.com.model

import scala.beans.BeanProperty
import javax.validation.constraints._
import org.hibernate.validator.constraints._
import java.util.Date
import org.springframework.data.annotation.Id

class IDCard {

  @Id
  @BeanProperty
  var card_id : String =_
  
  @NotNull(message ="Please enter your Card Name")
  @NotEmpty(message ="Pleae enter your Card Name")
  @BeanProperty
  var card_name : String =_
  
  @NotNull(message ="Please enter your Card Number")
  @NotEmpty(message ="Pleae enter your Card Number")
  @BeanProperty
  var card_number : String =_
  
  @BeanProperty
  var expiration_date: String =_
  
}