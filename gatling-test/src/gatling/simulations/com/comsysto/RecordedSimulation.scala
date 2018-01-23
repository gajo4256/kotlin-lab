package com.comsysto

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class RecordedSimulation extends Simulation {

  val httpProtocol = http
    .baseURL("http://localhost:9090")
    .userAgentHeader("curl/7.54.0")

  val scn = scenario(this.getClass.getSimpleName)
    .exec(sse("stocksStream").open("/stocks").queryParam("currency", "ETH").queryParam("currency", "BTC").queryParam("currency", "LTC").check(wsListen.within(10).until(10)))
    .pause(10)
    .exec(sse("stocksStream").close())

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
