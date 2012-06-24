
<%@page import="java.awt.Paint"%>
<%@page import="org.jfree.chart.renderer.xy.XYItemRenderer"%><%@page
	import="org.jfree.chart.plot.*"%>
<%@ page import="java.io.*"%>
<%@ page import="org.jfree.chart.*"%>
<%@ page import="org.jfree.data.xy.*"%>
<%@ page import="org.jfree.data.time.*"%>
<%@page import="be.lacerta.cq2.sim.hbn.XPLog"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Calendar"%>
<%@page import="be.lacerta.cq2.sim.hbn.User"%>
<%@page import="org.jfree.chart.renderer.xy.*"%>
<%@page import="java.awt.Font"%>
<%@page import="org.jfree.ui.RectangleEdge"%>
<%@page import="be.lacerta.cq2.utils.CQ2Functions"%>
<%@page import="org.jfree.chart.plot.IntervalMarker"%>
<%@page import="org.jfree.ui.*"%>
<%@page import="org.jfree.chart.labels.*"%>
<%@page import="org.jfree.data.general.DefaultPieDataset"%>
<%@page import="org.jfree.chart.labels.StandardPieSectionLabelGenerator"%>
<%@page import="org.jfree.chart.axis.ValueAxis"%>
<%@page import="org.jfree.chart.axis.NumberAxis"%>
<%@page import="org.jfree.chart.renderer.xy.XYLineAndShapeRenderer"%>
<%@page import="java.util.HashMap"%>


<%
 try
 {
  File image = File.createTempFile("image", "tmp");
  
  int userId=0;
  
  if(request.getParameter("id")!=null) userId = Integer.parseInt(request.getParameter("id"));
  String type = request.getParameter("type");
  
  JFreeChart chart;
  if(type!=null && type.equals("classes")) {
	  List<User> users = User.getUserList();
	  chart = createClassChart(users);
	  ChartUtilities.saveChartAsPNG(image, chart, 450, 300);
  } else if(userId==0) {
	  List<User> users = User.getUserList();
	  chart = createGlobalChart(users);
	  ChartUtilities.saveChartAsPNG(image, chart, 1000, 1000);
  } else {
	  chart = createUserChart(userId);
	  ChartUtilities.saveChartAsPNG(image, chart, 800, 300);
  } 

  // Assume that we have the chart
  
  
  FileInputStream fileInStream = new FileInputStream(image);
  OutputStream outStream = response.getOutputStream();   

  long fileLength;
  byte[] byteStream;
  
  fileLength = image.length();
  byteStream = new byte[(int)fileLength];
  fileInStream.read(byteStream, 0, (int)fileLength);
  
  response.setContentType("image/png");
  response.setContentLength((int)fileLength);
  response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
  response.setHeader("Pragma", "no-cache");
  
  fileInStream.close();
  outStream.write(byteStream);
  outStream.flush();
  outStream.close();
 
 }
 catch (IOException e)
 {
  System.err.println("Problem occurred creating chart.");
 }
 
%>

