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

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class JwtServiceTest @Autowired constructor(
    private val bean: JwtService
) {

    @Test
    fun createJwt() {
        val result = bean.createJwt(bean.defaultRequest())
        assertThat(result)
            .isEqualTo(
                "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL21iby5kZXYiLCJzdWIiOiI8Y2xpZW5" +
                        "0X2lkPiIsImF1ZCI6Imh0dHBzOi8vcmV2b2x1dC5jb20iLCJleHAiOjIyODUwODkxMzB9.PgwQbBDblW8UNY_v3dP3N" +
                        "3zqMPsnE0HpU8k6zIviIDft8o5JmhifJP0KsusL6tgecB-aeAem9zYRNbDrBhbqtETCg-ZFqqDchCgveHMbJBczy_oE" +
                        "DlWs0c7OgWQSWAI4tU_SoOGZu-fCykZ9M9r_Lqw26-T50YKO_ckGHNr7kuibJnNmM-iIrntEY40F0KnE_TTFn4nEfX2" +
                        "Dtw-Pw6m6NCQ5n5yzZErgFt8tZumInWyUAvkpM0IjgxmdmPTdDEx6wxklS0-CPluoNWnjZ4vQh7R6rem7iJvltNnb9H" +
                        "ezZ7T_gZWwfN6TNYSgAR-zFOLTcQUDTZ-eYNJBdIVGOsMTuQ"
            )
    }
}