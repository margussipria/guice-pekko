package eu.sipria.inject.pekko.pong

import javax.inject.{Inject, Named}

import com.google.inject.assistedinject.Assisted
import org.apache.pekko.actor.Actor

class Pong @Inject() (@Named("named.secret") secret: String, @Assisted public: String) extends Actor {

  def receive: Receive = {
    case PingMessage =>
      sender() ! PongMessage(secret, public)
  }
}

case class PongMessage(secret: String, public: String)

trait PongFactory {
  def create(public: String): Actor
}
