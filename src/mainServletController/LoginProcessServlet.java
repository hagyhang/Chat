package mainServletController;

import java.util.Enumeration;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.io.IOException;
import java.io.Serializable;
import java.net.HttpCookie;
import java.sql.SQLException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.ChatDbOperations;
import exceptions.ChatDbFailure;
import model.User;

//@WebServlet("/loginprocess")
public class LoginProcessServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

    public LoginProcessServlet() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		
		
	}
	protected void doAnyway(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		HttpSession session = request.getSession(true);
		/* by hagyhang
		System.out.println("SID: " + session.getId());
		System.out.println(request.isRequestedSessionIdFromCookie());
		System.out.println(request.isRequestedSessionIdFromUrl());
		System.out.println(request.isRequestedSessionIdFromUrl());
		System.out.println(request.isRequestedSessionIdValid());
		*/
		User loginUser=null;
		RequestDispatcher dispatch =null;
		try {
			int num = (int) session.getAttribute("num");
			session.setAttribute("num", num+1);
		}
		catch ( NullPointerException e){
			session.setAttribute("num", 0);
		}
//		System.out.println(session.getId());
		if (session.getAttribute("loginUser") != null){
			loginUser = (User) session.getAttribute("loginUser");
//			System.out.println(loginUser);
			session.setAttribute("loginUser", loginUser);
			dispatch = context.getRequestDispatcher("/WEB-INF/chatMainPage.jsp");
		}
		else {
			User user = null;
			try{
				 user = new User(request);
			}
			catch(Exception e){}
			if (user != null){
				String tName = user.getName();
				String tPass = user.getPass();
				if (tName == null || tPass == null) {
					System.out.println("There is an error returning back!");
					/* The form contained invalid data, transfer colontrol back to original form */					
					request.setAttribute("user", user);
					if(tName == null){
						request.setAttribute("errorInNameMsg", "Name can not be null !");}
					if(tPass == null){
						request.setAttribute("errorInPassMsg", "Password can't be null !");}
					dispatch = context.getRequestDispatcher("/login.jsp");
				} 
				else{
				
					try {
						loginUser = ChatDbOperations.loginToAccount(tName,tPass);
					} catch (ChatDbFailure | SQLException e) {
						e.printStackTrace();
					}
					//////If Database do not have any match found then login user/Password incorrect////
					if(loginUser==null){
						request.setAttribute("user", user);
						request.setAttribute("loginFailMsg", "Login Failed !");
						dispatch = context.getRequestDispatcher("/login.jsp");
					 }
					else{ //////////Else success!! //////
						session = request.getSession();
						session.setAttribute("loginUser", loginUser);
						dispatch = context.getRequestDispatcher("/WEB-INF/chatMainPage.jsp");
					}
				}		
			}
			else {
				dispatch = context.getRequestDispatcher("/login.jsp");
			}
		}
		dispatch.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAnyway(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAnyway(request, response);
	}
}
