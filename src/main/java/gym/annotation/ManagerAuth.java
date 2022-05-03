package gym.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@RequireAuth
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ManagerAuth
{
	
}
