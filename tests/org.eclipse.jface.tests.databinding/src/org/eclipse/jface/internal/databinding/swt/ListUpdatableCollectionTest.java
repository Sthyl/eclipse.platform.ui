/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.jface.internal.databinding.swt;

import org.eclipse.jface.databinding.DataBinding;
import org.eclipse.jface.databinding.IDataBindingContext;
import org.eclipse.jface.databinding.IObservableFactory;
import org.eclipse.jface.databinding.Property;
import org.eclipse.jface.databinding.SelectionAwareObservableCollection;
import org.eclipse.jface.databinding.swt.SWTProperties;
import org.eclipse.jface.databinding.swt.SWTObservableFactory;
import org.eclipse.jface.tests.databinding.scenarios.BindingScenariosTestSuite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

/**
 * @since 3.2
 *
 */
public class ListObservableCollectionTest extends AbstractGetAndSetSelectionObservableCollectionTest {

	/*
	 * Test method for 'org.eclipse.jface.internal.databinding.swt.CComboObservableCollection.getSelectedObject()'
	 */
	private List listControl;
	
	protected SelectionAwareObservableCollection getSelectionAwareObservable(String[] values) {
		Shell shell = BindingScenariosTestSuite.getShell();
		this.listControl = new List(shell, SWT.NONE);
		for (int i = 0; i < values.length; i++) {
			this.listControl.add(values[i]);
		}
		IDataBindingContext ctx = DataBinding.createContext(new IObservableFactory[] {new SWTObservableFactory()});
		return (SelectionAwareObservableCollection) ctx.createObservable(new Property(listControl, SWTProperties.ITEMS, String.class, new Boolean(true)));
	}
	
	protected Object getSelectedObjectOfControl() {
		int selectionIndex = this.listControl.getSelectionIndex();
		if (selectionIndex != -1) {
			return this.listControl.getItem(selectionIndex);
		} 
		return null;
	}
	
	protected void setSelectedValueOfControl(String value) {
		this.listControl.setSelection(new String[] {value});
	}
}
