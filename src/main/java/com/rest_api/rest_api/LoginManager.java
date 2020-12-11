package com.rest_api.rest_api;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.rest_api.rest_api.utils.Utils;

@Path("/login")
public class LoginManager extends HttpServlet {
	private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		try {
			String authHeader = request.getHeader("Authorization");
			response.setContentType("json/html");

			if (authHeader == null) {
				throw new ServletException("Unauthorized");
			}

			String encodedAuth = authHeader.substring(authHeader.indexOf(' ') + 1);
			String decodedAuth = new String(Base64.getDecoder().decode(encodedAuth));

			String username = decodedAuth.substring(0, decodedAuth.indexOf(':')).trim();
			String password = decodedAuth.substring(decodedAuth.indexOf(':') + 1).trim();

			int result = CustomerRepository.getIstance().getCustomerByUsernameAndPassword(username, password);

			if (result == 0) {
				response.getOutputStream()
						.println(this.ow.writeValueAsString(Utils.ApiGenericResponseBuilder("success", 200)));
			} else {
				throw new ServletException("Unauthorized");
			}
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

	}

}
