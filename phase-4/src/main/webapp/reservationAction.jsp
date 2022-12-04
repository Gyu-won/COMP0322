<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ page language="java" import="java.text.*, java.sql.*" %>
<%@ page import="user.UserDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="reservation.ReserveInfoDAO" %>
<%@ page import="reservation.ReservationInfo" %>
<jsp:useBean id="reserveinfo" class="reservation.ReservationInfo" scope="page" />
<jsp:setProperty name="reserveinfo" property="tid" />
<jsp:setProperty name="reserveinfo" property="depart" />
<jsp:setProperty name="reserveinfo" property="arrive" />
<jsp:setProperty name="reserveinfo" property="sid" />
<jsp:setProperty name="reserveinfo" property="ddate" />
<jsp:setProperty name="reserveinfo" property="dtime" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style.css" />
<title>Reservation :: UniBus</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<%
		String userAID = null;
		if(session.getAttribute("userAid") != null){
			userAID = (String) session.getAttribute("userAid");
		}
		
		if(userAID == null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('Please Sign In First!')");
			script.println("location.href = 'main.jsp'");
			script.println("</script>");
		}
		
		String[] rsid = request.getParameterValues("sid");
		String age = request.getParameter("child") + request.getParameter("teenager") + request.getParameter("adult");
		
		ReserveInfoDAO reserveDAO = new ReserveInfoDAO();
		ReservationInfo info = reserveDAO.getReserve(reserveinfo, rsid, age);
	%>

	<h1 class="px-1">Reservation Details</h1>
	<main class="px-1 py-1">
	<div class="border flex">
		<div class="bg-primary px-1 py-05"><h2>🎫 INFO</h2></div>
		<div class="grid-3 px-1 py-1 gap-1">
			<div>
				<h3>date</h3>
				<div class="emphasize"><%= info.getDdate() %></div>
			</div>
			
			<div>
				<h3>depart time</h3>
				<div class="emphasize"><%= info.getDtime() %></div>
			</div>
			
			<div>
				<h3>depart station</h3>
				<div class="emphasize"><%= info.getDepart() %></div>
			</div>
			<div>
				<h3>arrive station</h3>
				<div class="emphasize"><%= info.getArrive() %></div>
			</div>
			
			<div>
				<h3>seat number</h3>
				<div class="emphasize"><%= info.getSid() %></div>
			</div>
			
			<div>
				<h3>price</h3>
				<div class="emphasize"><%= info.getPrice() %></div>
			</div>
		</div>
	</div>
	<div class="grid-2 gap-1 py-1">
		<div class="btn blank">
			<a href="reservation.jsp?rtid = <%= info.getTid() %>&depart=<%= info.getDepart() %>&arrive=<%= info.getArrive() %>&ddate=<%= info.getDdate() %>&dtime=<%= info.getDtime() %>" style="width: auto; color: inherit; text-decoration: none;" value="back">Back to Seats</a>	
		</div>
		<div class="btn">
			<a href="complete.jsp?fee=<%= info.getPrice() %>&rtid=<%= info.getTid() %>&rsid=<%= info.getSid() %>&rage=<%= info.getAge() %>" style="width:auto; color: inherit; text-decoration: none;" value="reserve">Reserve</a>
		</div>
	</div>
	</main>
	<jsp:include page="footer.jsp" />
</body>
</html>