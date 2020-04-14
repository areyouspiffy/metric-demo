package com.brianmowen.metric_demo

import pureconfig.ConfigSource

case class ServiceConfig(httpPort: Int, healthPort: Int)

object Configuration {

  import pureconfig.generic.auto._

  def create: ServiceConfig = ConfigSource.default.loadOrThrow[ServiceConfig]

}
