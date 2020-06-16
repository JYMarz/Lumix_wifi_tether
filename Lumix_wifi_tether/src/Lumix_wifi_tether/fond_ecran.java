package Lumix_wifi_tether;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.lang.reflect.Method;
import javax.swing.AbstractButton;

import java.io.File;
import javax.swing.border.LineBorder;

import org.apache.commons.codec.binary.StringUtils;

import java.io.PrintWriter;
import java.util.*; 
import java.nio.charset.StandardCharsets; 
import java.nio.file.*; 
import java.io.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent; 
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import java.awt.FlowLayout;
import java.awt.Component;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JToggleButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class fond_ecran extends JFrame implements ActionListener{

	private JPanel contentPane;
	private String myChoice;
	private JPanel panel ;
    private static Thread streamViewerThread;
	private static Wifi_lumix wl;
	private static List<ArrayList<String>>listOfCommands;
	private static String bluetoothAdress;
	private static String comUp;
	private static String comDown;
	private static String comLeft;
	private static String comRight;
	private static String comGet;
	private static String cameraIp;
	private static int cameraNetMaskBitSize ;
	private static int udpinit;
	private JTextField txtIso;
	private JTextField txtSettings;
	private JTextField txtSize;
	private JTextField txtQuality;
	private JTextField txtLightMett;
	private JTextField txtVideoQual;
	private JTextField txtExposure;
	private JTextField txtAutoFocus;
	private JTextField txtDriveMode;
	private JTextField txtFilterSet;
	private JTextField txtColorMode;
	private JTextField txtGetSettings;
	private JComboBox comboBox_size;
	private JComboBox comboBox_quality;
	private JComboBox comboBox_light;
	private JComboBox comboBox_video;
	private JComboBox comboBox_expo;
	private JComboBox comboBox_afocus;
	private JComboBox comboBox_drivemode;
	private JComboBox comboBox_filter;
	private JComboBox comboBox_iso;
	private JComboBox comboBox_color;
	private JComboBox comboBox_whibal;
	private static String[] comboList;
	public static boolean notdev=true; 
	private static File f;
	private JTextField textField;
	private JTextField textField_1;
	private JComboBox comboBox_color_1;
	private JButton btnTake;
	private JButton btnVidStop;
	private JButton btnLeft;
	public boolean blue;
	public boolean yolo;
	private JButton btnRight;
	private JButton btnUp;
	private JButton btnDown;
	private JTextField txtBluetoothHcMoves;
	private JButton btnNewButton_1;
	private javax.swing.Timer t;
	private String BatVal;
	private JTextField TextBatVal;
	private int tCount;
	private	byte[] blueAns; 
	private JTextField textBlue;
	private String blueString;
	private JTextField textBlue2;
	private JToggleButton  tglbtnSound;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup2 = new ButtonGroup();
	public static AtomicInteger vidChoice = new AtomicInteger(0);
	public static AtomicBoolean sound = new AtomicBoolean(false);
	public static AtomicBoolean motionDetect = new AtomicBoolean(true);
	public static AtomicInteger maxArea = new AtomicInteger(100);
	public static AtomicInteger threshVal = new AtomicInteger(2);
	public static AtomicInteger blurSize = new AtomicInteger(3);
	public static AtomicInteger threshArea = new AtomicInteger(5);
	private JTextField textArea;
	private JTextField txtBlur;
	private JSlider slider_1;
	private JTextField txtThresh;
	private JSlider slider_2;
	private JTextField txtMeant;
	private JSlider slider_3;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String[] arg={"192.168.54.1","12","60606","true","false","false"};;
					if (args.length != 0) {
						 arg= args;
					}

					fond_ecran frame = new fond_ecran(arg);
			        frame.pack();
			        frame.setExtendedState( frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );
					frame.setVisible(true);
				if (notdev)	 send("mode=startstream&value=49199");
				frame.panel.repaint();

				} catch (Exception e) {
					e.printStackTrace();
				}


			}
		});
	}


	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws SocketException 
	 * @throws UnknownHostException 
	 * @throws NumberFormatException 
	 * @throws InterruptedException 
	 * @wbp.parser.constructor
	 */
	@SuppressWarnings("rawtypes")
	public fond_ecran(String[] args) throws NumberFormatException, UnknownHostException, SocketException, IOException, InterruptedException,NoSuchMethodException {

		setTitle("Fenetre");
		Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0,screen.width-100,screen.height - 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 1060, 632);
	    setMinimumSize(new Dimension(451, 434));
	    setResizable(true);
	    blueAns = new byte[200];
	    vidChoice.set(1);
//	    String path = fond_ecran.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString();
//	    String path2=path.substring(1, path.substring(0, path.lastIndexOf('/')).lastIndexOf('/')+1).replace("/","\\");
//		System.out.println(path);
//		System.out.println(path2);


//		System.out.println("file "+path2+"menus.txt exists="+fmenu);

// comboList is a global variable containing the parameters which will be put in the comBoBoxes
	    
		comboList= new String[] {"iso","whitebalance","lightmetering","afmode","exposure","videoquality","quality","pictsize","filter_setting","colormode","drivemode"};
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		panel= new JPanel();
		VideoPanel 	videoPanel = new VideoPanel();
		FlowLayout flowLayout = (FlowLayout) videoPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		if (notdev) {
	        Options options = Options.read(args);
	        cameraIp = options.getCameraIp();
	        cameraNetMaskBitSize = options.getCameraNetMaskBitSize();
	        udpinit= options.getudpinit();
	        blue=options.getblue();
	        yolo=options.getyolo();
	        notdev=options.getdev(); 
		}
