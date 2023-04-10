package service

import com.typesafe.config.ConfigFactory
import config.AppConfig
import org.scalatestplus.play.PlaySpec
import play.api.Configuration

class CalculateServiceSpec extends PlaySpec {

  val config1 = Configuration(
    ConfigFactory.parseString(s"""
                                 |
                                 |    allowedFruitList  = ["apple", "orange"]
                                 |    priceList = [60, 25]
                                 |
                                 |""".stripMargin)
  )

  val config2 = Configuration(
    ConfigFactory.parseString(s"""
                                 |
                                 |    allowedFruitList  = ["apple", "orange", "lemon"]
                                 |    priceList = [10, 20]
                                 |
                                 |""".stripMargin)
  )

"Calculate service " when {

  val appConfig = new AppConfig(config1)
  val calculateService = new CalculateService(config = appConfig)

   "calculate without discount " should {

     "return correct sum of 2.05 if list entered has mix of upper and lower case" in {

       val result = calculateService.calculateWithoutDiscount(List("Apple","Apple","Orange","Apple"))
       val expected = 2.05
       expected mustBe(result)
     }

     "return correct sum of 0.00 if list entered is empty" in {

       val result = calculateService.calculateWithoutDiscount(List())
       val expected = 0.00
       expected mustBe(result)
     }

     "return -1 if the config have mismatch number of items for approved list and price list" in {

       val appConfig = new AppConfig(config2)
       val calculateService = new CalculateService(config = appConfig)
       val result = calculateService.calculateWithoutDiscount(List())
       val expected = -1
       expected mustBe(result)
     }

     "return -2 if the list entered has item not in the approved list" in {

       val result = calculateService.calculateWithoutDiscount(List("Apple", "Lemon"))
       val expected = -2
       expected mustBe(result)
     }
   }

  "calculate with discount" should {

    "return £1.20 for three apples" in {
      val result = calculateService.calculateDiscount(List("Apple","Apple","Apple"))
      val expected = 1.20
      expected mustBe(result)
    }

    "return money for four oranges when six are passed " in {
      val result = calculateService.calculateDiscount(List("Orange","Orange","Orange","Orange","Orange","Orange"))
      val expected = 4 * 0.25
      expected mustBe(result)
    }

    "return money for six oranges when eight are passed " in {
      val result = calculateService.calculateDiscount(List("Orange","Orange","Orange","Orange","Orange","Orange","Orange","Orange"))
      val expected = 6 * 0.25
      expected mustBe(result)
    }
    "return money for two apples and four ranges if four apples and six oranges passed " in {
      val result = calculateService.calculateDiscount(List("Apple","Apple", "Apple","Apple","Orange","Orange","Orange","Orange","Orange","Orange"))
      val expected = (2 * 0.60) + (4 * 0.25)
      expected mustBe(result)
    }
  }


}

}
