package edu.neu.madcourse.cognito.core;

import com.amazonaws.auth.AWSAbstractCognitoDeveloperIdentityProvider;

/**
 * Created by kevin on 2/8/15.
 */

/* Package */class ExampleIdentityProvider extends
		AWSAbstractCognitoDeveloperIdentityProvider {
	private CognitoConfigContract cognitoConfigContract = null;

	/* Package */ExampleIdentityProvider(CognitoConfigContract contract) {
		super(contract.getAwsAccountId(), contract.getIdentityPoolId(),
				contract.getRegion());
		cognitoConfigContract = contract;
	}

	@Override
	public String getProviderName() {
		return cognitoConfigContract.getDeveloperProviderName();
	}

	@Override
	public String refresh() {
		DevAuthTokenGenerator.DevAuthToken devAuthToken = DevAuthTokenGenerator
				.generateToken(cognitoConfigContract);
		update(devAuthToken.getIdentityId(), devAuthToken.getToken());
		return token;
	}
}
