package lima.example.limaperaza

import Model.ConnectionClass
import Model.listaPacientes
import RecyclerViewHelper.Adaptador
import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Connect Views
        val btnAgregar = findViewById<Button>(R.id.btnAgregarPaciente)
        val rcvPaciente = findViewById<RecyclerView>(R.id.rcvPacientes)

        rcvPaciente.layoutManager = LinearLayoutManager(this)


        //Setup RecyclerView
        rcvPaciente.layoutManager = LinearLayoutManager(this)
        fun obtenerDatos(): List<listaPacientes>{
            val objConnection = ConnectionClass().cadenaConexion()

            //Query Setup
            val statement = objConnection?.createStatement()
            val resultSet = statement?.executeQuery("select * from tbPacientes")!!

            val listadoPacientes = mutableListOf<listaPacientes>()
            while (resultSet.next()){
                val uuid = resultSet.getString("uuid")
                val nombres = resultSet.getString("nombres")
                val apellidos = resultSet.getString("apellidos")
                val edad = resultSet.getInt("cantidad")
                val enfermedad = resultSet.getString("enfermedad")
                val numHabitacion = resultSet.getInt("numeroHabitacion")
                val numCama = resultSet.getInt("numeroCama")
                val medAsignado = resultSet.getString("medicamentoAsignado")
                val fechaNacimiento = resultSet.getString("fechaNacimiento")
                val medHora = resultSet.getDouble("horaMedicamento")
                val paciente = listaPacientes(uuid, nombres, apellidos, edad, enfermedad, numHabitacion, numCama, medAsignado, fechaNacimiento, medHora)
                listadoPacientes.add(paciente)


            }
            return listadoPacientes
        }
        CoroutineScope(Dispatchers.IO).launch{
            val ejecutarFuncion = obtenerDatos()

            withContext(Dispatchers.Main){
                val miAdaptador = Adaptador(ejecutarFuncion)
                rcvPaciente.adapter = miAdaptador
            }
        }

        btnAgregar.setOnClickListener{
        }

    }
}