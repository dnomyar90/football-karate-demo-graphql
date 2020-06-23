package mock

import com.intuit.karate.gatling.PreDef._
import io.gatling.core.Predef._
import scala.concurrent.duration._

class LoadTests extends Simulation {

  before {
    println("Load tests started")
  }
  
  // Amount of ramp users
  val amountRampUsers: Int = 50

  // Duration of ramp
  val amountRampDuration: Int = 10

  val playersTestQuery = scenario("Query Players")
    .exec(
      karateFeature("classpath:features/FindPlayers.feature")
    )

    val playersTestMutation = scenario("Mutation Players")
    .exec(
      karateFeature("classpath:features/InsertPlayer.feature")
    )

  setUp(
    playersTestMutation.inject(rampUsers(10) during 10),
    playersTestQuery.inject(rampConcurrentUsers(1) to (5) during 10)
    ).assertions(
        global.responseTime.max.lt(500),
        global.successfulRequests.percent.gt(95)
    )

  after {
    println("Load tests ended")
  }

} 