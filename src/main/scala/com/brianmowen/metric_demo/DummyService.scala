package com.brianmowen.metric_demo

import java.util.UUID

import cats.effect.Sync
import io.circe.Json
import natchez.Trace

trait DummyService[F[_]] {
  def fetchJson: F[Json]
  def random: F[UUID]
}

object DummyService {

  def impl[F[_]: Sync: Trace](httpBinClient: HttpBinClient[F]): DummyService[F] = new DummyService[F] {
    override def fetchJson: F[Json] = Trace[F].span("httpbin") {
      httpBinClient.json
    }

    override def random: F[UUID] = Trace[F].span("random") {
      Sync[F].delay(UUID.randomUUID())
    }
  }

}
