package Lumix_wifi_tether;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.opencv.core.*;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Size;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfRect2d;
import org.opencv.core.Point;
import org.opencv.core.CvType;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;
import org.opencv.core.Rect;
import org.opencv.core.Rect2d;
import org.opencv.core.Scalar;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Reads the camera video stream.
 *
 * The camera sends a continuous stream of UDP packets to whoever called its "startstream" method
 */
public class StreamViewer implements Runnable {

	   static {
	        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    }
	    private  Mat imag;
	    private   Mat outerBox;
	    private  Mat diff_frame;
	    private  Mat tempon_frame;
//	    private ArrayList<Rect> rect_array;
        
    /**
     * The local UDP socket for receiving the video stream.
     */
    private final DatagramSocket localUdpSocket;
    private static fond_ecran par;

    /**
     * The UDP port to listen on.
     */
    private final int localUdpPort;
    private static int i;
    private static int ii;
    private final boolean yolo;
    private static int maxArea;
    private static int threshVal;
    private static int blurSize;
    private static int threshArea;

    /**
     * IP address of the local network interface communicating with the camera.
     */
    private final InetAddress cameraIp;

    private final Consumer<BufferedImage> imageConsumer;

    private ExecutorService imageExecutor = Executors.newCachedThreadPool();
    private MatOfInt indices;
    private MatOfFloat confidences;
    private Rect2d[] boxesArray;
    private MatOfRect2d boxes;
    private List <String> names;
    private   List<Integer> clsIds; 
    private   List<Float> confs; 
    private   List<Rect2d> rects; 
	private String modelWeights ;
    private String modelConfiguration;
	private String fileName; 
	private Mat frame; 
    private    Mat dst ;
    private   Net net ;
    private   Size sz ;   
    private   List<Mat> result ;
    private   List<String> outBlobNames;
    private List <String> detectedNames;
    /**
     * Create the Lumix videostream reader connected to the default UDP port 49199.
     *
     * @param imageConsumer the consumer to receive the BufferedImages received from the camera
     * @param cameraIp IPv4 address of the camera.
     * @param cameraNetmaskBitSize Size of the camera network's subnet.
     * @throws UnknownHostException If the camera IP address cannot be parsed.
     * @throws SocketException On network communication errors.
     */
    public StreamViewer(Consumer<BufferedImage> imageConsumer, String cameraIp, int cameraNetmaskBitSize,fond_ecran paren,boolean yolo)
            throws UnknownHostException, SocketException {
        this(imageConsumer, cameraIp, cameraNetmaskBitSize, 49199,paren,yolo);
    }

