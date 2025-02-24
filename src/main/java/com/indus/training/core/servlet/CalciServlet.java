
package com.indus.training.core.servlet;

import com.indus.training.core.domain.CalciInput;
import com.indus.training.core.domain.CalciOutput;
import com.indus.training.core.exception.CalciException;
import com.indus.training.core.impl.Calci;
import com.indus.training.core.svc.ICalci;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class CalciServlet extends HttpServlet {
	private ICalci calciService = new Calci();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>Calculator</title></head><body>");
		out.println("<h2>Calculator</h2>");
		out.println("<form action='calci' method='post'>");
		out.println("<label for='param1'>Parameter 1:</label>");
		out.println("<input type='text' name='param1' required><br><br>");
		out.println("<label for='param2'>Parameter 2:</label>");
		out.println("<input type='text' name='param2' required><br><br>");
		out.println("<label for='operation'>Operation:</label>");
		out.println("<select name='operation' required>");
		out.println("<option value='add'>Addition</option>");
		out.println("<option value='subtract'>Subtraction</option>");
		out.println("<option value='multiply'>Multiplication</option>");
		out.println("<option value='divide'>Division</option>");
		out.println("</select><br><br>");
		out.println("<input type='submit' value='Calculate'>");
		out.println("</form>");
		out.println("</body></html>");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
			
			double param1 = Double.parseDouble(request.getParameter("param1"));
			double param2 = Double.parseDouble(request.getParameter("param2"));
			String operation = request.getParameter("operation");

			CalciInput calciInput = new CalciInput();
			calciInput.setParam1(param1);
			calciInput.setParam2(param2);

			CalciOutput calciOutput = null;

			switch (operation) {
			case "add":
				calciOutput = calciService.addition(calciInput);
				break;
			case "subtract":
				calciOutput = calciService.subtract(calciInput);
				break;
			case "multiply":
				calciOutput = calciService.multiply(calciInput);
				break;
			case "divide":
				calciOutput = calciService.division(calciInput);
				break;
			default:
				throw new CalciException("Invalid Operation");
			}

			out.println("<html><head><title>Calculation Result</title></head><body>");
			out.println("<h3>Result of " + calciOutput.getOperation() + ":</h3>");
			out.println("<p>Param1: " + calciOutput.getParam1() + "</p>");
			out.println("<p>Param2: " + calciOutput.getParam2() + "</p>");
			out.println("<p>Result: " + calciOutput.getResult() + "</p>");
			out.println("<a href='calci'>Go back to Calculator</a>");
			out.println("</body></html>");
		} catch (CalciException e) {
			out.println("<h3>Error: " + e.getMessage() + "</h3>");
		} catch (NumberFormatException e) {
			out.println("<h3>Error: Invalid input format. Please enter valid numbers.</h3>");
		}
	}
}
