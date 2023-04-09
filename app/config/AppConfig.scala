package config

import com.google.inject.Inject
import play.api.Configuration


class AppConfig @Inject() (configuration: Configuration){
  val approvedFruitList        = configuration.get[Seq[String]]("allowedFruitList")
  val priceList        = configuration.get[Seq[Double]]("priceList")
}
