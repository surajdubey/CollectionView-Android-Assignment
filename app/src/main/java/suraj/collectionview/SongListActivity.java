package suraj.collectionview;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.AssetManager;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class SongListActivity extends ActionBarActivity {

    final Context context = this;

    private Spinner sortSpinner;
    private Spinner rowNumberSpinner;
    GridView gridView[] = new GridView[15];

    int maxValue = 37;


    /** to store song name **/
    String songName[] = new String[maxValue];
    String artistName[] = new String[maxValue];
    String albumName[] = new String[maxValue];

    String uniqueArtistName[] = new String[maxValue];
    String uniqueAlbumName[] = new String[maxValue];

    ArrayList<String> songAlbumList[] = new ArrayList[maxValue];
    ArrayList<String> songArtistList[] = new ArrayList[maxValue];

    /** Counter **/
    int uniqueAlbumCounter = 0;
    int uniqueArtistCounter = 0;

    String selectedSortType = "";

    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String sortType[] = {"Artist", "Album"};
        final String rowNumber[] = {"1","2","3","4","5"};



        /*
        final ArrayList<String> sortType = new ArrayList<>();
        sortType.add("Artist");
        sortType.add("Album");
        */

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
        linearLayout = (LinearLayout) findViewById(R.id.linear);

        sortSpinner.setAdapter(sortAdapter);
        rowNumberSpinner.setAdapter(rowNumberAdapter);

        rowNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int itemPosition, long l) {
                /*for(int i=0;i<15;i++) {
                    gridView[i].setNumColumns(Integer.parseInt(rowNumber[itemPosition]));
                }*/
                int rowSelected = Integer.parseInt(rowNumber[itemPosition]);

                if(selectedSortType.equals(sortType[0]))
                {
                    for(int i=0;i<uniqueArtistCounter;i++)
                    {
                        int songSize = songArtistList[i].size();
                        int numCol = songSize/rowSelected;
                        if(songSize%rowSelected != 0)
                            numCol++;
                        gridView[i].setNumColumns(numCol);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int itemPosition, long l) {

                selectedSortType = sortType[itemPosition];
                if(itemPosition == 0)
                {

                    uniqueArtistCounter = 0;
                    // artist is selected
                    for(int i=0;i<maxValue;i++)
                    {
                        int artistPosition = findArtist(artistName[i],uniqueArtistCounter);

                        Log.d("debugging", artistName[i]+" "+i+" : Answer = "+artistPosition);

                        /** No previous arraylist **/
                        if(artistPosition == -1)
                        {
                            uniqueArtistName[uniqueArtistCounter] = artistName[i];
                            songArtistList[uniqueArtistCounter] = new ArrayList<String>();
                            songArtistList[uniqueArtistCounter].add(songName[i]);
                            uniqueArtistCounter++;
                        }
                        else
                        {
                            songArtistList[artistPosition].add(songName[i]);
                        }


                    }

                    setUpGrid();



                    Log.d("debugging", String.valueOf(uniqueArtistCounter));

                }
                //Toast.makeText(context, sortType[itemPosition], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //reading file

        AssetManager assetManager = getAssets();
        BufferedReader bufferedReader = null;
        String lineString = "";
        try{
            /** pass is used for while loop **/
            int pass = 0;
            bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("sample_music_data.csv")));
            lineString = bufferedReader.readLine();
            while((lineString = bufferedReader.readLine())!=null)
            {
                /** split the line with comma (,) **/
                String tempString[] = lineString.split(",");

                /** store song name **/
                songName[pass] = tempString[0];
                artistName[pass] = tempString[1];
                albumName[pass] = tempString[2];

                /** increment pass **/
                pass++;

            }
            Log.d("Suraj Dubey", "File reading done");

        }
        catch (Exception e)
        {

            Log.d("Collection", "Failed");
        }


        /*CustomGrid customGrid = new CustomGrid(context);
        GridView gridView = (GridView) findViewById(R.id.gridView);

        gridView.setAdapter(customGrid);
        gridView.setNumColumns(2);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, "Titanium", Toast.LENGTH_LONG).show();
            }
        });

        */


        /*ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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

        layoutParams = new ViewGroup.LayoutParams(500, 500);
        gridView.setLayoutParams(layoutParams);

        gridView.setColumnWidth((int) (200 / getApplicationContext().getResources().getDisplayMetrics().density));
        gridView.setNumColumns(2);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

        gridView.setAdapter(customGrid);

        innerLinearLayout.addView(gridView);
        horizontalScrollView.addView(innerLinearLayout);
        linearLayout.addView(horizontalScrollView);
        */



    }

    private void setUpGrid()
    {
        for(int i=0;i<uniqueArtistCounter;i++) {

            // Log.d("debugging", "Counter is "+String.valueOf(uniqueArtistCounter));

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            TextView textView = new TextView(this);
            textView.setText(uniqueArtistName[i]);
            textView.setLayoutParams(layoutParams);
            linearLayout.addView(textView);

            HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            horizontalScrollView.setLayoutParams(layoutParams);

            LinearLayout innerLinearLayout = new LinearLayout(this);
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            innerLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            innerLinearLayout.setLayoutParams(layoutParams);

            CustomGrid customGrid = new CustomGrid(context, songArtistList[i]);


            gridView[i] = new GridView(this);

            gridView[i].setColumnWidth((int) (200 / getApplicationContext().getResources().getDisplayMetrics().density));
            gridView[i].setNumColumns(2);
            gridView[i].setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            gridView[i].setVerticalSpacing((int) (20 / getApplicationContext().getResources().getDisplayMetrics().density));
            gridView[i].setHorizontalSpacing((int) (20 / getApplicationContext().getResources().getDisplayMetrics().density));

            int paddingSpace = (int) (20 / getApplicationContext().getResources().getDisplayMetrics().density);
            gridView[i].setPaddingRelative(paddingSpace, paddingSpace, paddingSpace, paddingSpace);
            layoutParams = new ViewGroup.LayoutParams(300*songArtistList[i].size(), 200);
            gridView[i].setLayoutParams(layoutParams);
            gridView[i].setAdapter(customGrid);

            innerLinearLayout.addView(gridView[i]);
            horizontalScrollView.addView(innerLinearLayout);
            linearLayout.addView(horizontalScrollView);
        }
    }

    private int findAlbum(String nameToSearch, int position)
    {
        int foundIndex = -1;
        for(int i = 0;i<position;i++)
        {
            if(albumName[i].equals(nameToSearch))
            {
                foundIndex = i;
                return i;
            }
        }
        return foundIndex;

    }


    private int findArtist(String nameToSearch, int position)
    {
        int foundIndex = -1;
        for(int i = 0;i<position;i++)
        {
            if(uniqueArtistName[i].equals(nameToSearch))
            {
                foundIndex = i;
                return i;
            }
        }
        return foundIndex;

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

        return super.onOptionsItemSelected(item);
    }
}
