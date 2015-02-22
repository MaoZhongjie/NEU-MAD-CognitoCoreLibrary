package edu.neu.madcourse.cognito.core;

import java.util.HashMap;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClient;
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenForDeveloperIdentityRequest;
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenForDeveloperIdentityResult;

/**
 * Generate Cognito authentication token in developer mode
 *
 * Created by kevin on 2/8/15.
 */
/* Package */class DevAuthTokenGenerator {

	/** Token Expiration period: ONE DAY 24h * 60min * 60sec */
	private static final long DEFAULT_TOKEN_EXP = 24 * 60 * 60l;

	/* Package */static DevAuthToken generateToken(
			CognitoConfigContract contract) {

		// create credentials
		BasicAWSCredentials credentials = new BasicAWSCredentials(
				contract.getDeveloperCredentialKey(),
				contract.getDevleoperCredentialSecrete());
		// create cognito identity client
		AmazonCognitoIdentityClient identityClient = new AmazonCognitoIdentityClient(
				credentials);
		// build identity request
		GetOpenIdTokenForDeveloperIdentityRequest idRequest = new GetOpenIdTokenForDeveloperIdentityRequest();
		// set identity pool
		idRequest.setIdentityPoolId(contract.getIdentityPoolId());
		// set token's expiration time to 30 min.
		idRequest.setTokenDuration(DEFAULT_TOKEN_EXP);

		// set developer provider name and our developer domain as logins;
		// we will generate a token for developer under developer domain,
		// just like we generate a token for user under Google+ domain, facebook
		// domain, etc.
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(contract.getDeveloperProviderName(),
				contract.getUserIdentifier());
		idRequest.setLogins(map);

		try {
			// request for token and identity id. Contains network operation
			GetOpenIdTokenForDeveloperIdentityResult idResp = identityClient
					.getOpenIdTokenForDeveloperIdentity(idRequest);
			return new DevAuthToken(idResp.getIdentityId(), idResp.getToken());
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Contract for holding IdentityId and Token information.
	 *
	 * @author kevin
	 *
	 */
	/* Package */static class DevAuthToken {
		private String identityId = null;
		private String token = null;

		public DevAuthToken(String identityId, String token) {
			this.identityId = identityId;
			this.token = token;
		}

		public String getToken() {
			return token;
		}

		public String getIdentityId() {
			return identityId;
		}
	}
}
