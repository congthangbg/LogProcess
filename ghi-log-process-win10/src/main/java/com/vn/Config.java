package com.vn;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jutils.jprocesses.JProcesses;
import org.jutils.jprocesses.model.ProcessInfo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import sun.management.VMManagement;

@Configuration
@EnableScheduling
public class Config {
	@Value("${url.api}")
	String ApiUrl1;
	private org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Config.class);

	@Autowired
	private ThreadPoolTaskScheduler scheduledTask;

	public static final String CHECK_BKAV = "Bkav";

	@SuppressWarnings("unused")
	@Scheduled(fixedDelayString = "${time.schedule}")
	public void scheduleFixedDelayTask() throws UnknownHostException, AWTException, InterruptedException {
		try {
			
			// check chương trình đã được cài đặt chưa
			List<String> ls = null;// WinRegistry.readStringSubKeys(WinRegistry.HKEY_LOCAL_MACHINE, "SOFTWARE\\" +
									// CHECK_BKAV);

			ObjectMapper mapper2 = new ObjectMapper();
			// Call server
			ObjectNode nodeServer = mapper2.createObjectNode();
			String user = System.getProperty("user.name");

			// Tạo Object Node
			ObjectNode node = mapper2.createObjectNode();
			// Đẩy thông tin vào Object Node
//			node.put("PID", getCurrentProcessId());
			Map<String, Boolean> map = listIp2();
			ArrayNode arrayNodeIp = mapper2.createArrayNode();
			ArrayNode arrayNodeIp2 = mapper2.createArrayNode();
			for (String i : map.keySet()) {
				if (map.get(i) == true) {
					nodeServer.put("ip", i);
					arrayNodeIp2.add(i);
				}
				if (map.get(i) == false) {
					arrayNodeIp.add(i);
				}
			}
			node.put("ip", arrayNodeIp2);
			node.put("ipTinh", arrayNodeIp.toString());
			nodeServer.put("ipTinh", arrayNodeIp.toString());

//            node.put("IP", arrayNode1);
			// Tạo Array Node
			ArrayNode arrayNode = mapper2.createArrayNode();

			List<ProcessInfo> processesList = JProcesses.getProcessList();
			nodeServer.put("userLocal", user);
			node.put("userLocal", user);
			// Lấy list Process đang run

			String cmd = "TASKLIST /V /FO LIST";
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			List<String> lst = Arrays.asList("csrss.exe", "svchost.exe", "fontdrvhost.exe", "igfxCUIService.exe",
					"spoolsv.exe", "MsMpEng.exe", "oravssw.exe", "omtsreco.exe", "WmiPrvSE.exe", "NisSrv.exe",
					"dllhost.exe", "sihost.exe", "taskhostw.exe", "igfxEM.exe", "igfxHK.exe", "igfxTray.exe",
					"explorer", "GoogleCrashHandler.exe", "RuntimeBroker.exe", "browser_broker.exe", "dwm.exe",
					"SecurityHealthSystray.exe", "RtkNGUI64.exe", "smss.exe", "winlogon.exe",
					"StartMenuExperienceHost.exe", "ApplicationFrameHost.exe", "SkypeBackgroundHost.exe",
					"SecurityHealthService.exe", "RAVBg64.exe", "jusched.exe", "TscHelp.exe", "SnagPriv.exe",
					"splwow64.exe", "WinStore.App.exe", "SgrmBroker.exe", "ShellExperienceHost.exe", "dwm.exe",
					"splwow64.exe", "explorer.exe", "TscHelp.exe", "jusched.exe", "javaw.exe", "conhost.exe",
					"System Idle Process", "System", "Registry", "wininit.exe", "lsass.exe", "WmiApSrv.exe",
					"SearchFilterHost.exe", "ctfmon.exe", "SystemSettings.exe", "SystemPropertiesAdvanced.exe",
					"LockApp.exe", "MpCopyAccelerator.exe", "GoogleCrashHandler64.exe", "services.exe", "sppsvc.exe",
					"TiWorker.exe", "Memory Compression", "SecurityHealthHost.exe", "WUDFHost.exe",
					"IntelCpHDCPSvc.exe", "WindowsInternal.ComposableShell.Experiences.TextInput.InputApp.exe",
					"audiodg.exe", "Microsoft.Photos.exe", "SnagitEditor.exe", "Snagit32.exe", "SearchIndexer.exe",
					"MicrosoftEdge.exe", "MicrosoftEdgeCP.exe", "MicrosoftEdgeSH.exe", "TrustedInstaller.exe",
					"java.exe", "dasHost.exe", "tibrs.exe", "fsnotifier64.exe", "WINWORD.EXE", "OfficeClickToRun.exe");
//			if (ls != null) {
			for (ProcessInfo processInfo2 : processesList) {
				ObjectNode node2 = mapper2.createObjectNode();
				if (!lst.contains(processInfo2.getName())) {
					node2.put("Pid", processInfo2.getPid());
					node2.put("Name", processInfo2.getName());
					node2.put("Used Time", processInfo2.getTime() + "");
					node2.put("Full command", processInfo2.getCommand() + "");
					arrayNode.add(node2);
				}
			}
//			} else {
//				try {
//					String message = "Bạn chưa cài " + CHECK_BKAV + " !";
//					displayTray(message);
//				} catch (Exception e) {
//					throw new RuntimeException(e);
//				}
//			}

			input.close();
			if (ls == null) {
				node.put("setup", "Chưa cài " + CHECK_BKAV);
			}else {
				node.put("setup", "");
			}
			//Check connect internet
			List<String> listCheckConnect = checkConnectInternet();
			String joinedString = (listCheckConnect.toString());
			if(!joinedString.contains("Ping request could not find host google.com")) {
				node.put("connect", "Connect Internet");
			}else {
				node.put("connect", "");
			}
			// write to String
			node.put("json", arrayNode);

			String json2 = mapper2.writeValueAsString(node);

			nodeServer.put("json", json2);
			String json3 = mapper2.writeValueAsString(nodeServer);

//			Boolean a = sendData(json2);

			// Write to File
			String pattern = "yyyy_MM_dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(new Date());

//            final Path path = Paths.get("../TestProcess/src/main/resources/log/" + date + ".json");

			final Path path = Paths.get("logs/" + date + ".json");

			Files.write(path, Arrays.asList(json2), StandardCharsets.UTF_8,
					Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public void displayTray(String message) throws AWTException {
		SystemTray tray = SystemTray.getSystemTray();

		Image image = Toolkit.getDefaultToolkit().createImage("icon.png");

		TrayIcon trayIcon = new TrayIcon(image, "Thông báo");
		trayIcon.setImageAutoSize(true);

		trayIcon.setToolTip("Hệ thống thông báo lỗi!");
		tray.add(trayIcon);
		trayIcon.displayMessage("Thông báo", message, MessageType.ERROR);
	}

	public boolean sendData(String data)
			throws NoSuchAlgorithmException, KeyManagementException, IOException, InterruptedException {

//        String r1 = "\\\"";
//        String r2 = "\"{";
//        String r3 = "}\"";
//        String r4 = "\\\\\\\\";
//        data = data.replace(r1, "\"");
//        data = data.replace(r2, "{");
//        data = data.replace(r3, "}");
//        data = data.replace(r4, "\\\\");

//      

//        String ApiUrl = "http://10.0.50.128:7003/test/api/v1/getLog";
		String ApiUrl = ApiUrl1 + "/api/v1/getLog";
//		try {
//			ApiUrl = readFileToString("api.txt");
//		} catch (IOException ex) {
//			java.util.logging.Logger.getLogger(sendData.class.getName()).log(Level.SEVERE, null, ex);
//		}

		if (ApiUrl.trim().equals("")) {
			return false;
		} else {
			ApiUrl = ApiUrl.trim();
		}

		// Kiểm tra kết nối
		boolean testURL = false;
		try {
			URL url = new URL(ApiUrl);
			URLConnection connection = url.openConnection();
			connection.connect();
			testURL = true;
		} catch (MalformedURLException e) {
			System.out.println("Not connected: " + ApiUrl);
			testURL = false;
		} catch (IOException e) {
			System.out.println("Not connected: " + ApiUrl);
			testURL = false;
		}

		if (testURL == false) {
			return false;
		}

		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			HttpPost post = new HttpPost(ApiUrl);
			post.setHeader("User-Agent", "Java client");
			post.setEntity(new StringEntity(data));
			org.apache.http.HttpResponse response = client.execute(post);
			BufferedReader bufferedReader = new BufferedReader(
					new java.io.InputStreamReader(response.getEntity().getContent()));
			StringBuilder builder = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				builder.append(line);
				builder.append(System.lineSeparator());

			}
			System.out.println("------>Call server success!");
			return true;
		} catch (Exception e) {
			System.out.println("-------->Call server error!");
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unused")
	private static int getCurrentProcessId()
			throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		Field jvm = runtime.getClass().getDeclaredField("jvm");
		jvm.setAccessible(true);
		@SuppressWarnings("restriction")
		VMManagement management = (VMManagement) jvm.get(runtime);
		Method method = management.getClass().getDeclaredMethod("getProcessId");
		method.setAccessible(true);
		return (int) method.invoke(management);
	}

	static public String readFileToString(String filename) throws FileNotFoundException, IOException {
		File file = new File(filename);
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();

		String str = new String(data, "UTF-8");
		return str;
	}

	public Map<String, Boolean> listIp2() throws IOException {
		String line;
		String line1 = "ipconfig /all";
		Map<String, Boolean> map = new HashMap<>();

		Boolean flag = null;

		Process p = Runtime.getRuntime().exec(line1);
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while ((line = input.readLine()) != null) {
			if (line.contains("DHCP Enabled")) {
				String a = line.substring(line.indexOf(":") + 1).trim();
				flag = true;
				if (a.equals("No")) {
					flag = false;
				}
			}
			if (line.contains("IPv4 Address")) {

				String a = line.substring(line.indexOf(":") + 1).trim();
				if(a.contains("(Preferred)")) {
					 a = line.substring(line.indexOf(":") + 1).trim().replace("(Preferred)", "");
				}
				
				if (flag != null) {
					map.put(a, flag);
				}
				flag = null;
			}
		}
//		map.put("10.0.50.178", false);
//		map.put("10.0.50.179", false);
//		System.out.println(map);
		input.close();
		return map;
	}
	
	public List<String> checkConnectInternet() throws IOException {
		String line;
		String line1 = "ping google.com";
		List<String> a = new ArrayList<>();
		Process p = Runtime.getRuntime().exec(line1);
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while ((line = input.readLine()) != null) {
			
			a.add(line);
		}
		input.close();
		return a;
	}

}
