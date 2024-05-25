package zona_fit.conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;

public class Conexion {
    public static Connection getConexion(){
        Connection conexion = null;
        var baseDatos = "zonafit";
        var url = "jdbc:mysql://localhost:3306/"+baseDatos;
        var usuario = "root";
        var password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url,usuario,password);
        }catch (Exception e){
            System.out.println("Error al conectar base de datos = " + e.getMessage());
        }
        return conexion;
    }

    public static void main(String[] args) {
        var conexion = Conexion.getConexion();
        if(conexion != null){
            System.out.println("Conexion establecida"+conexion);
        }else {
            System.out.println("Error al conectarse");
        }
    }
}