<%!JFreeChart createClassChart(List<User> users) {
	 	DefaultPieDataset dataset = new DefaultPieDataset();
	 	 for(User user : users) {
	 		 try {
	 		 if(user.getMage()!=null && user.getMage().getLevel()!=null &&user.getMage().getLevel()>0) {
		 		 try{
		 		 	Integer value = dataset.getValue(user.getCq2class()).intValue();
		 		 	dataset.setValue(user.getCq2class(),value+1);
		 		 } catch(org.jfree.data.UnknownKeyException e) {
		 			dataset.setValue(user.getCq2class(),1);
		 		 }
	 		 }
	 		 } catch (org.hibernate.ObjectNotFoundException onfe) { }
	 	 }

	 	JFreeChart chart = ChartFactory.createPieChart(
	            "Mage classes",  // chart title
	            dataset,             // data
	            false,               // include legend
	            false,
	            false
	        );
		
	 	chart.setBackgroundPaint(java.awt.Color.black);
		chart.getTitle().setPaint(new java.awt.Color(160,160,160));
		chart.getTitle().setBackgroundPaint(java.awt.Color.black);
		chart.getTitle().setFont(new java.awt.Font("Verdana", Font.BOLD, 18));
		

		PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("Verdana", Font.PLAIN, 10));
        plot.setNoDataMessage("No data available");
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1} ({2})"));
		plot.setLabelLinkPaint(new java.awt.Color(220,220,220));
		plot.setBackgroundPaint(java.awt.Color.black);
		plot.setLabelBackgroundPaint(new java.awt.Color(160,160,160));
		
        return chart;
			
	 }
 
 
 	JFreeChart createUserChart(int userId) {
		 User user = (User)User.get(User.class,userId);
		  
		  List<XPLog> xpLogs = XPLog.getXPLogs(userId);
		  
		  TimeSeries xpSeries = new TimeSeries("Total XP");
		  TimeSeries activitySeries = new TimeSeries("Activity (XP/24h)");
		  
		  XPLog prevLog = null;
		  
		  HashMap<Calendar,Integer> interpPoints = new HashMap<Calendar,Integer>();
		  Calendar lastInterpPoint=null;

		  for(XPLog log : xpLogs) {
			  Date date = log.getTime();
			  Calendar cal = Calendar.getInstance();
			  cal.setTime(date);
			  xpSeries.addOrUpdate(new Second(cal.get(Calendar.SECOND), cal.get(Calendar.MINUTE),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR)), log.getXp());
			  
			  if(lastInterpPoint==null) {
				  lastInterpPoint=cal;
				  lastInterpPoint.set(Calendar.MINUTE,0);
				  lastInterpPoint.set(Calendar.SECOND,0);
				  lastInterpPoint.set(Calendar.MILLISECOND,0);
			  }
			  
			  if(prevLog!=null) {

				  Date prevDate = prevLog.getTime();
		  		  Calendar previous = Calendar.getInstance();
		  		  previous.setTime(prevDate);
		  		  
		  		  // vaste punten berekenen dmv interpolatie (per uur)
				  while(lastInterpPoint.getTimeInMillis()-(1000*60*60)>cal.getTimeInMillis()){
					  Calendar newInterpPoint = Calendar.getInstance();
					  newInterpPoint.setTime(lastInterpPoint.getTime());
					  newInterpPoint.add(Calendar.HOUR,-1);
					  
					  int interpolatedXp = XPLog.getInterpolatedXp(log,prevLog,newInterpPoint);
					  interpPoints.put(newInterpPoint,interpolatedXp);
					  lastInterpPoint=newInterpPoint;
				  }

			  }

			  prevLog=log;
		  }
		  
		  // XP/dag adden aan grafiek voor elk uur
		  for(Calendar meetPunt : interpPoints.keySet()) {

			  Calendar dayBefore = Calendar.getInstance();
			  dayBefore.setTimeInMillis(meetPunt.getTimeInMillis());
			  dayBefore.add(Calendar.HOUR,-24);
			  
			  if(interpPoints.containsKey(dayBefore)) {
				  int xpDayBefore = interpPoints.get(dayBefore);
				  int xpDiff = Math.max(0,interpPoints.get(meetPunt)-xpDayBefore);
				  activitySeries.addOrUpdate(new Second(meetPunt.get(Calendar.SECOND), meetPunt.get(Calendar.MINUTE),meetPunt.get(Calendar.HOUR_OF_DAY),meetPunt.get(Calendar.DAY_OF_MONTH),meetPunt.get(Calendar.MONTH)+1,meetPunt.get(Calendar.YEAR)), xpDiff);
			  }
		  }
		  
		  TimeSeriesCollection dataset = new TimeSeriesCollection(xpSeries);
		  TimeSeriesCollection dataset2 = new TimeSeriesCollection(activitySeries);
		  
		  JFreeChart chart = ChartFactory.createTimeSeriesChart(
		          user.getCq2name() + "'s XP progress", 
		          null, 
		          null,
		          dataset, 
		          true, 
		          true, 
		          false
		      );
	
	
		  //chart.setBorderVisible(false);
		  chart.setBackgroundPaint(java.awt.Color.BLACK);
		  chart.getTitle().setPaint(new java.awt.Color(160,160,160));
		  chart.getTitle().setFont(new java.awt.Font("Verdana", Font.BOLD, 18));
		  
		  chart.getLegend().setBackgroundPaint(java.awt.Color.black);
		  chart.getLegend().setItemPaint(new java.awt.Color(160,160,160));
		  
		  XYPlot plot = (XYPlot) chart.getPlot();

		  plot.setBackgroundPaint(java.awt.Color.black);
		  plot.setDomainGridlinePaint(new java.awt.Color(160,160,160));
		  plot.setRangeGridlinePaint(new java.awt.Color(100,100,100));
		  plot.setRangeGridlinesVisible(false);
		  plot.getDomainAxis().setTickLabelPaint(new java.awt.Color(160,160,160));
		  plot.getRangeAxis().setTickLabelPaint(new java.awt.Color(160,160,160));
		  plot.getRangeAxis().setTickLabelFont(new Font("Verdana", Font.PLAIN, 12));
		  plot.getRangeAxis().setLabel("Total XP");
		  plot.getRangeAxis().setLabelPaint(java.awt.Color.red);
		  plot.getRangeAxis().setLabelFont(new Font("Verdana", Font.PLAIN, 12));
		  
		  
		  final ValueAxis axis2 = new NumberAxis("Activity");
	      plot.setRangeAxis(1, axis2);
	      axis2.setTickLabelPaint(new java.awt.Color(160,160,160));
	      axis2.setTickLabelFont(new Font("Verdana", Font.PLAIN, 12));
	      axis2.setLabelPaint(java.awt.Color.blue);
	      plot.setDataset(1, dataset2);
	      plot.mapDatasetToRangeAxis(1, 1);

		  
		  XYItemRenderer renderer = plot.getRenderer();
		  renderer.setSeriesPaint(0, java.awt.Color.red);
		  
		  final XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
	      renderer2.setSeriesPaint(0, java.awt.Color.blue);
		  renderer2.setSeriesShapesVisible(0,false);
	      plot.setRenderer(1, renderer2);
	        
		  double plotMax = plot.getRangeAxis().getUpperBound();
			 int level=1;
			 int xp = 0;
			  while(xp < plotMax) {
			    IntervalMarker target = new IntervalMarker(xp, CQ2Functions.getXP(level+1));
		        target.setLabel(""+level);
		        target.setLabelFont(new Font("Verdana", Font.PLAIN, 9));
		        target.setLabelPaint(new java.awt.Color(160,160,160));
		        target.setLabelAnchor(RectangleAnchor.LEFT);
		        target.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
		        if(level%2==0) target.setPaint(java.awt.Color.black);
		        else target.setPaint(new java.awt.Color(15,15,15));
		        target.setOutlinePaint(new java.awt.Color(0,0,0));
		        plot.addRangeMarker(target, Layer.BACKGROUND);
		        level++;
		        xp = CQ2Functions.getXP(level);
			  }
		  
		  return chart;
 	}

	JFreeChart createGlobalChart(List<User> users) {

		Collections.sort(users,new Comparator<User>() {
			public int compare(User u1, User u2) {
				return XPLog.getLastXp(u2.getId())-XPLog.getLastXp(u1.getId());
		    }
		});
		
		  User first = users.get(0);
		  List<XPLog> xpLogs = XPLog.getXPLogs(first.getId());
		  
		  TimeSeries series = new TimeSeries(first.getCq2name() + " " + first.getLevel());
		  for(XPLog log : xpLogs) {
			  Date date = log.getTime();
			  Calendar cal = Calendar.getInstance();
			  cal.setTime(date);
			  series.addOrUpdate(new Second(cal.get(Calendar.SECOND), cal.get(Calendar.MINUTE),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR)), log.getXp());
		  }
		  
		  XYDataset dataset = new TimeSeriesCollection(series);
		  
		  JFreeChart chart = ChartFactory.createTimeSeriesChart(
		          "Global XP progress", 
		          null, 
		          null,
		          dataset, 
		          true, 
		          true, 
		          false
		      );
		  
		  users.remove(0);
		  
		  XYPlot plot = (XYPlot) chart.getPlot();
		  
		  int dataSetIndex=0;

		  
		  for(User user : users) {
			  dataSetIndex++;
			  xpLogs = XPLog.getXPLogs(user.getId());
			  if(xpLogs.size()>1) {
				  series = new TimeSeries(user.getCq2name() + " " + user.getLevel());
				  for(XPLog log : xpLogs) {
					  Date date = log.getTime();
					  Calendar cal = Calendar.getInstance();
					  cal.setTime(date);
					  series.addOrUpdate(new Second(cal.get(Calendar.SECOND), cal.get(Calendar.MINUTE),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR)), log.getXp());
				  }
				  dataset = new TimeSeriesCollection(series);
				  plot.setDataset(dataSetIndex,dataset);
				  plot.setRenderer(dataSetIndex, new StandardXYItemRenderer());
			  }
		  }
		
		  //chart.setBorderVisible(false);
		  chart.setBackgroundPaint(java.awt.Color.BLACK);
		  
		  chart.getTitle().setPaint(new java.awt.Color(160,160,160));
		  chart.getTitle().setFont(new java.awt.Font("Verdana", Font.BOLD, 18));
		  
		  chart.getLegend().setPosition(RectangleEdge.RIGHT);
		  chart.getLegend().setItemFont(new Font("Verdana", Font.PLAIN, 10));
		  chart.getLegend().setBackgroundPaint(java.awt.Color.BLACK);
		  chart.getLegend().setItemPaint(new java.awt.Color(160,160,160));

		  
		  plot.setBackgroundPaint(new java.awt.Color(0,0,0));
		  plot.setDomainGridlinePaint(new java.awt.Color(100,100,100));
		  plot.setRangeGridlinePaint(new java.awt.Color(100,100,100));
		  plot.setRangeGridlinesVisible(false);
		  
		  plot.getDomainAxis().setTickLabelPaint(new java.awt.Color(160,160,160));
		  plot.getRangeAxis().setTickLabelPaint(new java.awt.Color(160,160,160));
		  
		  
		  double plotMax = plot.getRangeAxis().getUpperBound();
		 int level=1;
		 int xp = CQ2Functions.getXP(level);
		  while(xp < plotMax) {
		    IntervalMarker target = new IntervalMarker(xp, CQ2Functions.getXP(level+1));
	        target.setLabel(""+level);
	        target.setLabelFont(new Font("Verdana", Font.PLAIN, 11));
	        target.setLabelPaint(new java.awt.Color(160,160,160));
	        target.setLabelAnchor(RectangleAnchor.LEFT);
	        target.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
	        if(level%2==0) target.setPaint(java.awt.Color.black);
	        else target.setPaint(new java.awt.Color(10,10,10));
	        target.setOutlinePaint(new java.awt.Color(0,0,0));
	        
	        plot.addRangeMarker(target, Layer.BACKGROUND);
	        level++;
	        xp = CQ2Functions.getXP(level);
		  }
		  
		  return chart;
	}%>