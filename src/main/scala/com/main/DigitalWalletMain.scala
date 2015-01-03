package main.scala.com.main

import org.springframework.boot.SpringApplication
import java.util.HashMap
import scala.beans.BeanProperty
import main.scala.com.model.User
import org.springframework.data.mongodb.core.query._
import main.scala.com.model.Counters

object DigitalWalletMain {
  
  def main(args: Array[String]) {
   
    createUserCounter();
    createWebLoginCounter();
    createIdCardCounter();
    createBankAccountCounter();
    
    SpringApplication.run(classOf[Services]);
 }
  def createUserCounter()=
  {
    var getUserCountQuery : Query  = new Query(Criteria.where("name").is("user"));
  	var userCount : Counters = MongoDBMain.getMongoOps().findOne(getUserCountQuery,classOf[Counters],DigitalWallet.getCOUNTER_COLLECTION());
	if(userCount==null)
	{
		userCount = new Counters();
    	userCount.setName("user");
    	userCount.setSeq(10000);
    	MongoDBMain.getMongoOps.insert(userCount,DigitalWallet.getCOUNTER_COLLECTION())
	}
  }
  
  def createBankAccountCounter()=
  {
    var getbankaccountCountQuery : Query  = new Query(Criteria.where("name").is("bankaccount"));
  	var bankAccountCount : Counters = MongoDBMain.getMongoOps().findOne(getbankaccountCountQuery,classOf[Counters],DigitalWallet.getCOUNTER_COLLECTION());
	if(bankAccountCount==null)
	{
		bankAccountCount = new Counters();
    	bankAccountCount.setName("bankaccount");
    	bankAccountCount.setSeq(10000);
    	MongoDBMain.getMongoOps.insert(bankAccountCount,DigitalWallet.getCOUNTER_COLLECTION())
	}
  }
	def createWebLoginCounter()=
  {
    var getwebloginCountQuery : Query  = new Query(Criteria.where("name").is("weblogin"));
  	var webloginCount : Counters = MongoDBMain.getMongoOps().findOne(getwebloginCountQuery,classOf[Counters],DigitalWallet.getCOUNTER_COLLECTION());
	if(webloginCount==null)
	{
		webloginCount = new Counters();
    	webloginCount.setName("weblogin");
    	webloginCount.setSeq(10000);
    	MongoDBMain.getMongoOps.insert(webloginCount,DigitalWallet.getCOUNTER_COLLECTION())
	}
  }
	
	def createIdCardCounter()=
  {
    var getIdCardCountQuery : Query  = new Query(Criteria.where("name").is("idcard"));
  	var IdCardCount : Counters = MongoDBMain.getMongoOps().findOne(getIdCardCountQuery,classOf[Counters],DigitalWallet.getCOUNTER_COLLECTION());
	if(IdCardCount==null)
	{
		IdCardCount = new Counters();
    	IdCardCount.setName("idcard");
    	IdCardCount.setSeq(10000);
    	MongoDBMain.getMongoOps.insert(IdCardCount,DigitalWallet.getCOUNTER_COLLECTION())
	}
  }
}