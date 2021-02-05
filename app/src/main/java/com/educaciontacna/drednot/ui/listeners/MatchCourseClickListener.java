/*
 * Copyright (c) 2020. rogergcc
 */

package com.educaciontacna.drednot.ui.listeners;

import android.widget.ImageView;

import com.educaciontacna.drednot.ui.model.MatchCourse;

public interface MatchCourseClickListener {

    void onScrollPagerItemClick(MatchCourse courseCard, ImageView imageView); // Shoud use imageview to make the shared animation between the two activity

}
