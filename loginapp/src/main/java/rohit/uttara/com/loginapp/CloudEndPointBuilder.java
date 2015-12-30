package rohit.uttara.com.loginapp;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.rohit.uttara.backend.loginBeanApi.LoginBeanApi;

import javax.annotation.Nonnull;

/**
 * Created by rohit on 30/12/15.
 */
public class CloudEndPointBuilder {
    public CloudEndPointBuilder(){

    }
    @Nonnull
    public static LoginBeanApi getEndPoint(){
        LoginBeanApi.Builder builder = new LoginBeanApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null)
                .setApplicationName("LoginApp");
        return builder.build();
    }
}
