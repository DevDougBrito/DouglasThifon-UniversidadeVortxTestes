/*
 * Copyright 2011-2020 GatlingCorp (https://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package computerdatabase

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class Pokemon extends Simulation {

  val httpProtocol = http
    .baseUrl("https://pokeapi.co") // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .doNotTrackHeader("1")
    .acceptLanguageHeader("pt-BR,pt;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val scn = scenario("Chamando todos os Pokemons") // A scenario is a chain of requests and pauses
    .exec(
      http("Todos os Pokemons")
        .get("/api/v2/pokemon")
    )
    .pause(10 milliseconds) // Note that Gatling has recorded real time pauses
    .exec(
      http("Pokemon de ID 1")
        .get("/api/v2/pokemon/1")
    )
    .pause(10 milliseconds)
    .exec(
      http("Evoluções")
        .get("/api/v2/evolution-chain/1")
    )
    .pause(10 milliseconds)
    .exec(
      http("Localização")
        .get("/api/v2/location/1")
    )
    .pause(10 milliseconds)
    .exec(
      http("Como pegar")
        .get("/api/v2/encounter-method/1")
    )
    .pause(10 milliseconds)
    .exec(
      http("request_10") // Here's an example of a POST request
        .post("/computers")
        .formParam("name", "Beautiful Computer") // Note the triple double quotes: used in Scala for protecting a whole chain of characters (no need for backslash)
        .formParam("introduced", "2012-05-30")
        .formParam("discontinued", "")
        .formParam("company", "37")
    )

  setUp(scn.inject(atOnceUsers(5)).protocols(httpProtocol))
}
