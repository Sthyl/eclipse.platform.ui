/*******************************************************************************
 * Copyright (c) 2010, 2014 Tom Schindl and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Tom Schindl <tom.schindl@bestsolution.at> - initial API and implementation
 *******************************************************************************/
package org.eclipse.e4.ui.css.swt.internal.theme;

import org.eclipse.e4.ui.css.swt.theme.ITheme;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.e4.ui.css.swt.theme.IThemeManager;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/*
 * ATTENTION WHEN RENAMING, REMOVING, ... you need to update 3.x org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/internal/Workbench.java
 */
/**
 * This class allows 3.x code to bootstrap the themes engine with having a real dependency on it
 */
public class BootstrapTheme3x {
	public BootstrapTheme3x(Display display) {
		this(display, null);
	}

	public BootstrapTheme3x(Display display, String themeId) {
		Bundle bundle = FrameworkUtil.getBundle(BootstrapTheme3x.class);
		BundleContext context = bundle.getBundleContext();
		ServiceReference<IThemeManager> ref = context
				.getServiceReference(IThemeManager.class);
		IThemeManager mgr = context.getService(ref);
		final IThemeEngine engine = mgr.getEngineForDisplay(display);
		ITheme theme = engine.registerTheme(IThemeEngine.DEFAULT_THEME_ID, "Default Theme", "platform:/plugin/org.eclipse.e4.ui.css.swt.theme/css/dummy.css");
		if( themeId == null ) {
			engine.setTheme(theme, false);
		} else {
			engine.setTheme(themeId, false);
		}
	}
}