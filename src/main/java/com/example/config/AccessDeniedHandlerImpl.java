package com.example.config;

import com.example.i18n.MessageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    private static final String LOG_REQUEST_PATTERN = "REQUEST method {} requested URI {} session ID {}: Access denied.";
    private static final String LOG_RESPONSE_PATTERN = "RESPONSE {} requested URI {}, session ID {}: Access denied \n{}";

    private static final Logger iLogger = LoggerFactory.getLogger(AccessDeniedHandlerImpl.class);

    @Autowired
    private MessageProvider iMessages;

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        String jsonObject = "{"
                + "\"status\": \"" + HttpStatus.FORBIDDEN.toString() + "\","
                + "\"message\":\"" + iMessages.getText("error.accessDenied") + "\""
                + "}";

        iLogger.info(LOG_REQUEST_PATTERN, httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), httpServletRequest.getHeader(HttpHeaders.COOKIE));
        iLogger.info(LOG_RESPONSE_PATTERN, httpServletResponse.getStatus(), httpServletRequest.getRequestURI(), httpServletRequest.getHeader(HttpHeaders.COOKIE), jsonObject);


        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        PrintWriter writer = httpServletResponse.getWriter();
        writer.print(jsonObject);
        writer.flush();
        writer.close();
    }
}
