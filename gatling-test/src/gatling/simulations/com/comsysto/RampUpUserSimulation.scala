package com.comsysto

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class RampUpUserSimulation extends Simulation {

  val httpProtocol = http
    .baseURL("http://localhost:9090")
    .userAgentHeader("curl/7.54.0")

  val scn = scenario(this.getClass.getSimpleName)
    .exec(sse("stocksStream").open("/stocks").queryParam("currency", "ETH").queryParam("currency", "LTC").queryParam("currency", "BTC").check(wsListen.within(30).until(30)))
    .pause(10)
    .exec(sse("stocksStream").close())

  setUp(scn.inject(rampUsersPerSec(10) to(20) during(2 minutes) randomized).protocols(httpProtocol))
}