// Initiate connection and start video stream		
		if (notdev) wl = new Wifi_lumix(cameraIp,80,60606);
		TimeUnit.SECONDS.sleep(1);
// the menus.txt file contains the different options for a given parameter
		f=new File("menus.txt");
//		blue=true;

//		boolean fmenu=f.exists();
		//System.out.println("fmenu "+ fmenu);
//if not in development mode, this list will be read from the camera and  menus.txt will be replaced or created in populateComboMenus
//data is a single string containing all menus.txt content		
		String data=""; 
		if (notdev) populateComboMenus();
		data = new String(Files.readAllBytes(Paths.get("menus.txt")));
			    	System.out.println(data);
		if (notdev) {

// check if bluetooth device can be connected	        
			if (blue) {		
		        List <String> blues=new ArrayList<>();
		        try (Stream<String> stream = Files.lines(Paths.get("blueconfig.txt"))) {
					blues = stream.collect(Collectors.toList());
//					System.out.println(blues);
//					System.out.println(stream);
					if (blues.size() !=0) {
						bluetoothAdress=blues.get(2);
						comUp= blues.get(3);
						comDown=blues.get(4);
						comLeft=blues.get(5);
						comRight=blues.get(6);
						comGet=blues.get(7);
					}
//					System.out.println(bluetoothAdress);

				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("File not found");
				}
			
				try {
					
				HC05 hc=new HC05(bluetoothAdress);
				if ((HC05.os==null) ) blue=false;
				} catch (Exception e) {
					e.printStackTrace();
					blue=false;
				}
			}
			System.out.println("blue="+Boolean.toString(blue));
// try to start the viewer 
	        System.out.println("Trying to connect to camera " + cameraIp + " on subnet with mask size " +
	                cameraNetMaskBitSize);
	        try {
	            StreamViewer streamViewer = new StreamViewer(videoPanel::displayNewImage, cameraIp, cameraNetMaskBitSize,this,yolo);
	            streamViewerThread = new Thread(streamViewer);
	            streamViewerThread.start();
	        } catch (SocketException e) {
	            System.out.println("Socket creation error : " + e.getMessage());
	            System.exit(1);
	        } catch (UnknownHostException e) {
	            System.out.println("Cannot parse camera IP address: " + cameraIp + ".");
	            System.exit(2);
	        }
		}
// set all the comboBoxes buttons and text fields
/*
 For each ComboBox, a listener catches the change in selection and send the according command to the camera
 The initial list is set up either from the camera menus (see function GetMenuItems or from the file menus.txt (if notdev =false)
 the initially selected item is set after getting the camera value (or
 */
		comboBox_iso = new JComboBox();
		comboBox_iso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			myChoice= (String) comboBox_iso.getSelectedItem();
//			System.out.println("*"+"mode=setsetting&type=iso&value="+myChoice.replace(" ", "&value2=")+"*");
			if(notdev) send("mode=setsetting&type=iso&value="+myChoice.replace(" ", "&value2="));
			}
		});
		comboBox_iso.setBounds(126, 329, 140, 19);
		String[] listOfItems=new String[0];
		listOfItems= GetMenuItems(data," iso ");
//		for (int j=0; j<listOfItems.length;j++) System.out.println(listOfItems);
//		String[] list2 = new String[] {"toto", "truc"};
		comboBox_iso.setModel(new DefaultComboBoxModel(listOfItems));
		String iso_select=receiveGetting("iso");
		if (iso_select.length() >0)	comboBox_iso.setSelectedItem(iso_select);
		if (listOfItems.length<2) comboBox_iso.setEnabled(false);
