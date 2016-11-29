package easyrent.iiitd.com.easyrent_v1;


import android.content.Intent;
        import android.database.Cursor;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentActivity;
        import android.support.v4.app.FragmentManager;
        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.UUID;


public abstract class ListingActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();
    private final int REQ=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmentcon);

        FragmentManager fm=getSupportFragmentManager();
        Fragment frag=fm.findFragmentById(R.id.fragment_container);//get the fragment

        if(frag==null){
            frag=createFragment();
            fm.beginTransaction().add(R.id.fragment_container,frag).commit();

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this,"you hit settings",Toast.LENGTH_SHORT).show();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    /*@Override
    protected void onDestroy() { //needs to perform close up operations
        super.onDestroy();
        TaskHub th=TaskHub.get(this);
        ArrayList<Task> tasks=th.getTasks();
        DatabaseHelper db=new DatabaseHelper(this);

        for(Task t:tasks){

            Cursor c=db.retrieveRec(t.getId().toString());
            c.moveToFirst();
            if(c.getCount()>0) {
                //continue;//update them also
                int stat=0;
                if(t.isStatus())stat=1;
                boolean s=   db.update(t.getId().toString(),t.getTitle(),t.getDescription(),t.getDoc(),stat);
                Log.d("Inside destroy: ",String.valueOf(s));
            }
            else    //inserts ony newly added tasks in db
            {
                int status;if(t.isStatus())status=1;else status=0;
                db.insert(t.getId().toString(),t.getTitle(),t.getDescription(),t.getDoc(),status);
            }
        }}*/

}
