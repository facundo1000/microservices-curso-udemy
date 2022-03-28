package com.demo.springbootservicezuulserver.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class PreTimeElapsedFilter extends ZuulFilter {

  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() throws ZuulException {

    RequestContext context = RequestContext.getCurrentContext();
    HttpServletRequest request = context.getRequest();
    log.info(
        String.format(
            "%s request routing to %s", request.getMethod(), request.getRequestURL().toString()));
    Long initTime = System.currentTimeMillis();
    request.setAttribute("Initial Time", initTime);
    return null;
  }
}
