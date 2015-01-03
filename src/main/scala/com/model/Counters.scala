package main.scala.com.model

import scala.beans.BeanProperty
import org.springframework.data.annotation.Id

class Counters
{

  @Id
  @BeanProperty
  var name: String =_
  
  @BeanProperty
  var seq : Int =_
}