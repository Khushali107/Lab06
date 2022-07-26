/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author KHUSH
 */
public class ShoppingListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String action = "";
       
        if(request.getParameter("action") != null) {
            action = request.getParameter("action");
        }
        
        if(!action.equals("logout")) {        } else {
                 session.invalidate();
            String message = "You have successfully logged out";
            request.setAttribute("message", message);
            
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
            return;
        }
        
        if(username != null) {
            getServletContext().getRequestDispatcher("/WEB-INF/shoppingList.jsp").forward(request, response);
            return;
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        return;
         }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ArrayList<String> items = (ArrayList<String>) session.getAttribute("items");
        String action = "";
        
       if(request.getParameter("action") == null) {
        } else {
            action = request.getParameter("action");
        }
        
        if(items == null) {
            items = new ArrayList<>();
        }
        
        if(action.equals("register")) {
            String usernameInput = request.getParameter("username");

           
            if(usernameInput == null || usernameInput.equals("")) {
                String message = "Please enter a username";
                request.setAttribute("usernameError", message);

                getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
                return;
            } else {
                session.setAttribute("username", usernameInput);
                
                getServletContext().getRequestDispatcher("/WEB-INF/shoppingList.jsp").forward(request, response);
                return;
            }
        } else if (action.equals("add")) {
            String item = request.getParameter("item");
            
            if(item != null && !item.equals("")) {
                items.add(item);
                session.setAttribute("items", items);
                
                response.sendRedirect("ShoppingList"); // to ensure post is not repeated upon page refresh
                return;
            } else {
                String message = "Please enter an item";
                request.setAttribute("addError", message);
                
                getServletContext().getRequestDispatcher("/WEB-INF/shoppingList.jsp").forward(request, response);
                return;
            }
            
        } else if (action.equals("delete")) {
            String itemSelected = request.getParameter("shoppingItems");
            
            System.out.println(itemSelected);
            
            if(itemSelected != null) {
                items.remove(itemSelected);
            
                response.sendRedirect("ShoppingList"); // to ensure post is not repeated upon page refresh
                return;
            } else {
                System.out.println("should work");
                String message = "Please select the item you want to delete";
                request.setAttribute("deleteError", message);
                
                getServletContext().getRequestDispatcher("/WEB-INF/shoppingList.jsp").forward(request, response);
                return;
            }
        }
            }
        }
    

