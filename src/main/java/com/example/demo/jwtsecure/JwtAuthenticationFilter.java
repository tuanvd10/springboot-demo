package com.example.demo.jwtsecure;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (isByPassRequest(request)) {
			System.err.printf("url by pass: %s \n", request.getServletPath());
			filterChain.doFilter(request, response);
			return;
		}
		try {
			// Lấy jwt từ request
			String jwt = getJwtFromRequest(request);
			System.err.printf("jwt token: %s \n", jwt);
			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				// Lấy id user từ chuỗi jwt
				Long userId = tokenProvider.getUserIdFromJWT(jwt);
				System.out.println("User id: " + userId);
				// Lấy thông tin người dùng từ id
				UserDetails userDetails = userService.loadUserById(userId);
				if (userDetails != null) {
					System.out.printf("user name request: %s and role %s \n", userDetails.getUsername(),
							userDetails.getAuthorities());
					// Nếu người dùng hợp lệ, set thông tin cho Seturity Context
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (Exception ex) {
			System.err.println("failed on set user authentication");
			ex.printStackTrace();
		}
		filterChain.doFilter(request, response);
	}

	private boolean isByPassRequest(HttpServletRequest request) {
		final List<Pair<String, String>> listByPassRequest = Arrays.asList(Pair.of("", ""));
		return false;
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		// Kiểm tra xem header Authorization có chứa thông tin jwt không
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
