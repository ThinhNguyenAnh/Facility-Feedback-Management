/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.feedback.FeedbackDTO;
import app.feedback.FeedbackDetailDTO;
import app.response.ResponseDTO;
import app.statistic.StatisticDAO;
import app.users.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "NotificationResponseController", urlPatterns = {"/NotificationResponseController"})
public class NotificationResponseController extends HttpServlet {

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
         try {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_EMP");
            StatisticDAO dao = new StatisticDAO();
            List<ResponseDTO> list = new ArrayList<>();
            List<FeedbackDTO> listFB = new ArrayList<>();
            String count = request.getParameter("notification");
            int notification = 0;
            int check = Integer.parseInt(count);
            int check2 = dao.countForNotificationEmployeeResponse();
            if (check2 > check) {
                notification = check2 - check;
                list = dao.getListFeedbackDetailForNotificationResponse(notification);
                for (int i = 0; i<list.size(); i++) {
                if(i==0){
                    FeedbackDTO feedback = dao.getFeedbackByIDResponse(list.get(i).getFeedbackID());
                    listFB.add(feedback);
                }else{
                    if(!list.get(i).getFeedbackID().equals(list.get(i-1).getFeedbackID())){
                         FeedbackDTO feedback = dao.getFeedbackByIDResponse(list.get(i).getFeedbackID());
                         listFB.add(feedback);
                    }
                }
            }
            }
            
            PrintWriter out = response.getWriter();
            if (list.size() > 0) {
                out.println(list.size());
            }
            for (FeedbackDTO feedback : listFB) {
                out.println("<div class=\"notification-item\" onclick=\"handleReloadPage()\">\n"
                        + "                                                <div class=\"pipe-item-heading\">\n"
                        + "                                                    <div class=\"pipe-item-title-wrapper\">\n"
                        + "                                                        <h3 class=\"pipe-item-title\">Feedback " + feedback.getFeedbackID() + "</h3>\n"
                        + "                                                        <p class=\"pipe-item-desc\">\n"
                        + "                                                            <strong>Send by:</strong> " + feedback.getFullName() + "\n"
                        + "                                                        </p>\n"
                        + "                                                    </div>\n"
                        + "                                                    <div class=\"pipe-item-date\">" + feedback.getDate() + "</div>\n"
                        + "                                                </div>\n"
                        + "                                                <div class=\"pipe-item-bottom\">\n"
                        + "                                                    <p class=\"pipe-bottom-item\">\n"
                        + "                                                        <strong>Response by</strong>\n"
                        + "                                                        " + feedback.getEmpName()+ "\n"
                        + "                                                    </p>\n"
                        + "                                                </div>\n"
                        + "                                            </div>");
            }
        } catch (Exception e) {
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
