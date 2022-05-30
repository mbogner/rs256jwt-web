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

package dev.mbo.rs256.util

import dev.mbo.rs256.getResourceAsText
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.security.PrivateKey
import java.security.PublicKey

@SpringBootTest
internal class RSAKeyParserTest @Autowired constructor(
    private val bean: RSAKeyParser
) {

    @Test
    fun parsePKCS8Key() {
        val result: PrivateKey = bean.parsePKCS8Key(getResourceAsText("/ssl/privatekey_pkcs8.pem"))
        assertThat(result).isNotNull
    }

    @Test
    fun parseX509Key() {
        val result: PublicKey = bean.parseX509Key(getResourceAsText("/ssl/publickey.pem"))
        assertThat(result).isNotNull
    }

    @Test
    fun parse() {
        val result: KeyPair = bean.parse(
            getResourceAsText("/ssl/privatekey_pkcs8.pem"),
            getResourceAsText("/ssl/publickey.pem")
        )
        assertThat(result).isNotNull
        assertThat(result.privateKey).isNotNull
        assertThat(result.publicKey).isNotNull
    }

    @Test
    fun extractBase64() {
        assertThat(bean.extractBase64("\n-----BEGIN CERTIFICATE-----asd\n\r-----End CERTIFICATE-----\r"))
            .isEqualTo("asd")
    }
}