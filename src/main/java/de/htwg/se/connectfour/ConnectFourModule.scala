package de.htwg.se.connectfour

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import de.htwg.se.connectfour.mvc.controller.Controller
import net.codingwell.scalaguice.ScalaModule

class ConnectFourModule extends AbstractModule with ScalaModule {

  val DEFAULT_COLUMNS: Int = 7
  val DEFAULT_ROWS: Int = 6

  override def configure(): Unit = {

    bindConstant().annotatedWith(Names.named("columns")).to(DEFAULT_COLUMNS)
    bindConstant().annotatedWith(Names.named("rows")).to(DEFAULT_ROWS)
    bind[Controller].to[mvc.controller.GridController]
  }
}
