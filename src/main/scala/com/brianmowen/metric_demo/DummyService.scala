package com.brianmowen.metric_demo

import java.util.UUID

import cats.effect.Sync
import io.circe.Json

trait DummyService[F[_]] {
  def fetchJson: F[Json]
  def random: F[UUID]
}

object DummyService {

  def impl[F[_]: Sync](httpBinClient: HttpBinClient[F]): DummyService[F] = new DummyService[F] {
    override def fetchJson: F[Json] = httpBinClient.json

    override def random: F[UUID] = Sync[F].delay(UUID.randomUUID())
  }

}
