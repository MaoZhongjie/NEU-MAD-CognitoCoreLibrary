package edu.neu.madcourse.cognito.core;

import java.util.HashMap;

import android.content.Context;
import android.os.AsyncTask;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognito.CognitoSyncManager;
import com.amazonaws.mobileconnectors.cognito.Dataset;

import edu.neu.madcourse.cognito.core.DevAuthTokenGenerator.DevAuthToken;

/**
 * Created by kevin on 2/8/15.
 */
public class CognitoDataSetProvider {

	/**
	 * CognitoDataSetProvider Initialization result callback
	 *
	 * @author kevin
	 *
	 */
	public interface OnProviderInitResultCallback {
		public void onSuccess();

		public void onFailure(String errorMessage);
	}

	private static final Object lock = new Object();
	private CognitoSyncManager clientInstance = null;
	private static CognitoDataSetProvider instance = null;

	public static void initialize(Context context,
			CognitoConfigContract contract,
			OnProviderInitResultCallback callback) {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					buildProvider(context, contract, callback);
				}
			}
		}
	}

	public static CognitoDataSetProvider getInstance() {
		return instance;
	}

	public Dataset getDataSet(Context context, String datasetName) {
		return clientInstance == null ? null : clientInstance
				.openOrCreateDataset(datasetName);
	}

	private static void buildProvider(Context context,
			CognitoConfigContract contract,
			OnProviderInitResultCallback callback) {
		CognitoDeveloperLogin loginTask = new CognitoDeveloperLogin(context,
				contract, callback);
		loginTask.execute(new CognitoConfigContract[] { contract });
	}

	/* Package */CognitoDataSetProvider(Context context,
			CognitoConfigContract contract, DevAuthToken token) {
		clientInstance = new CognitoSyncManager(context, contract.getRegion(),
				getCredentialProvider(context, contract, token));
	}

	private CognitoCachingCredentialsProvider getCredentialProvider(
			Context context, CognitoConfigContract contract,
			DevAuthToken devAuthToken) {
		ExampleIdentityProvider cognitoIdentityProvider = new ExampleIdentityProvider(
				contract);
		CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
				context, cognitoIdentityProvider, contract.getUnauthRole(),
				contract.getAuthRole());
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(contract.getUserIdentifier(), devAuthToken.getToken());
		credentialsProvider.setLogins(map);
		return credentialsProvider;
	}

	/* Package */static class CognitoDeveloperLogin extends
			AsyncTask<CognitoConfigContract, Void, DevAuthToken> {

		private static final String LOGIN_FAILURE_MSG = "Failed to login into Cognito Identity Pool in developer mode";
		private Context context;
		private CognitoConfigContract contract;
		private OnProviderInitResultCallback onProviderInitResultCallback;

		/* Package */CognitoDeveloperLogin(Context context,
				CognitoConfigContract contract,
				OnProviderInitResultCallback onLoginResultCallback) {
			this.context = context;
			this.contract = contract;
			this.onProviderInitResultCallback = onLoginResultCallback;
		}

		@Override
		protected DevAuthTokenGenerator.DevAuthToken doInBackground(
				CognitoConfigContract... contracts) {
			return DevAuthTokenGenerator.generateToken(contracts[0]);
		}

		@Override
		protected void onPostExecute(DevAuthToken token) {
			if (token == null)
				onProviderInitResultCallback.onFailure(LOGIN_FAILURE_MSG);
			else {
				CognitoDataSetProvider.instance = new CognitoDataSetProvider(
						context, contract, token);
				onProviderInitResultCallback.onSuccess();
			}
		}
	}
}
