package eu.sipria.inject.pekko.pong

import javax.inject.Inject

import eu.sipria.inject.pekko.{InjectedActorSupport, TestString}
import org.apache.pekko.actor.Actor
import org.apache.pekko.pattern.{ask, pipe}
import org.apache.pekko.util.Timeout

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

class Ping @Inject() (pongFactory: PongFactory)(implicit ec: ExecutionContext) extends Actor with InjectedActorSupport {

  implicit val timeout: Timeout = Timeout(5 seconds)

  val pong = injectedChild(pongFactory.create(TestString.public), "pong")

  def receive: Receive = {
    case PingMessage =>
      pipe(pong ? PingMessage) to sender()
  }
}

case object PingMessage
