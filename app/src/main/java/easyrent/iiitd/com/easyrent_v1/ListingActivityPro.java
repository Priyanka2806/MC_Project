package easyrent.iiitd.com.easyrent_v1;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.View;

public class ListingActivityPro extends ListingActivity {
    @Override
    protected Fragment createFragment() {
        return new ListingFragment();
    }
}
