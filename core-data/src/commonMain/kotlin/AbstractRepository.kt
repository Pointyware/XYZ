/*
 * Copyright (c) 2024-2025 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.xyz.core.data

/**
 * In domain-driven design, a repository is a mechanism for encapsulating storage, retrieval, and search behavior which emulates a collection of objects.
 *
 * The aggregate root is the object that is the primary focus of the repository. The repository is responsible for managing the aggregate root and its children.
 *
 */
abstract class AbstractRepository<E: Any?> {

    abstract fun put(entity: E)
    abstract fun find(predicate: (E)->Boolean): E?
    abstract fun remove(entity: E)
}
