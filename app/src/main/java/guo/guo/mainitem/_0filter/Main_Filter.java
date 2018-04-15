package guo.guo.mainitem._0filter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import guo.guo.R;

/**
 *
 *          参考:http://blog.csdn.net/zhangzeyuaaa/article/details/40187789
 *          参考:http://blog.csdn.net/ljz2009y/article/details/32160555
 */
public class Main_Filter extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private SearchView searchviewFilter;
    private ListView lvFilter;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__filter);
        searchviewFilter = (SearchView) findViewById(R.id.searchview_filter);
        lvFilter = (ListView) findViewById(R.id.lv_filter);

        searchviewFilter.setOnQueryTextListener(this);

        //   new EditText(this).addTextChangedListener(); //监听文字的变化
        arrayList = new ArrayList<>(Arrays.asList(new String[]{"郭郭","郭","Bei jing",
                "Shang hai", "Chang sha", "Chang chun", "Nan jing",
                "Dong jing", "Ji nan", "Qing dao", "Xiang tan",
                "Zhu zhou", "Heng yang"}));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        lvFilter.setAdapter(adapter);
        lvFilter.setTextFilterEnabled(true);//开启过滤功能
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // 默认的会有个搜索框
        /*if (TextUtils.isEmpty(newText)) {
            lvFilter.clearTextFilter();
        } else {
            lvFilter.setFilterText(newText);
        }*/

        ListAdapter adapter = lvFilter.getAdapter();
        if (adapter instanceof Filterable){
            Filter filter = ((Filterable) adapter).getFilter();
            if (TextUtils.isEmpty(newText)){
                filter.filter(null);
            }else {
                filter.filter(newText);
            }
        }
        return true;
    }

}
