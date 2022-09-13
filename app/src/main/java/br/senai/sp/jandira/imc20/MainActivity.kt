package br.senai.sp.jandira.imc20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import br.senai.sp.jandira.imc20.databinding.ActivityMainBinding
import br.senai.sp.jandira.imc20.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        binding.textSignup.setOnClickListener {
            val openSignupActivity = Intent(this, SignupActivity::class.java)
            startActivity(openSignupActivity)
        }

        binding.buttonLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {

        if (validar()) {
            val email = binding.editTextEmailLogin.text.toString()
            val password = binding.editTextPasswordLogin.text.toString()

            val dados = getSharedPreferences("dados", MODE_PRIVATE)
            val emailSP = dados.getString("email", "E-mail não encontrado")
            val passSP = dados.getString("password", "")

            // verificar se os dados de autenticacao estao corretos

            if (email == emailSP && password == passSP) {
                val openCalculate = Intent(this, CalculatorActivity::class.java)
                startActivity(openCalculate)
            } else {
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun validar(): Boolean {
        if (binding.editTextEmailLogin.text.isEmpty()) {
            binding.editTextEmailLogin.error = "E-mail is required"
            return false
        }
        if (binding.editTextPasswordLogin.text.isEmpty()) {
            binding.editTextPasswordLogin.error = "Password is required"
            return false
        }
        return true
    }
}