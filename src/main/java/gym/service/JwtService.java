package gym.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService
{
	@Value("${jwt.secret}")
	private String secret;
	
	/**
	 * Expire time in mins
	 */
	@Value("${jwt.expire}")
	private long expire;
	
	public String genToken(Integer userId)
	{
		return Jwts.builder()
			.setAudience(userId.toString())
			.setExpiration(new Date(System.currentTimeMillis() + this.expire * 1000 * 60))
			.signWith(SignatureAlgorithm.HS256, this.secret)
			.compact();
	}
	
	/**
	 * @param token Token to validate
	 * @return User id saved in payload if this token is valid and not expired
	 */
	public Integer validateToken(String token)
	{
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return(
			claims.getExpiration().getTime() > System.currentTimeMillis()
			? Integer.parseInt(claims.getAudience())
			: null
		);
	}
}
