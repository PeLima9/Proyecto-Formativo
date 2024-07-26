package RecyclerViewHelper

import Model.ConnectionClass
import Model.listaPacientes
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lima.example.limaperaza.DetallePaciente
import lima.example.limaperaza.R

class Adaptador(private var Datos: List<listaPacientes>): RecyclerView.Adapter<ViewHolder>(){
    //Get-Set Kotlin Equivalent
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
        return ViewHolder(vista)
    }

    //Edit Product
    fun editProduct(nombre: String, uuid: String){
        GlobalScope.launch(Dispatchers.IO){
            val objConexion = ConnectionClass().cadenaConexion()
            val updateProduct = objConexion?.prepareStatement("update tbPacientes set nombre = ? where uuid = ?")!!
            updateProduct.setString(1, nombre)
            updateProduct.setString(2, uuid)
            updateProduct.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()

            withContext(Dispatchers.Main){
                updateAfterEdit(uuid, nombre)
            }
        }
    }

    //Update Register
    fun updateAfterEdit(uuid: String, newName: String){
        val index = Datos.indexOfFirst{it.uuid == uuid}
        Datos[index].nombres = newName
        notifyItemChanged(index)
    }
    fun updateList (nuevaLista: List<listaPacientes>){
        Datos = nuevaLista
        notifyDataSetChanged()
    }




    //Delete Register
    fun eliminarRegistro(nombre: String, posicion: Int){
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)

        //Delete data
        GlobalScope.launch(Dispatchers.IO){
            val objConexion = ConnectionClass().cadenaConexion()
            val deleteProduct = objConexion?.prepareStatement("delete tbPacientes where nombre = ?")!!
            deleteProduct.setString(1, nombre)
            deleteProduct.executeUpdate()

            //Confirm deletion
            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()

        }

        //Notify item deletion
        Datos = listaDatos.toList()
        notifyItemRemoved(posicion)
        notifyDataSetChanged()

    }

    override fun getItemCount() = Datos.size


    //Card elements functionality
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val paciente = Datos[position]
        holder.txtPaciente.text = paciente.nombres

        //Click on delete icon
        holder.imgDelete.setOnClickListener{
            val context = holder.itemView.context

            //Create Builder
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Eliminar")
            builder.setMessage("Seguro que deseas eliminar este registro para siempre (mucho tiempo)?")

            builder.setPositiveButton("Si"){
                    dialog, wich ->
                eliminarRegistro(paciente.nombres, position)
            }

            builder.setNegativeButton("No"){
                    dialog, wich ->
                dialog.dismiss()
            }

            //Show Dialog
            val dialog = builder.create()
            dialog.show()
        }

        //Click on edit icon
        holder.imgEdit.setOnClickListener{
            val context = holder.itemView.context

            //Create Builder
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Editar")

            //Create TextBox
            val textBox = EditText(context)
            textBox.setHint(paciente.nombres)

            //Show TextBox
            builder.setView(textBox)

            //Button Functions
            builder.setPositiveButton("Actualizar"){
                    dialog, wich ->
                editProduct(textBox.text.toString(), paciente.uuid)
            }

            builder.setNegativeButton("Cancelar"){
                    dialog, wich ->
                dialog.dismiss()
            }

            //Show Dialog
            val dialog = builder.create()
            dialog.show()
        }

        //Clicking the card
        holder.itemView.setOnClickListener{
            val context = holder.itemView.context

            //Screen navigation
            val detailsScreen = Intent(context, DetallePaciente::class.java)

            //Sending the values
            /*
            detailsScreen.putExtra("UUID", producto.uuid)
            detailsScreen.putExtra("nombre", producto.nombre)
            detailsScreen.putExtra("precio", producto.precio)
            detailsScreen.putExtra("cantidad", producto.cantidad)

             */

            //Launching activity
            context.startActivity(detailsScreen)
        }

    }

    //Update Data
    fun updateRCV(nuevaLista: List<listaPacientes>){
        Datos = nuevaLista
        notifyDataSetChanged()

    }

}