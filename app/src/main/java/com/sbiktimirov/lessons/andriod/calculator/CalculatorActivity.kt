package com.sbiktimirov.lessons.andriod.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.sbiktimirov.calculator.engine.Calculation
import com.sbiktimirov.calculator.engine.CalculatorEngine

private const val TAG = "CalculatorActivity"

/** Ключ бандла для хранения значения результата калькулятора.
 * */
private const val KEY_CALCULATION_RESULT = "calculation_result"

class CalculatorActivity : AppCompatActivity() {
    private lateinit var calculationResult: TextView
    private lateinit var calculator: CalculatorEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        calculationResult = findViewById(R.id.calculation_result)
        calculator = CalculatorEngine()

        findViewById<Button>(R.id.button_0).setOnClickListener { digit("0") }
        findViewById<Button>(R.id.button_1).setOnClickListener { digit("1") }
        findViewById<Button>(R.id.button_2).setOnClickListener { digit("2") }
        findViewById<Button>(R.id.button_3).setOnClickListener { digit("3") }
        findViewById<Button>(R.id.button_4).setOnClickListener { digit("4") }
        findViewById<Button>(R.id.button_5).setOnClickListener { digit("5") }
        findViewById<Button>(R.id.button_6).setOnClickListener { digit("6") }
        findViewById<Button>(R.id.button_7).setOnClickListener { digit("7") }
        findViewById<Button>(R.id.button_8).setOnClickListener { digit("8") }
        findViewById<Button>(R.id.button_9).setOnClickListener { digit("9") }
        findViewById<Button>(R.id.button_comma).setOnClickListener { comma() }
        findViewById<Button>(R.id.button_equals).setOnClickListener { equals() }
        findViewById<Button>(R.id.button_plus).setOnClickListener { plus() }
        findViewById<Button>(R.id.button_minus).setOnClickListener { minus() }
        findViewById<Button>(R.id.button_multiply).setOnClickListener { multiply() }
        findViewById<Button>(R.id.button_division).setOnClickListener { divide() }
        findViewById<Button>(R.id.button_clear).setOnClickListener { clear() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_CALCULATION_RESULT, calculator.calculation)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val calculation: Calculation =
            savedInstanceState.getSerializable(KEY_CALCULATION_RESULT) as Calculation

        if (calculation != null) {
            calculator = CalculatorEngine(calculation)
            calculationResult.text = calculation.input
        }
    }

    private fun digit(string: String) {
        calculationResult.text = calculator.addDigit(string.toInt()).calculation.input
    }

    private fun comma() {
        calculationResult.text = calculator.comma().calculation.input
    }

    private fun multiply() {
        calculationResult.text = calculator.multiply().calculation.input
    }

    private fun minus() {
        calculationResult.text = calculator.minus().calculation.input
    }

    private fun plus() {
        calculationResult.text = calculator.plus().calculation.input
    }

    private fun divide() {
        calculationResult.text = calculator.divide().calculation.input
    }

    private fun clear() {
        calculationResult.text = calculator.clear().calculation.input
    }

    private fun equals() {
        calculationResult.text = calculator.compute().calculation.input
    }
}