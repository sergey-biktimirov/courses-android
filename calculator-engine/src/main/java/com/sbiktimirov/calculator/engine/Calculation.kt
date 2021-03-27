package com.sbiktimirov.calculator.engine

import java.io.Serializable

data class Calculation(
    var input: String = "",
    var result: String = "",
    var value1: String? = "",
    var operator: Char? = null,
    var value2: String? = null,
    var isDecimal: Boolean = false,
    var isNegative: Boolean = false
) : Serializable