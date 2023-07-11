package org.geogebra.web.full.gui.menubar;

import org.geogebra.common.main.Localization;
import org.geogebra.common.move.events.BaseEvent;
import org.geogebra.common.move.ggtapi.events.LogOutEvent;
import org.geogebra.common.move.ggtapi.events.LoginEvent;
import org.geogebra.common.move.views.BooleanRenderable;
import org.geogebra.common.move.views.EventRenderable;
import org.geogebra.gwtutil.FileSystemAPI;
import org.geogebra.web.full.css.MaterialDesignResources;
import org.geogebra.web.full.gui.ShareControllerW;
import org.geogebra.web.full.gui.menu.icons.DefaultMenuIconProvider;
import org.geogebra.web.full.gui.menubar.action.ClearAllAction;
import org.geogebra.web.full.gui.menubar.action.ExportImage;
import org.geogebra.web.full.gui.menubar.action.SaveAction;
import org.geogebra.web.full.gui.menubar.action.SaveLocalAction;
import org.geogebra.web.full.gui.menubar.action.ShareAction;
import org.geogebra.web.full.gui.menubar.item.ExitExamItem;
import org.geogebra.web.full.gui.view.algebra.MenuItem;
import org.geogebra.web.html5.bridge.GeoGebraJSNativeBridge;
import org.geogebra.web.html5.gui.menu.AriaMenuItem;
import org.geogebra.web.html5.main.AppW;
import org.geogebra.web.resources.SVGResource;
import org.gwtproject.user.client.ui.Widget;

import com.himamis.retex.editor.share.util.Unicode;

/**
 * Web implementation of FileMenu
 */
public class FileMenuW extends Submenu implements BooleanRenderable, EventRenderable {

	private AriaMenuItem shareItem;
	private AriaMenuItem openFileItem;

	private final Localization loc;

	/**
	 * @param app
	 *            application
	 */
	public FileMenuW(final AppW app) {
		super("file", app);
		addExpandableStyleWithColor(false);
		this.loc = app.getLocalization();
		initActions();
	}

	private void initActions() {
		if (getApp().isExam()) {
			addItem(new ExitExamItem());
			return;
		}

		buildFileMenu();

		getApp().getNetworkOperation().getView().add(this);
		if (!getApp().getNetworkOperation().isOnline()) {
			render(false);
		}
		if (getApp().getLoginOperation() != null) {
			getApp().getLoginOperation().getView().add(this);
		}
	}

	private void buildFileMenu() {
		if (getApp().enableOnlineFileFeatures()) {
			addFileNewItem();
			addOpenFileItem();
			addSaveItems();
			addSeparator();
		}
		addExportImageItem();
		if (getApp().enableOnlineFileFeatures()) {
			addShareItem();
		}
		addDownloadAsItem();
		addPrintItem();
	}

	private void updateOpenFileButton() {
		openFileItem.setHTML(MainMenu.getMenuBarHtmlClassic(
				MaterialDesignResources.INSTANCE.openFileMenu().getSafeUri().asString(),
				loc.getMenu("Open")));
	}

	/**
	 * Open share dialog for given app
	 *
	 * @param app
	 *            application
	 * @param anchor
	 *            relative element
	 */
	public static void share(AppW app, Widget anchor) {
		ShareControllerW sc = (ShareControllerW) app.getShareController();
		if (GeoGebraJSNativeBridge.get() == null) {
			sc.setAnchor(anchor);
			sc.share();
		} else {
			sc.getBase64();
		}
	}

	/**
	 * @param online
	 *            whether the application is online renders a the online -
	 *            offline state of the FileMenu
	 */
	@Override
	public void render(boolean online) {
		shareItem.setEnabled(online);
		if (!online) {
			shareItem.setTitle(loc.getMenu("Offline"));
		} else {
			shareItem.setTitle("");
		}
	}

	@Override
	public void renderEvent(BaseEvent event) {
		if (event instanceof LoginEvent || event instanceof LogOutEvent) {
			updateOpenFileButton();
		}
	}

	@Override
	public SVGResource getImage() {
		return MaterialDesignResources.INSTANCE.fileMenu();
	}

	@Override
	protected String getTitleTranslationKey() {
		return "File";
	}

	private void addFileNewItem() {
		addItem(new MenuItem<>("New",
				MaterialDesignResources.INSTANCE.newFileMenu(),
				new ClearAllAction(true)));
	}

	private void addShareItem() {
		shareItem = addItem(new MenuItem<>("Share",
				DefaultMenuIconProvider.INSTANCE.exportFile(),
				new ShareAction()));
	}

	private void addExportImageItem() {
		addItem(new MenuItem<>("exportImage",
				MaterialDesignResources.INSTANCE.export_image_black(),
				new ExportImage()));
	}

	private void addSaveItems() {
		if (getApp().getLAF().undoRedoSupported()) {
			addItem(new MenuItem<>("SaveOnline",
					DefaultMenuIconProvider.INSTANCE.saveOnline(),
					new SaveAction()));
			if (FileSystemAPI.isSupported()) {
				addItem(new MenuItem<>("SaveToYourPC",
						DefaultMenuIconProvider.INSTANCE.save(),
						new SaveLocalAction()));
			}
		}
	}

	private void addOpenFileItem() {
		openFileItem =
				addItem(MainMenu.getMenuBarHtml(
						MaterialDesignResources.INSTANCE.openFileMenu(),
						loc.getMenu("Open")),
						true, new MenuCommand(getApp()) {

					@Override
					public void doExecute() {
						app.openSearch(null);
					}
				});
	}

	private void addDownloadAsItem() {
		if (getApp().getLAF().exportSupported()) {
			addItem(MainMenu.getMenuBarHtml(
					MaterialDesignResources.INSTANCE.file_download_black(),
					loc.getMenu("DownloadAs") + Unicode.ELLIPSIS), true,
					new ExportMenuW(getApp()), true);
		}
	}

	private void addPrintItem() {
		if (getApp().getLAF().printSupported()) {
			AriaMenuItem printItem = new AriaMenuItem(
					MainMenu.getMenuBarHtml(
							MaterialDesignResources.INSTANCE.print_black(),
							loc.getMenu("PrintPreview")),
					true, new MenuCommand(getApp()) {

				@Override
				public void doExecute() {
					getApp().getDialogManager()
							.showPrintPreview();
				}
			});
			// updatePrintMenu();
			addItem(printItem);
		}
	}
}
