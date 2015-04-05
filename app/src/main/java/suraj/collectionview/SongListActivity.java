package suraj.collectionview;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.GridLayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class SongListActivity extends ActionBarActivity {

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear);

        for(int i=0;i<2;i++) {

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            TextView textView = new TextView(this);
            textView.setText("Group Name");
            textView.setLayoutParams(layoutParams);
            linearLayout.addView(textView);

            HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            horizontalScrollView.setLayoutParams(layoutParams);

            LinearLayout innerLinearLayout = new LinearLayout(this);
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            innerLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            innerLinearLayout.setLayoutParams(layoutParams);

            CustomGrid customGrid = new CustomGrid(context);

            GridView gridView = new GridView(this);
            gridView.setColumnWidth((int) (200 / getApplicationContext().getResources().getDisplayMetrics().density));
            gridView.setNumColumns(2);
            gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            gridView.setVerticalSpacing((int) (20 / getApplicationContext().getResources().getDisplayMetrics().density));
            gridView.setHorizontalSpacing((int) (20 / getApplicationContext().getResources().getDisplayMetrics().density));
            gridView.setPaddingRelative((int) (20 / getApplicationContext().getResources().getDisplayMetrics().density), 0, 0, 0);
            layoutParams = new ViewGroup.LayoutParams(500, ViewGroup.LayoutParams.WRAP_CONTENT);
            gridView.setLayoutParams(layoutParams);
            gridView.setAdapter(customGrid);

            innerLinearLayout.addView(gridView);
            horizontalScrollView.addView(innerLinearLayout);
            linearLayout.addView(horizontalScrollView);
        }












        /*String[] numbers = new String[] {
                "A", "B", "C", "D", "E",
                "F"};

        TextView textView = (TextView) findViewById(R.id.group);
        textView.setText("Changed Text");



        //LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear);

        //CustomGrid customGrid = new CustomGrid(context);
        GridView gridView = (GridView) findViewById(R.id.gridView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,numbers);

        gridView.setAdapter(adapter);
        //gridView.setNumColumns(2);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, "Titanium", Toast.LENGTH_LONG).show();
            }
        });

        */




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_song_list, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
