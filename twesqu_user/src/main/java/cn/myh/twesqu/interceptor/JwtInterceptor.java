package cn.myh.twesqu.interceptor;

import cn.myh.twesqu.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 前端请求接口时要加上头信息Authorization
     * 内容为Bearer+空格+token
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String auth = request.getHeader("Authorization");
        System.out.println("token: -------->" +auth);
        return super.preHandle(request, response, handler);
    }
}
