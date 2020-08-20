package com.lw.left_slide_view;


import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lw.leftslidelib.CustomLinearLayout;
import com.lw.leftslidelib.DPIUtil;
import com.lw.leftslidelib.LeftSlideView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
	
	private RecyclerView mRecyclerView;
	
	private MyAdapter mMyAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 初始化转换工具
		DPIUtil.setDensity(getResources().getDisplayMetrics().density);
		
		mRecyclerView = findViewById(R.id.recyclerView);
		View root = findViewById(R.id.root);
		if (root instanceof CustomLinearLayout) {
			CustomLinearLayout cll = (CustomLinearLayout) root;
			cll.setOnTouchListener(new CustomLinearLayout.OnTouchListener() {
				@Override
				public void doTouch(Point point) {
					if (mMyAdapter != null) {
						mMyAdapter.restoreItemView(point);
					}
				}
			});
		}
		
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mMyAdapter = new MyAdapter(this, mRecyclerView);
		mRecyclerView.setAdapter(mMyAdapter);
	}
	
	public static class MyAdapter extends RecyclerView.Adapter {
		
		private Context mContext;
		
		private RecyclerView mRecyclerView;
		
		private LeftSlideView mLeftSlideView;
		
		public MyAdapter(Context context, RecyclerView recyclerView) {
			this.mContext = context;
			this.mRecyclerView = recyclerView;
		}
		
		@NonNull
		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			
			final LeftSlideView leftSlideView = new LeftSlideView(mContext);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT
					, DPIUtil.dip2px(100.f));
			
			leftSlideView.setLayoutParams(params);
			LayoutInflater inflater = LayoutInflater.from(mContext);
			View contentView = inflater.inflate(R.layout.layout_content, null);
			View menuView = inflater.inflate(R.layout.layout_menu, null);
			
			menuView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Toast.makeText(mContext, "点击删除按钮", Toast.LENGTH_SHORT).show();
				}
			});
			
			contentView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Toast.makeText(mContext, "点击内容区域", Toast.LENGTH_SHORT).show();
				}
			});
			
			leftSlideView.addContentView(contentView);
			leftSlideView.addMenuView(menuView);
			leftSlideView.setRecyclerView(mRecyclerView);
			leftSlideView.setStatusChangeLister(new LeftSlideView.OnDelViewStatusChangeLister() {
				@Override
				public void onStatusChange(boolean show) {
					if (show) {
						// 如果编辑菜单在显示
						mLeftSlideView = leftSlideView;
					}
				}
			});
			
			
			return new MyVH(leftSlideView);
		}
		
		@Override
		public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		
		}
		
		@Override
		public int getItemCount() {
			return 100;
		}
		
		/**
		 * 还原itemView
		 * @param point
		 */
		public void restoreItemView(Point point) {
			if (mLeftSlideView != null) {
				
				int[] pos = new int[2];
				
				
				mLeftSlideView.getLocationInWindow(pos);
				
				int width = mLeftSlideView.getWidth();
				int height = mLeftSlideView.getHeight();
				
				// 触摸点在view的区域内，那么直接返回
				if (point.x >= pos[0] && point.y >= pos[1]
						&& point.x <= pos[0] + width && point.y <= pos[1] + height) {
					
					return;
				}
				
				mLeftSlideView.resetDelStatus();
			}
		}
	}
	
	public static class MyVH extends RecyclerView.ViewHolder {
		
		public MyVH(@NonNull View itemView) {
			super(itemView);
		}
	}
}
