package com.vn;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class test {
	public static void main(String[] args) throws ParseException, IOException, AWTException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		String line;
		String line1 = "ping google.com";
		List<String> a = new ArrayList<>();
		Process p = Runtime.getRuntime().exec(line1);
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while ((line = input.readLine()) != null) {
			
			a.add(line);
		}
		input.close();
		String joinedString = (a.toString());
		System.out.println(joinedString.replace("[", ""));
		// cách 2
//		ProcessHandle.allProcesses()
//        .forEach(process -> System.out.println(processDetails(process)));

		// cách 3
//		List<ProcessInfo> processesList = JProcesses.getProcessList();
//
//		for (final ProcessInfo processInfo : processesList) {
//		    System.out.println("Process PID: " + processInfo.getPid());
//		    System.out.println("Process Name: " + processInfo.getName());
//		    System.out.println("Process Used Time: " + processInfo.getTime());
//		    System.out.println("Full command: " + processInfo.getCommand());
//		    System.out.println("------------------"+processesList.size());
//		}
//		
//		ProcessHandle processHandle = ProcessHandle.current();
//	    ProcessHandle.Info processInfo = processHandle.info();
//
//	    System.out.println("PID: " + processHandle.pid());
//	    System.out.println("Arguments: " + processInfo.arguments());
//	    System.out.println("Command: " + processInfo.command());
//	    System.out.println("Instant: " + processInfo.startInstant());
//	    System.out.println("Total CPU duration: " + processInfo.totalCpuDuration());
//	    System.out.println("User: " + processInfo.user());

	}

	public void displayTray() throws AWTException {
		Thread thread = new Thread() {
			public void run() {
				SystemTray tray = SystemTray.getSystemTray();

				Image image = Toolkit.getDefaultToolkit().createImage("icon.png");

				TrayIcon trayIcon = new TrayIcon(image, "Tray demo");
				trayIcon.setImageAutoSize(true);

				trayIcon.setToolTip("System tray icon demo");
				try {
					tray.add(trayIcon);
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				trayIcon.displayMessage("Hello, world", "Notifycation demo", MessageType.WARNING);
			};
		};
		thread.start();

	}

}
