package bot

import akka.actor.ActorSystem
import com.github.nscala_time.time.Imports._

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.{FiniteDuration, MINUTES, SECONDS}
// ...now with system in current scope:

class BotTimer(val bot: Slavyane) {


  def run(): Unit = {

    val system = ActorSystem()
    implicit val ec = ExecutionContext.global

    var visitedDay = DateTime.now().dayOfWeek().get()
    var visited = false
    system.scheduler.schedule(FiniteDuration.apply(2, MINUTES), FiniteDuration.apply(40, MINUTES)) {
      val time = DateTime.now().hourOfDay().get()
      val day = DateTime.now().dayOfWeek().get()

      if (day != visitedDay && visited) {
        visited = false
      }

      if (time == 10 && !visited) {
        bot.goodMorning
        visitedDay = day
        visited = true
      }
    }

  }
}
