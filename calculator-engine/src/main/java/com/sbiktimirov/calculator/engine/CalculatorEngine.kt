package com.sbiktimirov.calculator.engine

import com.sbiktimirov.calculator.engine.extension.isNumber
import java.lang.Exception
import java.math.BigDecimal
import java.math.MathContext
import kotlin.math.abs

class CalculatorEngine(var calculation: Calculation = Calculation()) {
    private val COMMA_CHAR = '.'
    private val COMMA_DISPLAYED_CHAR = ','
    private val MULTIPLY_CHAR = 'x'
    private val DIVIDE_CHAR = '/'
    private val MINUS_CHAR = '-'
    private val PLUS_CHAR = '+'

    fun compute(): CalculatorEngine {
        calculation.isDecimal = false
        if (calculation.value2.isNumber()) {
            calculation.isDecimal = false
            val res = when (calculation.operator) {
                MINUS_CHAR -> calculation.value1!!.toBigDecimal()
                    .minus(calculation.value2!!.toBigDecimal())
                PLUS_CHAR -> calculation.value1!!.toBigDecimal()
                    .plus(calculation.value2!!.toBigDecimal())
                MULTIPLY_CHAR -> calculation.value1!!.toBigDecimal()
                    .multiply(calculation.value2!!.toBigDecimal())
                DIVIDE_CHAR -> calculation.value1!!.toBigDecimal()
                    .divide(calculation.value2!!.toBigDecimal(), MathContext.DECIMAL128)
                else -> throw ArithmeticException()
            }.stripTrailingZeros()
            calculation.result = res.toString().replace(COMMA_CHAR, COMMA_DISPLAYED_CHAR)
            calculation.input = res.toString().replace(COMMA_CHAR, COMMA_DISPLAYED_CHAR)
            calculation.value1 = res.toString()
            calculation.value2 = null
            calculation.operator = null
        }
        return this
    }

    fun addMathOperatorToInput(operator: Char) {
        if (lastCharInInputIsOperator()) {
            val _input = calculation.input.toCharArray()
            _input[calculation.input.length - 1] = operator
            calculation.input = String(_input)
        } else {
            calculation.input += operator
        }
    }

    fun setMathOperator(operator: Char) {
        this.calculation.operator = operator
        addMathOperatorToInput(operator)
    }

    fun lastCharInInputIsOperator(): Boolean {
        val lastCharInInput = calculation.input.lastOrNull()
        return if (lastCharInInput != null) {
            when (lastCharInInput) {
                MULTIPLY_CHAR -> true
                DIVIDE_CHAR -> true
                MINUS_CHAR -> true
                PLUS_CHAR -> true
                else -> false
            }
        } else {
            false
        }
    }

    fun minus(): CalculatorEngine {
        compute()
        if (!calculation.isNegative && !calculation.value1.isNumber() && calculation.value2 == null) {
            calculation.isNegative = true
            calculation.value1 += MINUS_CHAR
            addMathOperatorToInput(MINUS_CHAR)
        } else if (calculation.value1.isNumber()) {
            calculation.value2 = ""
            setMathOperator(MINUS_CHAR)
        }
        return this
    }

    fun plus(): CalculatorEngine {
        compute()
        if (calculation.isNegative && !calculation.value1.isNumber() && calculation.value2 == null) {
            calculation.isNegative = false
            setMathOperator(PLUS_CHAR)
        } else if (calculation.value1.isNumber()) {
            calculation.value2 = ""
            setMathOperator(PLUS_CHAR)
        }
        return this
    }

    fun multiply(): CalculatorEngine {
        compute()
        if (calculation.value1.isNumber()) {
            calculation.value2 = ""
            setMathOperator(MULTIPLY_CHAR)
        }
        return this
    }

    fun divide(): CalculatorEngine {
        compute()
        if (calculation.value1.isNumber()) {
            calculation.value2 = ""
            setMathOperator(DIVIDE_CHAR)
        }
        return this
    }

    fun comma(): CalculatorEngine {
        if (!calculation.isDecimal) {
            calculation.isDecimal = true
            if (calculation.value1.isNumber() && calculation.operator == null) {
                calculation.value1 += COMMA_CHAR
                calculation.input += COMMA_DISPLAYED_CHAR
            } else if (calculation.value2.isNumber() && calculation.operator != null) {
                calculation.value2 += COMMA_CHAR
                calculation.input += COMMA_DISPLAYED_CHAR
            }
        }
        return this
    }

    //TODO Исправить проблему лидирующих нолей
    fun addDigit(int: Int): CalculatorEngine {
        if (abs(int) > 9) throw Exception("Expected numbers from 0 to 9")
        val intString = abs(int).toString()
        if (calculation.operator == null) {
            calculation.value1 += intString
            calculation.input += intString
        } else {
            calculation.value2 += intString
            calculation.input += intString
        }
        return this
    }

    fun clear(): CalculatorEngine {
        calculation = Calculation()
        return this
    }
}