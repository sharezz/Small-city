package com.sharezzorama.smallcity

class Test {
    val string: String? = null
    private fun nullableTest(): String? {
        string?.let {
            return it
        }
    }
}