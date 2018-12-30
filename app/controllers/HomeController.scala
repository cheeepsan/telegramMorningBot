package controllers

import bot.{Db, Slavyane}
import javax.inject._
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  val token: String = "710158750:AAFRqPONEzXRVezWy4LEQBEb5rwrdtee9p4"

  val db = new Db
  val bot = new Slavyane(token, db)
  val eol = bot.run()

  def close() = Action { implicit request: Request[AnyContent] =>

    bot.shutdown() // initiate shutdown
    // Wait for the bot end-of-life
    Await.result(eol, Duration.Inf)
    db.getDbObject.close()

    Ok("closed")
  }

  def morning() = Action { implicit request: Request[AnyContent] =>
    bot.goodMorning()
    Ok("morning")
  }


  def newYear() = Action { implicit request: Request[AnyContent] =>
    bot.happyNewYear1()
    Ok("happy new year")
  }

  def newYear2() = Action { implicit request: Request[AnyContent] =>
    bot.happyNewYear2()
    Ok("happy new year")
  }

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok("running")
  }
}
