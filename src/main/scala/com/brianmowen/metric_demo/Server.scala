package com.brianmowen.metric_demo

import cats.effect.{ExitCode, IO, IOApp, Resource}
import org.http4s.client.asynchttpclient.AsyncHttpClient
import org.http4s.client.middleware.{Metrics => ClientMetrics}
import org.http4s.server.middleware.{Metrics => ServerMetrics}
import org.http4s.metrics.prometheus.{Prometheus, PrometheusExportService}

object Server extends IOApp {

  import org.http4s.dsl.{io => iodsl}

  override def run(args: List[String]): IO[ExitCode] = app.use { _ =>
    IO.never
  }

  def app: Resource[IO, ExitCode] =
    for {
      pm <- PrometheusExportService.build[IO]
      sc <- Resource.liftF(IO(Configuration.create))
      co <- Prometheus.metricsOps[IO](pm.collectorRegistry, "org_http4s_client")
      cl <- AsyncHttpClient.resource[IO]().map(c => ClientMetrics(co)(c))
      ht = HttpBinClient.impl(cl)
      so <- Prometheus.metricsOps[IO](pm.collectorRegistry)
      ap = ServerMetrics(so)(ApiRoute[IO](DummyService.impl(ht), iodsl))
      _ <- SetupServer.health[IO](sc, pm)
      _ <- SetupServer.http[IO](sc, ap, iodsl)
    } yield ExitCode.Success

}
