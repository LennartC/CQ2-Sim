<%@ page import="java.io.*"
%><%@ page import="java.awt.image.*"
%><%@ page import="javax.imageio.*"
%><%@ page import="be.lacerta.cq2.sim.hbn.UserImage"
%><%try {
    	UserImage i = UserImage.getImageForUser(Integer.parseInt(request.getParameter("id")));
    	if (i==null) return;
    	 
        BufferedImage buffer = be.lacerta.cq2.utils.ImageGenerator.htmlToImage(
        		"<html>"+
        		"<head>"+
        		"<style>"+
 				"	body {padding: 0; margin: 0; background-color: #000000; color: #A0A0A0;}"+
        		"</style>"+
        		"</head>"+
        		"<body  bgcolor=\"black\">"+
        		i.getText()+"</body>");

        // set the content type and get the output stream
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();

        // output the image as png
        ImageIO.write(buffer, "png", os);
        os.close();
    } catch (NumberFormatException nfe) {
    } catch (Exception ex) {
        System.out.println(ex);
    }%>