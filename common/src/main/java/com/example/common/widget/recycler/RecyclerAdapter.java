package com.example.common.widget.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class RecyclerAdapter<Data> extends
        RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnClickListener,View.OnLongClickListener{
    private List<Data> mDataList;
    private AdapterListener listener;
    public RecyclerAdapter()
    {
        this(null);
    }
    public RecyclerAdapter(AdapterListener<Data> listener)
    {
        this(new ArrayList<Data>(),listener);
    }
    public RecyclerAdapter(List<Data> dataList,AdapterListener<Data> listener)
    {
        this.listener=listener;
        this.mDataList=dataList;
    }

    /**
     * 复写默认的布局类型返回
     * @param position 坐标
     * @return xml的id
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position,mDataList.get(position));
    }

    /**
     * 得到布局类型
     * @param position 坐标
     * @param data 当前的数据
     * @return xml的ID用于创建viewholder
     */
    protected abstract int getItemViewType(int position,Data data);

    /**
     * 创建viewholder
     * @param viewGroup RecyclerView
     * @param viewType 界面类型,约定为xml的id
     * @return  ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder<Data> onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        //简单化操作，让类型为xml布局ID
        View root=inflater.inflate(viewType,viewGroup,false);
        //通过子类必须实现的方法，得到一个viewholder
        ViewHolder<Data> holder=onCreateViewHolder(root,viewType);
        root.setTag(R.id.tag_recycler_holder,holder);
        //设置点击事件
        root.setOnLongClickListener(this);
        root.setOnClickListener(this);
        //界面注解绑定
        holder.unbinder= ButterKnife.bind(holder,root);
        holder.callBack= (AdapterCallBack<Data>) this;
        return holder;
    }

    /**
     * 得到新的viewholder
     * @param root 根布局
     * @param viewType 布局类型，这里是xml的ID
     * @return viewholder
     */
    protected abstract ViewHolder<Data> onCreateViewHolder(View root,int viewType);

    /**
     * 数据与holder绑定
     * @param dataViewHolder ViewHolder
     * @param i 坐标
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder<Data> dataViewHolder, int i) {
        //得到要绑定的数据
        Data data=mDataList.get(i);
        //触发Holder的绑定方法
        dataViewHolder.bind(data);
    }
    //得到Adapter数据量
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 插入数据
     * @param data
     */
    public  void add(Data data)
    {
        mDataList.add(data);
        notifyItemInserted(mDataList.size()-1);
    }
    public void add(Data... datalist)
    {
        if (datalist!=null&&datalist.length>0)
        {
            Collections.addAll(mDataList,datalist);
            notifyItemRangeInserted(mDataList.size(),datalist.length);
        }
    }
    public void add(Collection<Data> datalist)
    {
        if (datalist!=null&&datalist.size()>0)
        {
            mDataList.addAll(datalist);
            notifyItemRangeInserted(mDataList.size(),datalist.size());
        }
    }

    /**
     * 清空数据
     */
    public void clear()
    {
        mDataList.clear();
        notifyDataSetChanged();
    }
    public void replace(Collection<Data> datalsit)
    {
        mDataList.clear();
        if (datalsit!=null&&datalsit.size()>0)
        {
            mDataList.addAll(datalsit);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        //得到viewholder
        ViewHolder viewHolder= (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.listener!=null)
        {
            //回调
            this.listener.onItemClick(viewHolder,mDataList.get(viewHolder.getAdapterPosition()));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ViewHolder viewHolder= (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.listener!=null)
        {
            //回调
            this.listener.onItemLongClick(viewHolder,mDataList.get(viewHolder.getAdapterPosition()));
        }
        return true;
    }

    /**
     * 设置适配器监听
     * @param adapterListener
     */
    public void setListener(AdapterListener<Data> adapterListener)
    {
        this.listener=adapterListener;
    }
    public interface AdapterListener<Data>
    {
        //点击事件
        void onItemClick(RecyclerAdapter.ViewHolder holder,Data data);
        //长按点击事件
        void onItemLongClick(RecyclerAdapter.ViewHolder holder,Data data);
    }
    /**
     * 自定义viewholder
     * @param <Data> 泛型数据
     */
    public abstract static class ViewHolder<Data> extends RecyclerView.ViewHolder{
        private Unbinder unbinder;
        protected Data mData;
        private AdapterCallBack<Data> callBack;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        /**
         * 用于绑定数据
         * @param data 要绑定的数据
         */
        void bind(Data data)
        {
            this.mData=data;
        }

        /**
         * 触发绑定的回调
         * @param data 数据
         */
        protected abstract  void  onBind(Data data);

        /**
         * holder更新自己对应的data数据
         * @param data
         */
        public void updateData(Data data)
        {
            if (this.callBack!=null)
            {
                this.callBack.update(data,this);
            }
        }
    }

}
