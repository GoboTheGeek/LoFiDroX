package ch.gobothegeek.lofidrox.security;

import org.apache.deltaspike.security.api.authorization.SecurityBindingType;

import javax.inject.Qualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(value = RUNTIME)
@Target( { TYPE, METHOD, PARAMETER, FIELD } )
@Documented
@Qualifier
public @interface LfdUserLogged { }
