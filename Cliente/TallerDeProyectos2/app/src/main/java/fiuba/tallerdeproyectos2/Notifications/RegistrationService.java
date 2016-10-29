package fiuba.tallerdeproyectos2.Notifications;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import fiuba.tallerdeproyectos2.R;

public class RegistrationService extends IntentService {

    public static final String token = "eHBDTZSKuiY:APA91bE4TOv-LShyTQ2aczeU-oMScY2v7-I_YNB2iphuOROx-y1Kkbwey90xw52f5pk29-ipPDW5DaXsFL5pVW0Q0-QcHKBCDzSKyhz2vXoncu0C5ffVPvUkQzslkRtQMJNVvONa2_xh";

    public RegistrationService() {
        super("RegistrationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID myID = InstanceID.getInstance(this);
        String registrationToken = null;
        try {
            registrationToken = myID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.d("Registration Token", registrationToken);
            GcmPubSub subscription = GcmPubSub.getInstance(this);
            subscription.subscribe(registrationToken, "/topics/test", null);
            subscription.subscribe(registrationToken, "/topics/course_2_1", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
