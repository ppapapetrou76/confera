package com.softconf.confera.security;

import com.softconf.confera.identity.users.User;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

public final class AuthTokenUtil {

  private static final String MAGIC_KEY = "softconf.events.api.magickey";

  private static final String TOKEN_KEY = "X-Auth-Token";

  private static final String SPLITTER = ":";

  private AuthTokenUtil() {
    // To avoid creation of new objects
  }

  public static String createToken(User user) {
    /* Expires in one month */
    LocalDateTime nowPlusOneMonth = LocalDateTime.now().plusMonths(1);
    ZonedDateTime zdt = nowPlusOneMonth.atZone(ZoneId.systemDefault());
    long expires = zdt.toInstant().toEpochMilli();
    return createToken(user, expires);
  }

  public static void addTokenToHttpResponse(String token, HttpServletResponse response) {
    response.addHeader(TOKEN_KEY, token);
    response.addCookie(new Cookie(TOKEN_KEY, token));
  }

  public static String extractAuthTokenFromRequest(HttpServletRequest httpRequest) {
    String authToken = httpRequest.getHeader(TOKEN_KEY);
    if (authToken == null && httpRequest.getCookies() != null) {
      for (Cookie cookie : httpRequest.getCookies()) {
        if (StringUtils.equals(TOKEN_KEY, cookie.getName())) {
          authToken = cookie.getValue();
          break;
        }
      }
    }
    if (authToken == null) {
      authToken = httpRequest.getParameter("token");
    }
    return authToken;
  }

  private static String createToken(User user, long expires) {
    StringBuilder tokenBuilder = new StringBuilder();
    tokenBuilder.append(user.getId());
    tokenBuilder.append(SPLITTER);
    tokenBuilder.append(expires);
    tokenBuilder.append(SPLITTER);
    tokenBuilder.append(AuthTokenUtil.computeSignature(user, expires));

    return tokenBuilder.toString();
  }

  public static String computeSignature(User user, long expires) {
    StringBuilder signatureBuilder = new StringBuilder();
    signatureBuilder.append(user.getEmail());
    signatureBuilder.append(SPLITTER);
    signatureBuilder.append(expires);
    signatureBuilder.append(SPLITTER);
    signatureBuilder.append(user.getPassword());
    signatureBuilder.append(SPLITTER);
    signatureBuilder.append(AuthTokenUtil.MAGIC_KEY);

    return Hash.getMD5(signatureBuilder.toString());
  }

  public static String getUserIdFromToken(String authToken) {
    if (null == authToken) {
      return null;
    }

    String[] parts = authToken.split(SPLITTER);
    return parts[0];
  }

  public static boolean validateToken(String authToken, User user) {
    String[] parts = authToken.split(SPLITTER);
    long expires = Long.parseLong(parts[1]);
    String signature = parts[2];

    if (expires < System.currentTimeMillis()) {
      return false;
    }

    return StringUtils.equals(signature, AuthTokenUtil.computeSignature(user, expires));
  }
}
