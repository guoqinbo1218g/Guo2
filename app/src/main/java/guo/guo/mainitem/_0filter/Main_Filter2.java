package guo.guo.mainitem._0filter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import guo.guo.R;

/**
 *  实现filterable 自定义过滤内容的方式
 */
public class Main_Filter2 extends AppCompatActivity {

    @BindView(R.id.searchview_filter)
    SearchView searchviewFilter;
    @BindView(R.id.lv_filter)
    ListView lvFilter;

    private ArrayList<String> stringArrayList;
    private FilterAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__filter);
        ButterKnife.bind(this);
        final String [] data = new String[]{"郭郭11","郭1234","Bei jing",
                "Shang hai", "Chang sha", "Chang chun", "Nan jing",
                "Dong jing", "Ji nan", "Qing dao", "Xiang tan",
                "Zhu zhou", "Heng yang"};

        stringArrayList = new ArrayList<>(Arrays.asList(data));
        adapter = new FilterAdapter();
        lvFilter.setAdapter(adapter);
        searchviewFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ListAdapter listAdapter = lvFilter.getAdapter();
                if (listAdapter instanceof Filterable){
                    if (!TextUtils.isEmpty(newText)) {
                        SearchFilter searchFilter = (SearchFilter) ((Filterable) adapter).getFilter();
                        searchFilter.filter(newText);
                    }else{ //这里是删除搜索后显示原有内容的
                        stringArrayList = new ArrayList<String>(Arrays.asList(data));
                        adapter.notifyDataSetChanged();
                    }
                }
                return true;
            }
        });
    }
    class FilterAdapter extends BaseAdapter implements Filterable{

        @Override
        public int getCount() {
            return stringArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return stringArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            if (convertView == null){
                convertView = View.inflate(getApplicationContext(),R.layout.item_main_filter2,null);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) convertView.findViewById(R.id.textview);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.textView.setText(stringArrayList.get(position));

            return convertView;
        }


        @Override
        public Filter getFilter() {
            return new SearchFilter();
        }
    }

    class SearchFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<String> searchList = new ArrayList<>();
            for (String str:stringArrayList) {
                if (str.contains(constraint)){
                    searchList.add(str);
                }
            }
            filterResults.values = searchList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            stringArrayList.clear();
            stringArrayList = (ArrayList<String>) results.values;
            if (stringArrayList != null && stringArrayList.size() >0){
                adapter.notifyDataSetChanged();
            }else{
                Toast.makeText(Main_Filter2.this, "没有搜索到好友", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class ViewHolder{
        private TextView textView;
    }
}
