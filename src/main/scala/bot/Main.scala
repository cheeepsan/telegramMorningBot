package bot

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App{



  val token: String = "710158750:AAGNG-rhf39-y4c8OY2PMdp-i0VtWwjyNm8"

  override def main(args: Array[String]): Unit = {
    //    val bot = new bot.Slavyane(token)

    val db = new Db
    val bot = new Slavyane(token, db)
    val watch: BotTimer = new BotTimer(bot)
    watch.run
    val eol = bot.run()
    println("Press [ENTER] to shutdown the bot, it may take a few seconds...")
    scala.io.StdIn.readLine()
    bot.shutdown() // initiate shutdown
    // Wait for the bot end-of-life
    Await.result(eol, Duration.Inf)
    db.getDbObject.close()
  }

}
