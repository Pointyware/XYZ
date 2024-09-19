/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.feature.login.remote.fake

import kotlinx.io.files.FileSystem
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.files.SystemTemporaryDirectory
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 * TODO: describe purpose/intent of FakeProfileServiceTest
 */
class FakeAuthServiceTest {

    lateinit var fakeProfileService: FakeProfileService

    @BeforeTest
    fun setUp() {
        val tempDirectory = SystemTemporaryDirectory
    }

    @AfterTest
    fun tearDown() {

    }

    @Test
    fun `fake should load users from file when initializing`() {

    }

    @Test
    fun `fake should save users when lifecycle controller emits onStop`() {
        /*
        Given
         */
    }
}
