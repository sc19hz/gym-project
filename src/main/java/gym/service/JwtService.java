package gym.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

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
	
	public Integer getUserId(HttpServletRequest request)
	{
		// Jwt parse function will check expire time for us!
		return Integer.parseInt(this.getClaims(request).getAudience());
	}
	
	public Claims getClaims(HttpServletRequest request) {
		return(
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(
				request.getHeader("Authorization").substring("Bearer ".length())
			).getBody()
		);
	}
}
