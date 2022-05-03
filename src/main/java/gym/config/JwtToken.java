package gym.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtToken
{
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expire}")
	private long expire;
	
	
}
