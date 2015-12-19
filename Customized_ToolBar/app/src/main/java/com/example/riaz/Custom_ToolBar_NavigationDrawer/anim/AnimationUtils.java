package com.example.riaz.Custom_ToolBar_NavigationDrawer.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnticipateInterpolator;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by riaz on 08/10/15.
 */
public class AnimationUtils {


    // part 1 this is default animation
    public static void animate (RecyclerView.ViewHolder holder, boolean goesDown)
    {


      AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown == true ? 300 : -300, 0);

        ObjectAnimator animatorTranslateX=   ObjectAnimator.ofFloat(holder.itemView, "translationX", -25, 25, -20, 20, -15, 15, -10, 10, -5, 5, 0);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(holder.itemView, "scaleX",0.5F,0.8F,1.0F);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(holder.itemView, "scaleY",0.5F,0.8F,1.0F);


        //adding animatorTranslateX scaleX&Y animation are optional, animator set is used to combine the two animations

        animatorSet.setInterpolator(new AnticipateInterpolator()); // optional

        animatorSet.setDuration(1000);
        animatorSet.playTogether(animatorTranslateX,animatorTranslateY,scaleX,scaleY);
        animatorSet.start();




    }

    // part 2 Library animation
    public static void animate (RecyclerView.ViewHolder holder)
    {

        YoYo.with(Techniques.Flash)  // many many available , Rubber Band and Wobble is almost same like our default animation
                .duration(1000)
                .playOn(holder.itemView);




    }
}
