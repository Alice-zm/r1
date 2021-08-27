package tmall.filter;

import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        String path=request.getServletContext().getContextPath();
        String path2=request.getRequestURI();
        String url= StringUtils.remove(path2,path);
        if(url.startsWith("/admin")||url.startsWith("/user")){
            String servletPath="/"+StringUtils.substringBetween(url,"_","_")+"Servlet";
            String method=StringUtils.substringAfterLast(url,"_");
            request.setAttribute("method",method);
            servletRequest.getRequestDispatcher(servletPath).forward(request,response);
            return;
        }
        filterChain.doFilter(request,response);

    }

    @Override
    public void destroy() {

    }
}
