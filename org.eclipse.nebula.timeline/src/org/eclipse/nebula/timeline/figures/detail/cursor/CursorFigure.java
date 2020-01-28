/*******************************************************************************
 * Copyright (c) 2019 Christian Pontesegger and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Christian Pontesegger - initial API and implementation
 *******************************************************************************/

package org.eclipse.nebula.timeline.figures.detail.cursor;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.Triangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.nebula.timeline.ICursor;
import org.eclipse.nebula.timeline.figures.IStyledFigure;
import org.eclipse.nebula.timeline.jface.ITimelineStyleProvider;
import org.eclipse.nebula.timeline.listeners.CursorListener;

public class CursorFigure extends Figure implements IStyledFigure {

	public static final int CURSOR_WIDTH = 14;

	private static final int TRIANGLE_SIZE = 6;

	private final RectangleFigure fLineFigure;

	public CursorFigure(ITimelineStyleProvider styleProvider) {
		setLayoutManager(new CursorFigureLayout());

		updateStyle(styleProvider);

		final Triangle topTriangle = new Triangle();
		topTriangle.setSize(TRIANGLE_SIZE, TRIANGLE_SIZE);
		topTriangle.setOpaque(true);
		topTriangle.setDirection(PositionConstants.RIGHT);
		add(topTriangle, BorderLayout.TOP);

		final Triangle bottomTriangle = new Triangle();
		bottomTriangle.setSize(TRIANGLE_SIZE, TRIANGLE_SIZE);
		topTriangle.setOpaque(true);
		bottomTriangle.setDirection(PositionConstants.LEFT);
		add(bottomTriangle, BorderLayout.BOTTOM);

		fLineFigure = new RectangleFigure();
		fLineFigure.setLineWidth(2);
		fLineFigure.getBounds().setWidth(2);
		add(fLineFigure, BorderLayout.CENTER);

		new CursorListener(this);
	}

	public long getEventTime() {
		final ICursor cursor = (ICursor) getParent().getLayoutManager().getConstraint(this);

		return cursor.getTimestamp();
	}

	@Override
	public void updateStyle(ITimelineStyleProvider styleProvider) {
		setForegroundColor(styleProvider.getCursorColor());
		setBackgroundColor(styleProvider.getCursorColor());
	}

	private class CursorFigureLayout extends BorderLayout {

		@Override
		public void layout(IFigure container) {
			super.layout(container);

			final Rectangle bounds = fLineFigure.getBounds();
			bounds.performTranslate((bounds.width() / 2) - 1, 0);
			bounds.setWidth(1);
			bounds.setY(container.getBounds().y());
			bounds.setHeight(container.getBounds().height());
		}
	}
}
