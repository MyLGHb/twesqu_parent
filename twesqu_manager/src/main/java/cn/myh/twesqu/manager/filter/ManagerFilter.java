package cn.myh.twesqu.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {

    // 前置过滤
    @Override
    public String filterType() {
        return "pre";
    }

    // 最高优先级0
    @Override
    public int filterOrder() {
        return 0;
    }

    // true表示执行此过滤器
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 获取 head 中的 token 然后转发
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String auth = request.getHeader("Authorization");
        if(auth != null) requestContext.addZuulRequestHeader("Authorization",auth);
        return null;
    }
}
