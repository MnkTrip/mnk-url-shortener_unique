import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/StoreData")
public class StoreData extends HttpServlet {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/TestDB";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Mayank@312";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data1 = request.getParameter("data1");
        String data2 = request.getParameter("data2");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Register PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
          int id=1;
            // Prepare SQL statement
            String sql = "INSERT INTO url_data (id,data1, data2) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, data1);
            stmt.setString(3, data2);

            // Execute the statement
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"message\": \"Data received and stored successfully!\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"message\": \"Failed to store data.\"}");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"An error occurred. Please try again later.\"}");
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
