package org.apache.cordova.plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.apache.cordova.plugin.AndroidProgressHUD;
import android.content.DialogInterface;

public class ActivityIndicator extends CordovaPlugin {

	private AndroidProgressHUD activityIndicator = null;
	private String text = null;
	private boolean cancelable = false;

	@Override
	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
		if (action.equals("show")) {
			final String text = args.getString(0);
			final boolean isFixed = args.getBoolean(1);
			final DialogInterface.OnCancelListener onCancelListener = new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					if (!isFixed) {
						while (activityIndicator != null) {
							activityIndicator.dismiss();
							callbackContext.success();
						}
					}
				}
			};
			// show(text, isFixed, onCancelListener);
			cordova.getActivity().runOnUiThread(new Runnable() {
				public void run() {
					if (activityIndicator != null) {
					activityIndicator.dismiss();
					activityIndicator = null;
				}
					if(isFixed) {
						activityIndicator = AndroidProgressHUD.show(ActivityIndicator.this.cordova.getActivity(), text, true, false, null, callbackContext);
				}	else {
					activityIndicator = AndroidProgressHUD.show(ActivityIndicator.this.cordova.getActivity(), text, true, true, onCancelListener, callbackContext);
				}
				}
			});
			// callbackContext.success();
			return true;
		} else if (action.equals("hide")) {
			hide();
			callbackContext.success();
			return true;
		}

		return false;
	}

	/**
	 * This show the ProgressDialog
	 * @param text - Message to display in the Progress Dialog
	 */
	public void show(String text, boolean cancelable) {
		this.text = text;
		this.cancelable = cancelable;


	}

	/**
	 * This hide the ProgressDialog
	 */
	public void hide() {
		if (activityIndicator != null) {
			activityIndicator.dismiss();
			activityIndicator = null;
		}
	}
}
