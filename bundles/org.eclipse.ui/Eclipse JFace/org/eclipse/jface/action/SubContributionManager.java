package org.eclipse.jface.action;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */
import java.util.*;

/**
 * A <code>SubContributionManager</code> is used to define a set of contribution
 * items within a parent manager.  Once defined, the visibility of the entire set can 
 * be changed as a unit.
 */
public abstract class SubContributionManager implements IContributionManager {
	/**
	 * The parent contribution manager.
	 */
	private IContributionManager parentMgr;
	
	/**
	 * Maps each item in the manager to a wrapper.  The wrapper is used to 
	 * control the visibility of each item.
	 */
	private Map mapItemToWrapper = new HashMap();

	/**
	 * The visibility of the manager,
	 */
	private boolean visible = false;
/**
 * Constructs a new <code>SubContributionManager</code>
 *
 * @param mgr the parent contribution manager.  All contributions made to the
 *      <code>SubContributionManager</code> are forwarded and appear in the
 *      parent manager.
 */
public SubContributionManager(IContributionManager mgr) {
	super();
	parentMgr = mgr;
}
/* (non-Javadoc)
 * Method declared on IContributionManager.
 */
public void add(IAction action) {
	add(new ActionContributionItem(action));
}
/* (non-Javadoc)
 * Method declared on IContributionManager.
 */
public void add(IContributionItem item) {
	SubContributionItem wrap = wrap(item);
	wrap.setVisible(visible);
	parentMgr.add(wrap);
	itemAdded(item, wrap);
}
/* (non-Javadoc)
 * Method declared on IContributionManager.
 */
public void appendToGroup(String groupName, IAction action) {
	appendToGroup(groupName, new ActionContributionItem(action));
}
/* (non-Javadoc)
 * Method declared on IContributionManager.
 */
public void appendToGroup(String groupName, IContributionItem item) {
	SubContributionItem wrap = wrap(item);
	wrap.setVisible(visible);
	parentMgr.appendToGroup(groupName, wrap);
	itemAdded(item, wrap);
}
/* (non-Javadoc)
 * Method declared on IContributionManager.
 */
public IContributionItem find(String id) {
	return parentMgr.find(id);
}
/* (non-Javadoc)
 * Method declared on IContributionManager.
 */
public IContributionItem[] getItems() {
	IContributionItem[] result = new IContributionItem[mapItemToWrapper.size()];
	mapItemToWrapper.keySet().toArray(result);
	return result;
}
/**
 * Returns the parent manager.
 *
 * @return the parent manager
 */
public IContributionManager getParent() {
	return parentMgr;
}
/* (non-Javadoc)
 * Method declared on IContributionManager.
 */
public void insertAfter(String id, IAction action) {
	insertAfter(id, new ActionContributionItem(action));
}
/* (non-Javadoc)
 * Method declared on IContributionManager.
 */
public void insertAfter(String id, IContributionItem item) {
	SubContributionItem wrap = wrap(item);
	wrap.setVisible(visible);
	parentMgr.insertAfter(id, wrap);
	itemAdded(item, wrap);
}
/* (non-Javadoc)
 * Method declared on IContributionManager.
 */
public void insertBefore(String id, IAction action) {
	insertBefore(id, new ActionContributionItem(action));
}
/* (non-Javadoc)
 * Method declared on IContributionManager.
 */
public void insertBefore(String id, IContributionItem item) {
	SubContributionItem wrap = wrap(item);
	wrap.setVisible(visible);
	parentMgr.insertBefore(id, wrap);
	itemAdded(item, wrap);
}
/* (non-Javadoc)
 * Method declared on IContributionManager.
 */
public boolean isDirty() {
	return parentMgr.isDirty();
}
/* (non-Javadoc)
 * Method declared on IContributionManager.
 */
public boolean isEmpty() {
	return parentMgr.isEmpty();
}
/**
 * Returns whether the contribution list is visible.
 * If the visibility is <code>true</code> then each item within the manager 
 * appears within the parent manager.  Otherwise, the items are not visible.
 *
 * @return <code>true</code> if the manager is visible
 */
public boolean isVisible() {
	return visible;
}
/**
 * Notifies that an item has been added.
 * <p>
 * Subclasses are not expected to override this method.
 * </p>
 *
 * @param item the item contributed by the client
 * @param wrap the item contributed to the parent manager as a proxy for the item
 *      contributed by the client
 */
protected void itemAdded(IContributionItem item, SubContributionItem wrap) {
	mapItemToWrapper.put(item, wrap);
}
/**
 * Notifies that an item has been removed.
 * <p>
 * Subclasses are not expected to override this method.
 * </p>
 *
 * @param item the item contributed by the client
 */
protected void itemRemoved(IContributionItem item) {
	mapItemToWrapper.remove(item);
}
/**
  * @deprecated Use getItems(String value) instead.
 */
public Enumeration items() {
	final Iterator i = mapItemToWrapper.values().iterator();
	return new Enumeration() {
		public boolean hasMoreElements() {
			return i.hasNext();
		}
		public Object nextElement() {
			return i.next();
		}
	};
}
/* (non-Javadoc)
 * Method declared on IContributionManager.
 */
public void markDirty() {
	parentMgr.markDirty();
}
/* (non-Javadoc)
 * Method declared on IContributionManager.
 */
public void prependToGroup(String groupName, IAction action) {
	prependToGroup(groupName, new ActionContributionItem(action));
}
/* (non-Javadoc)
 * Method declared on IContributionManager.
 */
public void prependToGroup(String groupName, IContributionItem item) {
	SubContributionItem wrap = wrap(item);
	wrap.setVisible(visible);
	parentMgr.prependToGroup(groupName, wrap);
	itemAdded(item, wrap);
}
/* (non-Javadoc)
 * Method declared on IContributionManager.
 */
public IContributionItem remove(String id) {
	IContributionItem result = parentMgr.remove(id);
	if (result != null)
		itemRemoved(result);
	return result;
}
/* (non-Javadoc)
 * Method declared on IContributionManager.
 */
public IContributionItem remove(IContributionItem item) {
	SubContributionItem wrap = (SubContributionItem)mapItemToWrapper.get(item);
	if (wrap == null)
		return null;
	IContributionItem result = parentMgr.remove(wrap);
	if (result == null)
		return null;
	itemRemoved(item);
	return item;
}
/* (non-Javadoc)
 * Method declared on IContributionManager.
 */
public void removeAll() {
	Iterator enum = mapItemToWrapper.values().iterator();
	while (enum.hasNext()) {
		IContributionItem item = (IContributionItem)enum.next();
		parentMgr.remove(item);
	}
	mapItemToWrapper.clear();
}
/**
 * Sets the visibility of the manager.  If the visibility is <code>true</code>
 * then each item within the manager appears within the parent manager.
 * Otherwise, the items are not visible.
 *
 * @param visible the new visibility
 */
public void setVisible(boolean visible) {
	this.visible = visible;
	if (mapItemToWrapper.size() > 0) {
		Iterator enum = mapItemToWrapper.values().iterator();
		while (enum.hasNext()) {
			IContributionItem item = (IContributionItem)enum.next();
			item.setVisible(visible);
		}
		parentMgr.markDirty();
	}
}
/**
 * Wraps a contribution item in a sub contribution item, and returns the new wrapper.
 */
protected SubContributionItem wrap(IContributionItem item) {
	return new SubContributionItem(item);
}
}
