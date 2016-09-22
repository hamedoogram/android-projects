// Generated code from Butter Knife. Do not modify!
package com.techmagic.locationapp.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class MapResultsActivity$$ViewInjector<T extends com.techmagic.locationapp.activity.MapResultsActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492942, "field 'btnDateFrom' and method 'showDateFromPickerDialog'");
    target.btnDateFrom = finder.castView(view, 2131492942, "field 'btnDateFrom'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.showDateFromPickerDialog();
        }
      });
    view = finder.findRequiredView(source, 2131492943, "field 'btnTimeFrom' and method 'showTimeFromPickerDialog'");
    target.btnTimeFrom = finder.castView(view, 2131492943, "field 'btnTimeFrom'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.showTimeFromPickerDialog();
        }
      });
    view = finder.findRequiredView(source, 2131492944, "field 'btnDateTo' and method 'showDateToPickerDialog'");
    target.btnDateTo = finder.castView(view, 2131492944, "field 'btnDateTo'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.showDateToPickerDialog();
        }
      });
    view = finder.findRequiredView(source, 2131492945, "field 'btnTimeTo' and method 'showTimeToPickerDialog'");
    target.btnTimeTo = finder.castView(view, 2131492945, "field 'btnTimeTo'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.showTimeToPickerDialog();
        }
      });
  }

  @Override public void reset(T target) {
    target.btnDateFrom = null;
    target.btnTimeFrom = null;
    target.btnDateTo = null;
    target.btnTimeTo = null;
  }
}
