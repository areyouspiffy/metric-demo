package com.brianmowen.metric_demo

import cats.effect.Sync
import io.circe.Json
import org.http4s.client.Client

trait HttpBinClient[F[_]] {
  def json: F[Json]
}

object HttpBinClient {

  def impl[F[_]: Sync](cl: Client[F]): HttpBinClient[F] = new HttpBinClient[F] {
    import org.http4s.circe.CirceEntityDecoder._
    import org.http4s.syntax.literals._

    override def json: F[Json] = {
      val reqUri = uri"""https://httpbin.org/json"""
      cl.expect[Json](reqUri)
    }
  }

}
