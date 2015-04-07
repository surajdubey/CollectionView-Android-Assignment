package suraj.collectionview;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class SongListActivity extends ActionBarActivity {

    final Context context = this;

    private Spinner sortSpinner;
    private Spinner rowNumberSpinner;
    GridView gridView[] = new GridView[15];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        final String sortType[] = {"Artist", "Album"};
        final String rowNumber[] = {"1","2","3","4","5"};

        setContentView(R.layout.activity_song_list);

        ArrayAdapter<String> sortAdapter =  new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, sortType);
        ArrayAdapter<String> rowNumberAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, rowNumber);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.custom_actionbar);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        sortSpinner = (Spinner) findViewById(R.id.sortSpinner);
        rowNumberSpinner = (Spinner) findViewById(R.id.rowNumberSpinner);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear);


        sortSpinner.setAdapter(sortAdapter);
        rowNumberSpinner.setAdapter(rowNumberAdapter);

        rowNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int itemPosition, long l) {
                for(int i=0;i<15;i++) {
                    gridView[i].setNumColumns(Integer.parseInt(rowNumber[itemPosition]));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        //actionBar.setBackgroundDrawable(new ColorDrawable(0xff00DDED));
        //Enabling dropdown list for ActionBar
      /*  actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);




        //for number of rows displayed on right hand side
        /*final String rowNumber[] = {"1","2","3","4","5"};

        //ArrayAdapter to populate dropdown list
        ArrayAdapter<String> rowNumberAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, rowNumber);
        //ArrayAdapter<String> rowNumberAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,)



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
        //actionBar.setListNavigationCallbacks(rowNumberAdapter, onNavigationListener);

        // String sortValue[] = {"Artist", "Album"};



        //spinneradapter

        //SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.actions,android.R.layout.simple_spinner_dropdown_item);
        //actionBar.setListNavigationCallbacks(rowNumberAdapter, onNavigationListener);

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

            int paddingSpace = (int) (20 / getApplicationContext().getResources().getDisplayMetrics().density);
            gridView[i].setPaddingRelative(paddingSpace, paddingSpace, paddingSpace, paddingSpace);
            layoutParams = new ViewGroup.LayoutParams(1000, ViewGroup.LayoutParams.MATCH_PARENT);
            gridView[i].setLayoutParams(layoutParams);
            gridView[i].setAdapter(customGrid);

            innerLinearLayout.addView(gridView[i]);
            horizontalScrollView.addView(innerLinearLayout);
            linearLayout.addView(horizontalScrollView);
        }

        gridView[0].setNumColumns(3);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_song_list, menu);

        /*MenuItem sortMenuSpinner = menu.findItem(R.id.menu_sort_type);

        Log.d("song","1");

        //sortMenuSpinner.setVisible( getSupportActionBar().getNavigationMode() == ActionBar.NAVIGATION_MODE_LIST );
        //Log.d("song",R.array.actions);

        //setting menu

        View view = sortMenuSpinner.getActionView();
        if(view == null)
            Log.d("song","null view");
        if(view instanceof Spinner)
        {

            Spinner spinner = (Spinner) view;
            spinner.setAdapter(ArrayAdapter.createFromResource(this, R.array.actions, android.R.layout.simple_spinner_dropdown_item));
            Log.d("song","5");

        }*/

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
