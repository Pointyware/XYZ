/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.entities.profile

/**
 * Describes a rider disability that may restrict driver acceptance.
 *
 * References:
 * 1. https://aafa.org/asthma/living-with-asthma/asthma-allergies-and-the-american-with-disabilities-act/
 * 2. https://www.ada.gov/regs2010/service_animal_qa.html
 *
 * @see Accommodation
 */
sealed class Disability {
    data object Blind : Disability()
    data object Deaf : Disability()
    data object Mobility : Disability()
    data object ServiceAnimal : Disability()
    data object Allergy : Disability()
}