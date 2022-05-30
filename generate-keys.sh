#!/bin/bash
#
# Copyright 2022 mbo.dev
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

OUTDIR=src/main/resources/ssl
rm -rf "$OUTDIR"
mkdir -p "$OUTDIR"

PRIVKEY="$OUTDIR"/privatekey.pem
PUBKEY="$OUTDIR"/publickey.pem
CERT="$OUTDIR"/certificate.cer
PKCS8="$OUTDIR"/privatekey_pkcs8.pem

# minimum key size for RS256 is 2048 bits
openssl genrsa -out "$PRIVKEY" 2048
openssl rsa -pubout -in "$PRIVKEY" -out "$PUBKEY"
openssl req -new -x509 -key "$PRIVKEY" -out "$CERT" -days 1825 \
  -subj "/C=AT/ST=Vienna/L=Vienna/O=mbo.dev/OU=IT Department/CN=mbo.dev"

# convert private key to pkcs8 format in order to import it from Java
openssl pkcs8 -topk8 -in "$PRIVKEY" -inform pem -out "$PKCS8" -outform pem -nocrypt