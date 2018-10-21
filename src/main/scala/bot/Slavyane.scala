package bot

import java.io.File

import com.bot4s.telegram.api.{Polling, RequestHandler, TelegramBot}
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.clients.ScalajHttpClient
import com.bot4s.telegram.methods.SendPhoto
import com.bot4s.telegram.models.{InputFile, Message}
import slick.driver.H2Driver.api._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


class Slavyane(val botToken: String, val db: Db) extends TelegramBot
  with Polling
  with Commands {
  val chats = TableQuery[Chats]
  override val client: RequestHandler  = new ScalajHttpClient(botToken)
  //  val chatId: ChatId = ChatId(id)
  //  val file: InputFile = InputFile(new File("files/slav.jpg").toPath)
  //  request(SendPhoto(chatId, file ))


  onCommand("/utro") { implicit msg: Message =>
    val chatId = msg.chat.id
    println(chatId)
    val ret = for {
      exists: Boolean <- db.getDbObject.run(chats.filter(_.chatId === chatId).exists.result)
      p <- processChatId(exists, chatId)

    } yield p

    val records = Await.result(ret, Duration.Inf)

    if (records == 0) {
      reply("Славяне! Вы уже добавлены в список.")
    } else {
      reply("Увидимся утром!")
    }
  }

  def goodMorning() = {
    val file: InputFile = InputFile(new File("files/slav.jpg").toPath)

    val dbObj = db.getDbObject

    val list = Await.result(dbObj.run(chats.result), Duration.Inf).toList
    list.foreach {
      x =>
        request(SendPhoto(x.chatId, file ))
    }

  }

  def processChatId(exists: Boolean, chatId: Long): Future[Int] = {
    println(exists)
    if (exists) {
      Future(0)
    } else {
      db.insert(Chat(None, chatId))
    }
  }

  def list(): Unit = {
    println("test")
    db.getDbObject.run(chats.result).map(_.foreach {
      c =>
        println("  " + c.chatId)
    })
  }

}
