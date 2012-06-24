package be.lacerta.cq2.sim;



public class ResCheckExtension extends AbstractSimExtension {

	public void run(String page) {
		if (post) {
			try{
				int[] myRes = this.parseOwnRes(request.getParameter("inputPage"));
				String[] opponentResDescription = this.parseOpponentsResDescription(request.getParameter("inputPage"));
				int[][] oppRes = this.calculateOpponentsResources(myRes,opponentResDescription);
				request.setAttribute("rescheck_resources", oppRes);
			} catch(NullPointerException e1) {		
				e1.printStackTrace();
			} catch(NumberFormatException e2) {
				e2.printStackTrace();
			}
			
			String[] resNames = {"Overall", "Brimstone", "Crystal","Essence","Granite", "Power"};
			request.setAttribute("rescheck_resNames", resNames);
		}
		
		setPath("/resCheck.jsp");
	}
	

	private int[][] calculateOpponentsResources(int[] myRes, String[] oppResDescription) {
		
		int[][] naiveResult = new int[6][2];
		int[][] correctedResult = new int[6][2];
		
		for(int i=0;i<6;i++) {
			naiveResult[i] = this.convertDescriptionToAmount(myRes[i], oppResDescription[i]);
			correctedResult[i] = this.convertDescriptionToAmount(myRes[i], oppResDescription[i]);
		}
		
		//correct the minima (extrapolate)
		for(int i=1;i<5;i++) {
			int sum = 0;
			boolean noInfinity=true;
			for(int j=1;j<5;j++) {
				if(i!=j) {
					sum+=naiveResult[j][1];
					if(naiveResult[j][1]==Integer.MAX_VALUE) {
						noInfinity=false;
						break;
					}
				}
				
			}
			if(noInfinity) correctedResult[i][0] = Math.max(naiveResult[i][0],naiveResult[0][0]-sum);
		}

		
		//correct the maxima (extrapolate)
		if(naiveResult[0][1]!=Integer.MAX_VALUE) {
			for(int i=1;i<5;i++) {
				int sum = 0;
				for(int j=1;j<5;j++) {
						if(i!=j) sum+=naiveResult[j][0];
				}
				correctedResult[i][1] = Math.min(naiveResult[i][1],naiveResult[0][1]-sum);
			}
		}

		
		return correctedResult;
		
	}
	
	private int[] convertDescriptionToAmount(int myRes, String description) {
		int[] result = new int[2];
		if(description.equals("very poor")) {
			result[0] = 0;
			result[1] = (int) Math.rint(0.4*myRes);
			return result;
		} else if(description.equals("poor")) {
			result[0] = (int) Math.rint(0.4*myRes);
			result[1] = (int) Math.rint(0.8*myRes);
			return result;
		} else if(description.equals("equally rich")) {
			result[0] = (int) Math.rint(0.8*myRes);
			result[1] = (int) Math.rint(1.2*myRes);
			return result;
		} else if(description.equals("rich")) {
			result[0] = (int) Math.rint(1.2*myRes);
			result[1] = (int) Math.rint(1.6*myRes);
			return result;
		} else if(description.equals("very rich")) {
			result[0] = (int) Math.rint(1.6*myRes);
			result[1] = (int) Math.rint(2*myRes);
			return result;
		} else if(description.equals("filthy rich")) {
			result[0] = (int) Math.rint(2*myRes);
			result[1] = (int) Integer.MAX_VALUE;
			return result;
		} else if(description.equals("extremely weak")) {
			result[0] = 0;
			result[1] = (int) Math.rint(0.3*myRes);
			return result;
		} else if(description.equals("very weak")) {
			result[0] = (int) Math.rint(0.3*myRes);
			result[1] = (int) Math.rint(0.6*myRes);
			return result;
		} else if(description.equals("weak")) {
			result[0] = (int) Math.rint(0.6*myRes);
			result[1] = (int) Math.rint(0.8*myRes);
			return result;
		} else if(description.equals("equally powerful")) {
			result[0] = (int) Math.rint(0.8*myRes);
			result[1] = (int) Math.rint(1.2*myRes);
			return result;
		} else if(description.equals("powerful")) {
			result[0] = (int) Math.rint(1.2*myRes);
			result[1] = (int) Math.rint(1.4*myRes);
			return result;
		} else if(description.equals("very powerful")) {
			result[0] = (int) Math.rint(1.4*myRes);
			result[1] = (int) Math.rint(1.7*myRes);
			return result;
		} else if(description.equals("extremely powerful")) {
			result[0] = (int) Math.rint(1.7*myRes);
			result[1] = (int) Integer.MAX_VALUE;
			return result;
		}
		return null; //shouldn't happen
		
	}


	private int[] parseOwnRes(String inputPage) {
		String[] pageLines = inputPage.split("\n");
		int[] result = new int[6];
		for(String line : pageLines) {
			String[] explodedLine = line.split(":");
			if(line.indexOf("Brimstone: ")>-1) result[1] = Integer.parseInt(explodedLine[1].trim());
			else if(line.indexOf("Crystal: ")>-1) result[2] = Integer.parseInt(explodedLine[1].trim());
			else if(line.indexOf("Essence: ")>-1) result[3] = Integer.parseInt(explodedLine[1].trim());
			else if(line.indexOf("Granite: ")>-1) {
				result[4] = Integer.parseInt(explodedLine[1].trim());
				result[0] = result[1] + result[2] + result[3] + result[4];
			}
			else if(line.indexOf("Power: ")>-1) {
				result[5] = Integer.parseInt(explodedLine[1].trim());
				break;
			}
		}
		
		return result;
	}

	private String[] parseOpponentsResDescription(String inputPage) {
		String[] pageLines = inputPage.split("\n");
		String[] result = new String[6];
		boolean passedOverall = false;
		
		for(String line : pageLines) {
			if(line.indexOf("compared to you.")>-1) {
				result[0] = line.substring(line.indexOf(" is ")+4, line.indexOf("compared")-1);
				passedOverall=true;
			}
			if(passedOverall) {
				String[] explodedLine = line.split(":");
				if(line.indexOf("Brimstone: ")>-1) result[1] = explodedLine[1].trim();
				else if(line.indexOf("Crystal: ")>-1) result[2] = explodedLine[1].trim();
				else if(line.indexOf("Essence: ")>-1) result[3] = explodedLine[1].trim();
				else if(line.indexOf("Granite: ")>-1) result[4] = explodedLine[1].trim();
				else if(line.indexOf("Power: ")>-1) {
					result[5] = explodedLine[1].trim();
					break;
				}

			}
		}
		return result;
	}
	
	
	public static void main(String args[]) {
		String myRes = "Brimstone: 12786\nCrystal: 145034\nEssence: 1291\nGranite: 12479\nPower: 50881";	
		String otherRes = " Overall, Aramus is rich compared to you.\nBrimstone: filthy rich\nCrystal: poor\nEssence: filthy rich\nGranite: very poor\nPower: extremely weak";
		ResCheckExtension object = new ResCheckExtension();
		int[] ownResources = object.parseOwnRes(myRes);
		String[] oppRes = object.parseOpponentsResDescription(otherRes);
		int[][] otherResources = object.calculateOpponentsResources(ownResources, oppRes);
		for(int res : ownResources) {
			System.out.println(res);
		}
		System.out.println();
		for(String res : oppRes) {
			System.out.println(res);
		}
		System.out.println();
		for(int[] res : otherResources) {
			System.out.println(res[0] + " - " + res[1]);
		}
	}

	
}
