/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.ui.tests.commands;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.CommandException;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.tests.harness.util.UITestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests a bug found in
 * {@link ParameterizedCommand#generateCombinations(Command)} and its private
 * helper method <code>expandParameters</code>. The bug is causing a
 * <code>StackOverflowError</code> when dealing with commands that have
 * several optional parameters for which there are no
 * <code>IParameterValues</code>.
 *
 * @since 3.2
 */
@RunWith(JUnit4.class)
public class Bug125792Test extends UITestCase {

	public Bug125792Test() {
		super(Bug125792Test.class.getSimpleName());
	}

	private static final String COMMAND_ID = "org.eclipse.ui.tests.commands.bug125792";

	@Test
	public void testParameterizedCommand_generateCombinations()
			throws CommandException {
		Command command = getCommandService().getCommand(COMMAND_ID);
		ParameterizedCommand.generateCombinations(command);
	}

	private ICommandService getCommandService() {
		Object serviceObject = getWorkbench().getAdapter(ICommandService.class);
		if (serviceObject != null) {
			return (ICommandService) serviceObject;
		}
		return null;
	}

}
