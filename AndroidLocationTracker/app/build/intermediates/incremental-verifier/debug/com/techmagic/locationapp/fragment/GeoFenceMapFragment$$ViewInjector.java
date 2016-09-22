// Generated code from Butter Knife. Do not modify!
package com.techmagic.locationapp.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class GeoFenceMapFragment$$ViewInjector<T extends com.techmagic.locationapp.fragment.GeoFenceMapFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492940, "field 'btnToggleTracking' and method 'startTracking'");
    target.btnToggleTracking = finder.castView(view, 2131492940, "field 'btnToggleTracking'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.startTracking();
        }
      });
    view = finder.findRequiredView(source, 2131492955, "field 'btnStopTracking' and method 'stopTracking'");
    target.btnStopTracking = finder.castView(view, 2131492955, "field 'btnStopTracking'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.stopTracking();
        }
      });
    view = finder.findRequiredView(source, 2131492954, "method 'clearGeoPoints'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.clearGeoPoints();
        }
      });
  }

  @Override public void reset(T target) {
    target.btnToggleTracking = null;
    target.btnStopTracking = null;
  }
}
