package com.brianmowen.metric_demo

import cats.data.Kleisli
import cats.effect.Effect
import natchez.{EntryPoint, Span}
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object ApiRoute {

  def apply[F[_]: Effect](
      dummyService: DummyService[Kleisli[F, Span[F], *]],
      entryPoint: EntryPoint[F],
      dsl: Http4sDsl[F]): HttpRoutes[F] = {

    import dsl._
    import org.http4s.circe.CirceEntityEncoder._

    HttpRoutes.of[F] {

      case GET -> Root / "ping" => Ok("pong")

      case GET -> Root / "json" =>
        entryPoint.root("ping").use { span =>
          Ok(dummyService.fetchJson.run(span))
        }

      case GET -> Root / "random" =>
        entryPoint.root("random").use { span =>
          Ok(dummyService.random.map(_.toString).run(span))
        }

    }
  }

}
