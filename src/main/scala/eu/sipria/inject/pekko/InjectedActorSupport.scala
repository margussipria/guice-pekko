package eu.sipria.inject.pekko

import org.apache.pekko.actor.{Actor, ActorContext, ActorRef, Props}

trait InjectedActorSupport {

  def injectedChild(create: => Actor, name: String, props: Props => Props = identity)(implicit context: ActorContext): ActorRef = {
    context.actorOf(props(Props(create)), name)
  }
}
