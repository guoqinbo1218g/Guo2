package guo.guo.mainitem._4recycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import guo.guo.R;

/**
 *
 */
public class MainRecycleview extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private List<String> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//不改 theme的style 可以用此代码
        setContentView(R.layout.activity_main_recycleview);

        ButterKnife.bind(this);
        mData = new ArrayList<>();

        initToolbar();// 初始化toolbar
        initLinearView();
    }
    // apptheme 中 <item name="android:listDivider">@drawable/shape_recyclerview1</item>
    private void initLinearView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(linearLayoutManager);
        for (int i = 0; i < 77; i++) {
            mData.add("IBM");
        }
        MyrecyclerViewAdapter myAdapter = new MyrecyclerViewAdapter(mData);
        recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerview.setAdapter(myAdapter);
    }
    private void initGradView(){
        GridLayoutManager gradLayoutManager = new GridLayoutManager(this,3);
        recyclerview.setLayoutManager(gradLayoutManager);
        for (int i = 0; i < 77; i++) {
            mData.add("IBM");
        }
        MyrecyclerViewAdapter myAdapter = new MyrecyclerViewAdapter(mData);
        //recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerview.setAdapter(myAdapter);
    }
    private void StaggeredView(){
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(staggeredGridLayoutManager);
        MyrecyclerViewAdapter myAdapter = new MyrecyclerViewAdapter(mData);
        recyclerview.setAdapter(myAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {  //如果还有onMenuItemClick则先相应onMenuItemClick
        switch (item.getItemId()){
            case R.id.menu_menu1:
                initLinearView();
                Toast.makeText(this, "onOptionsItemSelected  1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_menu2:
                initGradView();
                Toast.makeText(this, "onOptionsItemSelected  2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_menu3:
                StaggeredView();
                Toast.makeText(this, "onOptionsItemSelected  3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_wifi:
                Toast.makeText(this, "onOptionsItemSelected  wifi", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_search:
                Toast.makeText(this, "onOptionsItemSelected  search", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
    private void initToolbar() {
        //设置toolbar的一些属性 ,也可以在xml中设置 toolbar:
        toolbar.setTitle("title");
        toolbar.setTitleMargin(-10,0,0,0);
        toolbar.setSubtitle("subtitle");
        //   android:elevation="4dp"  大概就是有一点浮动的效果，更加美观
        toolbar.setLogo(R.mipmap.ic_guo);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_navigation_icon);

        //toolbar.setOnMenuItemClickListener(this);
    }


    //加载menu,通过menu中的showAsAction确定如何显示图标
    //  通过自定义主题的"android:textColorSecondary"属性对应的就是右上角三个圆点的颜色了
    //改变 弹出popupwindow 的样式  toolbar:popupTheme="@style/ToolbarPopupTheme"
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;
    }

   /* @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_menu1:
                Toast.makeText(this, "onMenuItemClick  1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_menu2:
                Toast.makeText(this, "onMenuItemClick  2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_menu3:
                Toast.makeText(this, "onMenuItemClick  3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_wifi:
                Toast.makeText(this, "onMenuItemClick  wifi", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_search:
                Toast.makeText(this, "onMenuItemClick  search", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }*/
}
