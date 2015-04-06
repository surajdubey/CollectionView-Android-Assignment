package suraj.collectionview;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_song_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff00DDED));
        //Enabling dropdown list for ActionBar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear);

        final GridView gridView[] = new GridView[15];

        //for number of rows displayed on right hand side
        final String rowNumber[] = {"1","2","3","4","5"};

        //ArrayAdapter to populate dropdown list
        ArrayAdapter<String> rowNumberAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, rowNumber);


        //defining navigation listener
        ActionBar.OnNavigationListener onNavigationListener = new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                Toast.makeText(context, "Selected : "+rowNumber[itemPosition], Toast.LENGTH_SHORT).show();

                for(int i=0;i<15;i++)
                {
                    gridView[i].setNumColumns(Integer.parseInt(rowNumber[itemPosition]));
                }
                return false;
            }
        };

        /** Setting dropdown items and item navigation listener for the actionbar */
        actionBar.setListNavigationCallbacks(rowNumberAdapter, onNavigationListener);



        for(int i=0;i<15;i++) {

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

            gridView[i] = new GridView(this);
            gridView[i].setColumnWidth((int) (200 / getApplicationContext().getResources().getDisplayMetrics().density));
            gridView[i].setNumColumns(2);
            gridView[i].setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            gridView[i].setVerticalSpacing((int) (20 / getApplicationContext().getResources().getDisplayMetrics().density));
            gridView[i].setHorizontalSpacing((int) (20 / getApplicationContext().getResources().getDisplayMetrics().density));
            gridView[i].setPaddingRelative((int) (20 / getApplicationContext().getResources().getDisplayMetrics().density), 0, 0, 0);
            layoutParams = new ViewGroup.LayoutParams(500, ViewGroup.LayoutParams.WRAP_CONTENT);
            gridView[i].setLayoutParams(layoutParams);
            gridView[i].setAdapter(customGrid);

            innerLinearLayout.addView(gridView[i]);
            horizontalScrollView.addView(innerLinearLayout);
            linearLayout.addView(horizontalScrollView);
        }

        gridView[0].setNumColumns(3);












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
        /*if (id == R.id.action_settings) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }
}
