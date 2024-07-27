package lima.example.limaperaza

import Model.ConnectionClass
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AgregarPaciente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_paciente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Add stuff
        btnAgregar.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = ConnectionClass().cadenaConexion()

                //Query setup
                val addProducto =
                    objConexion?.prepareStatement("insert into tbProductosLima(uuid, nombre, precio, cantidad) values(?, ?, ?, ?)")!!
                addProducto.setString(1, UUID.randomUUID().toString())
                addProducto.setString(2, txtNombre.text.toString())
                addProducto.setInt(3, txtPrecio.text.toString().toInt())
                addProducto.setInt(4, txtCantidad.text.toString().toInt())
                addProducto.executeUpdate()

                val newProducts = obtenerDatos()
                withContext(Dispatchers.Main){
                    (rcvDatos.adapter as? Adaptador)?.updateRCV(newProducts)
                }




            }

        }
    }
}