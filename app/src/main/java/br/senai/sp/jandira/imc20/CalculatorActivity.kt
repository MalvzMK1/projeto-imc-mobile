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

        var data = getSharedPreferences("dados", MODE_PRIVATE)

        binding.buttonCalculate.setOnClickListener {
            calculateBmi()
        }
    }

    private fun calculateBmi() {
        val openResult = Intent(this, ResultActivity::class.java)
        val data = getSharedPreferences("dados", MODE_PRIVATE)
        var editor = data.edit()

        if (validateInputDatas()) {
            Toast.makeText(this, "Eba", Toast.LENGTH_SHORT).show()
            if (binding.editTextHeightCalculator.text.isEmpty()) {
                var height = data.getFloat("height", 0.0f)
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
                var height = binding.editTextHeightCalculator.text.toString().toFloat()
                var weight = binding.editTextWeightCalculator.text.toString().toInt()

                var bmi = getBmi(weight, height).toDouble()
                var statusBmi = getStatusBmi(bmi, this)

                editor.putFloat("height", height)
                editor.putInt("weight", weight)
                editor.commit()

                openResult.putExtra("bmi", bmi)
                openResult.putExtra("statusBmi", statusBmi)

                startActivity(openResult)
            }
        } else {
            Toast.makeText(this, "Invalid datas", Toast.LENGTH_SHORT).show()
        }
    }

//        var weightForm = binding.editTextWeightCalculator.text.toString()
//        var heightForm = binding.editTextHeightCalculator.text.toString()
//
//
//        if (weightForm.isNotEmpty() && heightForm.isNotEmpty()) {
//            var weight = binding.editTextWeightCalculator.text.toString().toInt()
//            var height = binding.editTextHeightCalculator.text.toString().toFloat()
//
//            if (height == 0.0f) {
//                height = data.getFloat("height", 0.0f)
//            } else {
//                editor.putFloat("height", height)
//                editor.commit()
//            }
//
//            if (weight == 0) {
//                weight = data.getInt("weight", 0)
//            }
//            else if (weight != data.getInt("weight", 0)) {
//                editor.putInt("weight", weight)
//                editor.commit()
//            }
//
//            var bmi = getBmi(weight, height)
//            var statusBmi = getStatusBmi(bmi.toDouble(), this)
//
//            // Enviar dados de uma Activity pra outra
//            openResult.putExtra("bmi", bmi)
//            openResult.putExtra("statusBmi", statusBmi)
//
//            startActivity(openResult)
//        } else {
//            Toast.makeText(this, "ASDSADAD", Toast.LENGTH_SHORT).show()
//        }
//    }

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