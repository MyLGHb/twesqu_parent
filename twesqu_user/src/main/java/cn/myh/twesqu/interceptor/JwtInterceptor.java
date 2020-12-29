package cn.myh.twesqu.interceptor;

import cn.myh.twesqu.common.entity.Result;
import cn.myh.twesqu.common.entity.StatusCode;
import cn.myh.twesqu.common.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ObjectMapper objectMapper;

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
        if(auth == null || !auth.startsWith("Bearer ")) {
            output(response,"认证未通过，访问失败");
            return false;
        }
        try {
            final String token = auth.substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            if("admin".equals(claims.get("roles"))) request.setAttribute("admin_claims",claims);
            if("user".equals(claims.get("roles"))) request.setAttribute("user_claims",claims);
            System.out.println("token: -------->" +auth);
            return true;
        } catch (Exception e) {
            output(response,"认证已过期，访问失败");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * token 验证失败后响应数据的方法
     * @param res
     * @param message
     */
    private void output(HttpServletResponse res, String message) {
        Result result = new Result(false,StatusCode.LOGIN_ERROR,message);
        try {
            res.setContentType("application/json;charset=UTF-8;");
            PrintWriter writer = res.getWriter();
            String jsonStr = objectMapper.writeValueAsString(result);
            writer.println(jsonStr);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("JwtInterceptor 返回数据异常");
            e.printStackTrace();
        }
    }
}
