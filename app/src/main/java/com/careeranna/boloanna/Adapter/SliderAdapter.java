package com.careeranna.boloanna.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.careeranna.boloanna.R;

public class SliderAdapter extends PagerAdapter {

    private Context mConext;

    private LayoutInflater layoutInflater;

    private int[] slider_images;

    private String[] slider_heading;

    private String[] slider_description;

    public SliderAdapter(Context mConext) {

        this.mConext = mConext;

        this.slider_images = new int[] {
                R.drawable.intro_pic_1_indian_languages,
                R.drawable.bolo_anna_logo,
                R.drawable.bolo_anna_logo
        };

        this.slider_heading = new String[]{

                mConext.getResources().getString(R.string._7_83_000_users),
                "7,83,000+User",
                "7,83,000+User"

        };

        this.slider_description = new String[]{

                mConext.getResources().getString(R.string.largest_platform_to_learn_and_share_languages_in_indian_languages),
                "Largest Platform To Learn And Share Languages in Indian Languages.",
                "Largest Platform To Learn And Share Languages in Indian Languages."

        };
    }


    @Override
    public int getCount() {
        return slider_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o ;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        layoutInflater = (LayoutInflater) mConext.getSystemService(mConext.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_slider_on_board, container, false);

        ImageView sliderImage = view.findViewById(R.id.on_board_slider_image);
        TextView sliderHeading = view.findViewById(R.id.on_board_slider_heading);
        TextView sliderDescription = view.findViewById(R.id.on_board_slider_description);

        sliderImage.setImageResource(slider_images[position]);
        sliderHeading.setText(slider_heading[position]);
        sliderDescription.setText(slider_description[position]);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {


        container.removeView((RelativeLayout)object);

    }
}
