package guo.guo.mainitem._5dialogfragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import guo.guo.R;

public class MainDialogFragment extends AppCompatActivity{

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dialog_fragment);
        ButterKnife.bind(this);

    }

    private void showFirstDialogFragment() {//使用 onCreateView方式初始化dialog布局
        FirstDialogFragment firstDialog = new FirstDialogFragment();
        firstDialog.show(getSupportFragmentManager(), "dialog");
        //后面是tag 可以通过getFragmentManager.findFragmentByTag("dialog");来获得
    }

    private void showsecondFirstDialogFragment() {//使用 onCreateDialog方式初始化dialog布局
        SecondDialogFragment secondDialogFragment = new SecondDialogFragment();
        secondDialogFragment.show(getSupportFragmentManager(), "dialog2");
    }



    @OnClick({R.id.btn1, R.id.btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                showFirstDialogFragment();
                break;
            case R.id.btn2:
                showsecondFirstDialogFragment();
                break;
        }
    }
}
