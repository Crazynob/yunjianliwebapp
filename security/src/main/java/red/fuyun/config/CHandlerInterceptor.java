package red.fuyun.config;

import javax.servlet.*;
import java.io.IOException;



public class CHandlerInterceptor implements Filter {
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("----------------------------------CHandlerInterceptor------------------------------------");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
