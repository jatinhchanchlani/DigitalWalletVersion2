package main.scala.com.main.common;

import main.scala.com.model.User
import main.scala.com.main.MongoDBMain
import org.springframework.data.mongodb.core.query._
import org.springframework.data.mongodb.core.query.Update._
import main.scala.com.model.BankAccount
import main.scala.com.model.BankAccount
import main.scala.com.model.BankAccount
import java.util.HashMap
import java.util.ArrayList
import java.util.List
import main.scala.com.model.BankAccount
import main.scala.com.model.IDCard
import main.scala.com.model.WebLogin
import main.scala.com.main.DigitalWallet
import main.scala.com.model.Counters

object CommonQueries {

  def getUserObj(userID: String) : User =
		{
		  var searchUserQuery : Query  = new Query(Criteria.where("_id").is(userID));
		  var user : User = MongoDBMain.getMongoOps().findOne(searchUserQuery,classOf[User],DigitalWallet.getUSER_COLLECTION());
		  return user;
		}
  
  def deleteBankAccount(ba_id: String)  =
		{
		  var deleteBankAccountQuery : Query  = new Query(Criteria.where("_id").is(ba_id));
		  MongoDBMain.getMongoOps().remove(deleteBankAccountQuery,classOf[BankAccount],DigitalWallet.getBANK_COLLECTION());
		}
   def deleteCard(card_id: String)  =
		{
		  var deleteCardQuery : Query  = new Query(Criteria.where("_id").is(card_id));
		  MongoDBMain.getMongoOps().remove(deleteCardQuery,classOf[IDCard],DigitalWallet.getCARD_COLLECTION());
		}
     def deleteWebLogin(card_id: String)  =
		{
		  var deleteWebLoginQuery : Query  = new Query(Criteria.where("_id").is(card_id));
		  MongoDBMain.getMongoOps().remove(deleteWebLoginQuery,classOf[WebLogin],DigitalWallet.getWEB_COLLECTION());
		}
     def getUserCount() : Counters =
		{
		  var getUserCountQuery : Query  = new Query(Criteria.where("name").is("user"));
		  var userCount : Counters = MongoDBMain.getMongoOps().findOne(getUserCountQuery,classOf[Counters],DigitalWallet.getCOUNTER_COLLECTION);
		  return userCount;
		}
    def getBankAccountCount() : Counters =
		{
		  var getUserCountQuery : Query  = new Query(Criteria.where("name").is("bankaccount"));
		  var userCount : Counters = MongoDBMain.getMongoOps().findOne(getUserCountQuery,classOf[Counters],DigitalWallet.getCOUNTER_COLLECTION());
		  return userCount;
		}
     def getWebLoginCount() : Counters =
		{
		  var getwebloginCountQuery : Query  = new Query(Criteria.where("name").is("weblogin"));
		  var webloginCount : Counters = MongoDBMain.getMongoOps().findOne(getwebloginCountQuery,classOf[Counters],DigitalWallet.getCOUNTER_COLLECTION());
		  return webloginCount;
		}
     def getIdCardCount() : Counters =
		{
		  var getIdCardCountQuery : Query  = new Query(Criteria.where("name").is("idcard"));
		  var IdCardCount : Counters = MongoDBMain.getMongoOps().findOne(getIdCardCountQuery,classOf[Counters],DigitalWallet.getCOUNTER_COLLECTION());
		  return IdCardCount;
		}
}