//		TimeUnit.MILLISECONDS.sleep(200);

		contentPane.add(comboBox_iso);
		

		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(381, 53, 650, 434);

		
		JButton btnNewButton = new JButton("Restart Video Flux");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton rdbtnNewRadioNormal = new JRadioButton("Normal");
		rdbtnNewRadioNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnNewRadioNormal.isSelected()) vidChoice.set(1);
			}
		});
		rdbtnNewRadioNormal.setSelected(true);
		buttonGroup.add(rdbtnNewRadioNormal);
		rdbtnNewRadioNormal.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(rdbtnNewRadioNormal);
		
		JRadioButton rdbtnNewRadioBlur = new JRadioButton("Blur");
		rdbtnNewRadioBlur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnNewRadioBlur.isSelected()) vidChoice.set(2);
			}
		});
		buttonGroup.add(rdbtnNewRadioBlur);
		panel.add(rdbtnNewRadioBlur);
		
		JRadioButton rdbtnNewRadioDiff = new JRadioButton("Diff");
		rdbtnNewRadioDiff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnNewRadioDiff.isSelected()) vidChoice.set(3);
			}
		});
		buttonGroup.add(rdbtnNewRadioDiff);
		panel.add(rdbtnNewRadioDiff);
		panel.add(btnNewButton);
		panel.add(videoPanel);
		contentPane.add(panel);	
		txtIso = new JTextField();
		txtIso.setEditable(false);
		txtIso.setText("ISO");
		txtIso.setBounds(40, 328, 76, 20);
		contentPane.add(txtIso);
		txtIso.setColumns(10);
		
		txtSettings = new JTextField();
		txtSettings.setEditable(false);
		txtSettings.setText("Settings");
		txtSettings.setBounds(95, 298, 48, 20);
		contentPane.add(txtSettings);
		txtSettings.setColumns(10);
		
		txtSize = new JTextField();
		txtSize.setEditable(false);
		txtSize.setText("Size");
		txtSize.setBounds(40, 359, 76, 25);
		contentPane.add(txtSize);
		txtSize.setColumns(10);
		
		comboBox_size = new JComboBox();
		comboBox_size.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myChoice=(String) comboBox_size.getSelectedItem();
				System.out.println("*"+"mode=setsetting&type=pictsize&value="+myChoice.replace(" ", "&value2=")+"*");
				if(notdev) send("mode=setsetting&type=pictsize&value="+myChoice.replace(" ", "&value2="));
			}
		});
		comboBox_size.setBounds(126, 360, 140, 22);
		listOfItems=new String[0];
		listOfItems= GetMenuItems(data," pictsize ");
		comboBox_size.setModel(new DefaultComboBoxModel(listOfItems));
		String siz_select=receiveGetting("pictsize");
		if (siz_select.length() >0)	comboBox_size.setSelectedItem(siz_select);
		if (listOfItems.length<2) comboBox_size.setEnabled(false);
		contentPane.add(comboBox_size);
		
		txtQuality = new JTextField();
		txtQuality.setEditable(false);
		txtQuality.setText("Quality");
		txtQuality.setColumns(10);
		txtQuality.setBounds(40, 392, 76, 25);
		contentPane.add(txtQuality);
		
		comboBox_quality = new JComboBox();
		comboBox_quality.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myChoice=(String) comboBox_quality.getSelectedItem();
				System.out.println("*"+"mode=setsetting&type=quality&value="+myChoice.replace(" ", "&value2=")+"*");
				if(notdev) send("mode=setsetting&type=quality&value="+myChoice.replace(" ", "&value2="));
			}
		});
		comboBox_quality.setBounds(126, 393, 140, 22);
		listOfItems=new String[0];
		listOfItems= GetMenuItems(data," quality ");

		comboBox_quality.setModel(new DefaultComboBoxModel(listOfItems));
		String qual_select=receiveGetting("quality");
		if (qual_select.length() >0)	comboBox_quality.setSelectedItem(qual_select);
		if (listOfItems.length<2) comboBox_quality.setEnabled(false);
		contentPane.add(comboBox_quality);
		
		txtLightMett = new JTextField();
		txtLightMett.setEditable(false);
		txtLightMett.setText("Light Mt");
		txtLightMett.setColumns(10);
		txtLightMett.setBounds(40, 431, 76, 25);
		contentPane.add(txtLightMett);
		
		comboBox_light = new JComboBox();
		comboBox_light.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myChoice=(String) comboBox_light.getSelectedItem();
				System.out.println("*"+"mode=setsetting&type=lightmetering&value="+myChoice.replace(" ", "&value2=")+"*");
				if(notdev) send("mode=setsetting&type=lightmetering&value="+myChoice.replace(" ", "&value2="));
			}
		});
		comboBox_light.setBounds(126, 432, 140, 22);
		listOfItems=new String[0];
		listOfItems= GetMenuItems(data," lightmetering ");
		comboBox_light.setModel(new DefaultComboBoxModel(listOfItems));
		String light_select=receiveGetting("lightmetering");
		if (light_select.length() >0)	comboBox_light.setSelectedItem(light_select);
		if (listOfItems.length<2) comboBox_light.setEnabled(false);
		contentPane.add(comboBox_light);
		
		txtVideoQual = new JTextField();
		txtVideoQual.setEditable(false);
		txtVideoQual.setText("Video Qal");
		txtVideoQual.setColumns(10);
		txtVideoQual.setBounds(40, 467, 76, 25);
		contentPane.add(txtVideoQual);
		
		comboBox_video = new JComboBox();
		comboBox_video.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myChoice=(String) comboBox_video.getSelectedItem();
				System.out.println("*"+"mode=setsetting&type=videoquality&value="+myChoice.replace(" ", "&value2=")+"*");
				if(notdev) send("mode=setsetting&type=videoquality&value="+myChoice.replace(" ", "&value2="));
			}
		});
		comboBox_video.setBounds(126, 468, 140, 22);
		listOfItems=new String[0];
		listOfItems= GetMenuItems(data," videoquality ");
		comboBox_video.setModel(new DefaultComboBoxModel(listOfItems));
		String vid_select=receiveGetting("videoquality");
		if (vid_select.length() >0)	comboBox_video.setSelectedItem(vid_select);
		if (listOfItems.length<2) comboBox_video.setEnabled(false);
		contentPane.add(comboBox_video);
		
		txtExposure = new JTextField();
		txtExposure.setEditable(false);
		txtExposure.setText("Exposure");
		txtExposure.setColumns(10);
		txtExposure.setBounds(40, 503, 76, 25);
		contentPane.add(txtExposure);
		
		comboBox_expo = new JComboBox();
		comboBox_expo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myChoice=(String) comboBox_expo.getSelectedItem();
				System.out.println("*"+"mode=setsetting&type=exposure&value="+myChoice.replace(" ", "&value2=")+"*");
				if(notdev) send("mode=setsetting&type=exposure&value="+myChoice.replace(" ", "&value2="));
			}
		});
		comboBox_expo.setBounds(126, 504, 140, 22);
		listOfItems=new String[0];
		listOfItems= GetMenuItems(data," exposure ");
		comboBox_expo.setModel(new DefaultComboBoxModel(listOfItems));
		String exp_select=receiveGetting("exposure");
		if (exp_select.length() >0)	comboBox_expo.setSelectedItem(exp_select);
		if (listOfItems.length<2) comboBox_expo.setEnabled(false);
		contentPane.add(comboBox_expo);
		
		txtAutoFocus = new JTextField();
		txtAutoFocus.setEditable(false);
		txtAutoFocus.setText("Auto Focus");
		txtAutoFocus.setColumns(10);
		txtAutoFocus.setBounds(40, 539, 76, 25);
		contentPane.add(txtAutoFocus);
		
		comboBox_afocus = new JComboBox();
		comboBox_afocus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myChoice=(String) comboBox_afocus.getSelectedItem();
				System.out.println("*"+"mode=setsetting&type=afmode&value="+myChoice.replace(" ", "&value2=")+"*");
				if(notdev) send("mode=setsetting&type=afmode&value="+myChoice.replace(" ", "&value2="));
			}
		});
		comboBox_afocus.setBounds(126, 540, 140, 22);
		listOfItems=new String[0];
		listOfItems= GetMenuItems(data," afmode ");
		comboBox_afocus.setModel(new DefaultComboBoxModel(listOfItems));
		String af_select=receiveGetting("afmode");
		if (af_select.length() >0)	comboBox_afocus.setSelectedItem(af_select);
