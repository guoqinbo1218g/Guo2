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

import butterknife.BindView;
import butterknife.ButterKnife;
import guo.guo.R;

/**
 * 实现filterable 自定义过滤内容的方式
 */
public class Main_Filter3 extends AppCompatActivity {


    @BindView(R.id.searchview_filter)
    SearchView searchviewFilter;
    @BindView(R.id.lv_filter)
    ListView lvFilter;
    private ArrayList<Person> personList;
    private ArrayList<Person> newpersonList;
    private FilterAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__filter);
        ButterKnife.bind(this);


        personList = new ArrayList<>();
        personList.add(new Person("11", "guog123"));
        personList.add(new Person("72", "gguo12"));
        personList.add(new Person("42", "gugo23"));
        personList.add(new Person("68", "guonn13"));
        personList.add(new Person("31", "gunno1"));
        personList.add(new Person("14", "gnuno2"));
        personList.add(new Person("1231", "guo3"));

        newpersonList = personList;
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
                if (listAdapter instanceof Filterable) {
                    if (!TextUtils.isEmpty(newText)) {
                        SearchFilter searchFilter = (SearchFilter) ((Filterable) adapter).getFilter();
                        searchFilter.filter(newText);
                    } else { //这里是删除搜索后显示原有内容的
                        personList = newpersonList;
                        adapter.notifyDataSetChanged();
                    }
                }
                return true;
            }
        });
    }

    class FilterAdapter extends BaseAdapter implements Filterable {

        @Override
        public int getCount() {
            return personList.size();
        }

        @Override
        public Object getItem(int position) {
            return personList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.item_main_filter2, null);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) convertView.findViewById(R.id.textview);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.textView.setText(personList.get(position).toString());

            return convertView;
        }


        @Override
        public Filter getFilter() {
            return new SearchFilter();
        }
    }

    class SearchFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            ArrayList<Person> searchList = new ArrayList<>();
            for (Person per : personList) {
                if (per.getName().contains(constraint) || per.getAge().contains(constraint)) {
                    searchList.add(per);
                }
            }
            filterResults.values = searchList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            personList.clear();
            personList = (ArrayList<Person>) results.values;
            if (personList != null && personList.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(Main_Filter3.this, "没有搜索到好友", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class ViewHolder {
        private TextView textView;
    }
}
