package com.brianmowen.metric_demo

import cats.data.Kleisli
import cats.effect.{ExitCode, IO, IOApp, Resource}
import cats.implicits._
import natchez._
import org.http4s.client.Client
import org.http4s.client.asynchttpclient.AsyncHttpClient

object Server extends IOApp {

  import org.http4s.dsl.{io => iodsl}

  override def run(args: List[String]): IO[ExitCode] = app.use { _ =>
    IO.never
  }

  def app: Resource[IO, ExitCode] =
    for {
      sc <- Resource.liftF(IO(Configuration.create))
      cl <- AsyncHttpClient.resource[IO]()
      ht = HttpBinClient.impl[Kleisli[IO, Span[IO], *]](Client.liftKleisli(cl))
      et <- entryPoint
      ap = ApiRoute[IO](DummyService.impl[Kleisli[IO, Span[IO], *]](ht), et, iodsl)
      _ <- SetupServer.http[IO](sc, ap, iodsl)
    } yield ExitCode.Success

  def entryPoint: Resource[IO, EntryPoint[IO]] = {
    import io.chrisdavenport.log4cats.Logger
    import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
    import natchez.log.Log
    implicit val log: Logger[IO] = Slf4jLogger.getLogger[IO]
    Log.entryPoint[IO]("foo").pure[Resource[IO, *]]
  }

}
