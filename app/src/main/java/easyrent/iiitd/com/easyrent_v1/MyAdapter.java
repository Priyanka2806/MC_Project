
package easyrent.iiitd.com.easyrent_v1;

        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import java.util.List;

        import easyrent.iiitd.com.easyrent_v1.SharingList;

/**
 * Created by riya on 5/11/16.
 */


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<SharingList> sharingList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, details;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.name);
            details = (TextView) view.findViewById(R.id.details);
        }
    }


    public MyAdapter(List<SharingList> sharingList) {
        this.sharingList = sharingList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sharing_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SharingList item = sharingList.get(position);
        holder.title.setText(item.getName());
        holder.details.setText(item.getDetails());
    }

    @Override
    public int getItemCount() {
        return sharingList.size();
    }
}