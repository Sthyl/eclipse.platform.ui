package org.eclipse.ui.views.navigator;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */
import org.eclipse.swt.widgets.Shell;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.help.*;

/**
 * The ResourceNavigatorRenameAction is the rename action used by the
 * ResourceNavigator that also allows updating after rename.
 */
/* package */ class ResourceNavigatorRenameAction extends org.eclipse.ui.actions.RenameResourceAction {
	private TreeViewer viewer;
/**
 * Create a ResourceNavigatorRenameAction and use the tree of the supplied viewer
 * for editing.
 * @param shell Shell
 * @param treeViewer TreeViewer
 */
public ResourceNavigatorRenameAction(Shell shell, TreeViewer treeViewer) {
	super(shell, treeViewer.getTree());
	WorkbenchHelp.setHelp(this, new Object[] {INavigatorHelpContextIds.RESOURCE_NAVIGATOR_RENAME_ACTION});
	this.viewer = treeViewer;
}
/* (non-Javadoc)
 * Run the action to completion using the supplied path.
 */
protected void runWithNewPath(IPath path, IResource resource) {
	IWorkspaceRoot root = resource.getProject().getWorkspace().getRoot();
	super.runWithNewPath(path, resource);
	if (this.viewer != null) {
		IResource newResource = root.findMember(path);
		if (newResource != null)
			this.viewer.setSelection(new StructuredSelection(newResource), true);
	}
}
}
