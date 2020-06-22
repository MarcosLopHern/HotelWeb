package com.ipn.mx.utilidades;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "FiltroAcceso", urlPatterns = {"/*"})
public class FiltroAcceso implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String tipo = "";
        if(req.getSession().getAttribute("tipo") != null){
            tipo = req.getSession().getAttribute("tipo").toString();
        }
        String url = req.getRequestURI();
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        res.setDateHeader("Expires", 0);

        if(tipo.isEmpty()){ // Si el tipo es nulo
            // Si no es el index, el formulario de huespedes o un recurso js o css redirecciona al index
            if(!url.endsWith("/") && !url.contains("index") && !url.contains("huespedForm") && !url.contains("javax.faces.resource") && !url.contains(".js") && !url.contains(".css") && !url.contains(".jpg") && !url.contains(".png") && !url.contains(".jar") && !url.contains(".war"))
                res.sendRedirect(req.getServletContext().getContextPath()+"/");  
            else chain.doFilter(request, response);
        }else if(tipo.equals("huesped")){ // Si el tipo es huesped
            //Si intenta acceder a la lista de huespedes, al formulario de cuartos, a los reportes o la gráfica, regresa al inicio
            if(url.endsWith("/") || url.contains("index") || url.contains("listaHuespedes") || url.contains("cuartoForm") || url.contains("reportes"))
                res.sendRedirect(req.getContextPath()+"/faces/huespedes/bienvenida.xhtml");
            else chain.doFilter(request, response);
        }else if(tipo.equals("administrador")){ // Si el tipo es administrader
            if(url.endsWith("/") || url.contains("index"))
                res.sendRedirect(req.getContextPath()+"/faces/huespedes/bienvenida.xhtml");
            else chain.doFilter(request, response);
        }     
    }

    @Override
    public void destroy() {

    }
    
}
