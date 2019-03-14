package org.geogebra.common.gui.util.slider;

import org.geogebra.common.spy.SpyBuilder;
import org.geogebra.common.factories.FormatFactory;
import org.geogebra.common.kernel.Construction;
import org.geogebra.common.kernel.algos.ConstructionElement;
import org.geogebra.common.kernel.commands.AlgebraProcessor;
import org.geogebra.common.kernel.geos.GeoNumeric;
import org.geogebra.common.main.error.ErrorHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FormatFactory.class)
public class SliderBuilderTest {

	private SpyBuilder spyBuilder;
	private SliderBuilder sliderBuilder;
	private Construction construction;

	public SliderBuilderTest() {
		spyBuilder = new SpyBuilder();
	}

	@Before
	public void setUp() {
		construction = spyBuilder.getConstruction();
		AlgebraProcessor algebraProcessor = spyBuilder.getAlgebraProcessor();
		ErrorHandler errorHandler = spyBuilder.getErrorHandler();

		sliderBuilder = new SliderBuilder(algebraProcessor, errorHandler);
		sliderBuilder.withMin("-5").withMax("5").withStep("1").withLocation(0, 0);
	}

	@Test
	public void createSimple() {
		boolean wasSuppressedLabelsActive = construction.isSuppressLabelsActive();
		assert sliderBuilder.create() != null;
		assert isSliderInConstructionList();
		assert wasSuppressedLabelsActive == construction.isSuppressLabelsActive();
	}

	private boolean isSliderInConstructionList() {
		ConstructionElement slider = construction.getConstructionElement(0);
		return slider instanceof GeoNumeric;
	}

	@Test
	public void createWithEmptyInput() {
		boolean wasSuppressLabelsActive = construction.isSuppressLabelsActive();
		sliderBuilder.withMin("");
		try {
			sliderBuilder.create();
			assert false;
		} catch (Throwable ignored) {
		}
		assert !isSliderInConstructionList();
		assert wasSuppressLabelsActive == construction.isSuppressLabelsActive();
	}
}