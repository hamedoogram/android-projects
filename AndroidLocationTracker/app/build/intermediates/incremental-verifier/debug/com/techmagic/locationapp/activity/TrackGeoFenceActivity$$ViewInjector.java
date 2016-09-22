// Generated code from Butter Knife. Do not modify!
package com.techmagic.locationapp.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class TrackGeoFenceActivity$$ViewInjector<T extends com.techmagic.locationapp.activity.TrackGeoFenceActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492932, "field 'drawerLayout'");
    target.drawerLayout = finder.castView(view, 2131492932, "field 'drawerLayout'");
  }

  @Override public void reset(T target) {
    target.drawerLayout = null;
  }
}
