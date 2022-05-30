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

package dev.mbo.rs256.service

import com.fasterxml.jackson.databind.ObjectMapper
import dev.mbo.rs256.getResourceAsText
import dev.mbo.rs256.model.JwtRs256Request
import dev.mbo.rs256.model.Rs256Header
import dev.mbo.rs256.model.Rs256Payload
import dev.mbo.rs256.util.KeyParser
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.Date

@Service
class JwtService(
    private val keyParser: KeyParser,
    private val objectMapper: ObjectMapper,
) {

    private val defaultHeader = getResourceAsText("/req/header.json")
    private val defaultPayload = getResourceAsText("/req/payload.json")
    private val defaultPrivateKey = getResourceAsText("/ssl/privatekey_pkcs8.pem")
    private val defaultPublicKey = getResourceAsText("/ssl/publickey.pem")

    fun defaultRequest(): JwtRs256Request {
        return JwtRs256Request(defaultHeader, defaultPayload, defaultPrivateKey, defaultPublicKey)
    }

    fun createJwt(request: JwtRs256Request): String {
        val keyPair = keyParser.parse(request.privkey, request.pubkey)
        val header = objectMapper.readValue(request.header, Rs256Header::class.java)
        val payload = objectMapper.readValue(request.payload, Rs256Payload::class.java)
        return Jwts.builder()
            .setHeaderParam("alg", header.alg)
            .setHeaderParam("typ", header.typ)

            .setIssuer(payload.iss)
            .setSubject(payload.sub)
            .setAudience(payload.aud)
            .setExpiration(Date.from(Instant.ofEpochSecond(payload.exp)))

            .signWith(keyPair.privateKey)
            .compact()
    }

}