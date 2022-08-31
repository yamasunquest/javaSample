package top.sunquest.temp2project.filter;


import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class CookiesFilter extends ZuulFilter {

	@Override
	public String filterType() {
		return PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 101;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();

		//ctx.addZuulRequestHeader("Cookie", "h1=valueForh1");

		HttpServletRequest request = ctx.getRequest();

		if(request.getCookies() == null){
			return  null;
		}

		for(Cookie cookie : request.getCookies()) {
			System.out.println("domain:" + cookie.getDomain());
			System.out.println("path:" + cookie.getPath());
			System.out.println("cookie:" + cookie.getName() + "=" + cookie.getValue());
			System.out.println("==========================================================");
		}

		Cookie ck = new Cookie("Cookie","h1=valueForh1");
		ck.setDomain("localhost");
		ck.setPath("/");
		ck.setMaxAge(100000);
		//response.addCookie(ck);

		return null;
	}
}
