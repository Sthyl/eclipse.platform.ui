/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.e4.ui.workbench.addons.dndaddon;

import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.widgets.CTabFolder;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.graphics.Point;

/**
 *
 */
public class PartDragAgent extends DragAgent {
	public PartDragAgent(DnDManager manager) {
		super(manager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.e4.ui.workbench.addons.dndaddon.DragAgent#getElementToDrag(org.eclipse.e4.ui.
	 * workbench.addons.dndaddon.CursorInfo)
	 */
	@Override
	public MUIElement getElementToDrag(DnDInfo info) {
		// Drag a part that is in a stack
		if (info.curElement instanceof MPartStack
				&& (info.itemElement instanceof MPlaceholder || info.itemElement instanceof MPart)) {
			// Prevent dragging 'No Move' parts
			if (info.itemElement.getTags().contains(IPresentationEngine.NO_MOVE))
				return null;

			dragElement = info.itemElement;
			return info.itemElement;
		}

		// Drag a complete stack
		if (info.curElement instanceof MPartStack && info.itemElement == null) {
			// Only allow a drag to start if we're a CTabFolder
			if (!(info.curElement.getWidget() instanceof CTabFolder))
				return null;

			// Only allow a drag to start if we're inside the 'tab area' of the CTF
			CTabFolder ctf = (CTabFolder) info.curElement.getWidget();
			Point ctfPos = ctf.getDisplay().map(null, ctf, info.cursorPos);
			if (ctfPos.y > ctf.getTabHeight())
				return null;

			// Prevent dragging 'No Move' stacks
			if (info.curElement.getTags().contains(IPresentationEngine.NO_MOVE))
				return null;

			// Prevent dragging the last stack out of the shared area
			MUIElement parent = info.curElement.getParent();
			if (parent instanceof MArea)
				return null;

			dragElement = info.curElement;
			return info.curElement;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.e4.ui.workbench.addons.dndaddon.DragAgent#dragStart(org.eclipse.e4.ui.model.
	 * application.ui.MUIElement)
	 */
	@Override
	public void dragStart(MUIElement element, DnDInfo info) {
		super.dragStart(element, info);
		if (dndManager.getFeedbackStyle() != DnDManager.SIMPLE)
			dndManager.hostElement(element, 16, 10);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.e4.ui.workbench.addons.dndaddon.DragAgent#dragFinished()
	 */
	@Override
	public void dragFinished() {
		if (dragElement instanceof MPart) {
			EPartService ps = dndManager.getDragWindow().getContext().get(EPartService.class);
			ps.activate((MPart) dragElement);
		}
		super.dragFinished();
	}
}
