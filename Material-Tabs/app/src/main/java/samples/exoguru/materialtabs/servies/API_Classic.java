package samples.exoguru.materialtabs.servies;


import retrofit.http.GET;
import rx.Observable;
import samples.exoguru.materialtabs.handler.Constants;
import samples.exoguru.materialtabs.models.CategoryResult;

/**
 * Created by Windows on 01/04/2016.
 */
public interface API_Classic {
    @GET(Constants.CLASSIC_URL)
    Observable<CategoryResult> getClassic();
}
