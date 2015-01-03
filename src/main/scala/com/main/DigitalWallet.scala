package main.scala.com.main

import scala.beans.BeanProperty
import java.util.HashMap
import main.scala.com.model.User

object DigitalWallet
  {
  
  @BeanProperty
  val USER_COLLECTION : String = "User";
  
  @BeanProperty
  val BANK_COLLECTION : String = "BankAccount";
  
  @BeanProperty
  val CARD_COLLECTION : String = "IDCard";
  
  @BeanProperty
  val WEB_COLLECTION : String = "WebLogin";
  
  @BeanProperty
  val COUNTER_COLLECTION : String = "Counters";
  
  
  @BeanProperty
  val lookupURL : String ="http://www.routingnumbers.info/api/data.json?rn={routing_number}";

  @BeanProperty
  var appVersion : String ="v2";
  }