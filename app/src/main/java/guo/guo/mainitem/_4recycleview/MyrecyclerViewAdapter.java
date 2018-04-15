package guo.guo.mainitem._4recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import guo.guo.R;

/**
 * 作者：author
 * 时间：2017/7/27:14:49
 * 说明：
 */

public class MyrecyclerViewAdapter extends RecyclerView.Adapter<MyrecyclerViewAdapter.MyHolder>{
    private List<String> mData;
    public MyrecyclerViewAdapter(List<String> mData){
        this.mData = mData;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.recyclerview_linear_item,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        holder.textView.setText(mData.get(position)+"  "+position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_item);
        }

    }
}
