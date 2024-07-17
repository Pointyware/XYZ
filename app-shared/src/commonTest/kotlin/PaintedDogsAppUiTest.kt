/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.app

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 *
 */
class XyzAppUiTest {

    @BeforeTest
    fun setUp() {

    }

    @AfterTest
    fun tearDown() {

    }

    @Test
    fun create_profile() {
        /*
        TODO: Test profile creation
          1. Start Test Server in separate process; Open App
          2. Select create account => Open Create Account Screen
          3. Confirm => Open Home Screen; =NetworkError=> Show Error
         */
    }

    @Test
    fun create_fund_without_account() {
        /*
        TODO: Test fund creation; given a user exists and has no bank account
          1. Login as user
          2. Select Create Fund => Open Fund Creation Screen
          3. Fill Fund Details
          4. Select add bank account => Open Add Bank Account Screen
          5. Fill Bank Details
          6. Confirm => Return to Fund Details; =NetworkError=> Show Error
          7. Confirm => Open Home Screen; =NetworkError=> Show Error
         */
    }

    @Test
    fun create_fund_with_account() {
        /*
        TODO: Test Fund Creation; given a user exists and has a bank account
          1. Login as user
          2. Select Create Fund => Open Fund Creation Screen
          3. Fill Fund Details
          4. Select Bank Account
          5. Confirm => Open Home Screen; =NetworkError=> Show Error
         */
    }

    @Test
    fun add_destination_bank_account() {
        /*
        TODO: Test adding destination bank account; given a user exists
          1. Login as user
          2. Select Profile => Open Profile Screen
          3. Select add bank account => Open Add Bank Account Screen
          4. Fill Bank Details
          5. Confirm => Open Home Screen; =NetworkError=> Show Error
         */
    }

    @Test
    fun donate_to_fund_without_account() {
        /*
        TODO: Test donation to fund; given a fund exists for a user and another user exists without bank account
          1. Login as user without bank account
          2. Select the fund started by another user => Open Fund Details
          3. Select Donate => Open Donation Screen
          4. Fill Donation Details
          5. Select add bank account => Open Add Bank Account Screen
          6. Fill Bank Details
          7. Confirm => Return to Fund Details; =NetworkError=> Show Error
          8. Confirm => Open Home Screen; =NetworkError=> Show Error
         */
    }

    @Test
    fun donate_to_fund_with_account() {
        /*
        TODO: Test donation to fund; given a fund exists for a user and another user exists with source bank account
          1. Login as user with source bank account
          2. Select the fund started by another user => Open Fund Details
          3. Select Donate => Open Donation Screen
          4. Fill Donation Details
          5. Select source bank account
          6. Confirm => Open Home Screen; =NetworkError=> Show Error
         */
    }
}
