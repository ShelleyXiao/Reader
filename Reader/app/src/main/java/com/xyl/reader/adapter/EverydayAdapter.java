package com.xyl.reader.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xyl.architectrue.utils.CommonUtils;
import com.xyl.reader.R;
import com.xyl.reader.base.baseAdapter.BaseRecylerViewAdapter;
import com.xyl.reader.base.baseAdapter.BaseRecylerViewHolder;
import com.xyl.reader.bean.AndroidBean;
import com.xyl.reader.databinding.ItemEverydayOneBinding;
import com.xyl.reader.databinding.ItemEverydayThreeBinding;
import com.xyl.reader.databinding.ItemEverydayTitleBinding;
import com.xyl.reader.databinding.ItemEverydayTwoBinding;
import com.xyl.reader.http.rx.RxBus;
import com.xyl.reader.http.rx.RxCodeEventType;
import com.xyl.reader.utils.ImageLoadUtil;

import java.util.List;

/**
 * User: ShaudXiao
 * Date: 2017-01-18
 * Time: 16:00
 * Company: zx
 * Description:
 * FIXME
 */


public class EverydayAdapter extends BaseRecylerViewAdapter<List<AndroidBean>> {

    private final int ITEM_TYPE_TITLE = 0x01;
    private final int ITEM_TYPE_ONE = 0x02;
    private final int ITEM_TYPE_TWO = 0x03;
    private final int ITEM_TYPE_THREE = 0x04;

    @Override
    public int getItemViewType(int position) {
        if(!TextUtils.isEmpty(getData().get(position).get(0).getType_title())) {
            return ITEM_TYPE_TITLE;
        } else if(getData().get(position).size() == 1) {
            return ITEM_TYPE_ONE;
        } else if(getData().get(position).size() == 2) {
            return ITEM_TYPE_TWO;
        } else if(getData().get(position).size() == 3) {
            return ITEM_TYPE_THREE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public BaseRecylerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_TITLE:
                return new TitleViewHolder(parent, R.layout.item_everyday_title);
            case ITEM_TYPE_ONE:
                return new OneItemViewHolder(parent, R.layout.item_everyday_one);
            case ITEM_TYPE_TWO:
                return new TwoItemViewHolder(parent, R.layout.item_everyday_two);
            default:
                return new ThreeItemViewHolder(parent, R.layout.item_everyday_three);
        }

    }

    private class TitleViewHolder extends BaseRecylerViewHolder<List<AndroidBean>, ItemEverydayTitleBinding> {
        public TitleViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void onBindingViewHolder(List<AndroidBean> object, int position) {
            int index = 0;
            String title = object.get(0).getType_title();
            binding.tvTitle.setText(title);
            if("Android".equals(title)) {
                binding.ivTitleIcon.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_android));
                index = 0;
            }else  if("福利".equals(title)) {
                binding.ivTitleIcon.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_meizi));
                index = 1;
            } else  if("IOS".equals(title)) {
                binding.ivTitleIcon.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_ios));
                index = 2;
            }else  if("休息视频".equals(title)) {
                binding.ivTitleIcon.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_movie));
                index = 2;
            }else  if("拓展资源".equals(title)) {
                binding.ivTitleIcon.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_source));
                index = 2;
            }else  if("瞎推荐".equals(title)) {
                binding.ivTitleIcon.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_xia));
                index = 2;
            } else  if("APP".equals(title)) {
                binding.ivTitleIcon.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_title_app));
                index = 2;
            }

            if(position == 0) {
                binding.line.setVisibility(View.GONE);
            } else {
                binding.line.setVisibility(View.VISIBLE);
            }

            final  int finalIndex = index;
            binding.lvTitleMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RxBus.getBus().post(RxCodeEventType.JUMP_TYPE, finalIndex);
                }
            });
        }
    }

    private class OneItemViewHolder extends BaseRecylerViewHolder<List<AndroidBean>, ItemEverydayOneBinding> {

        public OneItemViewHolder(ViewGroup parent, int layoutID) {
            super(parent, layoutID);
        }

        @Override
        public void onBindingViewHolder(List<AndroidBean> object, int position) {
            if("福利".equals(object.get(0).getType())) {
                binding.tvOneText.setVisibility(View.GONE);
                binding.ivOneIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(binding.ivOneIcon.getContext())
                        .load(object.get(0).getUrl())
                        .placeholder(R.drawable.img_two_bi_one)
                        .error(R.drawable.img_two_bi_one)
                        .into(binding.ivOneIcon);
            } else {
                binding.tvOneText.setVisibility(View.VISIBLE);
                setDes(object, 0, binding.tvOneText);
                displayRandomImage(1, 0, position, binding.ivOneIcon);
                setOnClick(binding.llOne, object.get(0));
            }
        }
    }

    private class TwoItemViewHolder extends BaseRecylerViewHolder<List<AndroidBean>, ItemEverydayTwoBinding> {
        TwoItemViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void onBindingViewHolder(List<AndroidBean> object, int position) {
            setDes(object, 0, binding.tvTwoTextFirst);
            displayRandomImage(2, 0, position, binding.ivTwoIconFirst);
            setOnClick(binding.llTwoFirst, object.get(0));
            setDes(object, 1, binding.tvTwoTextSecond);
            displayRandomImage(2, 1, position, binding.ivTwoIconScecond);
            setOnClick(binding.llTwoScecond, object.get(0));
        }
    }

    private class ThreeItemViewHolder extends BaseRecylerViewHolder<List<AndroidBean>, ItemEverydayThreeBinding> {

        ThreeItemViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void onBindingViewHolder(List<AndroidBean> object, int position) {
            setDes(object, 0, binding.tvThreeTextFirst);
            displayRandomImage(3, 0, position, binding.ivThreeIconThird);
            setOnClick(binding.llThreeFirst, object.get(0));

            setDes(object, 1, binding.tvThreeTextSecond);
            displayRandomImage(3, 1, position, binding.ivThreeIconScecond);
            setOnClick(binding.llThreeScecond, object.get(0));

            setDes(object, 2, binding.tvThreeTextThird);
            displayRandomImage(3, 3, position, binding.ivThreeIconThird);
            setOnClick(binding.llThreeThird, object.get(0));
        }
    }

    private void setDes(List<AndroidBean> object, int position, TextView textView) {
        textView.setText(object.get(position).getDesc());
    }

    private void displayRandomImage(int imgNumber, int position, int itemPosition, ImageView iv) {
        ImageLoadUtil.displayRandom(imgNumber, position, itemPosition, iv);
    }

    private void setOnClick(final LinearLayout linearLayout, final AndroidBean bean) {
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
