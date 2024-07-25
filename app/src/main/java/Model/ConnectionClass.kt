package Model

import oracle.ons.Connection
import java.sql.DriverManager

class ConnectionClass{
    fun cadenaConexion(): java.sql.Connection? {
        try{
            //Credenciales
            val ip = "jdbc:oracle:thin:@10.10.2.70:1521:xe"
            val user = "PFLimaPeraza"
            val password = "LimaPeraza"

            //Usar credenciales
            val connection = DriverManager.getConnection(ip, user, password)
            return connection
        }
        //En caso de error
        catch(e: Exception){
            println("Error: $e")
            return null
        }
    }


}