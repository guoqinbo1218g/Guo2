package guo.guo.mainitem._14PreferenceScreen;


import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MyPreferenceActivity extends PreferenceActivity{
    private static final String TAG = "MyPreferenceActivity";

//    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_preference);
//        getFragmentManager().beginTransaction().replace(R.id.fragment,new MyPreferenceFragment()).commit();
//        addPreferencesFromResource(R.xml.preference1);// 不推荐,推荐使用Fragment的方式
        this.getFragmentManager().beginTransaction()
                .replace(android.R.id.content,new MyPreferenceFragment()).commit();
    }


}
