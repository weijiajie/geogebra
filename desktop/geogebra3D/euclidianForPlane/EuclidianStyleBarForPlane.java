package geogebra3D.euclidianForPlane;

import geogebra.common.euclidian.EuclidianConstants;
import geogebra.common.kernel.geos.GeoElement;
import geogebra.euclidian.EuclidianStyleBarD;
import geogebra.gui.util.MyToggleButton;

import java.util.ArrayList;



/**
 * StyleBar for view for plane
 * 
 * @author matthieu
 *
 */
public class EuclidianStyleBarForPlane extends EuclidianStyleBarD {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private MyToggleButton btnCenterAndOrientation;


	/**
	 * Common constructor.
	 * @param ev
	 */
	public EuclidianStyleBarForPlane(EuclidianViewForPlane ev) {
		super(ev);
	}
	

	@Override
	protected void addGraphicsDecorationsButtons(){
		//add(btnShowAxes);
		add(btnShowGrid);
	}
	
	@Override
	protected void addBtnRotateView(){

		add(btnCenterAndOrientation);

	}
	

	@Override
	protected boolean isVisibleInThisView(GeoElement geo){
		return geo.isVisibleInView3D() ;
	}
	
	
	@Override
	protected void processSource(Object source, ArrayList<GeoElement> targetGeos){
		
		if (source.equals(btnCenterAndOrientation)) {
			((EuclidianViewForPlane) ev).updateCenterAndOrientationRegardingView();
		}else
			super.processSource(source, targetGeos);
	}

	@Override
	protected void createButtons() {

		super.createButtons();
		
		// ========================================
		// button
		btnCenterAndOrientation = new MyToggleButton(app.getImageIcon("view_default.gif"),
				iconHeight) {

			private static final long serialVersionUID = 1L;

			@Override
			public void update(Object[] geos) {
				// always show this button unless in pen mode
				this.setVisible(mode != EuclidianConstants.MODE_PEN);
			}
		};
		btnCenterAndOrientation.addActionListener(this);

		
	}	
	
	
	@Override
	public void setLabels(){
		super.setLabels();
		btnCenterAndOrientation.setToolTipText(app.getPlainTooltip("stylebar.Orientation"));
		
	}
	
	@Override
	protected void updateGUI(){
		super.updateGUI();
		
		btnCenterAndOrientation.removeActionListener(this);
		btnCenterAndOrientation.setSelected(false);
		btnCenterAndOrientation.addActionListener(this);
		
		

		
	}
	
	

}