    /**
     * Create the Lumix videostream reader.
     *
     * @param imageConsumer the consumer to receive the BufferedImages received from the camera
     * @param cameraIp IPv4 address of the camera.
     * @param cameraNetmaskBitSize Size of the camera network's subnet.
     * @param udpPort The UDP port to listen on.
     * @throws UnknownHostException If the camera IP address cannot be parsed.
     * @throws SocketException On network communication errors.
     */
    public StreamViewer(Consumer<BufferedImage> imageConsumer, String cameraIp, int cameraNetmaskBitSize, int udpPort,fond_ecran paren,boolean useyolo)
            throws UnknownHostException, SocketException {
        this.imageConsumer = imageConsumer;
        this.cameraIp = NetUtil.findLocalIpInSubnet(cameraIp, cameraNetmaskBitSize);
        this.par=paren;
        this.yolo=useyolo;
        this.localUdpPort = udpPort;
        this.localUdpSocket = new DatagramSocket(this.localUdpPort);

        System.out.println("UDP Socket on " + this.cameraIp.getHostAddress() + ":" + this.localUdpPort
                + " created");
        if (yolo) {
	        List <String> yolos=new ArrayList<>();
	        this.detectedNames=new ArrayList<>();
	        try (Stream<String> stream = Files.lines(Paths.get("yoloconfig.txt"))) {
				yolos = stream.collect(Collectors.toList());
//				System.out.println(blues);
//				System.out.println(stream);
				if (yolos.size() !=0) {
					this.modelWeights=yolos.get(2);
					this.modelConfiguration= yolos.get(3);
					this.fileName=yolos.get(4);
					for (int j=5; j<yolos.size()-1;j++) {
						this.detectedNames.add(yolos.get(j));
						System.out.println(yolos.get(j));
					}
				}
//				System.out.println(bluetoothAdress);

			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("File not found");
			}
//   		 this.modelWeights = "C:\\Yolov3\\yolov3.weights"; //Download and load only wights for YOLO , this is obtained from official YOLO site//
//   	     this.modelConfiguration = "C:\\Yolov3\\yolov3.cfg";//Download and load cfg file for YOLO , can be obtained from official site//
//   	     this.fileName = "C:\\Yolov3\\yolov3names.txt"; //file with class names//
   	        
   	        this.names=new ArrayList<>();
   	        try (Stream<String> stream = Files.lines(Paths.get(this.fileName))) {
   				this.names = stream.collect(Collectors.toList());

   			} catch (IOException e) {
   				e.printStackTrace();
   			}
          
   	        
   	       this.frame = new Mat(); // define a matrix to extract and store pixel info from video//
   	        this.dst = new Mat ();
   	       this.net = Dnn.readNetFromDarknet(modelConfiguration, modelWeights); //OpenCV DNN supports models trained from various frameworks like Caffe and TensorFlow. It also supports various networks architectures based on YOLO//

   	        //Mat image = Imgcodecs.imread("D:\\yolo-object-detection\\yolo-object-detection\\images\\soccer.jpg");
   	        this.sz = new Size(288,288);
   	        
   	        this.result = new ArrayList<>();
   	        this.outBlobNames = getOutputNames(net);
           }
    }

    private BufferedImage retrieveImage(DatagramPacket receivedPacket) {

        final byte[] videoData = getImageData(receivedPacket);

        BufferedImage img = null;
        try {
            img = ImageIO.read(new ByteArrayInputStream(videoData));
        } catch (IOException e) {
            System.err.println("Error while reading image data");
            e.printStackTrace();
        }

        return img;
    }

    /**
     * The camera sends one JPEG image in each UDP packet.
     *
     * @param receivedPacket a received camera image packet
     * @return the jpeg image data
     */
    private byte[] getImageData(DatagramPacket receivedPacket) {
        final byte[] udpData = receivedPacket.getData();
        // The camera adds some kind of header to each packet, which we need to ignore
        int videoDataStart = getImageDataStart(receivedPacket, udpData);
        return Arrays.copyOfRange(udpData, videoDataStart, receivedPacket.getLength());
    }

