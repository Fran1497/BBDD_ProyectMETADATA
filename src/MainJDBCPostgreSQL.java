import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainJDBCPostgreSQL {
    private static final String URL = "jdbc:postgresql://localhost:5432/empresa";
    private static final String usuario = "postgres";
    private static final String password = "root";

    public static void main(String[] args) {
        try {
            Connection conexion = DriverManager.getConnection(URL, usuario, password);

            DatabaseMetaData dbmd = conexion.getMetaData();

            ResultSet resul = null;
            String nombre = dbmd.getDatabaseProductName();
            String driver = dbmd.getDriverName();
            String url = dbmd.getURL();
            String usuario = dbmd.getUserName();
            System.out.println("INFORMACIÓN SOBRE LA BASE DE DATOS:");
            System.out.println("===================================");
            System.out.println("Nombre: " + nombre);
            System.out.println("Driver: " + driver);
            System.out.println("URL: " + url);
            System.out.println("Usuario: " + usuario);

            String[] tipos = {"TABLE"};
            resul = dbmd.getTables("empresa","empresa" ,null, tipos);

            while(resul.next()) {
                String catalogo = resul.getString(1); //columna 1 que

                String esquema = resul.getString(2);
                String tabla = resul.getString(3);
                String tipo = resul.getString(4);
                System.out.println(tipo + " - Catálogo: " + catalogo + ", Esquema:" + esquema +", Nombre: " + tabla);
            }
            resul = dbmd.getTables(null, null, null, tipos);


            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM empleados");
            ResultSetMetaData meta = rs.getMetaData();

            int columnas = meta.getColumnCount();
            System.out.println("Número de columnas: " + columnas);

            for (int i = 1; i <= columnas; i++) {
                System.out.println("Columna " + i + ": " + meta.getColumnName(i));
            }

            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(MainJDBCPostgreSQL.class.getName()).log(Level.SEVERE , null, ex);
        }




    }

}

