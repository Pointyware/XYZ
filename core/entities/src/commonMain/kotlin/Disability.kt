/*
 * Copyright (c) 2024 Pointyware
 */

package org.pointyware.xyz.core.entities

/**
 * Describes a rider disability that may restrict driver acceptance.
 */
sealed class Disability {
    data object Blind : Disability()
    data object Deaf : Disability()
    data object Wheelchair : Disability()
    data object ServiceAnimal : Disability()
    data object Allergy : Disability()
}
