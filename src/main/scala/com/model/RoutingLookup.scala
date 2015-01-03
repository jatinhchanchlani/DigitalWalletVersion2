package main.scala.com.model

import scala.beans.BeanProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
class RoutingLookup
{

  @BeanProperty
  var routing_number : String =_   
  
  @BeanProperty
  var code : Int =_
  
  @BeanProperty
  var customer_name : String =_
}