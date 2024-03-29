/*
 * File: Menus.java
 * 
 * Copyright (C) 2011 The Humanitarian FOSS Project (http://hfoss.org)
 * 
 * This file is part of POSIT-Haiti Server.
 *
 * POSIT-Haiti Server is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by 
 * the Free Software Foundation; either version 3.0 of the License, or (at
 * your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not visit http://www.gnu.org/licenses/gpl.html.
 */

package haiti.server.gui;


import haiti.server.datamodel.LocaleManager;
import haiti.server.datamodel.User;

import haiti.server.datamodel.LocaleManager;
import haiti.server.gui.DataEntryGUI.DbSource;
import haiti.server.gui.Menus.Task;
import haiti.server.datamodel.AttributeManager;
import haiti.server.datamodel.AttributeManager.MessageStatus;
import haiti.server.datamodel.AttributeManager.MessageType;
import haiti.server.datamodel.DAO;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.applet.Applet;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

/**
 *  Implements a menu system for DataEntryGUI.
 */

public class Menus implements ActionListener, PropertyChangeListener {
	
	public static final String MENU_FILE = "File";
	//public static final String MENU_OPEN_FILE = "OpenFile"; // Removed for now.
	public static final String MENU_OPEN_DB = "OpenDB";
	public static final String MENU_FILTER = "Filter";

	public static final String MENU_BENEFICIARY_FILTER = "BeneficiaryFilter";
	public static final String MENU_BENEFICIARY_FILTER_NEW = "NewBeneficiary";
	public static final String MENU_BENEFICIARY_FILTER_PENDING = "PendingBeneficiary";
	public static final String MENU_BENEFICIARY_FILTER_PROCESSED = "ProcessedBeneficiary";
	public static final String MENU_BENEFICIARY_FILTER_ARCHIVED = "ArchivedBeneficiary";
	public static final String MENU_BENEFICIARY_FILTER_ALL = "AllBeneficiaries";
	public static final String MENU_UPDATE_FILTER = "UpdateFilter";
	public static final String MENU_UPDATE_FILTER_NEW = "NewUpdate";
	public static final String MENU_UPDATE_FILTER_PENDING = "PendingUpdate";
	public static final String MENU_UPDATE_FILTER_PROCESSED = "ProcessedUpdate";
	public static final String MENU_UPDATE_FILTER_ARCHIVED = "ArchivedUpdate";
	public static final String MENU_UPDATE_FILTER_ALL = "AllUpdates";
	public static final String MENU_ALL = "All";
	public static final String MENU_QUIT = "Quit";
	public static final String MENU_ABOUT = "About";  // About DataEntryGUI...";
	public static final String MENU_LOCALE = "Locale";
	public static final String MENU_ENGLISH = "English";
	public static final String MENU_FRENCH = "French";
	public static final String MENU_HELP = "Help";
	//public static final String MENU_ADMIN = "Admin"; // Removed for now.
	//public static final String MENU_CREATE = "CreateNewUser";
	public static final String MENU_ARCHIVE_DB = "ArchiveDb";
	public static final String MENU_OUTPUT_ARCHIVE = "OutputArchive";
	public static final String MENU_MOBILE_DB = "MobileDb";
	public static final String MENU_ATTENDANCE = "AbsentReport";

	
	public static Locale[] supportedLocales = {Locale.FRENCH, Locale.ENGLISH};	
	private static MenuBar mbar;	
	private static DataEntryGUI gui;
    private ProgressMonitor progressMonitor;
	private Task task;	
	
	public Menus(DataEntryGUI gui) {
		this.gui = gui;
	}

