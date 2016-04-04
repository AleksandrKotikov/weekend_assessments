package samples.exoguru.materialtabs.servies;

import retrofit.RestAdapter;
import samples.exoguru.materialtabs.handler.Constants;

/**
 * Created by Windows on 04/04/2016.
 */
public class API_Services {

    public static API_Classic createClassicAPI() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL);
        return builder.build().create(API_Classic.class);
    }

    public static API_POP createPOP_API() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL);
        return builder.build().create(API_POP.class);
    }

    public static API_Rock createRockAPI() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL);
        return builder.build().create(API_Rock.class);
    }
}
