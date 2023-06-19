package org.foo.modules.loginprovider;
import org.jahia.params.valves.LoginUrlProvider;
import org.jahia.params.valves.LogoutUrlProvider;
import org.osgi.service.component.annotations.Component;

import javax.servlet.http.HttpServletRequest;

@Component(immediate = true)
public class CustomLoginProvider implements LoginUrlProvider, LogoutUrlProvider {
    public String getLoginUrl(HttpServletRequest request) {
        return request.getContextPath() + "/modules/login-provider/html/login.html?";
    }

    public boolean hasCustomLoginUrl() {
        return true;
    }

    public String getLogoutUrl(HttpServletRequest request){
        return request.getContextPath() + "/modules/login-provider/html/logout.html?";
    }

    public boolean hasCustomLogoutUrl(){
        return true;
    }
}