//seems trying to get afmode does not work so lets set it to 49 area mode
		else comboBox_afocus.setSelectedItem("49area");
		if (listOfItems.length<2) comboBox_afocus.setEnabled(false);
		contentPane.add(comboBox_afocus);
		
		txtDriveMode = new JTextField();
		txtDriveMode.setEditable(false);
		txtDriveMode.setText("Drive Mode");
		txtDriveMode.setColumns(10);
		txtDriveMode.setBounds(40, 575, 76, 25);
		contentPane.add(txtDriveMode);
		
		comboBox_drivemode = new JComboBox();
		comboBox_drivemode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myChoice=(String) comboBox_drivemode.getSelectedItem();
				System.out.println("*"+"mode=setsetting&type=drivemode&value="+myChoice.replace(" ", "&value2=")+"*");
				if(notdev) send("mode=setsetting&type=drivemode&value="+myChoice.replace(" ", "&value2="));
			}
		});
		comboBox_drivemode.setBounds(126, 576, 140, 22);
		listOfItems=new String[0];
		listOfItems= GetMenuItems(data," drivemode ");
		comboBox_drivemode.setModel(new DefaultComboBoxModel(listOfItems));
		String dri_select=receiveGetting("drivemode");
		if (dri_select.length() >0)	comboBox_drivemode.setSelectedItem(dri_select);
		if (listOfItems.length<2) comboBox_drivemode.setEnabled(false);
		contentPane.add(comboBox_drivemode);
		
		txtFilterSet = new JTextField();
		txtFilterSet.setEditable(false);
		txtFilterSet.setText("Filter Set");
		txtFilterSet.setColumns(10);
		txtFilterSet.setBounds(40, 608, 76, 25);
		contentPane.add(txtFilterSet);
		
		comboBox_filter = new JComboBox();
		comboBox_filter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myChoice=(String) comboBox_filter.getSelectedItem();
				System.out.println("*"+"mode=setsetting&type=filter_setting&value="+myChoice.replace(" ", "&value2=")+"*");
				if(notdev) send("mode=setsetting&type=filter_setting&value="+myChoice.replace(" ", "&value2="));
			}
		});
		comboBox_filter.setBounds(126, 609, 140, 22);
		listOfItems=new String[0];
		listOfItems= GetMenuItems(data," filter_setting ");
		comboBox_filter.setModel(new DefaultComboBoxModel(listOfItems));
		String fil_select=receiveGetting("filter_setting");
		if (fil_select.length() >0)	comboBox_filter.setSelectedItem(fil_select);
		if (listOfItems.length<2) comboBox_filter.setEnabled(false);
		contentPane.add(comboBox_filter);
		
		txtColorMode = new JTextField();
		txtColorMode.setEditable(false);
		txtColorMode.setText("Color Mode");
		txtColorMode.setColumns(10);
		txtColorMode.setBounds(40, 644, 76, 25);
		contentPane.add(txtColorMode);
		
		comboBox_color = new JComboBox();
		comboBox_color.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myChoice=(String) comboBox_color.getSelectedItem();
				System.out.println("*"+"mode=setsetting&type=colormode&value="+myChoice.replace(" ", "&value2=")+"*");
				if(notdev) send("mode=setsetting&type=colormode&value="+myChoice.replace(" ", "&value2="));
	
			}
		});
		comboBox_color.setBounds(126, 645, 140, 22);
		listOfItems=new String[0];
		listOfItems= GetMenuItems(data," colormode ");
		comboBox_color.setModel(new DefaultComboBoxModel(listOfItems));
		String col_select=receiveGetting("colormode");
		if (col_select.length() >0)	comboBox_color.setSelectedItem(col_select);
		if (listOfItems.length<2) comboBox_color.setEnabled(false);
		contentPane.add(comboBox_color);
		
		
		txtGetSettings = new JTextField();
		txtGetSettings.setHorizontalAlignment(SwingConstants.CENTER);
		txtGetSettings.setEditable(false);
		txtGetSettings.setText("Battery rem Charge");
		txtGetSettings.setBounds(5, 31, 111, 20);
		contentPane.add(txtGetSettings);
		txtGetSettings.setColumns(10);

		textField = new JTextField();
		textField.setText("Color Temp");
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(40, 716, 76, 25);
		contentPane.add(textField);
		
		JComboBox comboBox_colortemp = new JComboBox();
		comboBox_colortemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				myChoice=(String) comboBox_colortemp.getSelectedItem();
				System.out.println("*"+"mode=setsetting&type=colormode&value=color_temp&value2="+myChoice+"*");
				if(notdev) send("mode=setsetting&type=colormode&value=color_temp&value2="+myChoice);
	
			}
		});
		comboBox_colortemp.setBounds(126, 717, 140, 22);
		listOfItems=new String[] {"2500","3000","3500","4000","4500","5000","5500","6000","6500","7000","7500","8000","8500","9000","9500","10000"};
		comboBox_colortemp.setModel(new DefaultComboBoxModel(listOfItems));

		contentPane.add(comboBox_colortemp);
		
		textField = new JTextField();
		textField.setText("White Bal");
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(40, 680, 76, 25);
		contentPane.add(textField);
		
		comboBox_whibal = new JComboBox();
		comboBox_whibal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				myChoice=(String) comboBox_whibal.getSelectedItem();
				System.out.println("*"+"mode=setsetting&type=whitebalance&value="+myChoice.replace(" ", "&value2=")+"*");
				if(notdev) send("mode=setsetting&type=whitebalance&value="+myChoice.replace(" ", "&value2="));
	
			}
		});
		comboBox_whibal.setBounds(126, 681, 140, 22);
		listOfItems=new String[0];
		listOfItems= GetMenuItems(data," whitebalance ");
		comboBox_whibal.setModel(new DefaultComboBoxModel(listOfItems));
		String bal_select=receiveGetting("whitebalance");
		if (bal_select.length() >0)	comboBox_whibal.setSelectedItem(bal_select);
		if (listOfItems.length<2) comboBox_whibal.setEnabled(false);
		contentPane.add(comboBox_whibal);
		
		JButton btnZoomin = new JButton("Zoom in");
		btnZoomin.addMouseListener(new MouseAdapter() {
//			private boolean first=true;
//			private Timer t=new Timer(300, new ActionListener() {
//				          public void actionPerformed(ActionEvent e) {

//			System.out.println("op1");
//			if(notdev) send("mode=camctrl&type=focus&value=tele-fast");
//				          }
//				       });
			@Override
			public void mousePressed(MouseEvent arg0) {
//				if (first) {
//					first=false;
//					t.restart();
//				}
//				else t.restart();
				if(notdev) send("mode=camcmd&value=tele-normal");
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if(notdev) send("mode=camcmd&value=zoomstop");
//				t.stop();
			}
		});

		btnZoomin.setBounds(542, 522, 89, 23);
		contentPane.add(btnZoomin);
		
		JButton btnZoomOut = new JButton("Zoom out");
		btnZoomOut.addMouseListener(new MouseAdapter() {
			//private boolean first=true;
//		private Timer t=new Timer(300, new ActionListener() {
//	          public void actionPerformed(ActionEvent e) {
//
//	        	  System.out.println("op2");
//	  			if(notdev) send("mode=camcmd&value=wide-fast");
//	          }
//	       });
		@Override
		public void mousePressed(MouseEvent arg0) {
//			if (first) {
//				first=false;
//				t.restart();
//			}
//			else t.restart();
			if(notdev) send("mode=camcmd&value=wide-normal");
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
//			t.stop();
			if(notdev) send("mode=camcmd&value=zoomstop");
		}
		});
		btnZoomOut.setBounds(730, 522, 89, 23);
		contentPane.add(btnZoomOut);
		
		btnTake = new JButton("Take Picture");
		btnTake.setBackground(new Color(255, 218, 185));
		btnTake.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(notdev) send("mode=camcmd&value=capture");
			}
		});
		btnTake.setBounds(633, 575, 100, 80);
		contentPane.add(btnTake);
		
		JButton btnVidRec = new JButton("Start video rec");
		btnVidRec.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(notdev) send("mode=camcmd&value=video_recstart");
			}
		});
		btnVidRec.setBackground(new Color(255, 218, 185));
		btnVidRec.setBounds(528, 717, 103, 23);
		contentPane.add(btnVidRec);
		
		btnVidStop = new JButton("Stop video rec");
		btnVidStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(notdev) send("mode=camcmd&value=video_recstop");	
			}
		});
		btnVidStop.setBackground(new Color(255, 218, 185));
		btnVidStop.setBounds(730, 717, 103, 23);
		contentPane.add(btnVidStop);
