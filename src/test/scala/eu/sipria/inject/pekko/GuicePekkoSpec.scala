package eu.sipria.inject.pekko

import java.util.UUID

import com.google.inject.name.Names
import com.google.inject.{AbstractModule, Guice, Key}
import eu.sipria.inject.pekko.pong._
import net.codingwell.scalaguice.ScalaModule
import org.apache.pekko.actor.{ActorRef, ActorRefFactory, ActorSystem}
import org.apache.pekko.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, flatspec, matchers}

import scala.concurrent.ExecutionContext

object TestString {
  val secret = UUID.randomUUID().toString
  val public = UUID.randomUUID().toString
}

class GuicePekkoSpec extends TestKit(ActorSystem("TestActorSystem")) with ImplicitSender with flatspec.AnyFlatSpecLike with matchers.must.Matchers with BeforeAndAfterAll {

  override def afterAll(): Unit = TestKit.shutdownActorSystem(system)

  "Guice" should "create all actors correctly" in {
    val injector = Guice.createInjector(new PekkoModule)
    val ping = injector.getInstance(Key.get(classOf[ActorRef], Names.named("ping")))

    ping ! PingMessage

    expectMsg(PongMessage(TestString.secret, TestString.public))
  }
}

class PekkoModule(implicit system: ActorSystem) extends AbstractModule with ScalaModule with PekkoModuleSupport {

  override def configure(): Unit = {
    bind[ActorSystem].toInstance(system)
    bind[ActorRefFactory].toInstance(system)
    bind[ExecutionContext].toInstance(system.dispatcher)

    bind[String].annotatedWithName("named.secret").toInstance(TestString.secret)

    bindActor[Ping]("ping")
    bindActorFactory[Pong, PongFactory]()
  }
}
