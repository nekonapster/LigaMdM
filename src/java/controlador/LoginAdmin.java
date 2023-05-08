/*
 controlador de la pagina de Login
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.dao.UsuariosJpaController;
import modelo.entidades.Usuarios;

/**
 *
 * @author martin
 */
//revisar si los loginAdmin van en mayusculas o minusculas. y porque cuando pongo el urlPattern en min. y hago run desde esta pagina sale error 404 
@WebServlet(name = "loginAdmin", urlPatterns = {"/loginAdmin"})
public class LoginAdmin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //String usuario = request.getParameter("usuario");
            //String pass = request.getParameter("pass");
            String sesionArrancadaAdministrador = "sesionAdmin.jsp";

            final String PU = "LigaMdMPU";

            EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
            UsuariosJpaController ujc = new UsuariosJpaController(emf);
            List<Usuarios> usuariosLista = ujc.findUsuariosEntities();

            for (Usuarios usuarioLista : usuariosLista) {
                if (usuarioLista.getNombre().equals("admin")&&
                        usuarioLista.getPass().equals("rootroot") &&
                        usuarioLista.getRol().equals("Administrador")){
                        
                    response.sendRedirect(sesionArrancadaAdministrador);
                    return;
                } 
            }
            getServletContext().getRequestDispatcher("/loginAdmin.jsp").forward(request, response);
        } catch (Exception e) {
           System.out.println("Error " + e.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
