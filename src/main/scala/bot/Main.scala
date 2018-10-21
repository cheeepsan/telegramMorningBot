package bot

import java.io.File

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App{





  override def main(args: Array[String]): Unit = {
    val token: String = ConfigFactory.parseFile(new File("conf/application.conf")).getString("token")


    val db = new Db
    val bot = new Slavyane(token, db)
    val eol = bot.run()
    val watch: BotTimer = new BotTimer(bot)
    watch.run

    println("Press [ENTER] to shutdown the bot, it may take a few seconds...")
    scala.io.StdIn.readLine()
    bot.shutdown() // initiate shutdown
    // Wait for the bot end-of-life
    Await.result(eol, Duration.Inf)
    db.getDbObject.close()
  }

}
