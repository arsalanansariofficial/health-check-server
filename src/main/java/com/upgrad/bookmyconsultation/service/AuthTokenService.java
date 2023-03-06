package com.upgrad.bookmyconsultation.service;

import com.upgrad.bookmyconsultation.entity.User;
import com.upgrad.bookmyconsultation.entity.UserAuthToken;
import com.upgrad.bookmyconsultation.exception.AuthorizationFailedException;
import com.upgrad.bookmyconsultation.exception.UserErrorCode;
import com.upgrad.bookmyconsultation.provider.token.JwtTokenProvider;
import com.upgrad.bookmyconsultation.repository.UserAuthTokenRepository;
import com.upgrad.bookmyconsultation.repository.UserRepository;
import com.upgrad.bookmyconsultation.util.DateTimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class AuthTokenService {
	UserRepository userRepository;
	UserAuthTokenRepository userAuthDao;

	@Autowired
	public AuthTokenService(UserRepository userRepository, UserAuthTokenRepository userAuthDao) {
		this.userRepository = userRepository;
		this.userAuthDao = userAuthDao;
	}

	/**
	 * @param user is the user details
	 * @return UserAuthToken
	 * repaired by Arsalan Ansari
	 *
	 * this function is provided with the stub code to set up project
	 * but found a major Bug caused by this method so, I repaired it
	 * with my code. Bug details are given below
	 *
	 * cause - final UserAuthTokenVerifier tokenVerifier = new UserAuthTokenVerifier(userAuthToken);
	 * fetch only one unique access token for a certain user, but it is possible
	 * only until the user logs out and when the user logs out of the application
	 * there is a logout endpoint hit by the frontend which invalidates the
	 * current authentication token of the user and when the user logs in again
	 * second time then all things will work fine as there is only one access token
	 * issued to the user and that is invalidated so the function will provide
	 * new access token to user everything will work fine till now, the problem
	 * comes when the user clicks the login for the third time and this time
	 * there are two rows of access token fetched from the database that will throw
	 * 500 internal server error and the user will not able to log in again in to the application.
	 *
	 * effect - user can not log in more than two times in to the application
	 *
	 * the old stub code is commented down in the functions body
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	public UserAuthToken issueToken(final User user) {
		final ZonedDateTime now = DateTimeProvider.currentProgramTime();
		final List<UserAuthToken> userAuthTokenList = (List<UserAuthToken>) userAuthDao.findAll();
		for (UserAuthToken userAuthToken : userAuthTokenList) {
			if (userAuthToken.getUser().getEmailId().equals(user.getEmailId())) {
				final UserAuthTokenVerifier tokenVerifier = new UserAuthTokenVerifier(userAuthToken);
				if (tokenVerifier.isActive()) {
					return userAuthToken;
				}
			}
		}
		final JwtTokenProvider tokenProvider = new JwtTokenProvider(user.getPassword());
		final ZonedDateTime expiresAt = now.plusHours(8);
		final String authToken = tokenProvider.generateToken(user.getEmailId(), now, expiresAt);
		System.out.println(authToken);
		final UserAuthToken authTokenEntity = new UserAuthToken();
		authTokenEntity.setUser(user);
		authTokenEntity.setAccessToken(authToken);
		authTokenEntity.setLoginAt(now);
		authTokenEntity.setExpiresAt(expiresAt);
		userAuthDao.save(authTokenEntity);
		return authTokenEntity;

		//		final UserAuthToken userAuthToken = userAuthDao.findByUserEmailId(user.getEmailId());
		//		final UserAuthTokenVerifier tokenVerifier = new UserAuthTokenVerifier(userAuthToken);
		//		if (tokenVerifier.isActive()) {
		//			return userAuthToken;
		//		}
		//
		//		final JwtTokenProvider tokenProvider = new JwtTokenProvider(user.getPassword());
		//		final ZonedDateTime expiresAt = now.plusHours(8);
		//		final String authToken = tokenProvider.generateToken(user.getEmailId(), now, expiresAt);
		//		System.out.println(authToken);
		//		final UserAuthToken authTokenEntity = new UserAuthToken();
		//		authTokenEntity.setUser(user);
		//		authTokenEntity.setAccessToken(authToken);
		//		authTokenEntity.setLoginAt(now);
		//		authTokenEntity.setExpiresAt(expiresAt);
		//		userAuthDao.save(authTokenEntity);
		//
		//		return authTokenEntity;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void invalidateToken(final String accessToken) throws AuthorizationFailedException {

		final UserAuthToken userAuthToken = userAuthDao.findByAccessToken(accessToken);
		final UserAuthTokenVerifier tokenVerifier = new UserAuthTokenVerifier(userAuthToken);
		if (tokenVerifier.isNotFound()) {
			throw new AuthorizationFailedException(UserErrorCode.USR_005);
		}
		if (tokenVerifier.hasExpired()) {
			throw new AuthorizationFailedException(UserErrorCode.USR_006);
		}

		userAuthToken.setLogoutAt(DateTimeProvider.currentProgramTime());
		userAuthDao.save(userAuthToken);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public UserAuthToken validateToken(@NotNull String accessToken) throws AuthorizationFailedException {
		final UserAuthToken userAuthToken = userAuthDao.findByAccessToken(accessToken);
		final UserAuthTokenVerifier tokenVerifier = new UserAuthTokenVerifier(userAuthToken);
		if (tokenVerifier.isNotFound() || tokenVerifier.hasLoggedOut()) {
			throw new AuthorizationFailedException(UserErrorCode.USR_005);
		}
		if (tokenVerifier.hasExpired()) {
			throw new AuthorizationFailedException(UserErrorCode.USR_006);
		}
		return userAuthToken;
	}
}