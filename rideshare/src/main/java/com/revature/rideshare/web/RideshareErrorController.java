package com.revature.rideshare.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RideshareErrorController extends AbstractErrorController {
	
	public RideshareErrorController(ErrorAttributes errorAttributes) {
		super(errorAttributes);
	}

	@Override
	public String getErrorPath() {
		return "/#/error";
	}

//	public void handleError(HttpServletRequest request, HttpServletResponse response) {
//		HttpStatus status = getStatus(request);
//		String destination = getErrorPath() + "?reason=" + status.value();
//		try {
//			response.sendRedirect(destination);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	@RequestMapping("/error")
	public ResponseEntity<String> handleError(HttpServletRequest request) {
		HttpStatus status = getStatus(request);
		String errorPage = getErrorPath() + "?reason=" + status.value();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", errorPage);
		ResponseEntity<String> response = new ResponseEntity<String>(headers, HttpStatus.SEE_OTHER);
		return response;
	}
	
}
