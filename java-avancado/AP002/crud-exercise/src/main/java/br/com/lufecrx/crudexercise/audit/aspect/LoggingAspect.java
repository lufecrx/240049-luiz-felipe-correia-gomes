package br.com.lufecrx.crudexercise.audit.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.lufecrx.crudexercise.audit.service.AuditService;
import br.com.lufecrx.crudexercise.auth.model.User;

@Aspect
@Component
public class LoggingAspect {

    @Autowired
    private AuditService auditService;

    private static ThreadLocal<String> clientIpAddress = new ThreadLocal<>();

    public static void setClientIpAddress(String ipAddress) {
        clientIpAddress.set(ipAddress);
    }

    public static void clearClientIpAddress() {
        clientIpAddress.remove();
    }

    // Exclui AuditService do observado para evitar loop infinito
    @Before("execution(* br.com.lufecrx.crudexercise.audit.service.AuditService.*(..))")
    public void logServiceAccess(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String description = "Method execution";
        String userId = null;
        String origin = clientIpAddress.get();

       // Recupera informações do usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            userId = user.getUsername();
        }else{
           userId = "unauthenticated user";
        }
 
        auditService.logEvent(methodName, description, userId, joinPoint.getTarget().getClass().getSimpleName(), origin);
    }
}