package service

import config.AppConfig
import play.api.mvc.ControllerComponents

import javax.inject.Inject


class CalculateService @Inject()(config: AppConfig) {

  val fruitList: Seq[String] = config.approvedFruitList
  val priceList: Seq[Double] = config.priceList


  def calculate(list: List[String]): Double = {
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



}
