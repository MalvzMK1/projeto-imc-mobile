package br.senai.sp.jandira.imc20

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import br.senai.sp.jandira.imc20.model.User

class SignupActivity : AppCompatActivity() {

    lateinit var textLogin: TextView
    lateinit var editName: EditText
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var editWeight: EditText
    lateinit var editHeight: EditText
    lateinit var buttonSaveUser: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar!!.hide()

        // Instanciar os componentes (views)
        textLogin = findViewById(R.id.login_text)
        editName = findViewById(R.id.edit_text_name)
        editEmail = findViewById(R.id.edit_text_email)
        editPassword = findViewById(R.id.edit_text_password)
        editWeight = findViewById(R.id.edit_text_weight)
        editHeight = findViewById(R.id.edit_text_height)
        buttonSaveUser = findViewById(R.id.save_user_button)

        buttonSaveUser.setOnClickListener {
            saveUser()
        }

        // Botao para voltar para a activity main
        textLogin.setOnClickListener {
            val openMainActivity = Intent(this, MainActivity::class.java)
            startActivity(openMainActivity)
        }
    }

    private fun saveUser() {

        val user = User()
        user.id = 1
        user.name = editName.text.toString()
        user.email = editEmail.text.toString()
        user.password = editPassword.text.toString()
        user.weight = editWeight.text.toString().toInt()
        user.height = editHeight.text.toString().toDouble()

        // Gravar o usuario no shared preferences

        // Passo 1 - Obter uma instancia do shared preferences
        val datas = getSharedPreferences("dados", MODE_PRIVATE)

        // Passo 2 - Criar um editor para o arquivo
        val editor = datas.edit()

        // Passo 3 - Inserir os dados no arquio SharedPreferences
        editor.putInt("id", user.id)
        editor.putString("name", user.name)
        editor.putString("email", user.email)
        editor.putString("password", user.password)
        editor.putInt("weight", user.weight)
        editor.putFloat("height", user.height.toFloat())

        if (editor.commit()) {
            Toast.makeText(this, "Usuario salvo com sucesso", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Ocorreu um erro na gravação", Toast.LENGTH_SHORT).show()
        }

    }
}