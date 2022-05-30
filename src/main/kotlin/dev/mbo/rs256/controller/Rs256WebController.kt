/*
 * Copyright 2022 mbo.dev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.mbo.rs256.controller

import dev.mbo.rs256.model.JwtRs256Request
import dev.mbo.rs256.service.JwtService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import javax.validation.Valid

@Controller
class Rs256WebController(
    private val jwtService: JwtService,
) {

    private val log: Logger = LoggerFactory.getLogger(javaClass)
    private val pageName = "rs256"

    @GetMapping(path = ["/rs256"])
    fun get(model: Model): String {
        updateModel(model, jwtService.defaultRequest())
        message(model)
        return pageName
    }

    @PostMapping(path = ["/rs256"])
    fun post(@Valid @ModelAttribute body: JwtRs256Request, model: Model): String {
        try {
            updateModel(model, body, jwtService.createJwt(body))
            message(model, "success")
        } catch (exc: Exception) {
            log.error("sending failed", exc)
            message(model, "failure")
        }
        return pageName
    }

    private fun updateModel(model: Model, body: JwtRs256Request, result: String? = null) {
        model.addAttribute("body", body)
        model.addAttribute("result", result)
    }

    private fun message(model: Model, message: String? = null) {
        model.addAttribute("message", message)
    }

}