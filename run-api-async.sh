#!/bin/sh

#
# Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the Affero GPL-3.0 license.
#

(./gradlew :api:buildFatJar && \
  ./gradlew :api:runFatJar 2>&1 > api-server.log & ) && \
  ./gradlew :feature:ride:jvmTest --tests '*ClientTest'
