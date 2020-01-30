package org.eclipse.nebula.widgets.timeline;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.nebula.widgets.timeline.figures.RootFigure;
import org.eclipse.nebula.widgets.timeline.listeners.TimelineScaler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class TimelineComposite extends Composite {

	private final RootFigure fRootFigure;
	private final LocalResourceManager fResourceManager;

	public TimelineComposite(Composite parent, int style) {
		super(parent, style);

		fResourceManager = new LocalResourceManager(JFaceResources.getResources(), this);

		setLayout(new FillLayout());

		final Canvas canvas = new Canvas(this, SWT.DOUBLE_BUFFERED);
		canvas.setBackground(ColorConstants.black);
		final LightweightSystem lightWeightSystem = new LightweightSystem(canvas);

		fRootFigure = new RootFigure(fResourceManager);
		fRootFigure.setFont(parent.getFont());
		lightWeightSystem.setContents(fRootFigure);

		// draw2d does not directly support mouseWheelEvents, so register on canvas
		canvas.addMouseWheelListener(new TimelineScaler(this));
	}

	public RootFigure getRootFigure() {
		return fRootFigure;
	}

	@Override
	public void dispose() {
		fRootFigure.getStyleProvider().dispose();
		fResourceManager.dispose();

		super.dispose();
	}
}
