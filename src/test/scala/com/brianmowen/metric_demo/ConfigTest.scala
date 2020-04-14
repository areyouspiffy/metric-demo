package com.brianmowen.metric_demo

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ConfigTest extends AnyWordSpec with Matchers {

  "Load a config" in {
    Configuration.create
  }

}