	/**
	 * Creates the Menu Bar for the main data entry form
	 */
	public void createMenuBar() {
		Menu fileMenu = new Menu(LocaleManager.resources.getString(MENU_FILE));
		Menu openDbMenu = new Menu(LocaleManager.resources.getString(MENU_OPEN_DB));
		Menu filterMenu = new Menu(LocaleManager.resources.getString(MENU_FILTER));
		//addMenuItem(fileMenu, LocaleManager.resources.getString(MENU_OPEN_FILE), KeyEvent.VK_N, false);
		addMenuItem(fileMenu, LocaleManager.resources.getString(MENU_OPEN_DB), KeyEvent.VK_D, false);
		fileMenu.add(filterMenu);
		addMenuItem(fileMenu, LocaleManager.resources.getString(MENU_ATTENDANCE), 0, false);
		addMenuItem(fileMenu, LocaleManager.resources.getString(MENU_QUIT), KeyEvent.VK_Q, false);

		Menu localeMenu = new Menu(LocaleManager.resources.getString(MENU_LOCALE));
		addMenuItem(localeMenu, LocaleManager.resources.getString(MENU_ENGLISH), KeyEvent.VK_E, false);	
		addMenuItem(localeMenu, LocaleManager.resources.getString(MENU_FRENCH), KeyEvent.VK_F, false);	

		Menu beneficiaryMenu = new Menu(LocaleManager.resources.getString(MENU_BENEFICIARY_FILTER));
		Menu updateMenu = new Menu(LocaleManager.resources.getString(MENU_UPDATE_FILTER));

		filterMenu.add(beneficiaryMenu);
		filterMenu.add(updateMenu);
//		addMenuItem(openDbMenu, LocaleManager.resources.getString(MENU_ALL), KeyEvent.VK_A, false);
		
		addMenuItem(beneficiaryMenu, LocaleManager.resources.getString(MENU_BENEFICIARY_FILTER_NEW), 0, false);
		addMenuItem(updateMenu, LocaleManager.resources.getString(MENU_UPDATE_FILTER_NEW), 0, false);
		
		mbar = new MenuBar();
		mbar.add(fileMenu);
		mbar.add(localeMenu);

		//if (gui.isAdmin) {
			addMenuItem(beneficiaryMenu, LocaleManager.resources.getString(MENU_BENEFICIARY_FILTER_PENDING), 0, false);
			addMenuItem(beneficiaryMenu, LocaleManager.resources.getString(MENU_BENEFICIARY_FILTER_PROCESSED), 0 , false);
			addMenuItem(beneficiaryMenu, LocaleManager.resources.getString(MENU_BENEFICIARY_FILTER_ARCHIVED), 0, false);
			addMenuItem(beneficiaryMenu, LocaleManager.resources.getString(MENU_BENEFICIARY_FILTER_ALL), 0, false);
			addMenuItem(updateMenu, LocaleManager.resources.getString(MENU_UPDATE_FILTER_PENDING), 0, false);
			addMenuItem(updateMenu, LocaleManager.resources.getString(MENU_UPDATE_FILTER_PROCESSED), 0, false);
			addMenuItem(updateMenu, LocaleManager.resources.getString(MENU_UPDATE_FILTER_ARCHIVED), 0, false);
			addMenuItem(updateMenu, LocaleManager.resources.getString(MENU_UPDATE_FILTER_ALL), 0, false);

			//Menu adminMenu = new Menu(LocaleManager.resources.getString(MENU_ADMIN));
			//addMenuItem(adminMenu, LocaleManager.resources.getString(MENU_CREATE), KeyEvent.VK_C, false);

			//mbar.add(adminMenu);
		//}
		
		Menu helpMenu = new Menu(LocaleManager.resources.getString(MENU_HELP));
		addMenuItem(helpMenu, LocaleManager.resources.getString(MENU_ABOUT), 0, false);

		mbar.setHelpMenu(helpMenu);
		gui.setMenuBar(mbar);
	}

	/**
	 * Utility method to create menu items that use the item's label (text) as its
	 * action command.
	 * @param menu the parent menu.
	 * @param label the menu item's label and action command.
	 * @param shortcut
	 * @param shift
	 */
	void addMenuItem(Menu menu, String label, int shortcut, boolean shift) {
		MenuItem item;
		if (shortcut==0)
			item = new MenuItem(label);
		else {
			if (shift) {
				item = new MenuItem(label, new MenuShortcut(shortcut, true));
			} else {
				item = new MenuItem(label, new MenuShortcut(shortcut));
			}
		}
		item.setActionCommand(label);
		menu.add(item);
		item.addActionListener(this);
	}

	/**
	 * Returns menu bar to the GUI.
	 * @return
	 */
	public static MenuBar getMenuBar() {
		return mbar;
	}

	/**
	 * Class that gets/marks absentees as processed in a background thread.
	 * TODO:  Doesn't properly show progress.  Do it!
	 *
	 *
	 */
	class Task extends SwingWorker<Void, Void> {
		@Override
		public Void doInBackground() {
			setProgress(2);
			gui.readMessagesIntoGUI(DbSource.DATA_BASE, AttributeManager.MessageStatus.NEW,
					AttributeManager.MessageType.ATTENDANCE);
			setProgress(100);
			return null;
		}
		@Override
		public void done() {
			progressMonitor.setProgress(100);
		}
	}
	
