package easyrent.iiitd.com.easyrent_v1;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by lenovo on 10/21/2016.
 */
public class HintAdapter extends ArrayAdapter<String>{

    public HintAdapter(Context theContext, List<String> objects, int theLayoutResId) {
        super(theContext, theLayoutResId, objects);
    }

    @Override
    public int getCount() {
        int c=super.getCount();
        if(c>0)
            return c-1;
        else
            return c;

    }
}
