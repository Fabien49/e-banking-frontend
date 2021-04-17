
package com.fabienIT.escaladefriendsp6ocr.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        httpServletResponse.sendRedirect("/");

        System.out.println("La liste des rôle est : " + roles.toString());

        /* if (roles.contains("ADMIN")) {
            System.out.println("Redirection vers la page des admins");
            httpServletResponse.sendRedirect("/");

        } else if (roles.contains("MEMBRE")) {
            System.out.println("Redirection vers la page des membres");
            httpServletResponse.sendRedirect("/");

        } else if (roles.contains("UTILISATEURCONNECTE")) {
            System.out.println("Redirection vers la page des utilisateurs connecté");
            httpServletResponse.sendRedirect("/");

        } else {
            System.out.println("Rôle inconnu");
           */
/* httpServletResponse.sendRedirect("/");
            httpServletResponse.sendRedirect("/visiteur/sites");
            httpServletResponse.sendRedirect("/home");
            httpServletResponse.sendRedirect("/inscription");
            httpServletResponse.sendRedirect("/login");
            httpServletResponse.sendRedirect("/results");
            httpServletResponse.sendRedirect("/sitePageEscalade");
            httpServletResponse.sendRedirect("/topoForm");
            httpServletResponse.sendRedirect("/topoListe");
            httpServletResponse.sendRedirect("/pageEscalade/(id=${s.id})");*//*

        }
    }
}
*/
    }
}