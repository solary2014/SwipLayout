package scrollviewgroup.lly.com.swiplayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyView;
    private ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyView = (RecyclerView) findViewById(R.id.listview);
        recyView.setLayoutManager(new LinearLayoutManager(this));
        recyView.setAdapter(new MyAdapter());
        recyView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0 || dy<0){
                    SwipeLayoutManager.getInstance().closeCurrentLayout();
                }
            }
        });
        //1.准备数据
        for (int i = 0; i < 30; i++) {
            list.add("name - "+i);
        }

    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements SwipeLayout.OnSwipeStateChangeListener {


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder Holder = new MyViewHolder(LayoutInflater.from(MainActivity.this).
                    inflate(R.layout.adapter_list,parent,false));
            return Holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv_name.setText(list.get(position));
            holder.swipeLayout.setTag(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public void onOpen(Object tag) {
            Toast.makeText(MainActivity.this,"第"+(Integer)tag+"个打开", 0).show();
        }
        @Override
        public void onClose(Object tag) {
            Toast.makeText(MainActivity.this,"第"+(Integer)tag+"个关闭", 0).show();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            TextView tv_name,tv_delete;
            SwipeLayout swipeLayout;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
                swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipeLayout);
                swipeLayout.setOnSwipeStateChangeListener(MyAdapter.this);
            }
        }
    }
}
