package samples.exoguru.materialtabs.servies;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import rx.Observable;
import samples.exoguru.materialtabs.handler.Constants;
import samples.exoguru.materialtabs.models.CategoryResult;
import samples.exoguru.materialtabs.models.Result;
import samples.exoguru.materialtabs.models.SongStyleModel;

/**
 * Created by Windows on 01/04/2016.
 */
public interface API_POP {
    @GET(Constants.POP_URL)
    Observable<CategoryResult> getPop();
}