//define an abstract class to trigger action event on the Bluetooth position buttons to make them visible		
/*		Class<AbstractButton> abstractClass = AbstractButton.class;
	      Method fireMethod;

	      // signature: public ActionEvent(Object source, int id, String command)

	      // get the Method object of protected method fireActionPerformed
	      //fireMethod = abstractClass.getDeclaredMethod("fireActionPerformed",ActionEvent.class);

	      fireMethod = abstractClass.getDeclaredMethod("fireActionPerformed",ActionEvent.class);
	      // set accessible, so that no IllegalAccessException is thrown when
	      // calling invoke()
	      fireMethod.setAccessible(true);*/


	      // signature: invoke(Object obj, Object... args)
//	      fireMethod.invoke(btnBlueLeft,myActionEvent);
		btnLeft = new JButton("L");
//		if (blue==0 ) btnBlueLeft.setEnabled(false);	
		
/*		btnBlueLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			System.out.println(e.getSource());
			System.out.println(e.getActionCommand());
			}
		});*/
/*		btnBlueLeft.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				String property=arg0.getPropertyName();
				if("enabled".equals(property)) btnBlueLeft.setEnabled(true);
			}
		});*/
		btnLeft.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (notdev & blue) {
					String cmd=comLeft+"\r\n";
					try {
				    	 HC05.send(cmd);
					} catch (Exception a) {
						 a.printStackTrace();
						 blue=false;
					}
				}
					
				}

		});
		btnLeft.setBackground(new Color(127, 255, 212));
		btnLeft.setBounds(1109, 246, 89, 54);
		if (!blue) btnLeft.setEnabled(false);

		contentPane.add(btnLeft);
