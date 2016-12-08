package com.yujianmeng.selp.grabble;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

//TODO add copyright stuff
public class FragmentAchievement extends Fragment {

    private RecyclerView mAchievementRecyclerView;
    private AchievementAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievement, container, false);

        mAchievementRecyclerView = (RecyclerView) view.findViewById(R.id.achievement_recycler_view);
        mAchievementRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    private void updateUI(){
        AchievementLab achievementLab = AchievementLab.get(getActivity());
        List<Achievement> achievements = achievementLab.getAchievements();
        if(mAdapter==null){
            mAdapter = new AchievementAdapter(achievements);
            mAchievementRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.setAchievements(achievements);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class AchievementHolder extends RecyclerView.ViewHolder {

        private RelativeLayout mBG;
        private ImageView mImage;
        private TextView mName;
        private TextView mDescription;
        private TextView mDate;

        private boolean completed;

        private Typeface mFont;

        private Achievement mAchievement;

        public AchievementHolder(View itemView) {
            super(itemView);

            mFont = Typeface.createFromAsset(getActivity().getAssets(),"generica_bold.otf");

            mBG = (RelativeLayout) itemView.findViewById(R.id.achievement_bg);
            mImage = (ImageView) itemView.findViewById(R.id.achievement_image);
            mName = (TextView) itemView.findViewById(R.id.achievement_name);
            mDescription = (TextView) itemView.findViewById(R.id.achievement_descript);
            mDate = (TextView) itemView.findViewById(R.id.achievement_time);

            mName.setTypeface(mFont);
            mDescription.setTypeface(mFont);
            mDate.setTypeface(mFont);
        }

        public void bindAchievement(Achievement achievement){
            mAchievement = achievement;
            completed = !mAchievement.getmDate().equals("No Unlocked Yet");
            mName.setText(mAchievement.getmName());
            if(completed){
                mImage.setImageResource(mAchievement.getImageString());
                mBG.setBackgroundResource(R.drawable.design_achievement_bg_t);
                mDescription.setText(mAchievement.getmDescription());
                mDate.setText(mAchievement.getmDate());
            }else{
                mImage.setImageResource(R.drawable.achievement_icon_lock);
                mBG.setBackgroundResource(R.drawable.design_achievement_bg_f);
                mDescription.setText(mAchievement.getmHint());
                mDate.setText("");
            }

            mBG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AchievementLab.get(getActivity()).updateAchievement(mAchievement,true);
                    mBG.setBackgroundResource(R.drawable.design_achievement_bg_t);
                    mDescription.setText(mAchievement.getmDescription());
                    mDate.setText(mAchievement.getmDate());
                    mImage.setImageResource(mAchievement.getImageString());
                }
            });
            mBG.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AchievementLab.get(getActivity()).updateAchievement(mAchievement,false);
                    mBG.setBackgroundResource(R.drawable.design_achievement_bg_f);
                    mDescription.setText(mAchievement.getmHint());
                    mDate.setText("");
                    mImage.setImageResource(R.drawable.achievement_icon_lock);
                    return true;
                }
            });
        }
    }

    private class AchievementAdapter extends RecyclerView.Adapter<AchievementHolder> {

        private List<Achievement> mAchievements;

        public AchievementAdapter(List<Achievement> achievements) {
            mAchievements = achievements;
        }

        @Override
        public AchievementHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_achievement, parent, false);
            return new AchievementHolder(view);
        }

        @Override
        public void onBindViewHolder(AchievementHolder holder, int position) {
            Achievement achievement = mAchievements.get(position);
            holder.bindAchievement(achievement);
        }

        @Override
        public int getItemCount() {
            return mAchievements.size();
        }

        public void setAchievements(List<Achievement> achievements) {
            mAchievements = achievements;
        }
    }
}
