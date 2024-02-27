package com.example.calcy

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.calcy.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CalculatorViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.result.isVisible=false
        viewModel.expression.observe(this) { expression ->
            binding.result.isVisible=false
            binding.data.text = expression
        }

        viewModel.result.observe(this) { result ->
            binding.result.text ="= $result"

        }
        binding.deletebtn.setOnClickListener {
            viewModel.onDeleteClick()
        }
        binding.AllClear.setOnClickListener{viewModel.clear()}
        binding.equal.setOnClickListener{    viewModel.onEqualClick()
            binding.result.isVisible=true}
    }

    fun ondigitclick(view: View) {
        val digit = (view as Button).text.toString()
        viewModel.onDigitClick(digit)


    }

    fun onOperatorClick(view: View) {
        val operator = (view as Button).text.toString()
        viewModel.onOperatorClick(operator)
    }
}
