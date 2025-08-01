package com.smarthostel.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smarthostel.dao.StudentDAO;
import com.smarthostel.dao.UserDAO;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String name = request.getParameter("name");  // extra field from form

        UserDAO userDAO = new UserDAO();
        int userId = userDAO.registerUserAndReturnId(username, password, role);

        if (userId > 0) {
            if ("student".equals(role)) {
                StudentDAO studentDAO = new StudentDAO();
                studentDAO.insertStudent(userId, name);
            }

            request.setAttribute("message", "Registration successful! Please login.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Username already exists or error occurred.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }
    }
}
