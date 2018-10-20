package bot

import akka.actor.ActorSystem
import com.github.nscala_time.time.Imports._

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.{FiniteDuration, MINUTES}
// ...now with system in current scope:

class BotTimer(val bot: Slavyane) {


  def run(): Unit = {
    val goalTime = 9.hour

    val system = ActorSystem()
    implicit val ec = ExecutionContext.global
    system.scheduler.schedule(FiniteDuration.apply(1, MINUTES), FiniteDuration.apply(2, MINUTES)) {
      val clock = DateTime.now().hourOfDay().get()

//      if (clock == 9) {
        bot.goodMorning
//      }
    }

  }
}
