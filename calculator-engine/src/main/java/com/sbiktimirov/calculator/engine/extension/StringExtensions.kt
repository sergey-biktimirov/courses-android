package com.sbiktimirov.calculator.engine.extension


fun String?.isNumber(): Boolean {
    return if (this != null) {
        this.toBigDecimalOrNull() != null
    } else {
        false
    }
}