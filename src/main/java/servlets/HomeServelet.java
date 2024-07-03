package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HomeServelet
 */
@WebServlet("/home")
public class HomeServelet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public HomeServelet() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().print("<h1>Hello my name is Ishimwe Mwiza Grace and My id is 25192</h1>");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the parameter from the request
        String enteredid = request.getParameter("id");

        // Check if enteredid is null before using it
        if (enteredid != null && enteredid.matches("\\\\d+")) {
            // Convert enteredid to an integer
            Integer id = Integer.parseInt(enteredid);

            try {
                String db_url = "jdbc:postgresql://localhost:5432/best_pro_db";
                String username = "";
                String password = "Admin";

                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection(db_url, username, password);
                PreparedStatement pst = con.prepareStatement("Select * from student where id = ?");
                pst.setInt(1, id);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    response.getWriter().print("<h1>Your name is " + name + " and id is " + id + "</h1>");
                } else {
                    response.getWriter().print("<h1>INVALID ID OR ID NOT FOUND</h1>");
                }

                // Close resources
                rs.close();
                pst.close();
                con.close();

            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().print("<h1>Database Error: " + e.getMessage() + "</h1>");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                response.getWriter().print("<h1>Driver Class Not Found: " + e.getMessage() + "</h1>");
            }

        } else {
            // Handle the case where enteredid is null or doesn't match the pattern
            response.getWriter().write("Invalid ID or ID not provided.");
        }
    }
}
