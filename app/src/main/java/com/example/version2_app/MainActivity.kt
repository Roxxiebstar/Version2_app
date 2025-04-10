package com.example.version2_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity()
{
    // Referencia al DAO
    private lateinit var userDao: UserDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        //Referenciar a los elementos de mi IU principales
        val editTextName = findViewById<EditText>(R.id.editNombre)
        val buttonSave = findViewById<Button>(R.id.buttonSave)

        // Obtener instancia de la base de datos y el DAO
        val db = AppDatabase.getDatabase(applicationContext)
        userDao = db.userDao()

        // Configurar el botón para guardar el usuario
        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()

            if (name.isNotBlank()) {
                // Llamar al insertUser desde una corrutina
                CoroutineScope(Dispatchers.IO).launch {
                    val user = User(id = 1, name = name)
                    userDao.insertUser(user)
                    // Verificamos que se haya guardado consultándolo
                    val savedUser = userDao.getUser()

                    if (savedUser != null) {
                        runOnUiThread {
                            Toast.makeText(
                                this@MainActivity,
                                "Usuario guardado: ${savedUser.name}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}