	 /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName() ) {
            int progress = (Integer) evt.getNewValue();
            progressMonitor.setProgress(progress);
            String message =
                String.format("Completed %d%%.\n", progress);
            progressMonitor.setNote(message);
            //taskOutput.append(message);
            if (progressMonitor.isCanceled() || task.isDone()) {
                Toolkit.getDefaultToolkit().beep();
                if (progressMonitor.isCanceled()) {
                    task.cancel(true);
                    //taskOutput.append("Task canceled.\n");
                } else {
                	progressMonitor.close();
                    //taskOutput.append("Task completed.\n");
                }
                //startButton.setEnabled(true);
            }
        }
 
    }
 
	/**
	 * Handles all Menu actions
	 */
	public void actionPerformed(ActionEvent e) {
		if ((e.getSource() instanceof MenuItem)) {

			String selectedMenuItemText = e.getActionCommand();
			System.out.println("Doing Menu Item:  " + selectedMenuItemText);

			if (selectedMenuItemText.equals(LocaleManager.resources.getString(MENU_ABOUT))) {
				gui.showAboutBox();
				createMenuBar();
				gui.setMenuBar(Menus.getMenuBar());	
				gui.repaint();				
			}
			//else if (selectedMenuItemText.equals(LocaleManager.resources.getString(MENU_OPEN_FILE)))  {
			//	gui.readMessagesIntoGUI(DbSource.FILE);
			//} 
			else if (selectedMenuItemText.equals(LocaleManager.resources.getString(MENU_OPEN_DB))){
				gui.chooseDb();
			}
			else if (selectedMenuItemText.equals(LocaleManager.resources.getString(MENU_ATTENDANCE))) {
				progressMonitor = new ProgressMonitor(gui, "Getting absentees", "", 0, 100);
				progressMonitor.setMillisToPopup(0); // Pop up immediately
				progressMonitor.setMillisToDecideToPopup(0);
				progressMonitor.setProgress(0);
				task = new Task();
				task.addPropertyChangeListener(this);
				task.execute();
				//startButton.setEnabled(false);
				//gui.readMessagesIntoGUI(DbSource.DATA_BASE, AttributeManager.MessageStatus.NEW, AttributeManager.MessageType.ATTENDANCE);
			}
			else if (selectedMenuItemText.equals(LocaleManager.resources.getString(MENU_BENEFICIARY_FILTER_NEW))) {
				gui.readMessagesIntoGUI(DbSource.DATA_BASE, AttributeManager.MessageStatus.NEW, AttributeManager.MessageType.REGISTRATION);
				createMenuBar();
				gui.setMenuBar(Menus.getMenuBar());	
				gui.repaint();
			} 
			else if (selectedMenuItemText.equals(LocaleManager.resources.getString(MENU_BENEFICIARY_FILTER_PENDING))) {
				gui.readMessagesIntoGUI(DbSource.DATA_BASE, AttributeManager.MessageStatus.PENDING, AttributeManager.MessageType.REGISTRATION);
				createMenuBar();
				gui.setMenuBar(Menus.getMenuBar());	
				gui.repaint();
			} 
			else if (selectedMenuItemText.equals(LocaleManager.resources.getString(MENU_BENEFICIARY_FILTER_PROCESSED))) {
				gui.readMessagesIntoGUI(DbSource.DATA_BASE, AttributeManager.MessageStatus.PROCESSED, AttributeManager.MessageType.REGISTRATION);
				createMenuBar();
				gui.setMenuBar(Menus.getMenuBar());	
				gui.repaint();
			} 
//			else if (selectedMenuItemText.equals(LocaleManager.resources.getString(MENU_BENEFICIARY_FILTER_ARCHIVED))) {
//				gui.readMessagesIntoGUI(DbSource.DATA_BASE, AttributeManager.MessageStatus.ARCHIVED, AttributeManager.MessageType.REGISTRATION);
//				createMenuBar();
//				gui.setMenuBar(Menus.getMenuBar());	
//				gui.repaint();
//			}  
			else if (selectedMenuItemText.equals(LocaleManager.resources.getString(MENU_BENEFICIARY_FILTER_ALL))) {
				gui.readMessagesIntoGUI(DbSource.DATA_BASE, AttributeManager.MessageStatus.ALL, AttributeManager.MessageType.REGISTRATION);
				createMenuBar();
				gui.setMenuBar(Menus.getMenuBar());	
				gui.repaint();
			}  
			else if (selectedMenuItemText.equals(LocaleManager.resources.getString(MENU_UPDATE_FILTER_NEW))) {
				gui.readMessagesIntoGUI(DbSource.DATA_BASE, AttributeManager.MessageStatus.UPDATED, AttributeManager.MessageType.REGISTRATION);
				createMenuBar();
				gui.setMenuBar(Menus.getMenuBar());	
				gui.repaint();
			} 
			else if (selectedMenuItemText.equals(LocaleManager.resources.getString(MENU_UPDATE_FILTER_PENDING))) {
				gui.readMessagesIntoGUI(DbSource.DATA_BASE, AttributeManager.MessageStatus.PENDING, AttributeManager.MessageType.UPDATE);
				createMenuBar();
				gui.setMenuBar(Menus.getMenuBar());	
				gui.repaint();
			} 
			else if (selectedMenuItemText.equals(LocaleManager.resources.getString(MENU_UPDATE_FILTER_PROCESSED))) {
				gui.readMessagesIntoGUI(DbSource.DATA_BASE, AttributeManager.MessageStatus.PROCESSED, AttributeManager.MessageType.UPDATE);
				createMenuBar();
				gui.setMenuBar(Menus.getMenuBar());	
				gui.repaint();
			} 
//			else if (selectedMenuItemText.equals(LocaleManager.resources.getString(MENU_UPDATE_FILTER_ARCHIVED))) {
//				gui.readMessagesIntoGUI(DbSource.DATA_BASE, AttributeManager.MessageStatus.ARCHIVED, AttributeManager.MessageType.UPDATE);
//				createMenuBar();
//				gui.setMenuBar(Menus.getMenuBar());	
//				gui.repaint();
//			}  
			else if (selectedMenuItemText.equals(LocaleManager.resources.getString(MENU_UPDATE_FILTER_ALL))) {
				gui.readMessagesIntoGUI(DbSource.DATA_BASE, AttributeManager.MessageStatus.ALL, AttributeManager.MessageType.UPDATE);
				createMenuBar();
				gui.setMenuBar(Menus.getMenuBar());	
				gui.repaint();
			}  
			else if (selectedMenuItemText.equals(LocaleManager.resources.getString(MENU_ALL))) {
				gui.readMessagesIntoGUI(DbSource.DATA_BASE, AttributeManager.MessageStatus.ALL, AttributeManager.MessageType.ALL);
				createMenuBar();
				gui.setMenuBar(Menus.getMenuBar());	
				gui.repaint();
			}
			//else if (selectedMenuItemText.equals(LocaleManager.resources.getString(MENU_CREATE))) {
			//	User newUser = new User(gui);
			//} 
			else if (selectedMenuItemText.equals(LocaleManager.resources.getString(MENU_ARCHIVE_DB))) {
			} 
			else if (selectedMenuItemText.equals(LocaleManager.resources.getString(MENU_QUIT))) {
				gui.quit();
			} 
			else if (selectedMenuItemText.equals(LocaleManager.resources.getString(MENU_ENGLISH))) {
				LocaleManager.currentLocale = Locale.ENGLISH;
				LocaleManager.resources = ResourceBundle.getBundle("MenusBundle", LocaleManager.currentLocale);
				System.out.println("Changing language to English "  + LocaleManager.currentLocale.toString());
				gui.refreshLocale();
				createMenuBar();
				gui.setMenuBar(Menus.getMenuBar());	 
				gui.repaint();
			}
			else if (selectedMenuItemText.equals(LocaleManager.resources.getString(MENU_FRENCH))) {
				LocaleManager.currentLocale = Locale.FRENCH;
				LocaleManager.resources = ResourceBundle.getBundle("MenusBundle", LocaleManager.currentLocale);
				System.out.println("Changing language to French " + LocaleManager.currentLocale.toString());
				gui.refreshLocale();
				createMenuBar();
				gui.setMenuBar(Menus.getMenuBar());	
				gui.repaint();
			}
		} 
	}
}
