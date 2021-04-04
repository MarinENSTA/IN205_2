package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.MembreService;
import com.ensta.librarymanager.service.impl.EmpruntServiceImpl;
import com.ensta.librarymanager.service.impl.LivreServiceImpl;
import com.ensta.librarymanager.service.impl.MembreServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DashboardServlet extends HttpServlet {

    LivreService livreService = LivreServiceImpl.getInstance();
    EmpruntService empruntService = EmpruntServiceImpl.getInstance();
    MembreService membreService = MembreServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");

        try {
            request.setAttribute("livres_count", livreService.count());
            request.setAttribute("membres_count", membreService.count());
            request.setAttribute("emprunts_count", empruntService.count());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        try {
            request.setAttribute("emprunts", empruntService.getListCurrent());
        } catch (ServiceException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        dispatcher.forward(request, response);
    }
}
