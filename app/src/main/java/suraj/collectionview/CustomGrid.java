package suraj.collectionview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomGrid extends BaseAdapter {
    private Context context;
    //String songName[];
    ArrayList<String> songNameList = new ArrayList();

    CustomGrid(Context context, ArrayList<String> songNameList)
    {
        this.context = context;
        //this.songName = songName;
        this.songNameList = songNameList;
    }

    @Override
    public int getCount() {
        return songNameList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View grid;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
        {
            grid = new View(context);
            grid = layoutInflater.inflate(R.layout.single_song, null);

            TextView songNameTextView = (TextView) grid.findViewById(R.id.songLabel);
            songNameTextView.setText(songNameList.get(position));

        }
        else
        {
            grid = (View) convertView;
        }

        return grid;
    }
}
