# guice-pekko

Library for injecting Pekko Actors with Google Guice

This library's functionality is provided also with [com.typesafe.play:play-guice](https://search.maven.org/search?q=g:com.typesafe.play%20AND%20(a:play-guice_2.12%20OR%20a:play-guice_2.13)),
but that library just for using ActorRefProvider requires full Play Framework.

## Module declaration

Explicitly extend Guice's `AbstractModule` when mixing in the support traits.
This works across Scala 2.12, 2.13, and 3; Scala 3's `ScalaModule` does not
extend `AbstractModule` for you.

```scala
import com.google.inject.AbstractModule
import eu.sipria.inject.pekko.PekkoModuleSupport
import net.codingwell.scalaguice.ScalaModule

class MyModule extends AbstractModule with ScalaModule with PekkoModuleSupport {
  override def configure(): Unit = {
    // Bind your ActorSystem, actors, and their dependencies here.
  }
}
```
