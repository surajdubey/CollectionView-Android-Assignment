package suraj.collectionview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomGrid extends BaseAdapter {
    private Context context;
    //String songName[];

    CustomGrid(Context context)
    {
        this.context = context;
        //this.songName = songName;
    }

    @Override
    public int getCount() {
        return 6;
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
            songNameTextView.setText("Titanium");

        }
        else
        {
            grid = (View) convertView;
        }

        return grid;
    }
}
