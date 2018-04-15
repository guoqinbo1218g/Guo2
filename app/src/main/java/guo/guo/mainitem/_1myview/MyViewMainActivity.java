package guo.guo.mainitem._1myview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import guo.guo.R;

public class MyViewMainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private static final String TAG = "MyViewMainActivity";
    @BindView(R.id.lv_myview)
    ListView lvMyview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view);
        ButterKnife.bind(this);

        String[] data = new String[]{"0canvas和path绘制view"};
        ArrayList<String> arraydata = new ArrayList<>(Arrays.asList(data));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arraydata);
        lvMyview.setAdapter(arrayAdapter);
        lvMyview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
        switch (position) {

            case 0:
                intent= new Intent(this,CanvasPathActivity.class);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
        startActivity(intent);
    }
}
