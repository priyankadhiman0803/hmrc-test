package controllers


import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._


/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "HomeController GET" should {

    "calculate the list price without discount if N is passed in URL" in {
      val controller = inject[HomeController]
      val home = controller.index("N")
        .apply(FakeRequest(GET, "/calculate/discount/N")
          .withJsonBody(Json.toJson(List("Apple","orange","Apple"))))

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
      contentAsString(home) must include  ("£1.45")
    }

    "calculate the list price with discount if Y is passed in URL" in {
      val controller = inject[HomeController]
      val home = controller.index("Y")
        .apply(FakeRequest(GET, "/calculate/discount/Y")
          .withJsonBody(Json.toJson(List("Apple","orange","Apple","Orange","Orange"))))

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
      contentAsString(home) must include  ("£1.10")
    }
    "Show error message if the entered list has item not in approved list" in {
      val controller = inject[HomeController]
      val home = controller.index("N")
        .apply(FakeRequest(GET, "/calculate/discount/N")
          .withJsonBody(Json.toJson(List("Apple","orange","Apple","lemon"))))

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
      contentAsString(home) must include ("Elements entered should match the approved list")
    }

  }
}
