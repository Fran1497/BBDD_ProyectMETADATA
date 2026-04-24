import java.sql.*;
import java.util.Scanner;

public class SelectorBBDD {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String usuario = "postgres";
    private static final String password = "root";

    public static void main(String[] args) {
        try {
            Connection conexion = DriverManager.getConnection(URL, usuario, password);
            Scanner sc = new Scanner(System.in);


            // 1. Listar bases de datos

            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT datname FROM pg_database WHERE datistemplate = false");

            System.out.println("Bases de datos disponibles:");
            while (rs.next()) {
                System.out.println("- " + rs.getString(1));
            }

            System.out.println("\nSelecciona una base de datos:");
            String bd = sc.nextLine();


            // conexion

            String urlBD = "jdbc:postgresql://localhost:5432/" + bd;
            Connection conexionBD = DriverManager.getConnection(urlBD, usuario, password);

            DatabaseMetaData metaBD = conexionBD.getMetaData();


            // 3. Listar tablas

            String[] tipos = {"TABLE"};
            ResultSet tablas = metaBD.getTables(null, "public", null, tipos);

            System.out.println("\nTablas en la base de datos " + bd + ":");
            while (tablas.next()) {
                System.out.println("- " + tablas.getString("TABLE_NAME"));
            }

            System.out.println("\nSelecciona una tabla:");
            String tabla = sc.nextLine();


            // 4. Metadatos de la tabla

            Statement st2 = conexionBD.createStatement();
            ResultSet rs2 = st2.executeQuery("SELECT * FROM " + tabla);
            ResultSetMetaData meta = rs2.getMetaData();

            int columnas = meta.getColumnCount();
            System.out.println("\nColumnas de la tabla " + tabla + ":");
            System.out.println("====================================");

            for (int i = 1; i <= columnas; i++) {
                String nombreCol = meta.getColumnName(i);
                String tipo = meta.getColumnTypeName(i);
                int tam = meta.getColumnDisplaySize(i);
                boolean admiteNull = meta.isNullable(i) == ResultSetMetaData.columnNullable;

                System.out.println("Columna " + i);
                System.out.println("Nombre: " + nombreCol);
                System.out.println("Tipo: " + tipo);
                System.out.println("Longitud: " + tam);
                System.out.println("Admite NULL: " + admiteNull);
                System.out.println("------------------------------------");
            }

            conexion.close();
            conexionBD.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

