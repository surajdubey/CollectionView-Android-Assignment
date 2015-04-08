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

    /* Set context to this activity*/
    final Context context = this;

    /* Spinner for sorting type "album" or "artist"*/
    private Spinner sortSpinner;

    /* spinner for songs in row*/
    private Spinner rowNumberSpinner;

    /* Gridview for displaying songs name*/
    GridView gridView[];

    final String sortType[] = {"Artist", "Album"};
    final String rowNumber[] = {"1","2","3","4","5"};

    /* Maximum number of songs*/
    int maxValue = 37;

    /** to store song name **/
    String songName[] = new String[maxValue];

    /** array to store artist name */
    String artistName[] = new String[maxValue];

    /** array to store album name */
    String albumName[] = new String[maxValue];

    /* Array to store unique artist*/
    String uniqueArtistName[] = new String[maxValue];

    /* Array to store unique album*/
    String uniqueAlbumName[] = new String[maxValue];

    /* store song name for each album and artist */
    ArrayList<String> songAlbumList[] = new ArrayList[maxValue];
    ArrayList<String> songArtistList[] = new ArrayList[maxValue];

    /** Counter **/
    int uniqueAlbumCounter = 0;
    int uniqueArtistCounter = 0;

    /* row selected */
    int rowSelected = 1;

    /* selected sort type */
    String selectedSortType = "";

    /* used for reference of linearlayot in XML file */
    LinearLayout linearLayout;

    int songSize;

    /* column width in grid */
    int columnWidth = 430;

    /* horizontal spacing between grids */
    int horizontalSpacing = 40;

    /* vertical spacing between grids */
    int verticalSpacing = 40;

    /* screen density */
    float screenDensity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_song_list);

        /* get screen density */
        screenDensity = getApplicationContext().getResources().getDisplayMetrics().density;

        /* set adapter for sorting type*/
        ArrayAdapter<String> sortAdapter =  new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, sortType);

        /* set adapter for row number */
        ArrayAdapter<String> rowNumberAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, rowNumber);

        /* get reference of actionbar*/
        ActionBar actionBar = getSupportActionBar();

        /* custom layout for action bar*/
        actionBar.setCustomView(R.layout.custom_actionbar);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        /* Spinner for sort type */
        sortSpinner = (Spinner) findViewById(R.id.sortSpinner);

        /* Row count spinner */
        rowNumberSpinner = (Spinner) findViewById(R.id.rowNumberSpinner);

        /* reference to linearlayout*/
        linearLayout = (LinearLayout) findViewById(R.id.linear);

        /* set adapter */
        sortSpinner.setAdapter(sortAdapter);
        rowNumberSpinner.setAdapter(rowNumberAdapter);

        /* item selected listener for spinner */
        rowNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int itemPosition, long l) {

                // get selected row count value
                rowSelected = Integer.parseInt(rowNumber[itemPosition]);

                // update layout
                updateGridLayout();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int itemPosition, long l) {

                /* get selected sort type value */
                selectedSortType = sortType[itemPosition];

                if(itemPosition == 0)
                {

                    uniqueArtistCounter = 0;
                    // artist is selected
                    for(int i=0;i<maxValue;i++)
                    {
                        // find artist in unique artist array
                        int artistPosition = findArtist(artistName[i],uniqueArtistCounter);

                        /** No previous arraylist **/
                        if(artistPosition == -1)
                        {
                            /* add artist to unique artist */
                            uniqueArtistName[uniqueArtistCounter] = artistName[i];

                            /** initialize arrayList for unique artist */
                            songArtistList[uniqueArtistCounter] = new ArrayList<String>();

                            /** add song name to nuoque artist */
                            songArtistList[uniqueArtistCounter].add(songName[i]);

                            /* update unique artist counter */
                            uniqueArtistCounter++;
                        }
                        else
                        {
                            /* add song name to corresponding artist arraylist*/
                            songArtistList[artistPosition].add(songName[i]);
                        }
                    }
                }

                else //album sort selected
                {
                    uniqueAlbumCounter = 0;
                    // artist is selected
                    for(int i=0;i<maxValue;i++)
                    {
                        int albumPosition = findAlbum(albumName[i],uniqueAlbumCounter);

                        /** No previous arraylist **/
                        if(albumPosition == -1)
                        {
                            uniqueAlbumName[uniqueAlbumCounter] = albumName[i];
                            songAlbumList[uniqueAlbumCounter] = new ArrayList<String>();
                            songAlbumList[uniqueAlbumCounter].add(songName[i]);
                            uniqueAlbumCounter++;
                        }
                        else
                        {
                            songAlbumList[albumPosition].add(songName[i]);
                        }
                    }

                }

                /** set up grid */
                setUpGrid();

                updateGridLayout();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /* used for reading files line by line */
        BufferedReader bufferedReader = null;
        String lineString = "";
        try{
            /** pass is used for while loop **/
            int pass = 0;

            /* read sample_music_data.csv file */
            bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("sample_music_data.csv")));

            /* read first line */
            lineString = bufferedReader.readLine();

            /* starting from second line read line by line till line value is not null */
            while((lineString = bufferedReader.readLine())!=null)
            {
                /** split the line with comma (,) **/
                String tempString[] = lineString.split(",");

                /** store song name in songName array **/
                songName[pass] = tempString[0];

                /** store artist name in artistName array **/
                artistName[pass] = tempString[1];

                /** store album name in albumName array **/
                albumName[pass] = tempString[2];

                /** increment pass **/
                pass++;

            }
        }
        catch (Exception e)
        {
            Log.d("Collection", "Failed");
        }

    }

    private void setUpGrid()
    {
        /** clear all views in linear layout */
        linearLayout.removeAllViews();

        /** Maximum number of GridView to be displayed */
        int maxGridCount = 0;

        /** selected unique artist value if artist is selected or select unique album count*/
        if(selectedSortType.equals("Artist"))
        {
            maxGridCount = uniqueArtistCounter;
        }
        else
        {
            maxGridCount = uniqueAlbumCounter;
        }

        /** create array of GridView to be displayed **/
        gridView = new GridView[maxGridCount];

        /** for each artist or album name */
        for(int i=0;i<maxGridCount;i++) {

            /** Set up layout parameters width and height */
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            /** create new textview*/
            TextView textView = new TextView(this);

            /** set textview text */
            if(selectedSortType.equals("Artist"))
            {
                textView.setText(uniqueArtistName[i]);
            }
            else
            {
                textView.setText(uniqueAlbumName[i]);
            }

            /** set textview layout */
            textView.setLayoutParams(layoutParams);

            /* calculate padding space*/
            int paddingSpace = (int)(30/screenDensity);

            /* set padding for textview*/
            textView.setPadding(paddingSpace,paddingSpace, paddingSpace, paddingSpace);

            /** add textview to LinearLayout*/
            linearLayout.addView(textView);

            /** create new HorizontalScrollView */
            HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);

            /** create layout parameters*/
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            /** set layout for horizontalscrollview */
            horizontalScrollView.setLayoutParams(layoutParams);

            /** create inner linear layout */
            LinearLayout innerLinearLayout = new LinearLayout(this);
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            /** set orintation to horizontal */
            innerLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            innerLinearLayout.setLayoutParams(layoutParams);

            /** reference to customGrid adapter */
            CustomGrid customGrid;

            /** create grid as per sort type
             *  values to be taken from songlist of artist or album */
            if(selectedSortType.equals("Artist"))
            {
                customGrid = new CustomGrid(context, songArtistList[i]);
            }
            else
            {
                customGrid = new CustomGrid(context, songAlbumList[i]);
            }

            /** initialize new gridview */
            gridView[i] = new GridView(this);

            /** set column width for grid */
            gridView[i].setColumnWidth((int) (columnWidth / screenDensity));

            /** number of columns */
            gridView[i].setNumColumns(2);
            gridView[i].setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            gridView[i].setVerticalSpacing((int) (verticalSpacing / screenDensity));
            gridView[i].setHorizontalSpacing((int) (horizontalSpacing / screenDensity));

            /** calculate padding space */
            paddingSpace = (int) (20 / screenDensity);
            gridView[i].setPadding(paddingSpace, paddingSpace, paddingSpace, paddingSpace);
            //gridView[i].setPaddingRelative(paddingSpace, paddingSpace, paddingSpace, paddingSpace);

            /** create layout parameters */
            if(selectedSortType.equals("Artist"))
            {
                layoutParams = new ViewGroup.LayoutParams(columnWidth*songArtistList[i].size(), 200);
            }
            else
            {
                layoutParams = new ViewGroup.LayoutParams(columnWidth*songAlbumList[i].size(), 200);
            }

            /** set layout parameters for grid */
            gridView[i].setLayoutParams(layoutParams);

            /** set adapter for gridView */
            gridView[i].setAdapter(customGrid);

            /** add gridView to innerLinearLayout */
            innerLinearLayout.addView(gridView[i]);

            /** add innerLinearLayout to horizontalScrollView */
            horizontalScrollView.addView(innerLinearLayout);

            /** add horizontalScrollView to linearLayout */
            linearLayout.addView(horizontalScrollView);
        }
    }

    /** this method is used to update grid layout */
    private void updateGridLayout()
    {
        /** if artist is selected */
        if(selectedSortType.equals(sortType[0]))
        {
            /** for every unique artist */
            for(int i=0;i<uniqueArtistCounter;i++)
            {
                /** get number of songs */
                songSize = songArtistList[i].size();

                /** number of columns required */
                int numCol = songSize/rowSelected;

                /** increment numCol to display grid uniformly */
                if(songSize%rowSelected != 0)
                    numCol++;

                /** set number of columns to gridview */
                gridView[i].setNumColumns(numCol);

                /** calculate number of actual rows required */
                int numRows = songSize/numCol;
                if(songSize % numCol != 0)
                    numRows++;

                /** initialize layout parameters for gridView */
                LinearLayout.LayoutParams newLayoutParams = new LinearLayout.LayoutParams(columnWidth * numCol , 100 * numRows);

                /** set layout parameters for gridView */
                gridView[i].setLayoutParams(newLayoutParams);

            }

        }

        else
        {
            /** if album is selected
             * for every unique album */
            for(int i=0;i<uniqueAlbumCounter;i++)
            {
                /** get number of songs */
                songSize = songAlbumList[i].size();

                /** number of columns required */
                int numCol = songSize/rowSelected;

                /** increment numCol to display grid uniformly */
                if(songSize%rowSelected != 0)
                    numCol++;
                gridView[i].setNumColumns(numCol);

                /** calculate number of actual rows required */
                int numRows = songSize/numCol;
                if(songSize % numCol != 0)
                    numRows++;

                /** initialize layout parameters for gridView */
                LinearLayout.LayoutParams newLayoutParams = new LinearLayout.LayoutParams(columnWidth * numCol , 100 * numRows);

                /** set layout parameters for gridView */
                gridView[i].setLayoutParams(newLayoutParams);

            }

        }

    }

    /** this method returns index of album from uniqueAlbumName array
     *  if not found returns -1
     *  */
    private int findAlbum(String nameToSearch, int position)
    {
        int foundIndex = -1;
        for(int i = 0;i<position;i++)
        {
            if(uniqueAlbumName[i].equals(nameToSearch))
            {
                foundIndex = i;
                return i;
            }
        }
        return foundIndex;

    }

    /** this method returns index of artist from uniqueArtistName array
     *  if not found returns -1
     *  */
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