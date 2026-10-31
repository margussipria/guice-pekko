package eu.sipria.inject.pekko

import java.lang.reflect.Method

import com.google.inject.assistedinject.FactoryModuleBuilder
import com.google.inject.name.Names
import com.google.inject.{AbstractModule, Binder, Provider}
import org.apache.pekko.actor.{Actor, ActorRef, Props}

import scala.reflect.ClassTag

trait PekkoModuleSupport { this: AbstractModule =>

  private def binder: Binder = {
    val method: Method = classOf[AbstractModule].getDeclaredMethod("binder")
    if (!method.canAccess(this)) {
      method.setAccessible(true)
    }
    method.invoke(this).asInstanceOf[Binder]
  }

  def bindActor[T <: Actor: ClassTag](name: String, props: Props => Props = identity): Unit = {
    binder.bind(classOf[ActorRef])
      .annotatedWith(Names.named(name))
      .toProvider(providerOf[T](name, props))
      .asEagerSingleton()
  }

  def bindActorFactory[ActorClass <: Actor: ClassTag, FactoryClass: ClassTag](): Unit = {
    binder.install(
      new FactoryModuleBuilder()
        .implement(classOf[Actor], implicitly[ClassTag[ActorClass]].runtimeClass.asInstanceOf[Class[_ <: Actor]])
        .build(implicitly[ClassTag[FactoryClass]].runtimeClass)
    )
  }

  def providerOf[T <: Actor: ClassTag](name: String, props: Props => Props = identity): Provider[ActorRef] = {
    new ActorRefProvider(name, props)
  }
}
