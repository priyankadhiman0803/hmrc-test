package service

import config.AppConfig
import play.api.mvc.ControllerComponents

import javax.inject.Inject


class CalculateService @Inject()(config: AppConfig) {

  val fruitList: Seq[String] = config.approvedFruitList
  val priceList: Seq[Double] = config.priceList
  val halfDiscountedFruit = "apple"
  val twoThirdDiscountedFruit = "orange"


  /**
   * Step 1 from the test where discount is not applied
   * @param list
   * @return
   */

  def calculateWithoutDiscount(list: List[String]): Double = {
    val passedListLowerCase = list.map(_.toLowerCase)
    val distinctPassedList: Seq[String] = passedListLowerCase.toSet.toSeq
    if (distinctPassedList.forall(fruitList.contains)) {
      if(fruitList.size == priceList.size) {
        val fruitPricePair = (fruitList zip priceList).toMap
        val sumTotal: Double = passedListLowerCase.foldLeft(0.0) { (res, ele) =>
          res + fruitPricePair.getOrElse(ele, 0.0)
        }
      sumTotal/100
      }
      else -1
    } else -2
  }

  /**
   * Step 2 from the test where discount is  applied
   * @param list
   * @return
   */

  def calculateDiscount(list: List[String]): Double = {
    val passedListLowerCase = list.map(_.toLowerCase)
    val distinctPassedList: Seq[String] = passedListLowerCase.toSet.toSeq
    if (distinctPassedList.forall(fruitList.contains)) {
      val passedAppleList = passedListLowerCase.filter(_ == halfDiscountedFruit)
      val passedOrangeList = passedListLowerCase.filter(_ == twoThirdDiscountedFruit)
      val billableAppleCount = calculateBillableAppleCount(passedAppleList)
      val billableOrangeCount = calculateBillableOrangeCount(passedOrangeList)
      val billableList: List[String] = passedAppleList.take(billableAppleCount) ++ passedOrangeList.take(billableOrangeCount)
      if(fruitList.size == priceList.size) {
        val fruitPricePair = (fruitList zip priceList).toMap
        val sumTotal: Double = billableList.foldLeft(0.00) { (res, ele) =>
          res + fruitPricePair.getOrElse(ele, 0.00)
        }
        sumTotal/100
      }
      else -1
    } else -2
  }

  private def calculateBillableOrangeCount(orangeList: List[String]) = {
    val remainder = orangeList.size % 3
    val discount = (orangeList.size/3) * 2
    if( remainder == 0) discount else discount + remainder

  }
  private def calculateBillableAppleCount(appleList: List[String]) = {

    val remainder = appleList.size % 2
    val discount = (appleList.size/2)
    if( remainder == 0) discount  else discount + remainder

  }



}
