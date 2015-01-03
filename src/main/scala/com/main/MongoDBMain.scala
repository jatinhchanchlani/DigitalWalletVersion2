package main.scala.com.main

import scala.beans.BeanProperty
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.mongodb.MongoClient;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.ServerAddress;
import com.mongodb.MongoURI;

import com.mongodb.Mongo;

object MongoDBMain
{
  val LOCAL_DB_NAME: String   = "DigitWalletSystem";
  val LOCAL_MONGO_HOST : String = "localhost";
  val LOCAL_MONGO_PORT : Int = 27017;
  
  
  val DB_NAME: String   = "digitwalletsystem";
  val MONGO_HOST : String = "mongodb://jatin:jatin12345@ds045970.mongolab.com:45970/digitwalletsystem";
  val MONGO_PORT : Int = 45970;
  
  
  var mongo : Mongo = new Mongo(new MongoURI(MONGO_HOST))
  var factory : MongoDbFactory  =  new SimpleMongoDbFactory(mongo,DB_NAME);
  
  @BeanProperty
  val mongoOps : MongoOperations  = new MongoTemplate(factory);
  
}