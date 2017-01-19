/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.hmrcemailrenderer.services

import uk.gov.hmrc.hmrcemailrenderer.templates.TemplateLocator
import uk.gov.hmrc.play.test.UnitSpec

class NewMessageAlert_SA309Spec extends UnitSpec {

  val templateRenderer = new TemplateRenderer {
    override def locator: TemplateLocator = TemplateLocator

    override def commonParameters: Map[String, String] = Map.empty
  }

  "NewMessageAlert_SA309" should {
    "render as newMessageAlert_SA309" in {
      val renderResult = templateRenderer.render("newMessageAlert_SA309", Map.empty).right.get

      renderResult.subject shouldBe "You’ve got a new message from HMRC"
      renderResult.html should include("You have a new message from HMRC to let you know the deadline for completing a paper tax return has now passed so you will need to do your self assessment online by 31 January")
    }

    "render the first name only" in {
      val name = Map("recipientName_title" -> "MR", "recipientName_forename" -> "GEOFF", "recipientName_secondForename" -> "BOB", "recipientName_surname" -> "FISHER", "recipientName_honours" -> "LLB")
      val renderResult = templateRenderer.render("newMessageAlert_SA309", name).right.get

      renderResult.html should include("Dear GEOFF")
    }

    "have dear customer as salutation if the name is not provided" in {
      val renderResult = templateRenderer.render("newMessageAlert_SA309", Map.empty).right.get

      renderResult.html should include("Dear customer")
    }
  }
}
