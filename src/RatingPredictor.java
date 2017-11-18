import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RatingPredictor
{
	boolean success;
	private double BASE_MODEL_VAL = 3.2471;
	
	
	public RatingPredictor() 
	{
		this.BuildCSV();
	}
	
	public boolean getSuccess()
	{
		return success;
	}
	private void BuildCSV()
	{
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("data/test_set_updated.csv"), "UTF-8")))
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
				this.success = false;
			}
				
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			String line;
			ArrayList<String> headers = new ArrayList<String>();
			ArrayList<String> vals = new ArrayList<String>();
			
			/*copy headers*/
			line = reader.readLine();
			if(line.startsWith("\uFEFF"))
			{
				line = line.substring(1);
			}
			
			String[] splitStr = line.trim().split(",");
			
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
			String[] fullLine;
			double ratingPrediction;
			
			
			//*********************************************************************
			while((line = reader.readLine())!=null)
			{
				vals.clear();
				
				
				/* COLLECT VALS */
				fullLine = line.trim().split(",");
				for(int i = 0; i < fullLine.length; i++)
				{
					
					vals.add(fullLine[i]);
					
				}
				
				ratingPrediction = getPrediction(vals);
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
			this.success = false;
		}
		
	}

	private double getPrediction(ArrayList<String> vals) 
	{
		double sum = 0;

		
		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("data/linreg_model.txt"), "UTF-8")))
		{
			String line;
			ArrayList<String> modifiers = new ArrayList<String>();
			
			line = reader.readLine();
			if(line.startsWith("\uFEFF"))
			{
				line = line.substring(1);
			}
			
			String[] splitStr = line.trim().split(",");
			
			for(int i = 0; i < splitStr.length; i++)
			{
				modifiers.add(splitStr[i]);	
			}
			
			int modIndex = 0;
			
			//TODO - this may be a little too specific, lacks flexibility.
			for(int i = 1; i < vals.size(); i++)
			{
				if(i == 9 || i == 13 || i == 14 || i == 18 || i == 23)
				{
					//do nothing.
				}
				else
				{
					sum += Float.parseFloat(vals.get(i)) * Float.parseFloat(modifiers.get(modIndex));
					modIndex++;
				}
				
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		sum += BASE_MODEL_VAL;
		
		return sum;

	}

}
