package com.sq.dir

import org.mockito.ArgumentCaptor
import org.mockito.Mockito

/**
 * Copyright (c) 2019 Pandora Media, Inc.
 */

/**
 * Helper workaround functions to avoid Kotlin Runtime Exceptions when using Mockito.
 */
/**
 * Returns Mockito.eq() as nullable type to avoid java.lang.IllegalStateException when
 * null is returned.
 *
 * Generic T is nullable because implicitly bounded by Any?.
 */
fun <T> eq(obj: T): T = Mockito.eq<T>(obj)

/**
 * Returns Mockito.any() as nullable type to avoid java.lang.IllegalStateException when
 * null is returned.
 */
fun <T> any(): T = Mockito.any<T>()

/**
 * Returns Mockito.anyVararg() as nullable type to avoid java.lang.IllegalStateException when
 * null is returned.
 */
fun <T> anyVararg(): T = Mockito.any<T>()

/**
 * Returns ArgumentCaptor.capture() as nullable type to avoid java.lang.IllegalStateException
 * when null is returned.
 */
fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()

/**
 * Helper function for creating an argumentCaptor in kotlin.
 */
inline fun <reified T : Any> argumentCaptor(): ArgumentCaptor<T> =
    ArgumentCaptor.forClass(T::class.java)

/**
 * Helper function for creating an argThat in Kotlin
 */
fun <T> argThat(matcher: (T) -> Boolean): T = Mockito.argThat(matcher)