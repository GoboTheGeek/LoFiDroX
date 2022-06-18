package ch.gobothegeek.lofidrox.web;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import ch.gobothegeek.lofidrox.controllers.FileController;
import ch.gobothegeek.lofidrox.controllers.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// base url for all CRUD methods
@ApplicationPath("/crud")
public class LoFiDroXApplication extends Application {
	private final Logger logger = LoggerFactory.getLogger(LoFiDroXApplication.class);

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(UserController.class);
        classes.add(FileController.class);
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<>();
        return singletons;
    }
}