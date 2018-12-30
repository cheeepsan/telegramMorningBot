package bot

// Use H2Driver to connect to an H2 database
import java.io.File

import com.typesafe.config.{Config, ConfigFactory}
import slick.driver.H2Driver.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

case class Chat(option: Option[Int], chatId: Long)


// Definition of the Chats table
class Chats(tag: Tag) extends Table[Chat](tag, "Chats") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc) // This is the primary key column
  def chatId = column[Long]("chat_id")

  // Every table needs a * projection with the same type as the table's type parameter
  def * =  (id.?, chatId) <> (Chat.tupled, Chat.unapply)
}


class Db {
  val chats = TableQuery[Chats]
  val conf: Config = ConfigFactory.parseFile(new File("conf/db.conf"))
  val db: Database = Database.forConfig("h2mem1", conf)
  //  val db = Database.forConfig("h2mem1")
  try {
    val setup = DBIO.seq(
      // Create the tables, including primary and foreign keys
      chats.schema.create,
      chats ++= Seq(
        Chat(None, -238020854)
        //      Chat(None, -123131)
      )

    )

    // ...
    val setupFuture = db.run(setup)
    Await.result(setupFuture, Duration.Inf)
  } catch {
    case e =>
      println("Db exists already")
  }

  def getDbObject: Database = this.db

  def list(): Unit = {
    println("test")
    db.run(chats.result).map(_.foreach {
      x =>
        println("  " + x.chatId)
    })
  }

  def insert(chat: Chat) =  {

    db.run((chats returning chats.map(_.id)) += chat)
  }
}




