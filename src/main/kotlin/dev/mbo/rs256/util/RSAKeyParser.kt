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

import org.springframework.stereotype.Component
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64


@Component
class RSAKeyParser : KeyParser {

    private val kf: KeyFactory = KeyFactory.getInstance("RSA")

    override fun parse(privateKeyStr: String, publicKeyStr: String): KeyPair {
        return KeyPair(
            parsePKCS8Key(privateKeyStr),
            parseX509Key(publicKeyStr)
        )
    }

    fun parsePKCS8Key(str: String): PrivateKey {
        val p = extractBase64(str)
        val keySpecPKCS8 = PKCS8EncodedKeySpec(Base64.getDecoder().decode(p))
        return kf.generatePrivate(keySpecPKCS8)
    }

    fun parseX509Key(str: String): RSAPublicKey {
        val v = extractBase64(str)
        val keySpecX509 = X509EncodedKeySpec(Base64.getDecoder().decode(v))
        return kf.generatePublic(keySpecX509) as RSAPublicKey
    }

    fun extractBase64(key: String): String {
        return key
            .replace("\n", "")
            .replace("\r", "")
            .replace("-----BEGIN RSA PRIVATE KEY-----", "", ignoreCase = true)
            .replace("-----END RSA PRIVATE KEY-----", "", ignoreCase = true)
            .replace("-----BEGIN CERTIFICATE-----", "", ignoreCase = true)
            .replace("-----END CERTIFICATE-----", "", ignoreCase = true)
            .replace("-----BEGIN PRIVATE KEY-----", "", ignoreCase = true)
            .replace("-----END PRIVATE KEY-----", "", ignoreCase = true)
            .replace("-----BEGIN PUBLIC KEY-----", "", ignoreCase = true)
            .replace("-----END PUBLIC KEY-----", "", ignoreCase = true)
    }

}