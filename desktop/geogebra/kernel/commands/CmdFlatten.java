package geogebra.kernel.commands;

import geogebra.kernel.CircularDefinitionException;
import geogebra.kernel.GeoElement;
import geogebra.kernel.GeoList;
import geogebra.kernel.Kernel;
import geogebra.kernel.arithmetic.Command;
import geogebra.main.MyError;

	/**
	 * Flatten[ <GeoList> ]
	 */
class CmdFlatten extends CommandProcessor {

	/**
	 * Create new command processor
	 * 
	 * @param kernel
	 *            kernel
	 */
	public CmdFlatten(Kernel kernel) {
		super(kernel);
	}

	public GeoElement[] process(Command c) throws MyError {
		int n = c.getArgumentNumber();
		
		if (n!=1)
			throw argNumErr(app, c.getName(), n);
		
		boolean ok;
		GeoElement arg;
		arg = resArgs(c)[0];

		ok = arg.isGeoList();

		if (ok) {
			GeoElement[] ret = { kernel
					.Flatten(c.getLabel(), (GeoList) arg) };
			return ret;
		} else
			throw argErr(app, c.getName(), arg);
	}
}