/*	      ActionEvent myActionEvent = new ActionEvent(btnBlueLeft,
                  ActionEvent.ACTION_PERFORMED,
                  btnBlueLeft.getActionCommand());*/
		
		btnRight = new JButton("R");
		btnRight.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (notdev & (blue)) {
					String cmd=comRight+"\r\n";
					try {
				    	 HC05.send(cmd);
					} catch (Exception a) {
						 a.printStackTrace();
						 blue=false;
					}
				}	
			}
		});
		btnRight.setBackground(new Color(127, 255, 212));
		btnRight.setBounds(1310, 246, 89, 54);
		if (!blue) btnRight.setEnabled(false);
		contentPane.add(btnRight);
		
		btnUp = new JButton("U");
		btnUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (notdev & (blue)) {
					String cmd=comUp+"\r\n";
//		          	System.out.println("sent U");
					try {
				    	 HC05.send(cmd);
					} catch (Exception a) {
						 a.printStackTrace();
						 blue=false;
					}
				}	
			}
		});
		btnUp.setBackground(new Color(127, 255, 212));
		btnUp.setBounds(1233, 124, 56, 96);
		if (!blue) btnUp.setEnabled(false);
		contentPane.add(btnUp);
		
		btnDown = new JButton("D");
		btnDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (notdev & (blue)) {
					String cmd=comDown+"\r\n";
					try {
				    	 HC05.send(cmd);
					} catch (Exception a) {
						 a.printStackTrace();
						 blue=false;
					}
				}	
			}
		});
		btnDown.setBackground(new Color(127, 255, 212));
		btnDown.setBounds(1233, 318, 56, 96);
		if (!blue) btnDown.setEnabled(false);