    private int getImageDataStart(DatagramPacket receivedPacket, byte[] udpData) {
        int videoDataStart = 130;

        // The image data starts somewhere after the first 130 bytes, but at last in 320 bytes
        for (int k = 130; k < 320 && k < (receivedPacket.getLength() - 1); k++) {
            // The bytes FF and D8 signify the start of the jpeg data, see https://en.wikipedia.org/wiki/JPEG_File_Interchange_Format
            if ((udpData[k] == (byte) 0xFF) && (udpData[(k + 1)] == (byte) 0xD8)) {
                videoDataStart = k;
            }
        }

        return videoDataStart;
    }
      public  void bufferedImageToMat(BufferedImage bi, Mat mat) {
//  	  Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
  	  byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
  	  mat.put(0, 0, data);
  	  return ;
  	}
/*      public static Mat BufferedImage2Mat(BufferedImage image) throws IOException {
    	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    	    ImageIO.write(image, "jpg", byteArrayOutputStream);
    	    byteArrayOutputStream.flush();
    	    return Imgcodecs.imdecode(new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.IMREAD_UNCHANGED);
    	}*/
      private static BufferedImage toBufferedImage(Mat m) {
    	    if (!m.empty()) {
    	        int type = BufferedImage.TYPE_BYTE_GRAY;
    	        if (m.channels() > 1) {
    	            type = BufferedImage.TYPE_3BYTE_BGR;
    	        }
    	        int bufferSize = m.channels() * m.cols() * m.rows();
    	        byte[] b = new byte[bufferSize];
    	        m.get(0, 0, b); // get all the pixels
    	        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
    	        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    	        System.arraycopy(b, 0, targetPixels, 0, b.length);
    	        return image;
    	    }
    	    
    	    return null;
    	}
      public static ArrayList<Rect> detection_contours(Mat outmat) {
          Mat v = new Mat();
 //         Mat vv = outmat.clone();
          List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
          Imgproc.findContours(outmat, contours, v, Imgproc.RETR_LIST,
                  Imgproc.CHAIN_APPROX_SIMPLE);
   
          double maxArea = threshArea;
          int maxAreaIdx = -1;
          Rect r = null;
          ArrayList<Rect> rect_array = new ArrayList<Rect>();
   
          for (int idx = 0; idx < contours.size(); idx++) {
        	  Mat contour = contours.get(idx);
        	  double contourarea = Imgproc.contourArea(contour); 
        	  if (contourarea > maxArea) {
                  maxAreaIdx = idx;
                  r = Imgproc.boundingRect(contours.get(maxAreaIdx));
                  rect_array.add(r);
              }
   
          }
   
          v.release();
//          vv.release();
          return rect_array;
   
      }
      private static List<String> getOutputNames(Net net) {
    		List<String> names = new ArrayList<>();

            List<Integer> outLayers = net.getUnconnectedOutLayers().toList();
            List<String> layersNames = net.getLayerNames();

            outLayers.forEach((item) -> names.add(layersNames.get(item - 1)));//unfold and create R-CNN layers from the loaded YOLO model//
            return names;
    	}
    @Override
    public void run() {
        // The camera sends each image in one UDP packet, normally between 25000 and 30000 bytes. We set 35000 here to be safe.
        byte[] udpPacketBuffer = new byte[35000];
        //très long! tiny = temps réel
        if (yolo) {
		 modelWeights = "C:\\Yolov3\\yolov3.weights"; //Download and load only wights for YOLO , this is obtained from official YOLO site//
	     modelConfiguration = "C:\\Yolov3\\yolov3.cfg";//Download and load cfg file for YOLO , can be obtained from official site//
	     fileName = "C:\\Yolov3\\yolov3names.txt"; //file with class names//
	        
	        names=new ArrayList<>();
	        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
				names = stream.collect(Collectors.toList());

			} catch (IOException e) {
				e.printStackTrace();
			}
       
	        
	        Mat frame = new Mat(); // define a matrix to extract and store pixel info from video//
	        Mat dst = new Mat ();
	        Net net = Dnn.readNetFromDarknet(modelConfiguration, modelWeights); //OpenCV DNN supports models trained from various frameworks like Caffe and TensorFlow. It also supports various networks architectures based on YOLO//
	        //Thread.sleep(5000);

	        //Mat image = Imgcodecs.imread("D:\\yolo-object-detection\\yolo-object-detection\\images\\soccer.jpg");
	        Size sz = new Size(288,288);
	        
	        List<Mat> result = new ArrayList<>();
	        List<String> outBlobNames = getOutputNames(net);
        }
//	        for (String name: outBlobNames) {
//	        	System.out.println("name:"+name);
//	        }
        i=0;
        ii=0;
        int kk=0;
        int found=0;
        while (!Thread.interrupted()) {

            try {
 
                final DatagramPacket receivedPacket = new DatagramPacket(udpPacketBuffer, udpPacketBuffer.length,
                        cameraIp, localUdpPort);


                localUdpSocket.receive(receivedPacket);
//pas sûr que ce soit une bonne idée de lancer des threads parallèles... et même sûr que non!
//                imageExecutor.submit(() -> {

                    ArrayList<Rect> array = new ArrayList<Rect>();
                    BufferedImage newImage = retrieveImage(receivedPacket);
  if (fond_ecran.motionDetect.get()) {
	  
                    //debut traitement detection de mouvement
                    
                    threshVal = fond_ecran.threshVal.get();
                    threshArea=fond_ecran.threshArea.get();
                    blurSize=fond_ecran.blurSize.get();
                    maxArea=fond_ecran.maxArea.get();
 //nouvelle image au format BufferedImage
 //                   if (frame!=null) frame.release();
                    if (i==0) {
 //Initialize Mat with the right size
                    	imag= new Mat(newImage.getHeight(),newImage.getWidth(), CvType.CV_8UC3);
                    	outerBox = new Mat(imag.size(), CvType.CV_8UC1);
                    	diff_frame = new Mat(outerBox.size(), CvType.CV_8UC1);
                        tempon_frame = new Mat(outerBox.size(), CvType.CV_8UC1);
                        System.out.println("height: "+newImage.getHeight()+" Width: "+newImage.getWidth());
                         
                    }
                    bufferedImageToMat(newImage,imag);
//                    if (imag!=null) imag.release();
//                    frame.copyTo(imag);
//do some treatement
                   
                    Imgproc.cvtColor(imag, outerBox, Imgproc.COLOR_BGR2GRAY);
                    Imgproc.GaussianBlur(outerBox, outerBox, new Size(blurSize, blurSize), 0);
                    
                    if (i==0)  outerBox.copyTo(tempon_frame); 
                    
                    if (i >0) {
                        Imgproc.cvtColor(imag, outerBox, Imgproc.COLOR_BGR2GRAY);
                        Imgproc.GaussianBlur(outerBox, outerBox, new Size(blurSize, blurSize), 0);
                        Core.subtract(outerBox, tempon_frame, diff_frame);
                        Imgproc.adaptiveThreshold(diff_frame, diff_frame, 255,
                                Imgproc.ADAPTIVE_THRESH_MEAN_C,
                                Imgproc.THRESH_BINARY_INV, threshArea, threshVal);
                        array = detection_contours(diff_frame);
                        if (array.size() > 0) {
                        	if (fond_ecran.sound.get()) Toolkit.getDefaultToolkit().beep();  
                            Iterator<Rect> it2 = array.iterator();
                            while (it2.hasNext()) {
                                Rect obj = it2.next();
                                Imgproc.rectangle(imag, obj.br(), obj.tl(),
                                        new Scalar(0, 255, 0), 1);
                            }
     
                        }
     
                    }
  
                    if (fond_ecran.vidChoice.get()==1) {
                    	BufferedImage image = (toBufferedImage(imag));
                        imageConsumer.accept(image);
                    }
                    if (fond_ecran.vidChoice.get()==2) {
                    	BufferedImage image = (toBufferedImage(outerBox));
                        imageConsumer.accept(image);
                    }
                    if (fond_ecran.vidChoice.get()==3) {
                    	BufferedImage image = (toBufferedImage(diff_frame));
                        imageConsumer.accept(image);
                    }
                    i += 1;
                    if (i==10) {
  //                  	if (tempon_frame != null) tempon_frame.release();
  //                      System.out.println("Time "+java.time.Clock.systemDefaultZone()+ "Total"+(double) (Runtime.getRuntime().totalMemory())/1024+ " KB");ita1!
//                     System.out.println("Left: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024+ "KB");
         
                 	    outerBox.copyTo(tempon_frame);
 //                	   outerBox.release();
                 	   i=1;
//                 	   System.gc();
                    }
//fin du traitement detect mouvement
                    
  }
  else {
	  if (yolo) {
                    if (ii==0)  {
                    	frame= new Mat(newImage.getHeight(),newImage.getWidth(), CvType.CV_8UC3);
                    	
               	       clsIds = new ArrayList<>();
            	       confs = new ArrayList<>();
            	       rects = new ArrayList<>();

                    }
                    bufferedImageToMat(newImage,frame);
//début si traitement yolov
                    if (ii==20) {
                    	kk=1;
                    	found=0;
                    Mat blob = Dnn.blobFromImage(frame, 0.00392, sz, new Scalar(0), true, false); // We feed one frame of video into the network at a time, we have to convert the image to a blob. A blob is a pre-processed image that serves as the input.//
        	        net.setInput(blob);
                  System.out.println("Left: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024+ "KB");
        	       

        	        net.forward(result, outBlobNames); //Feed forward the model to get output //
        	     
  
        	            

  //      	       outBlobNames.forEach(System.out::println);
  //      	        result.forEach(System.out::println);

        	        float confThreshold = 0.6f; //Insert thresholding beyond which the model will detect objects//
        	        clsIds.clear();
        	        confs.clear();
        	        rects.clear();
        	        for (int i = 0; i < result.size(); ++i)
        	        {
        	            // each row is a candidate detection, the 1st 4 numbers are
        	            // [center_x, center_y, width, height], followed by (N-4) class probabilities
        	            Mat level = result.get(i);
        	            for (int j = 0; j < level.rows(); ++j)
        	            {
        	                Mat row = level.row(j);
        	                Mat scores = row.colRange(5, level.cols());
        	                Core.MinMaxLocResult mm = Core.minMaxLoc(scores);
        	                float confidence = (float)mm.maxVal;
        	                Point classIdPoint = mm.maxLoc;
        	                if (confidence > confThreshold)
        	                {
        	                    int centerX = (int)(row.get(0,0)[0] * frame.cols()); //scaling for drawing the bounding boxes//
        	                    int centerY = (int)(row.get(0,1)[0] * frame.rows());
        	                    int width   = (int)(row.get(0,2)[0] * frame.cols());
        	                    int height  = (int)(row.get(0,3)[0] * frame.rows());
        	                    int left    = centerX - width  / 2;
        	                    int top     = centerY - height / 2;

        	                    clsIds.add((int)classIdPoint.x);
        	                    confs.add((float)confidence);
        	                   rects.add(new Rect2d(left, top, width, height));
        	                }
        	            }
        	        }
        	        float nmsThresh = 0.8f;

// pb ligne suivante peut-être parce que confs est vide
//        	        System.out.println (confs.size());
          	        indices = new MatOfInt();
        	        if (confs.size()>0) {
 //       	        	System.out.println("Found");
        	        	found=1;
               	        MatOfFloat confidences = new MatOfFloat(Converters.vector_float_to_Mat(confs));
            	        boxesArray = rects.toArray(new Rect2d[0]);
            	        MatOfRect2d boxes = new MatOfRect2d(boxesArray);
  
        	        Dnn.NMSBoxes(boxes, confidences, confThreshold, nmsThresh, indices); //We draw the bounding boxes for objects here//
 //       	      	  System.out.println ("la");   
        	        
        	        }
 //       	        else  System.out.println("Not Found");
        	        ii=1;
                    }
                    if ((kk>0) && (found==1))
                    {
        	        int [] ind = indices.toArray();
 //       	        System.out.println("I am here");
        	        for (int i = 0; i < ind.length; ++i)
        	        {
        	            int idx = ind[i];
        	            for (int ch=0; ch<detectedNames.size();ch++) {
        	            if (names.get(clsIds.get(idx).intValue()).contentEquals(detectedNames.get(ch))) {
        	            Rect2d box = boxesArray[idx];
         	            Imgproc.rectangle(frame, box.tl(), box.br(), new Scalar(0,0,255), 2);
                       	if (fond_ecran.sound.get()) Toolkit.getDefaultToolkit().beep();  
        	            
        	            
//        	            System.out.println("indice:"+idx);
        	   //         System.out.println(names.get(clsIds.get(idx).intValue()));
        	            Float conf=confs.get(idx);
        	           Imgproc.putText(frame, names.get(clsIds.get(idx).intValue())+": "+String.format("%.2f", conf), new Point(box.x+5, box.y+20), Imgproc.FONT_HERSHEY_COMPLEX_SMALL, 1, new Scalar(0,0,255), 2);
        	        }
        	        }
        	        }
                    }
        	        
        	       
                    
                    	BufferedImage imag = toBufferedImage(frame);
                        imageConsumer.accept(imag);
  
                      //fin du traitement yolov             
 //                       bufferedImageToMat(newImage, frame);
 
                    ii+=1;
                    
  }
  }

            } catch (IOException e) {
                System.out.println("Error with client request : " + e.getMessage());
            }
            
        }

//        imageExecutor.shutdown();
        localUdpSocket.close();
    }

}
