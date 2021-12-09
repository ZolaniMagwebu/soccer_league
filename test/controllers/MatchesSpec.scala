package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

class MatchesSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "Matches GET" should {

    "render the League Ranking page from a new instance of controller" in {
      val controller = new Matches(stubControllerComponents())
      val home = controller.leagueRanking.apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Football league")
    }

    "render the League Ranking page from the router" in {
      val request = FakeRequest(GET, "/")
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Football league")
    }
  }
}
