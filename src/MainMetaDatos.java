import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMetaDatos {
        private static final String URL = "jdbc:postgresql://localhost:5432/empresa";
        private static final String usuario = "postgres";
        private static final String password = "root";

        public static void main(String[] args) {
            try {
                Connection conexion = DriverManager.getConnection(URL, usuario, password);
                Scanner sc = new Scanner(System.in);

                System.out.println("Introduce la consulta SQL (sin ; final):");
                String consulta = sc.nextLine();

                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(consulta);
                ResultSetMetaData meta = rs.getMetaData();

                int columnas = meta.getColumnCount();
                System.out.println("Número de columnas: " + columnas);
                System.out.println("===================================");

                for (int i = 1; i <= columnas; i++) {
                    String nombre = meta.getColumnName(i);
                    String tipo = meta.getColumnTypeName(i);
                    int ancho = meta.getColumnDisplaySize(i);
                    int nulls = meta.isNullable(i);

                    System.out.println("Columna " + i);
                    System.out.println("Nombre: " + nombre);
                    System.out.println("Tipo: " + tipo);
                    System.out.println("Ancho máximo: " + ancho);
                    System.out.println("Admite NULL: " + (nulls == ResultSetMetaData.columnNullable));
                    System.out.println("-----------------------------------");
                }

                //SELECT * FROM empleados ORDER BY salario (ejemplo)

                conexion.close();

            } catch (SQLException ex) {
                Logger.getLogger(MainJDBCPostgreSQL.class.getName()).log(Level.SEVERE , null, ex);
            }
        }
    }


