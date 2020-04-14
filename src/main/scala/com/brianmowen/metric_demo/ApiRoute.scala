package com.brianmowen.metric_demo

import cats.effect.Effect
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object ApiRoute {

  def apply[F[_]: Effect](dummyService: DummyService[F], dsl: Http4sDsl[F]): HttpRoutes[F] = {

    import cats.implicits._
    import dsl._
    import org.http4s.circe.CirceEntityEncoder._

    HttpRoutes.of[F] {

      case GET -> Root / "ping" => Ok("pong")

      case GET -> Root / "json" => Ok(dummyService.fetchJson)

      case GET -> Root / "random" => Ok(dummyService.random.map(_.toString))

    }
  }

}
