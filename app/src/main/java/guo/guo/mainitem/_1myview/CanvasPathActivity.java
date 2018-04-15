package guo.guo.mainitem._1myview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import guo.guo.R;
import guo.guo.mainitem._1myview2.BezierView;
import guo.guo.mainitem._1myview2.CanvasPathaddArc;
import guo.guo.mainitem._1myview2.CanvasPathaddPath;
import guo.guo.mainitem._1myview2.CanvasPathaddRect;
import guo.guo.mainitem._1myview2.CanvasdrawPath;

public class CanvasPathActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.path0)
    CanvasdrawPath path0;
    @BindView(R.id.path1)
    CanvasPathaddRect path1;
    @BindView(R.id.path2)
    CanvasPathaddPath path2;
    @BindView(R.id.path3)
    CanvasPathaddArc path3;
    @BindView(R.id.path4)
    BezierView path4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_path);
        ButterKnife.bind(this);
        //设置 navigation 相关
        toolbar.setNavigationIcon(R.mipmap.ic_navigation_icon);
        toolbar.setNavigationOnClickListener(view -> {
        });

        toolbar.setBackgroundColor(Color.parseColor("#c7eece"));
        toolbar.setTitle("自定义view");
        toolbar.setTitleTextColor(Color.RED);
        toolbar.setSubtitle("canvas结合path使用");
        toolbar.setLogo(R.mipmap.ic_guo);
        toolbar.setTitleMargin(-10, 0, 0, 0);
        toolbar.setOverflowIcon(getResources().getDrawable(R.mipmap.ic_menu)); // 设置溢出菜单的图标
        setSupportActionBar(toolbar);
        //toolbar.inflateMenu(R.menu.menu_myviewtoolbar);  //设置 Toolbar menu
        //toolbar.setOnMenuItemClickListener(item -> {return false;});

        path0.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_myviewtoolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//如果还有onMenuItemClick则先相应onMenuItemClick
        invisibleAllView();
        switch (item.getItemId()) {
            case R.id.menu_item0:
                path0.setVisibility(View.VISIBLE);
                break;
            case R.id.menu_item1:
                path1.setVisibility(View.VISIBLE);
                break;
            case R.id.menu_item2:
                path2.setVisibility(View.VISIBLE);
                break;
            case R.id.menu_item3:
                path3.setVisibility(View.VISIBLE);
                break;
            case R.id.menu_item4:
                path4.setVisibility(View.VISIBLE);
                break;
        }
        return true;
    }

    public void invisibleAllView() {
        path0.setVisibility(View.GONE);
        path1.setVisibility(View.GONE);
        path2.setVisibility(View.GONE);
        path3.setVisibility(View.GONE);
        path4.setVisibility(View.GONE);
    }

}