//		if (blue==0 ) btnDown.setEnabled(false);
		contentPane.add(btnDown);
		
		txtBluetoothHcMoves = new JTextField();
		txtBluetoothHcMoves.setHorizontalAlignment(SwingConstants.CENTER);
		txtBluetoothHcMoves.setText("Bluetooth HC05 Moves");
		txtBluetoothHcMoves.setEditable(false);
		txtBluetoothHcMoves.setBounds(1178, 65, 164, 20);
		contentPane.add(txtBluetoothHcMoves);
		txtBluetoothHcMoves.setColumns(10);
		
		btnNewButton_1 = new JButton("Try connecting");
		btnNewButton_1.addActionListener(this);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!blue) {
				try {
				HC05 hc=new HC05(bluetoothAdress);
				if ((HC05.os==null) ) blue=false;
				else {
					blue=true;
//					fireMethod.invoke(btnBlueLeft,myActionEvent);
					
				}
				} catch (Exception e) {
					e.printStackTrace();
					blue=false;
				}
				}
			}
		});
		btnNewButton_1.setBounds(1190, 504, 140, 23);
		
		contentPane.add(btnNewButton_1);
		
		TextBatVal = new JTextField();
		TextBatVal.setFont(new Font("Tahoma", Font.BOLD, 11));
		TextBatVal.setEditable(false);
		TextBatVal.setColumns(10);
		TextBatVal.setBounds(40, 65, 42, 20);
		contentPane.add(TextBatVal);
		
		textBlue = new JTextField();
		textBlue.setEditable(false);
		textBlue.setBounds(1160, 449, 202, 20);
		contentPane.add(textBlue);
		textBlue.setColumns(10);
		
		textBlue2 = new JTextField();
		textBlue2.setEditable(false);
		textBlue2.setColumns(10);
		textBlue2.setBounds(1141, 480, 245, 20);
		contentPane.add(textBlue2);
		
		tglbtnSound = new JToggleButton("Sound");
		tglbtnSound.addActionListener(this);
		tglbtnSound.setBounds(5, 124, 111, 23);
		contentPane.add(tglbtnSound);
		
		JSlider slider = new JSlider();
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				   JSlider source = (JSlider)e.getSource();
			        if (!source.getValueIsAdjusting()) {
			            maxArea.set((int)source.getValue());  
//			            System.out.println(String.valueOf(maxArea.get()));
			        }
			}
		});
		slider.setSnapToTicks(true);
		slider.setMinorTickSpacing(100);
		slider.setMajorTickSpacing(500);
		slider.setMinimum(100);
		slider.setMaximum(1000);
		slider.setOrientation(SwingConstants.VERTICAL);
		slider.setBounds(315, 84, 56, 149);
		contentPane.add(slider);
		
		textArea = new JTextField();
		textArea.setEditable(false);
		textArea.setText("Area");
		textArea.setBounds(323, 53, 31, 20);
		contentPane.add(textArea);
		textArea.setColumns(10);
		
		txtBlur = new JTextField();
		txtBlur.setText("Blur");
		txtBlur.setEditable(false);
		txtBlur.setColumns(10);
		txtBlur.setBounds(277, 53, 31, 20);
		contentPane.add(txtBlur);
		
		slider_1 = new JSlider();
		slider_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				   JSlider source = (JSlider)e.getSource();
			        if (!source.getValueIsAdjusting()) {
			            blurSize.set((int)source.getValue());  
//			            System.out.println(String.valueOf(blurSize.get()));
			        }
			}
		});
		slider_1.setValue(3);
		slider_1.setSnapToTicks(true);
		slider_1.setPaintTicks(true);
		slider_1.setPaintLabels(true);
		slider_1.setOrientation(SwingConstants.VERTICAL);
		slider_1.setMinorTickSpacing(2);
		slider_1.setMinimum(3);
		slider_1.setMaximum(15);
		slider_1.setMajorTickSpacing(5);
		slider_1.setBounds(265, 84, 48, 149);
		contentPane.add(slider_1);
		
		txtThresh = new JTextField();
		txtThresh.setText("Thresh");
		txtThresh.setEditable(false);
		txtThresh.setColumns(10);
		txtThresh.setBounds(204, 53, 42, 20);
		contentPane.add(txtThresh);
		
		slider_2 = new JSlider();
		slider_2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				   JSlider source = (JSlider)e.getSource();
			        if (!source.getValueIsAdjusting()) {
			            threshVal.set((int)source.getValue());  
//			            System.out.println(String.valueOf(threshVal.get()));
			        }
			}
			
		});
		slider_2.setValue(2);
		slider_2.setSnapToTicks(true);
		slider_2.setPaintTicks(true);
		slider_2.setPaintLabels(true);
		slider_2.setOrientation(SwingConstants.VERTICAL);
		slider_2.setMinorTickSpacing(1);
		slider_2.setMinimum(-4);
		slider_2.setMaximum(4);
		slider_2.setMajorTickSpacing(2);
		slider_2.setBounds(204, 84, 48, 149);
		contentPane.add(slider_2);
		
		txtMeant = new JTextField();
		txtMeant.setText("MeanT");
		txtMeant.setEditable(false);
		txtMeant.setColumns(10);
		txtMeant.setBounds(145, 53, 42, 20);
		contentPane.add(txtMeant);
		
		slider_3 = new JSlider();
		slider_3.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				   JSlider source = (JSlider)e.getSource();
			        if (!source.getValueIsAdjusting()) {
			            threshArea.set((int)source.getValue());  
//			            System.out.println(String.valueOf(threshArea.get()));
			        }
			}
		});
		slider_3.setValue(5);
		slider_3.setSnapToTicks(true);
		slider_3.setPaintTicks(true);
		slider_3.setPaintLabels(true);
		slider_3.setOrientation(SwingConstants.VERTICAL);
		slider_3.setMinorTickSpacing(2);
		slider_3.setMinimum(3);
		slider_3.setMaximum(15);
		slider_3.setMajorTickSpacing(2);
		slider_3.setBounds(145, 84, 48, 149);
		contentPane.add(slider_3);
		if (yolo) {
		JRadioButton rdbtnMotionDetect = new JRadioButton("Motion Detect");
		rdbtnMotionDetect.setSelected(true);
		rdbtnMotionDetect.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				   JRadioButton source = (JRadioButton)e.getSource();
			        if (source.isSelected()) {
			            motionDetect.set(true);
//			            System.out.println("motion detetct: "+motionDetect);
			        }
			}
		});
		rdbtnMotionDetect.setBounds(539, 7, 109, 46);
		buttonGroup2.add(rdbtnMotionDetect);
		contentPane.add(rdbtnMotionDetect);
		
		JRadioButton rdbtnObjectDetect = new JRadioButton("Object Detect");
		rdbtnObjectDetect.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				   JRadioButton source = (JRadioButton)e.getSource();
			        if (source.isSelected()) {
			            motionDetect.set(false);
//			            System.out.println("motion detetct: "+motionDetect);
			        }
			}
		});
		rdbtnObjectDetect.setBounds(883, 0, 109, 46);
		buttonGroup2.add(rdbtnObjectDetect);
		contentPane.add(rdbtnObjectDetect);
		}
// if not in development mode, the command mode=getstate is sent every 2s
		blueString= new String("Bluetooth motor values                    ");

		tCount=0;
		Timer t = new Timer(2000, new ActionListener() {
	          public void actionPerformed(ActionEvent e) {
//to keep alive camera
	if (notdev) {
		String result=send("mode=getstate");
	
//	        System.out.println(result);
	        getBat(result);
//	        System.out.println(BatVal);

	        tCount=tCount+1;
	        if (tCount==61) tCount=1; 
// id for bluetooth (with an invalid command)
			if (blue) {
				String cmd=comGet+"\r\n";

				try {
			    	 blueString= new String(HC05.send(cmd));
				} catch (Exception a) {
					 a.printStackTrace();
					 blue=false;
				}
				blue=HC05.getblue();
			}	
	          }
	          }
	       });
		t.addActionListener(this);
		t.start();
		}

	

// Class action listener for intercomponents comm	
	public void actionPerformed(ActionEvent e) {
/*		System.out.println(BatVal);
	        if (BatVal.equals("1/3")) TextBatVal.setText("||||");
	        if (BatVal.equals("2/3")) TextBatVal.setText("||||||||");
	        if (BatVal.equals("3/3")) TextBatVal.setText("||||||||||||");*/
		TextBatVal.setText(BatVal);
		btnUp.setEnabled(blue);
		btnDown.setEnabled(blue);
		btnRight.setEnabled(blue);
		btnLeft.setEnabled(blue);
		textBlue.setText("Connection: "+ String.valueOf(blue));
		textBlue2.setText(blueString);
		sound.set(tglbtnSound.isSelected());
		//this prevents camera to go to sleep by sending iso command every 2 minutes
		if (tCount==60) {
			myChoice= (String) comboBox_iso.getSelectedItem();
//			System.out.println("*"+"mode=setsetting&type=iso&value="+myChoice.replace(" ", "&value2=")+"*");
			if(notdev) send("mode=setsetting&type=iso&value="+myChoice.replace(" ", "&value2="));	
		}
//		System.out.println(sound.get());
    }
	public void getBat (String result) {
		String Answer=new String();
		if (notdev) {
		String s="<batt>";
		if (result.indexOf(s)>0) {
		String s2=result.substring(result.indexOf(s)+6);
		BatVal=s2.substring(0,s2.indexOf("<"));;
//		System.out.println("answer: ");
//		System.out.println(BatVal);
		return;
		}
		else System.out.println(result);
		BatVal="";
		return;
		
	}
	else {
		BatVal="";
		return;
	}
}
	
