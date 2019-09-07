package com.sq.dir

/**
 * Copyright (c) 2019 Pandora Media, Inc.
 */
val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }
