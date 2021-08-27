package tmall.filter;

import org.apache.commons.lang.StringUtils;
import tmall.bean.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class ForeAuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String[] noNeedAuthPage = new String[]{
                "checkLogin",
                "register",
                "loginAjax",
                "login",
                "product",
                "category",
                "search",
                "list"};
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        String contextPath=request.getServletContext().getContextPath();
        String uri=request.getRequestURI();
        String path= StringUtils.remove(uri,contextPath);
        System.out.println("path "+path);
        if(path.startsWith("/user")){
            String method=StringUtils.substringAfterLast(uri,"_");
            if(!Arrays.asList(noNeedAuthPage).contains(method)){
                User user= (User) request.getSession().getAttribute("user");
                System.out.println("user"+user);
                if(user==null)
                   {
                       System.out.println("user为空");
                    response.sendRedirect("login.jsp");
                    return;
                   }
            }
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
