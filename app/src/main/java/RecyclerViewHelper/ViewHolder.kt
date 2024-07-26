package RecyclerViewHelper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import lima.example.limaperaza.R

class ViewHolder(view: View): RecyclerView.ViewHolder(view){

    val txtPaciente: TextView = view.findViewById(R.id.txtNombrePaciente)

    val imgEdit: ImageView = view.findViewById(R.id.imgEdit)
    val imgDelete: ImageView = view.findViewById(R.id.imgDelete)

}