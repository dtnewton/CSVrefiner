import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RatingPredictor
{
	private ArrayList<String> headers;
	private ArrayList<String> vals;
	private String line;
	private String[] splitStr, recordData;
	private double ratingPrediction;
		
	public RatingPredictor() 
	{
		this.BuildCSV();
	}
	
	private void BuildCSV()
	{
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("data/test_main_updated.csv"), "UTF-8")))
		{
			
			File file = null;
			try 
			{
				file = new File("data/rating_predictions.csv");
				if(!file.exists())
				{
					file.createNewFile();
				}
			}catch(Exception e) 
			{
				e.printStackTrace();
			}
				
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			line = new String();
			this.headers = new ArrayList<String>();
			this.vals = new ArrayList<String>();
			
			/*copy headers*/
			this.line = reader.readLine();
			if(line.startsWith("\uFEFF"))
			{
				line = line.substring(1);
			}
			
			splitStr = line.trim().split(",");
			
			for(int i = 0; i < splitStr.length; i++)
			{		
			    headers.add(splitStr[i]);		
			}
			
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < headers.size(); i++)
			{
				sb.append(headers.get(i));
				if(i < headers.size() - 1) sb.append(",");		
			}
					
			line = sb.toString();
			writer.write(line);
			writer.newLine();
			
			/*copy data*/
			//*********************************************************************
			while((line = reader.readLine())!=null)
			{
				vals.clear();
			
				/* COLLECT VALS */
				recordData = line.trim().split(",");
				for(int i = 0; i < recordData.length; i++)
				{				
					vals.add(recordData[i]);				
				}
				
				this.ratingPrediction = getPrediction(vals);
				vals.set(0, Double.toString(ratingPrediction));
				
				/* WRITE */
				for(int i = 0; i < vals.size();i++)
				{
					
					if(!(i == (vals.size() - 1)))
					{
						writer.write(vals.get(i));
						writer.write(",");
					}
					else
					{
						writer.write(vals.get(i));
					}	
				}

				writer.newLine();
			}
			//*********************************************************************
			writer.close();
			reader.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	private double getPrediction(ArrayList<String> vals) 
	{
		double sum = 0;
		

		sum =

			     -0.1636 * Double.parseDouble(vals.get(1)) +
			     -0.034  * Double.parseDouble(vals.get(2)) +
			      0.2733 * Double.parseDouble(vals.get(3)) +
			     -0.1183 * Double.parseDouble(vals.get(4)) +
			      0.0566 * Double.parseDouble(vals.get(5)) +
			      0.3868 * Double.parseDouble(vals.get(6)) +
			      0.2473 * Double.parseDouble(vals.get(7)) +
			     -0.2469 * Double.parseDouble(vals.get(8)) +
			      0.1752 * Double.parseDouble(vals.get(10)) +
			      0.1285 * Double.parseDouble(vals.get(11)) +
			     -0.3533 * Double.parseDouble(vals.get(12)) +
			      0.0732 * Double.parseDouble(vals.get(15)) +
			     -0.0267 * Double.parseDouble(vals.get(16)) +
			     -0.0506 * Double.parseDouble(vals.get(17)) +
			     -0.058  * Double.parseDouble(vals.get(19)) +
			      0.1186 * Double.parseDouble(vals.get(20)) +
			      0.1473 * Double.parseDouble(vals.get(21)) +
			      0.0069 * Double.parseDouble(vals.get(22)) +
			      3.2471;

		
		return sum;

	}

}
