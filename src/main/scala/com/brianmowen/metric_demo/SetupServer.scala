package com.brianmowen.metric_demo

import java.util.concurrent.Executors

import cats.effect._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.metrics.prometheus.PrometheusExportService
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder

import scala.concurrent.ExecutionContext

object SetupServer {

  import org.http4s.server.middleware._
  import org.http4s.syntax.kleisli._

  val blockingEc = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(sys.runtime.availableProcessors() * 2))

  def http[F[_]: ConcurrentEffect: Timer: ContextShift](
      sc: ServiceConfig,
      api: HttpRoutes[F],
      dsl: Http4sDsl[F]): Resource[F, org.http4s.server.Server[F]] = {

    val router: HttpRoutes[F] = CORS(Router("/api" -> api))

    BlazeServerBuilder[F]
      .withHttpApp(router.orNotFound)
      .bindHttp(sc.httpPort, "0.0.0.0")
      .resource
  }

  def health[F[_]: ConcurrentEffect: Timer: ContextShift](
      sc: ServiceConfig,
      prometheusExportService: PrometheusExportService[F]) =
    BlazeServerBuilder[F]
      .withHttpApp(Router("/" -> prometheusExportService.routes).orNotFound)
      .bindHttp(sc.healthPort, "0.0.0.0")
      .resource

}
