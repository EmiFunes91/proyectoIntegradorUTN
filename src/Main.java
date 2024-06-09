import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el nombre del paciente:");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese el apellido del paciente:");
        String apellido = scanner.nextLine();

        System.out.println("Ingrese la dirección del paciente:");
        String direccion = scanner.nextLine();

        System.out.println("Ingrese el teléfono del paciente:");
        String telefono = scanner.nextLine();

        String sql = "INSERT INTO Personas (nombre, apellido, direccion, telefono) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.setString(3, direccion);
            preparedStatement.setString(4, telefono);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Paciente insertado exitosamente.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
