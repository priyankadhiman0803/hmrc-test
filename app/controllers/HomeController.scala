package controllers

import config.AppConfig

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json.{JsValue, Json}
import service.CalculateService

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents,
                               calculateService: CalculateService,
                               config: AppConfig) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index(discount: String) = Action { implicit request: Request[AnyContent] =>
   // val json: List[String] = request.body.asJson.get.as[List[String]]
    val json: List[String] = request.body.asJson match {
      case Some(value) => value.as[List[String]]
      case None => List()
    }
    val sum: Double = if(discount.equals("N"))
      calculateService.calculate(json)
    else calculateService.calculate(json)

    sum match {
      case -1 => Ok(Json.toJson("Element missing either in Fruit list or price list"))
      case -2 => Ok(Json.toJson(s"Elements entered should match the approved list i.e ${config.approvedFruitList}"))
      case _ => Ok(Json.toJson(s"£$sum"))
    }

  }
}