// get the different present settings of the camera
//it does not work for afmode, which however can be set...
	
	public String receiveGetting (String type) {
			String Answer=new String();
			if (notdev) {
			String s1=send("mode=getsetting&type="+type);
			String s=type+"=";
			if (s1.indexOf(s)>0) {
			String s2=s1.substring(s1.indexOf(s)+type.length()+2);
			Answer=s2.substring(0,s2.indexOf(">")-1);;
//			System.out.println("answer");
//			System.out.println(s1+" "+Answer);
			}
			else System.out.println(s1);
			Answer="";
			
		}
		else {
			Answer="";
		}
			return Answer;
	}
// send a command through the send com funtion of Wifi_lumix class

 
	public static String send(String command) {
		String a="";
        try {
			 a= wl.SendCom(command);
//			 System.out.println(a);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        return a;
	}

// extracts from the array of all lists of commands, the 2 lists (one for value, the second for value2) , for the parameter name
//and returns these two lists
	
	public static List<ArrayList<String>> getComboChoice(String name ) {
		List<ArrayList<String>> listOfChoices = new ArrayList<ArrayList<String>>();
		ArrayList<String> list1 = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		for (int i=0; i<listOfCommands.size();i++) {
		if (listOfCommands.get(i).get(0).equals(name)){

			list1.add(listOfCommands.get(i).get(1));
			list2.add(listOfCommands.get(i).get(2));
		}
		}
		listOfChoices.add(list1);
		listOfChoices.add(list2);
		
		return listOfChoices;

	}
//extracts from the content of "menus.txt" content (in a single string filin) the array of strings necessary to populate the choices
//for the parameter name each item is under the form "value" or "value value2"
	
	public String[] GetMenuItems(String filin,String name ) {
	
		String[] list1OfChoices= new String[0];
		
		int j=0;
		int k=0;
		int l=0;
		List<String> a = new ArrayList<String>();
		if (filin.contains(name)) j= filin.indexOf(name, 0);
		else return list1OfChoices;
		String sub1="";
		String item="";
		sub1=filin.substring(j,filin.indexOf(13,j));
//		System.out.println(sub1);
		j=sub1.indexOf('[', 0);
		while (j >=0) {
			j=sub1.indexOf('[', k);
			if (j>=0) {
			k=sub1.indexOf(']',j);
			item=sub1.substring(j+1,k);
			a.add(item);
//			System.out.println(item);	
			}
		}
		String[] listOfChoices= new String[a.size()];
		for (l=0;l<a.size();l++) listOfChoices[l]=a.get(l);
		return listOfChoices;

	}
// gets the list of all parameters through allmenu and the actual possible values through curmenu
// the results are parsed (class parser) to fabricate the list of string lists global variable listOfCommands
// and write the results in "menus.txt" file
	
	public static void populateComboMenus() throws InterruptedException {

		try {
			if(notdev) {
			System.out.println("Getting setting capabilities by getting allmenu");
			String outStr1=send("mode=getinfo&type=allmenu");
//	      System.out.println(outStr1);
			String outStr2=send("mode=getinfo&type=curmenu");
//			System.out.println("curmenu");;
//			System.out.println(outStr2);
	      TimeUnit.SECONDS.sleep(1);

				Parser ps= new Parser(outStr1,outStr2);
				listOfCommands = new ArrayList<ArrayList<String>>();
				listOfCommands=ps.GetParsed();

				f.createNewFile();
				PrintWriter writer = new PrintWriter("menus.txt", "UTF-8");
				
//				for (int i=0; i<listOfCommands.size();i++) System.out.println(listOfCommands.get(i));
				for (String element : comboList) {
//				    System.out.println("Element: " + element);
					List<String> list2 = new ArrayList<String>();
					List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
					list=getComboChoice(element);
//				    System.out.println("Element: " + element);
//				    System.out.println(list);
					if (!list.isEmpty()) {
					writer.print(" "+element+ " ");
					for (int i=0;i<list.get(0).size();i++) {
						list2.add(list.get(0).get(i)+" "+list.get(1).get(i));
						if(list.get(1).get(i).equals("")) writer.print("["+list.get(0).get(i)+"]");
						else writer.print("["+list.get(0).get(i)+" "+list.get(1).get(i)+"]");
//						if (element.equals("iso")) 	comboBox_iso.addItem(list.get(0).get(i)+" "+list.get(1).get(i));
					}
//				    System.out.println(list2);
//					String[] menuItem =list2.toArray(new String[0]) ;
//				    for (int j=0; j<menuItem.length;j++) System.out.println(menuItem[j]);
					}
					writer.println("");

				}
				writer.flush();
				writer.close();
			}	

			} catch (RuntimeException e) {
			  // TODO Bloc catch auto-généré
			  e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		

	}
	}

