package com.demo.springbootservicezuulserver.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class PostTimeElapsedFilter extends ZuulFilter {

  @Override
  public String filterType() {
    return "post";
  }

  @Override
  public int filterOrder() {
    return 2;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() throws ZuulException {

    RequestContext context = RequestContext.getCurrentContext();
    HttpServletRequest request = context.getRequest();
    log.info("Getting to post filter");
    Long initTime = (Long) request.getAttribute("Initial Time");
    Long finalTime = System.currentTimeMillis();
    Long timePast = finalTime - initTime;
    log.info(String.format("Elapsed time in seconds %s seg.",timePast.doubleValue()/1000.00));
    log.info(String.format("Elapsed time in miliseconds %s ms.",timePast));
    return null;
  }
}
