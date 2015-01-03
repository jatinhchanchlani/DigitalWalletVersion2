package main.scala.com.model

import scala.beans.BeanProperty
import javax.validation.constraints._
import org.hibernate.validator.constraints._
import org.springframework.data.annotation.Id

class WebLogin {

  @Id
  @BeanProperty
  var login_id : String=_
  
  @NotEmpty(message ="Please enter the website")
  @NotNull(message = "Please enter the website")
  @BeanProperty
  var url : String=_
  
  @NotEmpty(message ="Please enter your login id")
  @NotNull(message = "Please enter your login id")
  @BeanProperty
  var login : String=_
  
  @NotEmpty(message ="Please enter your password")
  @NotNull(message = "Please enter your password")
  @BeanProperty
  var password : String=_
}