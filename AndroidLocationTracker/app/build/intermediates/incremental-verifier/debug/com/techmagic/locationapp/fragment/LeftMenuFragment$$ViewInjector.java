// Generated code from Butter Knife. Do not modify!
package com.techmagic.locationapp.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class LeftMenuFragment$$ViewInjector<T extends com.techmagic.locationapp.fragment.LeftMenuFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492956, "field 'recyclerView'");
    target.recyclerView = finder.castView(view, 2131492956, "field 'recyclerView'");
  }

  @Override public void reset(T target) {
    target.recyclerView = null;
  }
}
