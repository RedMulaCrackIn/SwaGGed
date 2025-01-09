package swagged.utils;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import swagged.model.bean.UtenteBean;

import java.io.IOException;

@WebFilter(filterName = "/AccessControlFilter", urlPatterns = "/*")
public class AccessControlFilter extends HttpFilter implements Filter {

    private static final long serialVersionUID = 1L;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        UtenteBean utente = (UtenteBean) httpServletRequest.getSession().getAttribute("utente");
        String path = httpServletRequest.getServletPath();
        if (path.contains("/common/") && utente==null) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/homepage.jsp");
            return;
        } else if (path.contains("/admin/") && (utente==null || !utente.isAdmin())) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/homepage.jsp");
            return;
        }

        chain.doFilter(request, response);
    }
}