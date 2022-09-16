package br.senai.sp.jandira.imc20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.senai.sp.jandira.imc20.databinding.ActivityCalculatorBinding
import br.senai.sp.jandira.imc20.utils.getBmi
import br.senai.sp.jandira.imc20.utils.getStatusBmi

class CalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        loadUserProfile()

        binding.buttonCalculate.setOnClickListener {
            calculateBmi()
        }
    }

    private fun calculateBmi() {
        val openResult = Intent(this, ResultActivity::class.java)
        val data = getSharedPreferences("dados", MODE_PRIVATE)
        var editor = data.edit()
        var height = 0.0f

        if (validateInputDatas()) {
            if (binding.editTextHeightCalculator.text.isEmpty())
            {
                height = data.getFloat("height", 0.0f)
            } else
            {
                height = binding.editTextHeightCalculator.text.toString().toFloat()
            }
            var weight = binding.editTextWeightCalculator.text.toString().toInt()

            var bmi = getBmi(weight, height).toDouble()
            var statusBmi = getStatusBmi(bmi, this)

            editor.putFloat("height", height)
            editor.putInt("weight", weight)
            editor.commit()

            openResult.putExtra("bmi", bmi)
            openResult.putExtra("statusBmi", statusBmi)

            startActivity(openResult)
        } else {
            Toast.makeText(this, "Invalid datas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInputDatas() : Boolean{
        if (binding.editTextWeightCalculator.text.isEmpty()) {
            binding.editTextWeightCalculator.error = "Required field!"
            return false
        } else {
            return true
        }
    }

    private fun loadUserProfile() {
        val data = getSharedPreferences("dados", MODE_PRIVATE)

        binding.userNameString.text = data.getString("name", "")
        binding.userMailString.text = data.getString("email", "")
        binding.userWeightString.text = "Weight: ${data.getInt("weight", 0)}Kg"
        binding.userHeightString.text =  "Height: ${data.getFloat("height", 0.0F)}m"
    }
}