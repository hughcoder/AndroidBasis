package com.hugh.basis.rvlooper;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hugh.basis.R;
import com.hugh.basis.rvlooper.bean.GroupBookingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by chenyw on 2019-08-15.
 */
public class AutoRollAdapter extends RecyclerView.Adapter<AutoRollAdapter.BaseViewHolder> {
    HashMap<String, CountDownTimer> timerMap = new HashMap<>();
    ArrayList<String> timerKeyList = new ArrayList<>();
    private List<GroupBookingEntity> mData;


    public AutoRollAdapter(List<GroupBookingEntity> list) {
        this.mData = list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_booking_vh, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        final GroupBookingEntity data = mData.get(position % mData.size());
        holder.mTvName.setText(data.nickname);
        String allPeople = data.collage_people;
        String nowPeople = data.now_people;
        int leavePeople = Integer.valueOf(allPeople) - Integer.valueOf(nowPeople);
        long currenTime = Long.parseLong(data.current_time);
        long endTime = Long.parseLong(data.end_time);
        long seconds = endTime - currenTime;
        final String mCollageId = data.id;

        holder.mLayoutGoGourp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去参团
            }
        });
        SpannableString spannableString = new SpannableString("还差" + leavePeople + "人成团");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFD4747")), 2, spannableString.length() - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.mTvleaveMembers.setText(spannableString);
        String id = data.id;
        if (!timerKeyList.contains(id)) {
            timerKeyList.add(id);
        }

        // -----一开始初始化数据

        if (holder.countDownTimer != null) {
            holder.countDownTimer.cancel();
        }
        holder.countDownTimer = new CountDownTimer(seconds * 1000, 1000) {
            public void onTick(long l) {
                holder.mTvCountDown.setText(getTimeStr(l));
            }

            public void onFinish() {
                //倒计时结束
//                holder.timeTv.setText("00:00");
            }
        }.start();
        timerMap.put(id, holder.countDownTimer);
    }

    private String getTimeStr(long l) {
        long day = l / (1000 * 24 * 60 * 60); //单位天
        long hour = (l - day * (1000 * 24 * 60 * 60)) / (1000 * 60 * 60); //单位时
        long minute = (l - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60)) / (1000 * 60); //单位分
        long second = (l - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;//单位秒

        String hourStr = String.valueOf(hour);
        if (hourStr.length() == 1) {
            hourStr = "0" + hourStr;
        }
        String minStr = String.valueOf(minute);
        if (minStr.length() == 1) {
            minStr = "0" + minStr;
        }
        String secondStr = String.valueOf(second);
        if (secondStr.length() == 1) {
            secondStr = "0" + secondStr;
        }
        //如果day为0的时候天不显示
        if (day == 0) {
            return hourStr + ":" + minStr + ":" + secondStr;
        } else {
            return day + "天" + " " + hourStr + ":" + minStr + ":" + secondStr;
        }
    }


    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }


    public void onDestroy() {
        for (int i = 0; i < timerKeyList.size(); i++) {
            if (timerKeyList.get(i) != null) {
                if (timerMap.get(timerKeyList.get(i)) != null) {
                    CountDownTimer timer = timerMap.get(timerKeyList.get(i));
                    if (timer != null) {
                        timer.cancel();
                    }
                }
            }
        }
    }


    class BaseViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvCountDown;
        private ImageView mIvAvator;
        private TextView mTvName;
        private TextView mTvleaveMembers;
        private LinearLayout mLayoutGoGourp;
        private CountDownTimer countDownTimer;

        public BaseViewHolder(View view) {
            super(view);
            mIvAvator = view.findViewById(R.id.iv_avator);
            mTvName = view.findViewById(R.id.tv_name);
            mTvleaveMembers = view.findViewById(R.id.tv_leave_book);
            mTvCountDown = view.findViewById(R.id.tv_count_down);
            mLayoutGoGourp = view.findViewById(R.id.layout_go_group);
        }
    }


}