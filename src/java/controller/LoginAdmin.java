/*
 controlador de la pagina de Login
 */
package controller;

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
import modelo.entities.Usuarios;

/**
 *
 * @author martin
 */
@WebServlet(name = "LoginAdmin", urlPatterns = {"/LoginAdmin"}) //el urlPattern tiene que ver con la pagina jsp que quiero que redirija ?
public class LoginAdmin extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            final String PU = "LigaMdMPU";
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
            UsuariosJpaController ujc = new UsuariosJpaController(emf);
            List<Usuarios> usuariosLista = ujc.findUsuariosEntities();

            String loginAdminVista = "/loginAdmin.jsp";
            String sesionArrancadaAdministrador = "adminPage.jsp";
            String parametroEnviadoLoginAdminUsu = request.getParameter("usuario");
            String parametroEnviadoLoginAdminPass = request.getParameter("pass");

            if (parametroEnviadoLoginAdminUsu != null) {
                for (Usuarios usuarioLista : usuariosLista) {
                    if (usuarioLista.getNombre().equals(parametroEnviadoLoginAdminUsu) && usuarioLista.getPass().equals(parametroEnviadoLoginAdminPass)) {
                        response.sendRedirect(sesionArrancadaAdministrador);
                        return;
                    }
                }
            }
            //los foward siempre llevan barra en la direccion "/loginAdmin.jsp"
            getServletContext().getRequestDispatcher(loginAdminVista).forward(request, response);
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
