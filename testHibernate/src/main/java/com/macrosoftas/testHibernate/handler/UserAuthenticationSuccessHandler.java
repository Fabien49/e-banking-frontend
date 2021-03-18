package com.macrosoftas.testHibernate.handler;

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

        System.out.println("La liste des rôle est : " + roles.toString());

        if (roles.contains("ADMIN")) {
            System.out.println("Redirection vers la page de membre");
            httpServletResponse.sendRedirect("/membre");


        } else if (roles.contains("ROLE_UTILISATEURCONNECTE")) {
            System.out.println("Redirection vers la page des utilisateurs connecté");
            httpServletResponse.sendRedirect("/utilisateurCo");

        } else {
            System.out.println("Rôle inconnu");
            httpServletResponse.sendRedirect("/");
            httpServletResponse.sendRedirect("/sites");
            httpServletResponse.sendRedirect("/login");
        }
    }
}
