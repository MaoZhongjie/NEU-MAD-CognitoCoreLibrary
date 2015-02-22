package edu.neu.madcourse.cognito.core;

import com.amazonaws.regions.Regions;

/**
 * Contract of all Amazon Web Service account, Cognito account and profile data.
 *
 * @author kevin
 *
 */
public class CognitoConfigContract {

	private static final String DEFAULT_DEVELOPER_USER_IDENTIFIER = "domain.developer.madcourse.com";

	private String awsAccountId;
	private String identityPoolId;
	private String unauthRole;
	private String authRole;
	private Regions region;
	private String developerCredentialKey;
	private String devleoperCredentialSecrete;
	private String developerProviderName;
	private String devUserIdentifier;

	/**
	 * [Single User Mode] Constructor for CognitoConfigContract.
	 *
	 * @param awsAccountId
	 *            : Amazon Web Service account ID
	 * @param identityPoolId
	 *            : Identity Pool ID
	 * @param unauthRole
	 *            : unauthentication role for Identity Pool
	 * @param authRole
	 *            : authentication role for Identity Pool
	 * @param region
	 *            : region of Identity Pool
	 * @param developerCredentialKey
	 *            : Developer Credential's Key
	 * @param devleoperCredentialSecrete
	 *            : Developer Credential's Secrete
	 * @param developerProviderName
	 *            : The developer provider name is the "domain" by which Cognito
	 *            will refer to your users; you provided this domain while
	 *            creating/updating the identity pool.
	 * @param devUserIdentifier
	 *            : The developer user identifier is an identifier from your
	 *            backend that uniquely identifies a user.
	 */
	public CognitoConfigContract(String awsAccountId, String identityPoolId,
			String unauthRole, String authRole, Regions region,
			String developerCredentialKey, String devleoperCredentialSecrete,
			String developerProviderName) {
		this(awsAccountId, identityPoolId, unauthRole, authRole, region,
				developerCredentialKey, devleoperCredentialSecrete,
				developerProviderName, DEFAULT_DEVELOPER_USER_IDENTIFIER);
	}

	/**
	 * [Multiple Users Mode] Constructor for CognitoConfigContract.
	 *
	 * @param awsAccountId
	 *            : Amazon Web Service account ID
	 * @param identityPoolId
	 *            : Identity Pool ID
	 * @param unauthRole
	 *            : unauthentication role for Identity Pool
	 * @param authRole
	 *            : authentication role for Identity Pool
	 * @param region
	 *            : region of Identity Pool
	 * @param developerCredentialKey
	 *            : Developer Credential's Key
	 * @param devleoperCredentialSecrete
	 *            : Developer Credential's Secrete
	 * @param developerProviderName
	 *            : The developer provider name is the "domain" by which Cognito
	 *            will refer to your users; you provided this domain while
	 *            creating/updating the identity pool.
	 * @param devUserIdentifier
	 *            : The developer user identifier is an identifier from your
	 *            backend that uniquely identifies a user.
	 */
	public CognitoConfigContract(String awsAccountId, String identityPoolId,
			String unauthRole, String authRole, Regions region,
			String developerCredentialKey, String devleoperCredentialSecrete,
			String developerProviderName, String devUserIdentifier) {
		this.awsAccountId = awsAccountId;
		this.identityPoolId = identityPoolId;
		this.unauthRole = unauthRole;
		this.authRole = authRole;
		this.region = region;
		this.developerCredentialKey = developerCredentialKey;
		this.devleoperCredentialSecrete = devleoperCredentialSecrete;
		this.developerProviderName = developerProviderName;
		this.devUserIdentifier = devUserIdentifier;
	}

	public String getAwsAccountId() {
		return awsAccountId;
	}

	public String getIdentityPoolId() {
		return identityPoolId;
	}

	public String getUnauthRole() {
		return unauthRole;
	}

	public String getAuthRole() {
		return authRole;
	}

	public Regions getRegion() {
		return region;
	}

	public String getDeveloperCredentialKey() {
		return developerCredentialKey;
	}

	public String getDevleoperCredentialSecrete() {
		return devleoperCredentialSecrete;
	}

	public String getDeveloperProviderName() {
		return developerProviderName;
	}

	public String getUserIdentifier() {
		return devUserIdentifier;
	}
}
