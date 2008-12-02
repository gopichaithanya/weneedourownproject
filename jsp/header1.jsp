<%@page import="java.lang.String"%>
<center>
<table border="1" width="800" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="3" align="center">
    <table>
      <tr>
        <td>
        <div><embed width="407" height="142" type="application/x-shockwave-flash"
          src="phase2/images/arrowhead.swf"
          pluginspage="http://www.macromedia.com/go/getflashplayer" wmode="transparent" loop="true"
          autoplay="false" quality="high" /><img width="340" height="141" border="0" alt=""
          src="phase2/images/b001main_right.jpg" /></div>
        </td>
      </tr>
      <tr>
        <td>
        <div style="float: left" align="left" class="title1">Proj4398: MakeMyTrip.Com - An
        Online Travel Reservation System</div>
        <div style="float: right" align="right">
        <%
           String username = (String) session.getAttribute("username");
           if (null == username) {
        %> <a href="login.spring">Login</a> <a href="register.spring">Register</a>
        <%
           } else {
        %> <a href="logout.spring">Logout: <%=session.getAttribute("username")%></a> <%
    }
 %>
        </div>
        </td>
      </tr>
    </table>
    </td>
  </tr>
  <tr>
    <td width="187"><img src="phase2/images/users_feedback_s.gif" /></td>
    <td width="28"><img src="phase2/images/topmenu_img1.gif" /></td>
    <td align="left" background="phase2/images/products_b.gif" valign="middle"><span
      class="title2"><%=request.getParameter("title2")%></span></td>
  </tr>
  <tr>
    <td valign="top">
    <table border="1">
      <tr>
        <td align="left"><%@ include file="/WEB-INF/jsp/links.jsp"%></td>
      </tr>
    </table>
    <img width="187" src="phase2/images/img95560953.jpg" />
    <p><font color="#99cc00"><b>Find airline tickets.....</b></font></p>
    <p><font color="#969696"><b>.... for your dream<br />
    destinations at<br />
    MakeMyTrip.com </b></font></p>
    <p><font color="#969696"><b>Book travel for less<br />
    with specials on <br />
    cheap airfares.</b></font></p>
    </td>
    <td></td>
    <td valign="top">