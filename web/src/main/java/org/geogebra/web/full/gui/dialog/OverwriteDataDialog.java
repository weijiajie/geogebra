package org.geogebra.web.full.gui.dialog;

import org.geogebra.web.html5.main.AppW;
import org.geogebra.web.shared.components.dialog.ComponentDialog;
import org.geogebra.web.shared.components.dialog.DialogData;
import org.gwtproject.user.client.ui.Label;

public class OverwriteDataDialog extends ComponentDialog {

	public OverwriteDataDialog(AppW appW, DialogData data) {
		super(appW, data, false, true);

		Label label = new Label(appW.getLocalization().getMenu("overwriteCurrentData"));
		setDialogContent(label);
	}
}
