package br.senai.sp.jandira.imc20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.senai.sp.jandira.imc20.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val openCalculator = Intent(this, CalculatorActivity::class.java)

        supportActionBar!!.hide()

        // recuperar os valores que estao na intent

        val bmi = "%.2f".format(intent.getDoubleExtra("bmi", 0.0))
        val statusBmi = intent.getStringExtra("statusBmi")

        binding.textViewResult.text = bmi.toString()
        binding.textViewStatus.text = statusBmi

        binding.backButton.setOnClickListener {
            startActivity(openCalculator)
        }
    }
}