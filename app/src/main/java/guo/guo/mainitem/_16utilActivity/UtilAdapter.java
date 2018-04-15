package guo.guo.mainitem._16utilActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import guo.guo.R;
import guo.guo.mainitem.OnRecyclerViewItemClick;

/**
 * 作者：author
 * 时间：2018/3/21:15:16
 * 说明：
 */

public class UtilAdapter extends RecyclerView.Adapter<UtilAdapter.UtilHolder>{

    private List<String> mData;
    private Context context;
    private OnRecyclerViewItemClick onRecyclerViewItemClick;
    public UtilAdapter(Context context,List<String> mData){
        this.mData = mData;
        this.context = context;
    }
    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClick onRecyclerViewItemClick){
        this.onRecyclerViewItemClick = onRecyclerViewItemClick;
    }

    @Override
    public UtilAdapter.UtilHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UtilHolder(View.inflate(context, R.layout.recyclerview_linear_item,null));
    }

    @Override
    public void onBindViewHolder(UtilAdapter.UtilHolder holder, int position) {
        holder.textView.setText(mData.get(position));
        if (onRecyclerViewItemClick != null){
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerViewItemClick.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class UtilHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public UtilHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.tv_item);

        }
    }


}
