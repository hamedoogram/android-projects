// Generated code from Butter Knife. Do not modify!
package com.techmagic.locationapp.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class TrackLocationActivity$$ViewInjector<T extends com.techmagic.locationapp.activity.TrackLocationActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492941, "field 'tvLastUpdate'");
    target.tvLastUpdate = finder.castView(view, 2131492941, "field 'tvLastUpdate'");
    view = finder.findRequiredView(source, 2131492933, "field 'radioGroup'");
    target.radioGroup = finder.castView(view, 2131492933, "field 'radioGroup'");
    view = finder.findRequiredView(source, 2131492940, "field 'btnToggleTracking' and method 'toggleTracking'");
    target.btnToggleTracking = finder.castView(view, 2131492940, "field 'btnToggleTracking'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.toggleTracking();
        }
      });
    view = finder.findRequiredView(source, 2131492932, "field 'drawerLayout'");
    target.drawerLayout = finder.castView(view, 2131492932, "field 'drawerLayout'");
    view = finder.findRequiredView(source, 2131492939, "method 'clearData'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clearData();
        }
      });
  }

  @Override public void reset(T target) {
    target.tvLastUpdate = null;
    target.radioGroup = null;
    target.btnToggleTracking = null;
    target.drawerLayout = null;
  }
}
