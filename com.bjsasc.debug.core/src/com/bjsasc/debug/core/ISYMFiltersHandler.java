/*******************************************************************************
 * Copyright (c) 2006, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.bjsasc.debug.core;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.commands.IDebugCommandHandler;

/**
 * A step filters handler typically toggles the use of step filters
 * in a debug session based on the user preference setting. To determine if step filters
 * should be enabled use the method <code>isUseStepFilters()</code> in
 * {@link DebugPlugin}. 
 * <p>
 * Clients may implement this interface. The debug platform provides a
 * toggle step filters action that delegates to this handler interface. As well, the
 * debug platform provides an implementation of the step filters handler registered
 * as an adapter on objects that implement
 * {@link org.eclipse.debug.core.model.IStepFilters}.
 * </p>
 * @since 3.3
 */
public interface ISYMFiltersHandler extends IDebugCommandHandler {

}
